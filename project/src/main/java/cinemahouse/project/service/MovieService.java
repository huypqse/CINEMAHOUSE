package cinemahouse.project.service;

import cinemahouse.project.dto.MovieDTO;
import cinemahouse.project.dto.response.PageResponse;
import cinemahouse.project.entity.Movie;
//import cinemahouse.project.entity.MovieType;
//import cinemahouse.project.entity.ScreeningSession;
//import cinemahouse.project.entity.status.MovieStatus;
//import cinemahouse.project.exception.AppException;
//import cinemahouse.project.exception.ErrorCode;
//import cinemahouse.project.integration.minio.MinioChanel;
//import cinemahouse.project.mapper.MovieTypeMapper;
//import cinemahouse.project.mapper.ScreeningSessionMapper;
import cinemahouse.project.repository.MovieRepository;
import cinemahouse.project.repository.MovieTypeRepository;
import cinemahouse.project.repository.ScreeningSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

//import java.time.LocalDate;
//import java.util.List;
//import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
   private final MovieRepository movieRepository;
   private final MovieTypeRepository movieTypeRepository;
   private final ScreeningSessionRepository screeningSessionRepository;
//   private final MinioChanel minioChanel;

//    @Transactional
//    public MovieDTO createMovie(MovieDTO movieDTO, List<Long> movieTypes, List<Long> screeningSession) {
//        Set<MovieType> movieType = movieTypeRepository.findAllByIdIn(movieTypes).orElseThrow(() -> new AppException(ErrorCode.MOVIE_TYPE_NOT_FOUND));
//        Set<ScreeningSession> screeningSessionSet = screeningSessionRepository.findAllByIdIn(screeningSession).orElseThrow(() -> new AppException((ErrorCode.SCREENING_SESSION_NOT_FOUND)));
//        Movie movie = Movie.builder()
//                .name(movieDTO.getName())
//                .content(movieDTO.getContent())
//                .coverPhoto(minioChanel.upload(movieDTO.getCoverPhoto()))
//                .status(MovieStatus.NOW_SHOWING)
//                .releaseDate(LocalDate.now())
//                .startDate(LocalDate.now())
//                .movieTypes(movieType)
//                .language(movieDTO.getLanguage())
//                .screeningSessions(screeningSessionSet)
//                .actors(movieDTO.getActors())
//                .director(movieDTO.getDirector())
//                .ageLimit(movieDTO.getAgeLimit())
//                .duration(movieDTO.getDuration())
//                .build();
//        movieRepository.save(movie);
//
//
//        return MovieDTO.builder()
//                .name(movie.getName())
//                .content(movie.getContent())
//                .coverPhotoUrl(movie.getCoverPhoto())
//                .status(movie.getStatus())
//                .releaseDate(movie.getReleaseDate())
//                .startDate(movie.getStartDate())
//                .language(movie.getLanguage())
//                .actors(movie.getActors())
//                .director(movie.getDirector())
//                .ageLimit(movie.getAgeLimit())
//                .duration(movie.getDuration())
//                .build();
//    }


    public PageResponse<Movie> search(String language, int page, int size) {
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

}
