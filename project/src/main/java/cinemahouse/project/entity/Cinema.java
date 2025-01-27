package cinemahouse.project.entity;

import cinemahouse.project.entity.status.CinemaStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cinema extends AbstractEntity<Long> {

    @Column(nullable = false, unique = true, name = "cinema_name")
    String name;

    @Column(nullable = false, name = "cinema_address")
    String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "cinema_status")
    CinemaStatus cinemaStatus;

    @OneToMany(mappedBy = "cinema",
//            cascade = {
//            CascadeType.PERSIST, CascadeType.MERGE,
//             CascadeType.REFRESH
//    },
fetch = FetchType.LAZY)
    Set<ScreeningRoom> screeningRooms;
}
