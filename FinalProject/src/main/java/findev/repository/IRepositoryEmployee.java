package findev.repository;

import findev.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRepositoryEmployee extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT id FROM employees WHERE status_id=1", nativeQuery = true)
    public List<Long> getFreeEmployeesIds();
}
