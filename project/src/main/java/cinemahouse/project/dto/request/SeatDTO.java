package cinemahouse.project.dto.request;

import cinemahouse.project.dto.ScreeningRoomDTO;
import cinemahouse.project.dto.SeatTypeDTO;
import cinemahouse.project.entity.status.SeatStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatDTO {
    String row;
    Integer column;
    SeatStatus seatStatus;
    ScreeningRoomDTO screeningRoom;
    SeatTypeDTO seatType;


}
