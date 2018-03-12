package findev.service;

import findev.model.Employee;

import java.util.List;

public interface IServiceEmployee {

    boolean isExists(Long id);

    Employee getById(Long id);

    void save(Employee employee);

    void delete(Long id);

    List<Employee> getAll();
}
