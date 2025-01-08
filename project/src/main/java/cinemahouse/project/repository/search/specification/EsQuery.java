package cinemahouse.project.repository.search.specification;

import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

public interface EsQuery {
    CriteriaQuery build();
}

