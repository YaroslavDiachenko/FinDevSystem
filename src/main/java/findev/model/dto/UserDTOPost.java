package findev.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOPost {
    Long id;
    String username;
    String password;
    Long roleId;
}
