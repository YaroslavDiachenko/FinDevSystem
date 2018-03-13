package findev.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "departments")
@Getter
@Setter
@ToString
public class Department extends BaseEntity {
    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "department")
//    private List<Employee> employeeList;

    public Department() {
    }
}
