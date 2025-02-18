package cinemahouse.project.dto;

import cinemahouse.project.entity.status.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
     String username;
     Gender gender;
     String phoneNumber;
    LocalDate birthDate;
    String email;
}
