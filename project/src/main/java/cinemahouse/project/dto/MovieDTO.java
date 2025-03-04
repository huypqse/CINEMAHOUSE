package cinemahouse.project.dto;

import cinemahouse.project.entity.status.MovieStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieDTO {
    String name;
    String content;
    LocalDate duration;
    String language;
    String director;
    String actors;
    Integer ageLimit;
    MultipartFile coverPhoto;
    String coverPhotoUrl;
    LocalDate releaseDate;
    LocalDate startDate;
    MovieStatus status;
//    Set<MovieTypeDTO> movieTypes;
//    Set<ScreeningSessionDTO> screeningSessions;

}
