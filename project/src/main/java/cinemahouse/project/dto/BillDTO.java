package cinemahouse.project.dto;

import cinemahouse.project.dto.response.InitPaymentResponse;
import cinemahouse.project.entity.status.BillStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillDTO {
    LocalDate billDate;
    Instant billTime;
    Double total;
    BillStatus status;
    UserDTO user;
    String ipAddress;
    
}
