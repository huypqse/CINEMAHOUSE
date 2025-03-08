import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { SeatDTO } from "../src/types/SeatDTO";

const SOCKET_URL = "http://localhost:9090/ws"; // URL backend

let stompClient: Client | null = null;

export const connectWebSocket = (onMessage: (seats: SeatDTO[]) => void) => {
  const socket = new SockJS(SOCKET_URL);
  stompClient = new Client({
    webSocketFactory: () => socket as WebSocket, // Chuyển đổi SockJS thành WebSocket
    reconnectDelay: 5000, // Tự động kết nối lại sau 5 giây nếu mất kết nối
    onConnect: () => {
      console.log("✅ Connected to WebSocket");

      stompClient?.subscribe("/topic/seats", (message) => {
        try {
          const updatedSeats = JSON.parse(message.body).result;
          onMessage(updatedSeats);
        } catch (error) {
          console.error("❌ Error parsing WebSocket message:", error);
        }
      });
    },
    onStompError: (frame) => {
      console.error("❌ STOMP error:", frame);
    },
    onWebSocketError: (error) => {
      console.error("❌ WebSocket error:", error);
    },
  });

  stompClient.activate();
};

export const disconnectWebSocket = () => {
  if (stompClient) {
    stompClient.deactivate();
    console.log("🔌 Disconnected from WebSocket");
  }
};