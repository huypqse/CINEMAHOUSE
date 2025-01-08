package cinemahouse.project.dto;

import cinemahouse.project.entity.status.MovieStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieDTO {
    String name;
    String content;
    Duration duration;
    String language;
    String director;
    String actors;
    Integer ageLimit;
    String coverPhoto;
   LocalDate releaseDate;
    LocalDate startDate;
    MovieStatus status;

}
