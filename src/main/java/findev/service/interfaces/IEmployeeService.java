package findev.service.interfaces;

import findev.model.Employee;

import java.util.List;

public interface IEmployeeService extends ICrudService<Employee> {

    public Employee getByFirstNameAndLastName(String firstName, String lastName);

    public List<Long> getFreeEmployeesIds();

    public void registerEmployee(Employee employee);

    public void updateEmployee(Employee employee) throws IllegalAccessException;

    public void fetchClassProperties(Employee employee);

    public void changeStatusesBusyToFree();

    public void changeStatusesFreeToBusy(Long employeeId);
}
