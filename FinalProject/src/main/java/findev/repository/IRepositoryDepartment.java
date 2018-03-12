package findev.repository;

import findev.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryDepartment extends JpaRepository<Department, Long> {
}
