package findev.service;

import findev.model.Employee;

import java.util.List;

public interface IEmployeeService {

    boolean isExists(Long id);

    Employee getById(Long id);

    void save(Employee employee);

    void delete(Long id);

    List<Employee> getAll();
}
