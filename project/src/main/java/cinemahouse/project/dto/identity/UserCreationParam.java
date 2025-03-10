package cinemahouse.project.dto.identity;

import java.util.List;

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
public class UserCreationParam {
    boolean enabled;
    String email;
    boolean emailVerified;
    String username;
    String firstName;
    String lastName;
    List<Credential> credentials;
}
