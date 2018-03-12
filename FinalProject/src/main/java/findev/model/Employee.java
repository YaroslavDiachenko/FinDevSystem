package findev.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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

    @Column(name = "salary")
    private BigDecimal salary;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "departament_id", referencedColumnName = "id")
//    Department department;

    /*
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "HOUR_RATE")
    private BigDecimal hourRate;

    Position position;

    Department department;

    Status status;
*/
}
