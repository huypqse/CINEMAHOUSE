package cinemahouse.project.repository;

import cinemahouse.project.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatBookingRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByRowAndColumnAndScreeningRoom_NameAndSeatType_Name(String row, Integer column, String screeningRoomName, String seatTypeName);

}
