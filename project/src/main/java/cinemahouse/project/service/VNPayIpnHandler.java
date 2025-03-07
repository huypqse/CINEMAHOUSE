package cinemahouse.project.service;

import cinemahouse.project.constant.VNPayParams;
import cinemahouse.project.constant.VnpIpnResponseConst;
import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.SeatDTO;
import cinemahouse.project.dto.response.IpnResponse;
import cinemahouse.project.entity.*;
import cinemahouse.project.entity.status.BillStatus;
import cinemahouse.project.entity.status.PaymentStatus;
import cinemahouse.project.entity.status.SeatStatus;
import cinemahouse.project.exception.AppException;
import cinemahouse.project.exception.ErrorCode;
import cinemahouse.project.repository.BillRepository;
import cinemahouse.project.repository.SeatRepository;
import cinemahouse.project.repository.TicketRepository;
import cinemahouse.project.service.interfaces.EmailService;
import cinemahouse.project.service.interfaces.IpnHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class VNPayIpnHandler implements IpnHandler {

    private final VNPayService vnPayService;
    private final BillSevice billSevice;


    public IpnResponse process(Map<String, String> params) {
        if (!vnPayService.verifyIpn(params)) {
            return VnpIpnResponseConst.SIGNATURE_FAILED;
        }

        IpnResponse response;
        var txnRef = params.get(VNPayParams.TXN_REF);
        try {
            var bookingId = Long.parseLong(txnRef);

            billSevice.markBooked(bookingId);//danh dau booking thanh toan thanh cong
            response = VnpIpnResponseConst.SUCCESS;
        }
        catch (AppException e) {//trả về response code đúng theo quy định của VNPay
            switch (e.getErrorCode()) {
                case BILL_NOT_FOUND -> response = VnpIpnResponseConst.ORDER_NOT_FOUND;
                default -> response = VnpIpnResponseConst.UNKNOWN_ERROR;
            }
        }
        catch (Exception e) {
            response = VnpIpnResponseConst.UNKNOWN_ERROR;
        }

        log.info("[VNPay Ipn] txnRef: {}, response: {}", txnRef, response);
        return response;
    }
}
