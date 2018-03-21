package findev.service.interfaces;

import findev.model.Employee;
import findev.model.User;

import java.util.List;

public interface IEmployeeService extends ICrudService<Employee> {

    public List<Long> getFreeEmployeesIds();

    public void registerEmployee(Employee employee);

    public User generateUser(Employee employee);

    public void setNestedData(Employee employee);
}
