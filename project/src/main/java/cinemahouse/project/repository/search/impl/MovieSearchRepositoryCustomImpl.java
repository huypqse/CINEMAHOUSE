//package cinemahouse.project.repository.search.impl;
//
//import cinemahouse.project.entity.Movie;
//import cinemahouse.project.repository.MovieRepository;
//import cinemahouse.project.repository.search.MovieSearchRepositoryCustom;
//import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.client.elc.NativeQuery;
//import org.springframework.data.elasticsearch.core.SearchHit;
//import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
//
//import java.util.stream.Stream;
//
//@RequiredArgsConstructor
//public class MovieSearchRepositoryCustomImpl implements MovieSearchRepositoryCustom {
//    private final ElasticsearchTemplate elasticsearchTemplate;
//    private final MovieRepository repository;
//
//    @Override
//    public Stream<Movie> search(String query) {
//        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
//        return elasticsearchTemplate.search(nativeQuery, Movie.class).map(SearchHit::getContent).stream();
//    }
//
//    @Override
//    public void index(Movie entity) {
//        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
//    }
//
//    @Override
//    public void deleteFromIndex(Movie entity) {
//        elasticsearchTemplate.delete(entity);
//    }
//
//    @Override
//    public Page<Movie> search(final CriteriaQuery criteria) {
//        final var search = elasticsearchTemplate.search(criteria, Movie.class);
//        final var content = search.stream()
//                .map(SearchHit::getContent)
//                .toList();
//
//        final var pageable = criteria.getPageable();
//
//        return new PageImpl<>(content, pageable, search.getTotalHits());
//    }
//}
