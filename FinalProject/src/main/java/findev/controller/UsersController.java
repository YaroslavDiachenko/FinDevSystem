package findev.controller;

import findev.errorshandling.customexceptions.OccupiedNameException;
import findev.model.User;
import findev.model.dto.UserDTOGet;
import findev.model.dto.UserDTOPost;
import findev.model.dto.UserPasswordDTOPost;
import findev.service.interfaces.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Api
@RestController
@RequestMapping(value = "/users")

public class UsersController {
    @Autowired private IUserService userService;
    @Autowired private ModelMapper modelMapper;


    @ApiOperation(value = "get all users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<UserDTOGet>> getAll() {
        List<User> users = userService.getAll();
        if (users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<UserDTOGet> uDTOs = new ArrayList<>();
        for (User u : users) {
            uDTOs.add(modelMapper.map(u, UserDTOGet.class));
        }
        return new ResponseEntity<>(uDTOs, HttpStatus.OK);
    }


    @ApiOperation(value = "get user by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTOGet> getById(
            @PathVariable("userId") Long uId) {
        if (uId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User u = userService.getById(uId);
        if (u == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        UserDTOGet uDTOg = modelMapper.map(u, UserDTOGet.class);
        return new ResponseEntity<>(uDTOg, HttpStatus.OK);
    }


    @ApiOperation(value = "create new user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<UserDTOGet> create(
            @RequestBody UserDTOPost uDTOp) {
        if (uDTOp == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (userService.getByUsername(uDTOp.getUsername()) != null)
            throw new OccupiedNameException("User with specified username already exists.");
        User u = modelMapper.map(uDTOp, User.class);
        u.setId(null);
        userService.save(u);
        UserDTOGet uDTOg = modelMapper.map(u, UserDTOGet.class);
        return new ResponseEntity<>(uDTOg, HttpStatus.CREATED);
    }


    @ApiOperation(value = "update user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity<UserDTOGet> update(
            @PathVariable("userId") Long uId,
            @RequestBody UserDTOPost uDTOp) {
        if (uId == null || uDTOp == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean isExists = userService.isExists(uId);
        if (!isExists)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User u = modelMapper.map(uDTOp, User.class);
        u.setId(uId);
        userService.save(u);
        UserDTOGet uDTOg = modelMapper.map(u, UserDTOGet.class);
        return new ResponseEntity<>(uDTOg, HttpStatus.CREATED);
    }


    @ApiOperation(value = "delete user by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(
            @PathVariable("userId") Long uId) {
        if (uId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (userService.getById(uId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userService.delete(uId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


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


    @ApiOperation(value = "reset password")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
    public ResponseEntity resetPassword(
            @RequestParam("username") String username) {
        if (username == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        userService.resetPassword(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}