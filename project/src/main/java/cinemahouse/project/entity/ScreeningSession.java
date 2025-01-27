package cinemahouse.project.entity;

import cinemahouse.project.entity.status.ScreeningSessionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class  ScreeningSession extends AbstractEntity<Long>{

    @Column(name = "start_date", nullable = false)
    LocalDate startDate;

    @Column(name = "start_time", nullable = false)
    Instant startTime;

    @Column(name = "end_time", nullable = false)
    Instant endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "screening_session_status", nullable = false)
    ScreeningSessionStatus screeningSessionStatus;

    @ManyToOne(optional = false
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "movie_id", nullable = false)
    Movie movie;

    @ManyToOne(optional = false
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinColumn(name = "screening_room_id", nullable = false)
    ScreeningRoom screeningRoom;


}
