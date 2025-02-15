package cinemahouse.project.dto;

import cinemahouse.project.entity.status.TicketPriceStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketPriceDTO {
    Double price;
    TicketPriceStatus status;

}
