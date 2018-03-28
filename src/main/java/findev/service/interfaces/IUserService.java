package findev.service.interfaces;

import findev.model.Employee;
import findev.model.User;

public interface IUserService extends ICrudService<User> {

    public User getByUsername(String username);

    public void changePassword(String username, String oldPassword, String newPassword);

    public void resetPassword(String username);

    public User generateUser(Employee e);

    public String randomPassword();
}
