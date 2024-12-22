package cinemahouse.project.entity;

import cinemahouse.project.entity.status.ScreeningRoomStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScreeningRoom extends AbstractEntity<Long>{
    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "name", nullable = false)
    String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "screening_room_status", nullable = false)
    ScreeningRoomStatus screeningRoomStatus;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "roomtype_id", nullable = false)
    RoomType roomType;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "cinema_id", nullable = false)
    Cinema cinema;
}