package cinemahouse.project.dto;

import cinemahouse.project.entity.status.RoomTypeStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomTypeDTO {
    String name;
    RoomTypeStatus status;
}
