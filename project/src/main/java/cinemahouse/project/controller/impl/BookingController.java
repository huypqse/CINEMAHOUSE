package cinemahouse.project.controller.impl;


import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.BillDTO;
import cinemahouse.project.dto.SeatDTO;
import cinemahouse.project.dto.request.BillRequest;
import cinemahouse.project.dto.response.BillResponse;
import cinemahouse.project.service.BillSevice;
import cinemahouse.project.service.SeatBookingService;
import cinemahouse.project.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BillSevice billSevice;

    @PostMapping
    ApiResponse<BillResponse> bookTicket(@Valid @RequestBody BillRequest request,
                             HttpServletRequest httpServletRequest) {
        var ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        request.setIpAddress(ipAddress);

        log.info("Booking Request: {}", request);
        var response = billSevice.processPayment(request);
        return ApiResponse.<BillResponse>builder()
                .result(response)
                .build();

    }
    //FE sẽ gửi trạng thái định kỳ vào api nayf cho đến khi trạng thái booking đã đc booking hay kết thúc với time out nào đó
    @GetMapping("/{billId}/status")
    public ApiResponse<BillDTO> getBookingStatus(@PathVariable Long billId) {
        var response = billSevice.getBookingStatus(billId);
        return ApiResponse.<BillDTO>builder()
                .result(response)
                .build();
    }


}
