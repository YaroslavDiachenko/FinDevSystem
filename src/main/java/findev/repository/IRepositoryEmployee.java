package findev.repository;

import findev.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface IRepositoryEmployee extends JpaRepository<Employee, Long> {

    public Employee findEmployeeByFirstNameAndLastName(String firstName, String lastName);

    @Query(value = "SELECT id FROM employees WHERE status_id = 1", nativeQuery = true)
    public List<BigInteger> getFreeEmployeesIds();

    @Query(value = "UPDATE employees SET status_id = REPLACE(status_id, 2, 1) WHERE status_id = 2", nativeQuery = true)
    public void changeStatusesBusyToFree();

    @Query(value = "UPDATE employees SET status_id = 2 WHERE id = ?1", nativeQuery = true)
    public void changeStatusesFreeToBusy(Long employeeId);
}