package cinemahouse.project.entity;

import cinemahouse.project.entity.status.ScreeningRoomStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
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
    @OneToMany(mappedBy = "screeningRoom",
//            cascade = {
//            CascadeType.PERSIST, CascadeType.MERGE,
//             CascadeType.REFRESH
//    },
            fetch = FetchType.LAZY)
    Set<ScreeningSession> screeningSessions;

    @OneToMany(mappedBy = "screeningRoom",
//            cascade = {
//            CascadeType.PERSIST, CascadeType.MERGE,
//             CascadeType.REFRESH
//    },
            fetch = FetchType.LAZY)
    Set<Seat> seats;
    @ManyToOne(
            fetch = FetchType.EAGER
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "roomtype_id", nullable = false)
    RoomType roomType;

    @ManyToOne(
            fetch = FetchType.EAGER
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}
    )
    @JoinColumn(name = "cinema_id", nullable = false)
    Cinema cinema;
}
