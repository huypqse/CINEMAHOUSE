package cinemahouse.project.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {

    USD("USD"),
    VND("VND"),
    ;

    private final String value;
}
