package findev.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordDTOPost {
    String oldPassword;
    String newPassword;
}
