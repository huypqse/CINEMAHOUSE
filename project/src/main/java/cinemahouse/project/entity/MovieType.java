package cinemahouse.project.entity;

import cinemahouse.project.entity.status.MovieTypeStatus;
import jakarta.persistence.*;
import lombok.*;
        import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieType extends AbstractEntity<Long>{
    @Column(nullable = false, name = "type")
    String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "movie_type_status")
    MovieTypeStatus movieTypeStatus;

    @ManyToMany(mappedBy = "movieTypes", fetch = FetchType.LAZY)
    Set<Movie> movies;
}
