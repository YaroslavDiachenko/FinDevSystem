package findev.service.interfaces;

import findev.model.User;

public interface IUserService extends ICrudService<User> {

    public User getByUsername(String username);

    public void changePassword(String username, String oldPassword, String newPassword);
}
