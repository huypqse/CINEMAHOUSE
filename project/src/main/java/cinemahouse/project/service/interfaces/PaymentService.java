package cinemahouse.project.service.interfaces;

import cinemahouse.project.dto.request.InitPaymentRequest;
import cinemahouse.project.dto.response.InitPaymentResponse;

public interface PaymentService {
    InitPaymentResponse init(InitPaymentRequest request);
}
