package cinemahouse.project.repository.search.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MovieQuery implements EsQuery {

    private final List<Criteria> criteria = new ArrayList<>();

    private Pageable pageable = Pageable.unpaged();

    public static MovieQuery builder() {
        return new MovieQuery();
    }

    public MovieQuery withSearch(String search) {
        if (!ObjectUtils.isEmpty(search)) {
            criteria.add(
                            Criteria.where("language").contains(search)
//                            .or(Criteria.where("content").contains(search))
//                            .or(Criteria.where("language").contains(search))
//                            .or(Criteria.where("kinds.name").contains(search))
            );
        }
        return this;
    }

    public MovieQuery withPageable(Pageable pageable) {
        this.pageable = pageable;
        return this;
    }

    @Override
    public CriteriaQuery build() {
        final var criteria = this.criteria.stream()
                .reduce(Criteria::and)
                .orElse(null);
        return new CriteriaQuery(Objects.requireNonNullElseGet(criteria, Criteria::new))
                .setPageable(pageable);
    }
}

