package cinemahouse.project.repository;

import cinemahouse.project.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByLanguage(String language, Pageable pageable);
    Optional<Movie> findByName(String name);
}
