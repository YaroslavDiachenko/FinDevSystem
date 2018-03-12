package findev.controller;

import findev.model.Employee;
import findev.service.IServiceEmployee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/employees")
@Api(value="employee", description="Employees controller")
public class EmployeeController {
    @Autowired
    private IServiceEmployee serviceEmployee;

    // ==== getById =============================================================
    @ApiOperation(value = "Get object")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        if (employeeId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Employee employee = this.serviceEmployee.getById(employeeId);
        if (employee == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // ==== delete ==============================================================
    @ApiOperation(value = "Delete object")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        if (employeeId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean isExists = this.serviceEmployee.isExists(employeeId);
        if (!isExists) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.serviceEmployee.delete(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ==== update ===============================================================
    @ApiOperation(value = "Update object")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable("employeeId") Long employeeId,
            @RequestBody @Valid Employee employee,
            UriComponentsBuilder builder) {
//        if (employee == null || employeeId == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        boolean isExists = this.serviceEmployee.isExists(employeeId);
//        if (!isExists) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        if (serviceEmployee.getById(employeeId) == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        employee.setId(employeeId);
        this.serviceEmployee.save(employee);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(builder.path("/api/employees/{employeeId}").buildAndExpand(employee.getId()).toUri());
//        return new ResponseEntity<>(employee, headers, HttpStatus.OK);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // ==== create ===============================================================
    @ApiOperation(value = "Create object")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee, UriComponentsBuilder builder) {
        if (employee == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        HttpHeaders headers = new HttpHeaders();
        this.serviceEmployee.save(employee);
        headers.setLocation(builder.path("/api/employees/{employeeId}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<>(employee, headers, HttpStatus.CREATED);
    }

    // ==== getAll ==============================================================
    @ApiOperation(value = "Get all objects", response = Employee.class)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = this.serviceEmployee.getAll();
        if (employees.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

}
