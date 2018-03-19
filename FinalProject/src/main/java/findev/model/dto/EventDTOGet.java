package findev.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class EventDTOGet {
    private String eventTypeName;
    private String date;
    private BigDecimal hours;
    private List<String> employeesLastNames;

    public String getEventTypeName() {
        return eventTypeName;
    }
    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getHours() {
        return hours;
    }
    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public List<String> getEmployeesLastNames() {
        return employeesLastNames;
    }
    public void setEmployeesLastNames(List<String> employeesLastNames) {
        this.employeesLastNames = employeesLastNames;
    }
}
