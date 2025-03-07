package cinemahouse.project.repository;

import cinemahouse.project.entity.ScreeningSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ScreeningSessionRepository extends JpaRepository<ScreeningSession, Long> {
    Optional<Set<ScreeningSession>> findAllByIdIn(List<Long> screeningId);
}
