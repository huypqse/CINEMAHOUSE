package cinemahouse.project.dto;

import cinemahouse.project.entity.status.ScreeningSessionStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScreeningSessionDTO {
     Long id;
     LocalDate startDate;
     Instant startTime;
     Instant endTime;
     ScreeningSessionStatus screeningSessionStatus;
}
