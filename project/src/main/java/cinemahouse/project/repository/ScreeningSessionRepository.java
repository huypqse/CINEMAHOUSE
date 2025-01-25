package cinemahouse.project.repository;

import cinemahouse.project.entity.ScreeningSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningSessionRepository extends JpaRepository<ScreeningSession, Long> {
}
