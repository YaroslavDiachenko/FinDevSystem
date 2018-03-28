package findev.controller.minor;

import findev.errorshandling.customexceptions.OccupiedNameException;
import findev.model.EventType;
import findev.service.interfaces.IEventTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/eventtypes")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
public class EventTypesController {
    @Autowired private IEventTypeService eventTypeService;

    @ApiOperation(value = "get all event types")
    @GetMapping("")
    public ResponseEntity<List<EventType>> getAll() {
        List<EventType> etl = eventTypeService.getAll();
        if (etl.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(etl, HttpStatus.OK);
    }

    @ApiOperation(value = "get event type by id")
    @GetMapping("/{eventtypeId}")
    public ResponseEntity<EventType> getById(
            @PathVariable("eventtypeId") Long etId) {
        if (etId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        EventType et = eventTypeService.getById(etId);
        if (et == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(et, HttpStatus.OK);
    }

    @ApiOperation(value = "create new event type")
    @PostMapping("")
    public ResponseEntity<EventType> create(
            @RequestBody EventType et1) {
        if (et1 == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (eventTypeService.getByName(et1.getName()) != null)
            throw new OccupiedNameException("Event type with specified name already exists.");
        et1.setId(null);
        EventType et2 = eventTypeService.save(et1);
        return new ResponseEntity<>(et2, HttpStatus.CREATED);
    }

    @ApiOperation(value = "update event type")
    @PostMapping("/{eventtypeId}")
    public ResponseEntity<EventType> update(
            @PathVariable("eventtypeId") Long etId,
            @RequestBody EventType et) {
        if (etId == null || et == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean isExists = eventTypeService.isExists(etId);
        if (!isExists)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        et.setId(etId);
        eventTypeService.save(et);
        return new ResponseEntity<>(et, HttpStatus.CREATED);
    }

    @ApiOperation(value = "delete event type by id")
    @DeleteMapping("/{eventtypeId}")
    public ResponseEntity delete(
            @PathVariable("eventtypeId") Long etId) {
        if (etId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (eventTypeService.getById(etId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        eventTypeService.delete(etId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}