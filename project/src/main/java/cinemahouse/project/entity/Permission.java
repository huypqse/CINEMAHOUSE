package cinemahouse.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Permission extends AbstractEntity<Long>{
    @Column(nullable = false, unique = true)
    String name;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    Set<Role> roles = new HashSet<>();
}
