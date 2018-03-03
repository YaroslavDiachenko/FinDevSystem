package findev.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.mapping.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
public class Employee extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    private Set Department;
    private Set Status;

    @Column(name = "salary")
    private BigDecimal salary;
}
