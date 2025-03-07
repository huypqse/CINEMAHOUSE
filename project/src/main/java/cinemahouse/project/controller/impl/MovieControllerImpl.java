package cinemahouse.project.controller.impl;

import cinemahouse.project.controller.MovieController;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/movie")
@Tag(name = "Movie Controller")
@Slf4j
public class MovieControllerImpl implements MovieController {
    MovieService movieService;
//    @PostMapping("/search")
//    @Override
//    public ApiResponse<Page<Movie>> searchMovies(@RequestBody @Valid MovieFilterRequest filterRequest
//                                                        ) {
//        log.info("Searching movies with filter: {}", filterRequest);
//
//        final var result = movieService.execute(new CriteriaObject<MovieFilterRequest>().setObject(filterRequest));
//        log.info("result: {}", result);
//
//
//        return ApiResponse.<Page<Movie>>builder()
//                .code(HttpStatus.OK.value())
//                .result(result)
//                .build();
//    }
    @Operation(summary = "Elastic search", description = "Using elasticsearch ver 1")
    @GetMapping(value = "/search-version", headers = "apiVersion=v1.0")
    @Override
    public ApiResponse<PageResponse<Movie>> search(
            @RequestParam String language,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
//        movieService.reindexMovies();
        PageResponse<Movie> result = movieService.search(language, page, size);
        log.info("result: {}", result);
        return ApiResponse.<PageResponse<Movie>>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .build();
    }
//    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Override
//    public ApiResponse<MovieDTO> addMovie(@Valid MovieDTO movie, @RequestParam List<Long> movieType, @RequestParam List<Long> screeningSessions) {
//
//        MovieDTO result = movieService.createMovie(movie, movieType, screeningSessions);
//        return ApiResponse.<MovieDTO>builder()
//                .code(HttpStatus.OK.value())
//                .result(result)
//                .build();
//    }

}
