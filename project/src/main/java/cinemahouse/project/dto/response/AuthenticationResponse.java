package cinemahouse.project.dto.response;

import cinemahouse.project.dto.RoleDTO;
import cinemahouse.project.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {

    String token;
    List<RoleDTO> roles;
    boolean authenticated;
}
