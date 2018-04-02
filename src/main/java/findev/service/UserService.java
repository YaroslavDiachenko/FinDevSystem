package findev.service;

import findev.errorshandling.customexceptions.MissingUserException;
import findev.model.Employee;
import findev.model.Role;
import findev.model.User;
import findev.repository.IRepositoryUser;
import findev.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService implements IUserService {
    @Autowired private IRepositoryUser repositoryUser;
    @Autowired private EmailService emailService;
    @Autowired private PasswordEncoder passwordEncoder;


    @Override
    public boolean isExists(Long id) {
        return repositoryUser.exists(id);
    }
    @Override
    public User getById(Long id) {
        return repositoryUser.findOne(id);
    }
    @Override
    public User save(User u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return repositoryUser.save(u);
    }
    @Override
    public void delete(Long id) {
        repositoryUser.delete(id);
    }
    @Override
    public List<User> getAll() {
        return repositoryUser.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return repositoryUser.findByUsername(username);
    }


    /**
     * Change password for the user with specified username.
     * @param username - employee's username
     * @param oldPassword - current password
     * @param newPassword - new password
     */
    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = getByUsername(username);
        if (passwordEncoder.encode(oldPassword).equals(user.getPassword())) {
            user.setPassword(newPassword);
            repositoryUser.save(user);
        }
    }

    /**
     * Generate new password ({@link #randomPassword()}) for the user with specified username
     * and save to database. Send new password to user's email.
     * @param username - employee's username
     * @throws MissingUserException - if missing user with specified username
     */
    @Override
    public void resetPassword(String username) throws MissingUserException {
        User u = getByUsername(username);
        if (u == null)
            throw new MissingUserException("Missing user with specified username.");
        String newPassword = randomPassword();
        u.setPassword(newPassword);
        save(u);
        emailService.sentNewPasswordEmail(u.getEmployee(), newPassword);
    }

    /**
     * 1. Generate user with random password ({@link UserService#randomPassword()}), default user role
     * and username as lowercase combination of first and last names of the specified employee.
     * @param e - employee entity
     */
    @Override
    public User generateUser(Employee e) {
        User u = new User();
        u.setUsername(e.getFirstName().concat(e.getLastName()).toLowerCase());
        u.setPassword(randomPassword());
        Role role = new Role();
        role.setId(3L);
        u.setRole(role);
        return u;
    }

    /**
     * Generate random 10 alpha numeric symbols password.
     * @return - generated password
     */
    @Override
    public String randomPassword() {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            if (i%2 == 0) sb.append((char)(r.nextInt('z' - 'a') + 'a'));
            else sb.append(r.nextInt(10) + 1);
        }
        return sb.toString();
    }

}
