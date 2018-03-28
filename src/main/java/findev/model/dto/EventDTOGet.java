package findev.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class EventDTOGet {
    private String eventTypeName;
    private String date;
    private BigDecimal hours;
    private List<String> employeesLastNames;
}
