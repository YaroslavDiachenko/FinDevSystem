package findev.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOGet {
    private Long id;
    private String username;
    private String roleName;
}
