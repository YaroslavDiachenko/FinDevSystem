package findev.controller;


import findev.model.Employee;
import findev.model.Event;
import findev.model.EventType;
import findev.model.dto.EventDTOGet;
import findev.model.dto.EventDTOPost;
import findev.repository.IRepositoryEmployee;
import findev.repository.IRepositoryEvent;
import findev.repository.IRepositoryEventType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/events")
public class EventController {
    @Autowired
    public IRepositoryEvent repositoryEvent;
    @Autowired
    public IRepositoryEventType repositoryEventType;
    @Autowired
    private IRepositoryEmployee repositoryEmployee;
    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<EventDTOGet> get(
            @PathVariable("eventId") Long eventId) {
        if (eventId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Event event = repositoryEvent.findOne(eventId);
        if (event == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        EventDTOGet eventDTOGet = modelMapper.map(event, EventDTOGet.class);
        return new ResponseEntity<>(eventDTOGet, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<EventDTOGet> add(
            @RequestBody EventDTOPost eventDTOPost) {

        if (eventDTOPost == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Event event = modelMapper.map(eventDTOPost, Event.class);
        List<Long> freeEmployeesIds = repositoryEmployee.getFreeEmployeesIds();
        if (!freeEmployeesIds.containsAll(eventDTOPost.getEmployeesIds()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        event.setId(null);
        Event event1 = repositoryEvent.save(event);
        EventType eventType = repositoryEventType.findOne(event1.getEventType().getId());
        event1.setEventType(eventType);
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : event1.getEmployees()) {
            employees.add(repositoryEmployee.findOne(employee.getId()));
            if (employee.getStatus().getId() != 1)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        event1.setEmployees(employees);
        EventDTOGet eventDTOGet = modelMapper.map(event1, EventDTOGet.class);
        return new ResponseEntity<>(eventDTOGet, HttpStatus.CREATED);
    }

}
