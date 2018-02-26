package findev.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Statuses")
@Getter
@Setter
@ToString
public class Status extends BaseEntity {
    @Column(name = "name")
    private String name;
}
