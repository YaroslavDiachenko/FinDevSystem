package findev.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<User> users;
}
