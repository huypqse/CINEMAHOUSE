package cinemahouse.project.entity;

import cinemahouse.project.entity.status.RoomTypeStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomType extends AbstractEntity<Long>{
    @Column(nullable = false, unique = true)
    String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    RoomTypeStatus status;

    @OneToMany(
            mappedBy = "roomType",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY
    )
    Set<ScreeningRoom> screeningRooms = new HashSet<>();

}
