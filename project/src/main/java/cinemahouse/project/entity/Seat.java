package cinemahouse.project.entity;

import cinemahouse.project.entity.status.SeatStatus;
import jakarta.persistence.*;
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
public class Seat extends AbstractEntity<Long>{
    @Column(name = "seat_row", nullable = false)
    String row;

    @Column(name = "seat_column", nullable = false)
    Integer column;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    SeatStatus status;

    @ManyToOne(
            fetch = FetchType.EAGER
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "screening_room_id", nullable = false)
    ScreeningRoom screeningRoom;

    @ManyToOne(
            fetch = FetchType.EAGER
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "seat_type_id", nullable = false)
    SeatType seatType;

    @OneToMany(mappedBy = "seat")
    Set<Ticket> tickets;
}
