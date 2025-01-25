package cinemahouse.project.repository;

import cinemahouse.project.entity.TicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPriceRepository extends JpaRepository<TicketPrice, Long> {
}
