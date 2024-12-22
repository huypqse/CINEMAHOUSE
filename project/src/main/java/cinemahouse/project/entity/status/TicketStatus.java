package cinemahouse.project.entity.status;

public enum TicketStatus {
    AVAILABLE,  // Ticket is available for booking
    RESERVED,   // Ticket is reserved but not yet paid
    SOLD,       // Ticket has been sold and paid for
    CANCELLED,  // Ticket reservation has been cancelled
    EXPIRED,    // Ticket reservation has expired
    PENDING,    // Ticket payment is pending
    CHECKED_IN; // User has checked into the screening
}
