package findev.controller.minor;

import findev.errorshandling.customexceptions.OccupiedNameException;
import findev.model.Department;
import findev.service.interfaces.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/departments")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
public class DepartmentsController {
    @Autowired private IDepartmentService departmentService;

    @ApiOperation(value = "get all departments")
    @GetMapping("")
    public ResponseEntity<List<Department>> getAll() {
        List<Department> dl = departmentService.getAll();
        if (dl.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dl, HttpStatus.OK);
    }

    @ApiOperation(value = "get department by id")
    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getById(
            @PathVariable("departmentId") Long dId) {
        if (dId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Department d = departmentService.getById(dId);
        if (d == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @ApiOperation(value = "create new department")
    @PostMapping("")
    public ResponseEntity<Department> create(
            @RequestBody Department d1) {
        if (d1 == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (departmentService.getByName(d1.getName()) != null)
            throw new OccupiedNameException("Department with specified name already exists.");
        d1.setId(null);
        Department d2 = departmentService.save(d1);
        return new ResponseEntity<>(d2, HttpStatus.CREATED);
    }

    @ApiOperation(value = "update department")
    @PostMapping("/{departmentId}")
    public ResponseEntity<Department> update(
            @PathVariable("departmentId") Long dId,
            @RequestBody Department d) {
        if (dId == null || d == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean isExists = departmentService.isExists(dId);
        if (!isExists)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        d.setId(dId);
        departmentService.save(d);
        return new ResponseEntity<>(d, HttpStatus.CREATED);
    }

    @ApiOperation(value = "delete department by id")
    @DeleteMapping("/{departmentId}")
    public ResponseEntity delete(
            @PathVariable("departmentId") Long dId) {
        if (dId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (departmentService.getById(dId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        departmentService.delete(dId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}