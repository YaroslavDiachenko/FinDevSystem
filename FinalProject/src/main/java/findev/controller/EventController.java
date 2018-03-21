package findev.controller;


import findev.model.Employee;
import findev.model.Event;
import findev.model.EventType;
import findev.model.dto.EventDTOGet;
import findev.model.dto.EventDTOPost;
import findev.repository.IRepositoryEmployee;
import findev.repository.IRepositoryEventType;
import findev.service.interfaces.IEventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/events")
public class EventController {
    @Autowired
    public IEventService eventService;
    @Autowired
    public IRepositoryEventType repositoryEventType;
    @Autowired
    private IRepositoryEmployee repositoryEmployee;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<EventDTOGet> get(
            @PathVariable("eventId") Long eventId) {
        if (eventId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Event event = eventService.getById(eventId);
        if (event == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        EventDTOGet eventDTOGet = modelMapper.map(event, EventDTOGet.class);
        return new ResponseEntity<>(eventDTOGet, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
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
        Event event1 = eventService.save(event);
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
