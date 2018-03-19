package findev.model.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EventDTOPost {
    private Long id;
    private Long eventTypeId;
    private Date date;
    private BigDecimal hours;
    private List<Long> employeesIds;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }
    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getHours() {
        return hours;
    }
    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public List<Long> getEmployeesIds() {
        return employeesIds;
    }
    public void setEmployeesIds(List<Long> employeesIds) {
        this.employeesIds = employeesIds;
    }
}