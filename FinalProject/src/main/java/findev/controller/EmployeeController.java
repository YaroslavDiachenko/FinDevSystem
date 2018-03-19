package findev.controller;

import findev.model.Department;
import findev.model.Employee;
import findev.model.Position;
import findev.model.Status;
import findev.model.dto.EmployeeDTOGet;
import findev.model.dto.EmployeeDTOPost;
import findev.repository.IRepositoryDepartment;
import findev.repository.IRepositoryEmployee;
import findev.repository.IRepositoryPosition;
import findev.repository.IRepositoryStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/employees")
//@Api(value = "employee", description = "Employees controller")
public class EmployeeController {
//    @Autowired
//    private IEmployeeService serviceEmployee;

    @Autowired
    private IRepositoryEmployee repositoryEmployee;
    @Autowired
    private IRepositoryPosition repositoryPosition;
    @Autowired
    private IRepositoryDepartment repositoryDepartment;
    @Autowired
    private IRepositoryStatus repositoryStatus;
    @Autowired
    private ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<EmployeeDTOGet>> getAll() {
        List<Employee> employeeList = repositoryEmployee.findAll();
        if (employeeList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<EmployeeDTOGet> employeeDTOGetList = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeDTOGetList.add(modelMapper.map(employee, EmployeeDTOGet.class));
        }
        return new ResponseEntity<>(employeeDTOGetList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<EmployeeDTOGet> get(
            @PathVariable("employeeId") Long employeeId) {
        if (employeeId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Employee employee = repositoryEmployee.findOne(employeeId);
        if (employee == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        EmployeeDTOGet employeeDTOGet = modelMapper.map(employee, EmployeeDTOGet.class);
        return new ResponseEntity<>(employeeDTOGet, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<EmployeeDTOGet> add(
            @RequestBody EmployeeDTOPost employeeDTOPost) {
        if (employeeDTOPost == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Employee employee = modelMapper.map(employeeDTOPost, Employee.class);
        employee.setId(null);
        Employee employee1 = repositoryEmployee.save(employee);
        Position position = repositoryPosition.findOne(employee1.getPosition().getId());
        Department department = repositoryDepartment.findOne(employee1.getDepartment().getId());
        Status status = repositoryStatus.findOne(employee1.getStatus().getId());
        employee1.setPosition(position);
        employee1.setDepartment(department);
        employee1.setStatus(status);
        EmployeeDTOGet employeeDTOGet = modelMapper.map(employee1, EmployeeDTOGet.class);
        return new ResponseEntity<>(employeeDTOGet, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.POST)
    public ResponseEntity<EmployeeDTOGet> update(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody EmployeeDTOPost employeeDTOPost) {
        if (employeeId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean isExists = repositoryEmployee.exists(employeeId);
        if (!isExists)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Employee employee = modelMapper.map(employeeDTOPost, Employee.class);
        employee.setId(employeeId);
        Employee employee1 = repositoryEmployee.save(employee);
        Position position = repositoryPosition.findOne(employee1.getPosition().getId());
        Department department = repositoryDepartment.findOne(employee1.getDepartment().getId());
        Status status = repositoryStatus.findOne(employee1.getStatus().getId());
        employee1.setPosition(position);
        employee1.setDepartment(department);
        employee1.setStatus(status);
        EmployeeDTOGet employeeDTOGet = modelMapper.map(employee1, EmployeeDTOGet.class);
        return new ResponseEntity<>(employeeDTOGet, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(
            @PathVariable("employeeId") Long employeeId) {
        if (employeeId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (repositoryEmployee.findOne(employeeId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        repositoryEmployee.delete(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
