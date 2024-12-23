package cinemahouse.project.entity;

import cinemahouse.project.entity.status.TicketPriceStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketPrice extends AbstractEntity<Long>{
    @Column(nullable = false)
    Double price;

    @Enumerated(EnumType.STRING)
    TicketPriceStatus status;

    @OneToMany(mappedBy = "ticketPrice", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    Set<Ticket> tickets;
}
