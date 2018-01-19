package com.oms.event.service;


import com.oms.event.model.EventDTO;
import com.oms.event.model.EventResponse;

public interface EventService {
    public EventDTO addEvent(EventDTO eventDTO);
    public EventResponse searchEventsByOrderId(String id);
    public void deleteEventsByOrderId(String id);
}
