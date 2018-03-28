package findev.controller.minor;

import findev.errorshandling.customexceptions.OccupiedNameException;
import findev.model.Status;
import findev.service.interfaces.IStatusService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/statuses")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
public class StatusesController {
    @Autowired private IStatusService statusService;

    @ApiOperation(value = "get all statuses")
    @GetMapping("")
    public ResponseEntity<List<Status>> getAll() {
        List<Status> sl = statusService.getAll();
        if (sl.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(sl, HttpStatus.OK);
    }

    @ApiOperation(value = "get status by id")
    @GetMapping("/{statusId}")
    public ResponseEntity<Status> getById(
            @PathVariable("statusId") Long sId) {
        if (sId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Status s = statusService.getById(sId);
        if (s == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @ApiOperation(value = "create new status")
    @PostMapping("")
    public ResponseEntity<Status> create(
            @RequestBody Status s1) {
        if (s1 == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (statusService.getByName(s1.getName()) != null)
            throw new OccupiedNameException("Status with specified name already exists.");
        s1.setId(null);
        Status s2 = statusService.save(s1);
        return new ResponseEntity<>(s2, HttpStatus.CREATED);
    }

    @ApiOperation(value = "update status")
    @PostMapping("/{statusId}")
    public ResponseEntity<Status> update(
            @PathVariable("statusId") Long sId,
            @RequestBody Status s) {
        if (sId == null || s == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean isExists = statusService.isExists(sId);
        if (!isExists)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        s.setId(sId);
        statusService.save(s);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

    @ApiOperation(value = "delete status by id")
    @DeleteMapping("/{statusId}")
    public ResponseEntity delete(
            @PathVariable("statusId") Long sId) {
        if (sId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (statusService.getById(sId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        statusService.delete(sId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}