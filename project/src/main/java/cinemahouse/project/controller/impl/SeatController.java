package cinemahouse.project.controller.impl;

import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.SeatDTO;
import cinemahouse.project.service.SeatBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
@Slf4j
public class SeatController {
    private final SeatBookingService seatBookingService;

//    @MessageMapping("/get-seats")
//    @SendTo("/topic/seats")
    @GetMapping
    public ApiResponse<List<SeatDTO>> getSeats() {
        return ApiResponse.<List<SeatDTO>>builder()
                .result(seatBookingService.getAllSeats())
                .build();
    }
}
