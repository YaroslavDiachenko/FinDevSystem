package findev.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
    @OneToOne(mappedBy = "user")
    private Employee employee;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
