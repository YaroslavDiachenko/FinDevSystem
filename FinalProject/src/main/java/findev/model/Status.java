package findev.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "statuses")
@Getter
@Setter
@ToString
public class Status extends BaseEntity {
    @Column(name = "NAME")
    private String name;

    @Column(name = "COEFT")
    private String coefficient;

    @Column(name = "PAID_DAYS")
    private Integer paidDays;
}
