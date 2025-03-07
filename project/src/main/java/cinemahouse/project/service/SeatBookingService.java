package cinemahouse.project.service;

import cinemahouse.project.dto.SeatDTO;
import cinemahouse.project.entity.Seat;
import cinemahouse.project.exception.AppException;
import cinemahouse.project.exception.ErrorCode;
import cinemahouse.project.mapper.SeatMapper;
import cinemahouse.project.repository.SeatRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class SeatBookingService  {
    RedisTemplate<String, Object> redisTemplate;
    private static final String SEATS_KEY = "seats:";
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    /**
     * 📌 Read-Aside Cache: Lấy trạng thái nhiều ghế từ cache hoặc database (Tối ưu)
     */
    public List<Long> readAsideSeats(List<Long> seatIds) {
        List<Long> foundSeatIds = new ArrayList<>();
        List<Long> missingSeatIds = new ArrayList<>();
        List<String> redisKeys = seatIds.stream()
                .map(id -> SEATS_KEY + id) // Key: "seat:<id>"
                .collect(Collectors.toList());

        // 📌 1. MultiGet: Lấy tất cả ID ghế từ cache
        List<Object> cachedIds = redisTemplate.opsForValue().multiGet(redisKeys);

        for (int i = 0; i < seatIds.size(); i++) {
            Object cachedId = cachedIds.get(i);
            if (cachedId != null) {
                foundSeatIds.add(Long.parseLong(cachedId.toString())); // Lấy ID từ Redis
            } else {
                missingSeatIds.add(seatIds.get(i)); // Nếu cache không có, lưu lại ID để query DB
            }
        }

        // 📌 2. Nếu còn ghế chưa có trong cache, truy vấn DB
        if (!missingSeatIds.isEmpty()) {
            List<Long> dbSeatIds = seatRepository.findAllById(missingSeatIds)
                    .stream()
                    .map(Seat::getId)
                    .collect(Collectors.toList());

            if (dbSeatIds.isEmpty()) {
                throw new AppException(ErrorCode.SEAT_NOT_FOUND);
            }

            // 📌 3. MultiSet: Lưu tất cả ID ghế vào cache
            Map<String, Object> cacheData = dbSeatIds.stream()
                    .collect(Collectors.toMap(id -> SEATS_KEY + id, id -> id));
            redisTemplate.opsForValue().multiSet(cacheData);

            // 📌 4. Đặt thời gian hết hạn cho từng ghế
            for (Long id : dbSeatIds) {
                redisTemplate.expire(SEATS_KEY + id, 6, TimeUnit.MINUTES);
            }

            foundSeatIds.addAll(dbSeatIds);
        }

        log.info("Returning seat IDs from cache & DB: {}", foundSeatIds);
        return foundSeatIds;
    }

    public List<SeatDTO> getAllSeats() {
        return seatMapper.toDto(seatRepository.findAll());
    }

}
