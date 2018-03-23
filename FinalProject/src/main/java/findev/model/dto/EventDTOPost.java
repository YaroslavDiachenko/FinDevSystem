package findev.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EventDTOPost {
    private Long id;
    private Long eventTypeId;
    private Date date;
    private BigDecimal hours;
    private List<Long> employeesIds;
}