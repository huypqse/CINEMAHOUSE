package cinemahouse.project.entity;

import cinemahouse.project.entity.status.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(unique = true, nullable = false)
    private String vnpayTransactionId;  // Mã giao dịch VNPAY

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;  // Trạng thái giao dịch

    private String responseCode;  // Mã phản hồi từ VNPAY

    private LocalDateTime paymentDate;  // Ngày giao dịch

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
