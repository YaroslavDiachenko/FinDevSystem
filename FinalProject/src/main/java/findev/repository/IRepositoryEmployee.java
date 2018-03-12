package findev.repository;

import findev.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryEmployee extends JpaRepository<Employee, Long> {
}
