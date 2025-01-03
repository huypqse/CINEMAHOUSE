package cinemahouse.project.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Map;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEvent {
    String channel;// kênh
    String recipient;
    String templateCode;
    String subject;
    Map<String, Object> param;
}
