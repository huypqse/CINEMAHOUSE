package cinemahouse.project.controller;

import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.MovieDTO;
import cinemahouse.project.dto.request.MovieFilterRequest;
import cinemahouse.project.dto.response.PageResponse;
import cinemahouse.project.entity.Movie;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface MovieController {

//    public ApiResponse<Page<Movie>> searchMovies(@RequestBody @Valid MovieFilterRequest filterRequest);

     ApiResponse<PageResponse<Movie>> search(
            @RequestParam String language,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size);

//     ApiResponse<MovieDTO> addMovie(@Valid MovieDTO movie, @RequestParam List<Long> movieType, @RequestParam List<Long> screeningSessions);


}
