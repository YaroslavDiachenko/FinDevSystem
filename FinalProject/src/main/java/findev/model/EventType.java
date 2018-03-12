package findev.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "eventtypes")
@Getter
@Setter
@ToString
public class EventType extends BaseEntity {
    @Column(name = "NAME")
    private String name;

    @Column(name = "COEFT")
    private String coefficient;
}
