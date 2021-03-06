package be.unamur.hermes.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import be.unamur.hermes.business.service.EventService;
import be.unamur.hermes.dataaccess.entity.Event;

/**
 * Please note this is a read-only controller : event creation is of the entire
 * responsibility of the server.
 * 
 * @author Thomas_Elskens
 *
 */
@RestController
@RequestMapping({ "/events" })
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
	this.eventService = eventService;
    }

    // READ

    @GetMapping
    ResponseEntity<List<Event>> getEvents(@RequestParam("requestId") long requestId,
	    @RequestParam("eventType") Optional<String> eventType) {
	if (eventType.isPresent())
	    return ResponseEntity.ok(eventService.findByReq(requestId, eventType.get()));
	return ResponseEntity.ok(eventService.findByReq(requestId));
    }
}
