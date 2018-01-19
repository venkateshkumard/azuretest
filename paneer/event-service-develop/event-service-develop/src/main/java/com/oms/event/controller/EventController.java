package com.oms.event.controller;

import com.oms.event.model.EventDTO;
import com.oms.event.model.EventResponse;
import com.oms.event.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/addEvent")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDTO addEvent(@RequestBody EventDTO eventDTO) {
        return eventService.addEvent(eventDTO);
    }

    @GetMapping("/searchEventsByOrderId/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public EventResponse searchEventsByOrderId(@PathVariable String id) {
        return eventService.searchEventsByOrderId(id);
    }

    @DeleteMapping("/deleteEventsByOrderId/{id}")
    @ResponseStatus(HttpStatus.GONE)
    public void deleteEventsByOrderId(@PathVariable String id) {
        eventService.deleteEventsByOrderId(id);
    }
}
