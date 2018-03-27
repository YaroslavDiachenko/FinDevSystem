package findev.controller;

import findev.model.Employee;
import findev.model.User;
import findev.model.dto.EmployeeDTOGet;
import findev.model.dto.EmployeeDTOPost;
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
    @Autowired private ModelMapper modelMapper;

    @ApiOperation(value = "get all employees")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<EmployeeDTOGet>> getAll() {
        List<Employee> empls = employeeService.getAll();
        if (empls.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<EmployeeDTOGet> eDTOs = new ArrayList<>();
        for (Employee e : empls) {
            eDTOs.add(modelMapper.map(e, EmployeeDTOGet.class));
        }
        return new ResponseEntity<>(eDTOs, HttpStatus.OK);
    }

    @ApiOperation(value = "get employee by id")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<EmployeeDTOGet> getById(
            @PathVariable("employeeId") Long eId) {
        if (eId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Employee e = employeeService.getById(eId);
        if (e == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        EmployeeDTOGet eDTOg = modelMapper.map(e, EmployeeDTOGet.class);
        return new ResponseEntity<>(eDTOg, HttpStatus.OK);
    }

    @ApiOperation(value = "get employee related to current user")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/currentuser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<EmployeeDTOGet> getByCurrentUser(Principal p) {
        String currentUsername = p.getName();
        User u = userService.getByUsername(currentUsername);
        if (u.getEmployee() == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        EmployeeDTOGet eDTOg = modelMapper.map(u.getEmployee(), EmployeeDTOGet.class);
        return new ResponseEntity<>(eDTOg, HttpStatus.OK);
    }

    @ApiOperation(value = "create new employee")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<EmployeeDTOGet> create(
            @RequestBody EmployeeDTOPost eDTOp) {
        if (eDTOp == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Employee e = modelMapper.map(eDTOp, Employee.class);
        e.setId(null);
        employeeService.registerEmployee(e);
        EmployeeDTOGet eDTOg = modelMapper.map(e, EmployeeDTOGet.class);
        return new ResponseEntity<>(eDTOg, HttpStatus.CREATED);
    }

    @ApiOperation(value = "update employee")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.POST)
    public ResponseEntity<EmployeeDTOGet> update(
            @PathVariable("employeeId") Long eId,
            @RequestBody EmployeeDTOPost eDTOp) throws IllegalAccessException {
        if (eId == null || eDTOp == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean isExists = employeeService.isExists(eId);
        if (!isExists)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Employee e = modelMapper.map(eDTOp, Employee.class);
        e.setId(eId);
        employeeService.updateEmployee(e);
        EmployeeDTOGet eDTOg = modelMapper.map(e, EmployeeDTOGet.class);
        return new ResponseEntity<>(eDTOg, HttpStatus.CREATED);
    }

    @ApiOperation(value = "delete employee by id")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(
            @PathVariable("employeeId") Long eId) {
        if (eId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (employeeService.getById(eId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        employeeService.delete(eId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}