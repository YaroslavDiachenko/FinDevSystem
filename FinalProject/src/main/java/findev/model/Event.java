package findev.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
public class Event extends BaseEntity {
}
