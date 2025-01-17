package cinemahouse.project.repository;

import cinemahouse.project.entity.ScreeningRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRoomRepository extends JpaRepository<ScreeningRoom, Long> {
}
