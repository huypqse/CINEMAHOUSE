package cinemahouse.project.dto;

import lombok.Data;

@Data
public class CriteriaObject<Param> {
    private Param object;
    public CriteriaObject<Param> setObject(final Param object) {
        this.object = object;
        return this;
    }
}
