package cinemahouse.project.entity;

import cinemahouse.project.entity.status.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "bill_id", nullable = false, unique = true)
    private Bill bill;

    @Column(nullable = false)
    private BigDecimal amount;  // Số tiền thanh toán

    @Column(nullable = false)
    private String currency = "VND";  // Loại tiền

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;  // Trạng thái thanh toán

    private String vnpayTransactionId;  // Mã giao dịch VNPAY

    private String responseCode;  // Mã phản hồi từ VNPAY

    private LocalDateTime paymentDate;  // Ngày thanh toán

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();


    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}