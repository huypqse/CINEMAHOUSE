package cinemahouse.project.repository.search;

import cinemahouse.project.entity.Movie;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieSearchRepository extends ElasticsearchRepository<Movie, Long>, MovieSearchRepositoryCustom{
    @Query("{\"match\": {\"language\": \"?0\"}}")
    Page<Movie> search(String query, Pageable pageable);
}
