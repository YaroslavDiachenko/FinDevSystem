package findev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event extends BaseIdEntity {
    @ManyToOne
    @JoinColumn(name = "eventtype_id", referencedColumnName = "id")
    private EventType eventType;
    @Column(name = "date")
    private Date date;
    @Column(name = "hours")
    private BigDecimal hours;
    @ManyToMany
    @JoinTable(name = "events_employees",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;
}
