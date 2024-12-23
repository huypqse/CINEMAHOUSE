package cinemahouse.project.entity;

import cinemahouse.project.entity.status.CommentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends AbstractEntity<Long> {
    @Column(nullable = false, name = "content")
    String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    CommentStatus status;

    @OneToMany(mappedBy = "comment", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH
    }, fetch = FetchType.LAZY)
    Set<User> users;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "movie_id")
    Movie movie;

}
