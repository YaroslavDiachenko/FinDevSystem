package findev.service.interfaces;

import findev.model.Department;

public interface IDepartmentService extends ICrudService<Department> {
    public Department getByName(String name);

}
