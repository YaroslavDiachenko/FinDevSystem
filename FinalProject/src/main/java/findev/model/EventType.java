package findev.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "eventtypes")
public class EventType extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "coeft")
    private String coefficient;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCoefficient() {
        return coefficient;
    }
    public void setCoefficient(String coefficient) {
        this.coefficient = coefficient;
    }
}
