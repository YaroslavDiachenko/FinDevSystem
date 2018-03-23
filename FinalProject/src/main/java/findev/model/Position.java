package findev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "positions")
public class Position extends BaseIdAndNameEntity {
}
