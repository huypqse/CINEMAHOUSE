package cinemahouse.project.service;

import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.BillDTO;
import cinemahouse.project.dto.SeatDTO;
import cinemahouse.project.dto.request.BillRequest;
import cinemahouse.project.dto.request.InitPaymentRequest;
import cinemahouse.project.dto.response.BillResponse;
import cinemahouse.project.entity.*;
import cinemahouse.project.entity.status.BillStatus;
import cinemahouse.project.entity.status.PaymentStatus;
import cinemahouse.project.entity.status.SeatStatus;
import cinemahouse.project.entity.status.TicketStatus;
import cinemahouse.project.exception.AppException;
import cinemahouse.project.exception.ErrorCode;
import cinemahouse.project.mapper.BillMapper;
import cinemahouse.project.repository.*;
import cinemahouse.project.service.interfaces.EmailService;
import cinemahouse.project.service.interfaces.PaymentService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
 public class BillSevice {
    @NonFinal
    static final String SEAT_TYPE_VIP = "VIP";
    @NonFinal
    static final String SEAT_TYPE_REGULAR = "Regular";
    SeatBookingService seatBookingService;
    PaymentService paymentService;
    TicketRepository ticketRepository;
    SeatRepository seatRepository;
    TicketPriceRepository ticketPriceRepository;
    BillRepository billRepository;
    UserRepository userRepository;
    BillMapper billMapper;
    PaymentRepository paymentRepository;
    TicketSevice ticketSevice;
    SimpMessagingTemplate messagingTemplate;
     RedisTemplate<String, Object> redisTemplate;
     EmailService emailService;
    private static final String SEATS_KEY = "seats:";
    @Transactional
    public BillResponse processPayment(final BillRequest request) {
        String requestId = UUID.randomUUID().toString();

        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<Long> seatIDs = seatBookingService.readAsideSeats(request.getSeatIDs());
        if(seatIDs.isEmpty()) {
            throw new AppException(ErrorCode.SEAT_NOT_FOUND);
        }
        List<Seat> seats = seatRepository.findAllById(seatIDs);
        List<Long> alreadyBooked = seats.stream()
                .filter(seat -> seat.getStatus() == SeatStatus.BOOKED)
                .map(Seat::getId)
                .toList();

        if (!alreadyBooked.isEmpty()) {
            throw new AppException(ErrorCode.SEAT_BOOKED);
        }
        //WebSocket
        seats.forEach(seat -> {seat.setStatus(SeatStatus.RESERVED);});
        seatRepository.saveAll(seats);
        // Gửi chỉ danh sách ID ghế và trạng thái cập nhật qua WebSocket
        List<SeatDTO> seatAll = seatBookingService.getAllSeats();
//        List<SeatDTO> seatUpdates = seats.stream()
//                .map(seat -> new SeatDTO(seat.getId(), seat.getRow(), seat.getColumn(), seat.getStatus()))
//                .toList();

        messagingTemplate.convertAndSend("/topic/seats", ApiResponse.<List<SeatDTO>>builder()
                .result(seatAll)
                .build());
        // 🔹 Bảng giá vé
        Map<String, Double> seatPriceMap = Map.of(
                SEAT_TYPE_VIP, 200.00,
                SEAT_TYPE_REGULAR, 100.00
        );

        // 🔹 Tính tổng tiền
        double totalAmount = seats.stream()
                .mapToDouble(seat -> seatPriceMap.getOrDefault(seat.getSeatType().getName(), 0.0))
                .sum();

        // 🔹 Tạo hóa đơn và lưu vào DB
        Bill bill = Bill.builder()
                .billDate(LocalDate.now())
                .billTime(Instant.now())
                .status(BillStatus.PENDING)
                .total(totalAmount)
                .user(user)
                .build();
        billRepository.save(bill);


        Set<Ticket> tickets = seats.stream()
                .map(seat -> {
                    // 🔹 Lấy ScreeningSession
                    ScreeningSession screeningSession = seat.getScreeningRoom().getScreeningSessions()
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new AppException(ErrorCode.SESSION_NOT_EXISTED));

                    // 🔹 Lấy TicketPrice
                    TicketPrice ticketPrice;
                    if (SEAT_TYPE_VIP.equals(seat.getSeatType().getName())) {
                        ticketPrice = ticketPriceRepository.findByPrice(200.00)
                                .orElseThrow(() -> new AppException(ErrorCode.PRICE_NOT_EXISTED));
                    } else {
                        ticketPrice = ticketPriceRepository.findByPrice(100.00)
                                .orElseThrow(() -> new AppException(ErrorCode.PRICE_NOT_EXISTED));
                    }

                    return new Ticket(
                            TicketStatus.PENDING,
                            screeningSession,
                            ticketPrice,
                            bill, // 🔹 Thêm Bill
                            seat
                    );
                })
                .collect(Collectors.toSet());

        ticketRepository.saveAll(tickets); // 🔹 Lưu nhiều vé một lúc để tối ưu hiệu suất

        // 🔹 Cập nhật trạng thái hóa đơn và lưu lại
        bill.setStatus(BillStatus.PENDING);
        billRepository.save(bill);

        BigDecimal bigDecimalValue = new BigDecimal(String.valueOf(totalAmount));
        System.out.println("total"+ bill.getTotal().longValue());
        var initPayment = Payment.builder()
                .bill(bill)
                .amount(bigDecimalValue)
                .currency("VND")
                .status(PaymentStatus.PENDING)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        paymentRepository.save(initPayment);
        var initPaymentRequest = InitPaymentRequest.builder()
                .userId(bill.getUser().getId())
                .amount(bill.getTotal().longValue())
                .txnRef(String.valueOf(bill.getId()))
                .requestId(requestId)
                .ipAddress(UUID.randomUUID().toString())
                .build();
        var initPaymentResponse = paymentService.init(initPaymentRequest);

        var billDto = billMapper.toDto(bill);
        log.info("[request_id={}] User user_id={} created booking_id={} successfully", requestId, bill.getUser().getId(), bill.getId());
        return BillResponse.builder()
                .bill(billDto)
                .payment(initPaymentResponse)
                .build();
    }

    @Transactional
    public BillDTO markBooked(Long billId) throws IOException {
        final var billOpt = billRepository.findById(billId);
        if (billOpt.isEmpty()) {
            throw new AppException(ErrorCode.BILL_NOT_FOUND);
        }

        final var bill = billOpt.get();
        Payment payment = bill.getPayment();
        List<Long> ticketIds = bill.getTickets().stream().map(Ticket::getId).collect(Collectors.toList());
        final var tickets = ticketSevice.getRange(
                ticketIds
        );

        bill.setStatus(BillStatus.PAID);
        tickets.forEach(a -> a.setStatus(TicketStatus.SOLD));
        payment.setStatus(PaymentStatus.SUCCESS);
        ticketSevice.saveAll(tickets);
        billRepository.save(bill);
        paymentRepository.save(payment);

        List<Seat> seats = new ArrayList<>();
        if (bill.getStatus() == BillStatus.PAID && payment.getStatus() == PaymentStatus.SUCCESS) {
            Set<Ticket> ticketsSold = ticketRepository.findByBill_Id(bill.getId());
            List<String> seatNumbers = new ArrayList<>();
            List<String> ticketPrices = new ArrayList<>();
            for (Ticket t : ticketsSold) {
                seatNumbers.add(t.getSeat().getRow());
                ticketPrices.add(String.valueOf(t.getTicketPrice().getPrice()));
                seats.add(t.getSeat());// Chuyển giá vé sang String
            }
            Long[] seatIds = new Long[seats.size()];
            for (int i = 0; i < seats.size(); i++) {
                Seat seat = seats.get(i);
                seat.setStatus(SeatStatus.BOOKED);
                seatIds[i] = seat.getId(); // Gán ID vào mảng đúng cách
            }

            // Ghi vào database
            seatRepository.saveAll(seats);
//                List<SeatDTO> seatUpdates = seats.stream()
//                        .map(seat -> new SeatDTO(seat.getId(), seat.getRow(), seat.getColumn(), seat.getStatus()))
//                        .toList();
            List<SeatDTO> seatAll = seatBookingService.getAllSeats();
            messagingTemplate.convertAndSend("/topic/seats", ApiResponse.<List<SeatDTO>>builder()
                    .result(seatAll)
                    .build());
            // Xóa cache để đảm bảo dữ liệu mới nhất
            String cacheKey = SEATS_KEY + seatIds.toString();
            redisTemplate.delete(cacheKey);
            emailService.sendPaymentSuccess(
                    bill.getUser().getEmail(),
                    tickets.iterator().next().getScreeningSession().getScreeningRoom().getCinema().getName(),
                    bill.getUser().getUsername(),
                    bill.getUser().getPhoneNumber(),
                    tickets.iterator().next().getScreeningSession().getScreeningRoom().getCinema().getAddress(),
                    tickets.iterator().next().getScreeningSession().getMovie().getName(),
                    tickets.iterator().next().getScreeningSession().getStartDate().toString(),
                    tickets.iterator().next().getScreeningSession().getStartTime().toString(),
                    seatNumbers,
                    ticketPrices,
                    String.valueOf(bill.getTotal()),
                    "https://cinemahouse.com/ticket/" + bill.getId()
            );
        } else {
            log.info("Can not send payment success");
            seats.forEach(seat -> {seat.setStatus(SeatStatus.AVAILABLE);});
            seatRepository.saveAll(seats);
//                List<SeatDTO> seatUpdates = seats.stream()
//                        .map(seat -> new SeatDTO(seat.getId(), seat.getRow(), seat.getColumn(), seat.getStatus()))
//                        .toList();
            List<SeatDTO> seatAll = seatBookingService.getAllSeats();
            messagingTemplate.convertAndSend("/topic/seats", ApiResponse.<List<SeatDTO>>builder()
                    .result(seatAll)
                    .build());
        }
        return billMapper.toDto(bill);
    }

    public BillDTO getBookingStatus(Long billId) {
        final var billOpt = billRepository.findById(billId);
        if (billOpt.isEmpty()) {
            throw new AppException(ErrorCode.BILL_NOT_FOUND);
        }

        return billMapper.toDto(billOpt.get());
    }
}

