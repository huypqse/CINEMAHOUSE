package cinemahouse.project.controller;

import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.MovieDTO;
import cinemahouse.project.dto.request.MovieFilterRequest;
import cinemahouse.project.dto.response.PageResponse;
import cinemahouse.project.entity.Movie;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;


public interface MovieController {

    public ApiResponse<Page<Movie>> searchMovies(@RequestBody @Valid MovieFilterRequest filterRequest);

    public ApiResponse<PageResponse<Movie>> search(
            @RequestParam String language,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size);

    public ApiResponse<Movie> addMovie(@Valid MovieDTO movie);


}
