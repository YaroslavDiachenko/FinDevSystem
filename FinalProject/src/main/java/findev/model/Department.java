package findev.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "departments")
public class Department extends BaseIdAndNameEntity {
}