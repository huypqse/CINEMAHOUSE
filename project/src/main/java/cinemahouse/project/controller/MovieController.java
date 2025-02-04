package cinemahouse.project.controller;

import cinemahouse.project.dto.ApiResponse;
import cinemahouse.project.dto.CriteriaObject;
import cinemahouse.project.dto.MovieDTO;
import cinemahouse.project.dto.request.MovieFilterRequest;
import cinemahouse.project.dto.response.PageResponse;
import cinemahouse.project.entity.Movie;
import cinemahouse.project.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/movie")
@Tag(name = "Movie Controller")
@Slf4j
public class MovieController {
    MovieService movieService;
    @PostMapping("/search")
    public ApiResponse<Page<Movie>> searchMovies(@RequestBody @Valid MovieFilterRequest filterRequest
                                                        ) {
        log.info("Searching movies with filter: {}", filterRequest);

        final var result = movieService.execute(new CriteriaObject<MovieFilterRequest>().setObject(filterRequest));
        log.info("result: {}", result);

        return ApiResponse.<Page<Movie>>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }
    @Operation(summary = "Elastic search", description = "Using elasticsearch ver 1")
    @GetMapping("/search-version")
    public ApiResponse<PageResponse<Movie>> search(
            @RequestParam String language,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        movieService.reindexMovies();
        PageResponse<Movie> result = movieService.search(language, page, size);
        log.info("result: {}", result);
        return ApiResponse.<PageResponse<Movie>>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }
    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Movie> addMovie(@Valid MovieDTO movie) {
        Movie result = movieService.createMovie(movie);
        return ApiResponse.<Movie>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }

}
