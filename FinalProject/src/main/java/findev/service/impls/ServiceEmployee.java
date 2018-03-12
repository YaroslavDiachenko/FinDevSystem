package findev.service.impls;

import findev.model.Employee;
import findev.repository.IRepositoryEmployee;
import findev.service.IServiceEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEmployee implements IServiceEmployee {
    @Autowired
    private IRepositoryEmployee repositoryEmployee;

    @Override
    public boolean isExists(Long id) {
        return repositoryEmployee.exists(id);
    }

    @Override
    public Employee getById(Long id) {
        return repositoryEmployee.findOne(id);
    }

    @Override
    public void save(Employee employee) {
        repositoryEmployee.save(employee);
    }

    @Override
    public void delete(Long id) {
        repositoryEmployee.delete(id);
    }

    @Override
    public List<Employee> getAll() {
        return repositoryEmployee.findAll();
    }
}
