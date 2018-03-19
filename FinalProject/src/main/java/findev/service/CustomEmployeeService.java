package findev.service;

import findev.model.Employee;
import findev.repository.IRepositoryEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomEmployeeService implements IEmployeeService {
    @Autowired
    private IRepositoryEmployee repositoryEmployee;
    @Autowired
    public void setRepositoryEmployee(IRepositoryEmployee repositoryEmployee) {
        this.repositoryEmployee = repositoryEmployee;
    }

    @Transactional
    @Override
    public boolean isExists(Long id) {
        return repositoryEmployee.exists(id);
    }

    @Transactional
    @Override
    public Employee getById(Long id) {
        return repositoryEmployee.findOne(id);
    }

    @Transactional
    @Override
    public void save(Employee employee) {
        repositoryEmployee.save(employee);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repositoryEmployee.delete(id);
    }

    @Transactional
    @Override
    public List<Employee> getAll() {
        return repositoryEmployee.findAll();
    }
}