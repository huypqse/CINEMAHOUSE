package cinemahouse.project.dto.request;

import lombok.Data;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

@Data
public abstract class FilterRequest {

    public abstract CriteriaQuery query();
}
