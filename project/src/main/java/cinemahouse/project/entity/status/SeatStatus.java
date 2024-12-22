package cinemahouse.project.entity.status;

public enum SeatStatus {
    AVAILABLE,   // Seat is available for booking
    RESERVED,    // Seat has been reserved but not yet confirmed
    BOOKED,      // Seat is confirmed as booked
    OCCUPIED,    // Seat is occupied (e.g., the show is currently playing)
    UNAVAILABLE;  // Seat is unavailable for booking (e.g., under maintenance or blocked)
}
