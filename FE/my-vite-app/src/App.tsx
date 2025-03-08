// src/App.tsx
import React from 'react';
import SeatBooking from './service/SeatBooking'; // Đảm bảo đường dẫn đúng đến component SeatBooking

function App() {
  return (
    <>
      <h1>Ứng Dụng Đặt Ghế Rạp Chiếu Phim</h1>
      <SeatBooking />
    </>
  );
}

export default App;