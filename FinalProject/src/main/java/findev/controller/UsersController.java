package findev.controller;

        import findev.model.dto.UserPasswordDTOPost;
import findev.service.interfaces.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

        import java.security.Principal;

@Api
@RestController
@RequestMapping(value = "/users")

public class UsersController {
    @Autowired private IUserService userService;

    @ApiOperation(value = "change password")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public ResponseEntity changePasswordCurrentUser(
            @RequestBody UserPasswordDTOPost userPasswordDTOPost,
            Principal principal) {
        String currentUsername = principal.getName();
        userService.changePassword(currentUsername, userPasswordDTOPost.getOldPassword(), userPasswordDTOPost.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
