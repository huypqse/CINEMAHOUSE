package cinemahouse.project.dto.request;

import cinemahouse.project.repository.search.specification.MovieQuery;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieFilterRequest extends FilterRequest {
    String search;

    @Override
    public CriteriaQuery query() {
        return MovieQuery.builder()
                .withSearch(search)
//                .withPageable(PageResponse.builder().currentPage(page).pageSize(size).build().pageable())
                .build();
    }
}
