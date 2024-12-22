package cinemahouse.project.entity;

import cinemahouse.project.entity.status.BillStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bill extends AbstractEntity<Long>{
    @Column(name = "bill_date", nullable = false)
    LocalDate billDate;

    @Column(name = "bill_time", nullable = false)
    Instant billTime;

    @Column(name = "total_amount", nullable = false)
    Double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    BillStatus status;

    @ManyToOne( cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "bill", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    Set<Ticket> tickets;


}
