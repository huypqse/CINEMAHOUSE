package cinemahouse.project.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordCreationForFirstRequest {
    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;
}
