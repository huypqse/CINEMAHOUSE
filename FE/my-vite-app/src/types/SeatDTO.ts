export interface SeatDTO {
    id: number;
    row: string;
    column: number;
    status: "AVAILABLE" | "RESERVED" | "BOOKED";
  }
  