package cinemahouse.project.service;

import cinemahouse.project.dto.CriteriaObject;
import cinemahouse.project.dto.MovieDTO;
import cinemahouse.project.dto.request.MovieFilterRequest;
import cinemahouse.project.dto.response.PageResponse;
import cinemahouse.project.entity.Movie;
import cinemahouse.project.integration.minio.MinioChanel;
import cinemahouse.project.repository.MovieRepository;
import cinemahouse.project.repository.search.MovieSearchRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MovieService {
    MovieSearchRepository movieSearchRepository;
    MovieRepository movieRepository;
    MinioChanel minioChanel;
    public Page<Movie> execute(final CriteriaObject<MovieFilterRequest> param) {
        final var request = param.getObject();
        return movieSearchRepository.search(request.query());

    }
    public Movie createMovie(MovieDTO movieDTO) {
        Movie movie = Movie.builder()
                .name(movieDTO.getName())
                .content(movieDTO.getContent())
                .coverPhoto(minioChanel.upload(movieDTO.getCoverPhoto()))
                .build();
        return movieRepository.save(movie);
    }


    public PageResponse<Movie> search(String language, int page, int size) {
        if (page < 1 || size <= 0) {
            throw new IllegalArgumentException("Page must be >= 1 and size must be > 0");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        var result = movieSearchRepository.search(language, pageable);

        return PageResponse.<Movie>builder()
                .currentPage(page)
                .pageSize(pageable.getPageSize())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .orders(result.getContent().stream().toList())
                .build();
    }
    public PageResponse<Movie> search2(String language, int page, int size) {
        if (page < 1 || size <= 0) {
            throw new IllegalArgumentException("Page must be >= 1 and size must be > 0");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        var result = movieRepository.findByLanguage(language, pageable);

        return PageResponse.<Movie>builder()
                .currentPage(page)
                .pageSize(pageable.getPageSize())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .orders(result.getContent().stream().toList())
                .build();
    }
    public void reindexMovies() {
        Iterable<Movie> movies = movieRepository.findAll();
     movieSearchRepository.saveAll(movies);
    }
}
