import React, { useEffect, useState } from "react";
import axios from "axios";
import { SeatDTO } from "../types/SeatDTO";
import { connectWebSocket, disconnectWebSocket } from "../../websocket/websocket";

const SeatBooking: React.FC = () => {
  const [seats, setSeats] = useState<SeatDTO[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Load danh sách ghế ban đầu
  useEffect(() => {
    const fetchSeats = async () => {
      try {
        const response = await axios.get("http://localhost:9090/api/v1/seats");
        setSeats(response.data.result);
      } catch (err) {
        setError("Failed to load seats");
      } finally {
        setLoading(false);
      }
    };

    fetchSeats();

    // Kết nối WebSocket để nhận cập nhật ghế
    connectWebSocket((updatedSeats) => {
      setSeats(updatedSeats);
    });

    return () => {
      disconnectWebSocket(); // Ngắt kết nối khi component bị unmount
    };
  }, []);

  if (loading) {
    return <div>Loading seats...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div>
      <h2>Danh Sách Ghế</h2>
      <div style={{ display: "grid", gridTemplateColumns: "repeat(5, 1fr)", gap: "10px" }}>
        {seats.map((seat) => (
          <button
            key={seat.id}
            style={{
              padding: "10px",
              backgroundColor: seat.status === "AVAILABLE" ? "green" : seat.status === "RESERVED" ? "orange" : "red",
              color: "white",
              border: "none",
              cursor: seat.status === "AVAILABLE" ? "pointer" : "not-allowed",
            }}
            disabled={seat.status !== "AVAILABLE"}
          >
            {seat.row}
          </button>
        ))}
      </div>
    </div>
  );
};

export default SeatBooking;