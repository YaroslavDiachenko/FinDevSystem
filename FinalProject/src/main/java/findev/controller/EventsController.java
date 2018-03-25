package findev.controller;


import findev.model.Event;
import findev.model.dto.EventDTOGet;
import findev.model.dto.EventDTOGetCurrentUser;
import findev.model.dto.EventDTOPost;
import findev.service.interfaces.IEmployeeService;
import findev.service.interfaces.IEventService;
import findev.service.interfaces.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/events")
public class EventsController {
    @Autowired private IEventService eventService;
    @Autowired private IEmployeeService employeeService;
    @Autowired private IUserService userService;
    @Autowired private ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<EventDTOGet> getById(
            @PathVariable("eventId") Long eventId) {
        if (eventId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Event event = eventService.getById(eventId);
        if (event == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        EventDTOGet eventDTOGet = modelMapper.map(event, EventDTOGet.class);
        return new ResponseEntity<>(eventDTOGet, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/currentuser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<EventDTOGetCurrentUser>> getAllPerCurrentUser(
            @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
            @RequestParam("dateTo")   @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
            Principal principal) {
        String currentUsername = principal.getName();
        List<Event> eventList = eventService.getEventsPerEmployeePerPeriod(currentUsername, dateFrom, dateTo);
        if (eventList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<EventDTOGetCurrentUser> eventDTOGetCurrentUserList = new ArrayList<>();
        for (Event event : eventList) {
            eventDTOGetCurrentUserList.add(modelMapper.map(event, EventDTOGetCurrentUser.class));
        }
        return new ResponseEntity<>(eventDTOGetCurrentUserList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<EventDTOGet> createNew(
            @RequestBody EventDTOPost eventDTOPost) {
        if (eventDTOPost == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Event event = modelMapper.map(eventDTOPost, Event.class);
        event.setId(null);
        eventService.createEvent(event, eventDTOPost.getEmployeesIds());
        EventDTOGet eventDTOGet = modelMapper.map(event, EventDTOGet.class);
        return new ResponseEntity<>(eventDTOGet, HttpStatus.CREATED);
    }
}
