package cinemahouse.project.dto;

import cinemahouse.project.entity.status.MovieTypeStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieTypeDTO {
    Long id;
    String type;
    MovieTypeStatus status;
}
