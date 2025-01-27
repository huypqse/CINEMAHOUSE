package cinemahouse.project.entity;

import cinemahouse.project.entity.status.MovieStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "movies")
public class Movie extends AbstractEntity<Long>{
    @Column(nullable = false, name = "movie_name")
    String name;

    @Column(nullable = false, name = "content")
    String content;

    @Column(name = "duration")
    LocalDate duration;

    @Column(nullable = false, name = "language")
    String language;

    @Column(nullable = false, name = "director")
    String director;

    @Column(nullable = false, name = "actors")
    String actors;

    @Column(nullable = false, name = "age_limit")
    Integer ageLimit;

    @Column(nullable = false, name = "cover_photo",  columnDefinition = "text")
    String coverPhoto;

    @Column(nullable = false, name = "release_date")
    LocalDate releaseDate;

    @Column(nullable = false, name = "start_date")
    LocalDate startDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    MovieStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_typeofmovies",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_type_id")
    )
    Set<MovieType> movieTypes;
    @OneToMany(mappedBy = "movie",
//            cascade = {
//            CascadeType.PERSIST, CascadeType.MERGE,
//             CascadeType.REFRESH
//    },
            fetch = FetchType.LAZY)
    Set<ScreeningSession> screeningSessions;
}
