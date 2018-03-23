package findev.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeDTOPost {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal hourRate;
    private Long positionId;
    private Long departmentId;
    private Long statusId;
}
