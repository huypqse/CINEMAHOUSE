package cinemahouse.project.dto;

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
    Long id;
    String row;
    Integer column;
    SeatStatus status;

}
