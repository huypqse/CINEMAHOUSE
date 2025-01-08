package cinemahouse.project.dto;

import cinemahouse.project.entity.status.CinemaStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaDTO {
    String name;
    String address;
    CinemaStatus cinemaStatus;
    Set<ScreeningRoomDTO> screeningRooms;

}
