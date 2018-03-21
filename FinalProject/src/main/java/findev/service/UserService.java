package findev.service;

import findev.model.User;
import findev.repository.IRepositoryUser;
import findev.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private IRepositoryUser repositoryUser;
    @Override
    public boolean isExists(Long id) {
        return false;
    }

    @Override
    public User getById(Long id) {
        return repositoryUser.findOne(id);
    }

    @Override
    public User getByUsername(String username) {
        return repositoryUser.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return repositoryUser.save(user);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
