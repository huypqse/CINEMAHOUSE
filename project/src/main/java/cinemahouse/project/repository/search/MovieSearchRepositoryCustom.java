//package cinemahouse.project.repository.search;
//
//import cinemahouse.project.entity.Movie;
//import org.springframework.data.domain.Page;
//import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
//import org.springframework.scheduling.annotation.Async;
//
//import java.util.stream.Stream;
//
//public interface MovieSearchRepositoryCustom {
//    Stream<Movie> search(String query);
//
//    @Async
//    void index(Movie entity);
//
//    @Async
//    void deleteFromIndex(Movie entity);
//
//    Page<Movie> search(CriteriaQuery criteria);
//
//}
