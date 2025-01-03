package cinemahouse.project.dto.response;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import cinemahouse.project.entity.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String email;
    String firstName;
    String lastName;
    LocalDate dob;
    Boolean noPassword;
    Set<Role> roles = new HashSet<>();
}
