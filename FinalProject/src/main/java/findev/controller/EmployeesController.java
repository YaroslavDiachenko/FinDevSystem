package findev.controller;

import findev.model.*;
import findev.model.dto.EmployeeDTOGet;
import findev.model.dto.EmployeeDTOPost;
import findev.repository.IRepositoryDepartment;
import findev.repository.IRepositoryPosition;
import findev.repository.IRepositoryStatus;
import findev.service.interfaces.IEmployeeService;
import findev.service.interfaces.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Api
@RestController
@RequestMapping(value = "/employees")
public class EmployeesController {

    @Autowired private IEmployeeService employeeService;
    @Autowired private IUserService userService;
    @Autowired private IRepositoryPosition repositoryPosition;
    @Autowired private IRepositoryDepartment repositoryDepartment;
    @Autowired private IRepositoryStatus repositoryStatus;
    @Autowired private ModelMapper modelMapper;

    @ApiOperation(value = "get all employees")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<EmployeeDTOGet>> getAll() {
        List<Employee> employeeList = employeeService.getAll();
        if (employeeList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<EmployeeDTOGet> employeeDTOGetList = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeDTOGetList.add(modelMapper.map(employee, EmployeeDTOGet.class));
        }
        return new ResponseEntity<>(employeeDTOGetList, HttpStatus.OK);
    }

    @ApiOperation(value = "get object by id")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<EmployeeDTOGet> get(
            @PathVariable("employeeId") Long employeeId) {
        if (employeeId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Employee employee = employeeService.getById(employeeId);
        if (employee == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        EmployeeDTOGet employeeDTOGet = modelMapper.map(employee, EmployeeDTOGet.class);
        return new ResponseEntity<>(employeeDTOGet, HttpStatus.OK);
    }

    @ApiOperation(value = "get object related to current user")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/currentuser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<EmployeeDTOGet> getByCurrentUser(Principal principal) {
        String currentUsername = principal.getName();
        User currentUser = userService.getByUsername(currentUsername);
        if (currentUser.getEmployee() == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        EmployeeDTOGet employeeDTOGet = modelMapper.map(currentUser.getEmployee(), EmployeeDTOGet.class);
        return new ResponseEntity<>(employeeDTOGet, HttpStatus.OK);
    }

    @ApiOperation(value = "save new object")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<EmployeeDTOGet> createNew(
            @RequestBody EmployeeDTOPost employeeDTOPost) {
        if (employeeDTOPost == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Employee employee = modelMapper.map(employeeDTOPost, Employee.class);
        employee.setId(null);
        employeeService.registerEmployee(employee);

        EmployeeDTOGet employeeDTOGet = modelMapper.map(employee, EmployeeDTOGet.class);
        return new ResponseEntity<>(employeeDTOGet, HttpStatus.CREATED);
    }

    @ApiOperation(value = "update existing object")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.POST)
    public ResponseEntity<EmployeeDTOGet> update(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody EmployeeDTOPost employeeDTOPost) {
        if (employeeId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean isExists = employeeService.isExists(employeeId);
        if (!isExists)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Employee employee = modelMapper.map(employeeDTOPost, Employee.class);
        employee.setId(employeeId);
        Employee employee1 = employeeService.save(employee);
        Position position = repositoryPosition.findOne(employee1.getPosition().getId());
        Department department = repositoryDepartment.findOne(employee1.getDepartment().getId());
        Status status = repositoryStatus.findOne(employee1.getStatus().getId());
        employee1.setPosition(position);
        employee1.setDepartment(department);
        employee1.setStatus(status);
        EmployeeDTOGet employeeDTOGet = modelMapper.map(employee1, EmployeeDTOGet.class);
        return new ResponseEntity<>(employeeDTOGet, HttpStatus.CREATED);
    }

    @ApiOperation(value = "delete existing object")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(
            @PathVariable("employeeId") Long employeeId) {
        if (employeeId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (employeeService.getById(employeeId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        employeeService.delete(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}