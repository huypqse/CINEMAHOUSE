package cinemahouse.project.dto;

import cinemahouse.project.entity.status.PaymentStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDTO {
    BillDTO bill;
    BigDecimal amount;
    String currency;
    PaymentStatus status;
    String vnpayTransactionId;
    String responseCode;
    LocalDateTime paymentDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
