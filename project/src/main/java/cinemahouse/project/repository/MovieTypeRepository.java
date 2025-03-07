package cinemahouse.project.repository;

import cinemahouse.project.entity.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MovieTypeRepository extends JpaRepository<MovieType, Long> {
    Optional<Set<MovieType>> findAllByIdIn(List<Long> id);
}
