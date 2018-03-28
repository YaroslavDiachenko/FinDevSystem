package findev.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeDTOGet {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal hourRate;
    private String positionName;
    private String departmentName;
    private String statusName;
    private String userUsername;
}
