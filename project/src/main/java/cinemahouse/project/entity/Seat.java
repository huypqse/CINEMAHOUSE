package cinemahouse.project.entity;

import cinemahouse.project.entity.status.SeatStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seat extends AbstractEntity<Long>{
    @Column(name = "row", nullable = false)
    String row;

    @Column(name = "column", nullable = false)
    Integer column;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    SeatStatus status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
    @JoinColumn(name = "screening_room_id", nullable = false)
    ScreeningRoom screeningRoom;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
    @JoinColumn(name = "seat_type_id", nullable = false)
    SeatType seatType;
}
