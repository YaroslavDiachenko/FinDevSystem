package findev.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Events")
@Getter
@Setter
@ToString
public class Event extends BaseEntity {
    @Column(name = "name")
    private String name;
}
