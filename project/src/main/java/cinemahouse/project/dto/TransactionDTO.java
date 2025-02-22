package cinemahouse.project.dto;

import cinemahouse.project.entity.status.TransactionStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDTO {
    PaymentDTO payment;
    String vnpayTransactionId;
    TransactionStatus status;
    String responseCode;
    LocalDateTime paymentDate;
    LocalDateTime createdAt;

}
