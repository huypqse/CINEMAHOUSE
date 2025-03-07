package cinemahouse.project.repository;

import cinemahouse.project.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByIdIn(List<Long> ids);
    Set<Ticket> findByBill_Id(Long id);
}
