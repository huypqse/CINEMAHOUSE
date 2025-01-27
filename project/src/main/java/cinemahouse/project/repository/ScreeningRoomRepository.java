package cinemahouse.project.repository;

import cinemahouse.project.entity.ScreeningRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScreeningRoomRepository extends JpaRepository<ScreeningRoom, Long> {
    Optional<ScreeningRoom> findByName(String name);
}
