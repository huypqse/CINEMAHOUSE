package cinemahouse.project.service;

import cinemahouse.project.entity.Ticket;
import cinemahouse.project.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TicketSevice {
    TicketRepository ticketRepository;
    

    @Transactional
    public void saveAll(List<Ticket> tickets) {
        ticketRepository.saveAll(tickets);
    }

    public List<Ticket> getRange(List<Long> ticketIds) {
        return ticketRepository.findAllByIdIn(ticketIds);
    }
}
