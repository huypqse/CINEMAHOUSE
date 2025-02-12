package cinemahouse.project.dto;

import cinemahouse.project.entity.ScreeningSession;
import cinemahouse.project.entity.status.TicketStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketDTO {
    TicketStatus status;
    ScreeningSessionDTO screeningSession;
    TicketPriceDTO ticketPriceDTO;

}
