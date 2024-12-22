package cinemahouse.project.entity.status;

public enum BillStatus {
    PENDING,        // The bill has been generated but payment is pending
    PAID,
    CANCELLED,
    FAILED;
}
