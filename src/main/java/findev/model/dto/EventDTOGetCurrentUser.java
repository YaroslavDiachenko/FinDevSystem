package findev.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EventDTOGetCurrentUser {
    private String eventTypeName;
    private String date;
    private BigDecimal hours;
}
