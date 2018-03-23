package findev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class BaseIdAndNameEntity extends BaseIdEntity {
    @Column(name = "name")
    private String name;
}
