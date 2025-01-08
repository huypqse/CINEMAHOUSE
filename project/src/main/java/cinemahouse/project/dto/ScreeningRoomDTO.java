package cinemahouse.project.dto;

import cinemahouse.project.entity.status.ScreeningRoomStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScreeningRoomDTO {
    String name;
    ScreeningRoomStatus screeningRoomStatus;
    RoomTypeDTO roomTypeDTO;
    CinemaDTO cinemaDTO;

}
