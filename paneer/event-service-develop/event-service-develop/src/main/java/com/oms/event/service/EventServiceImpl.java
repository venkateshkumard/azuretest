package com.oms.event.service;

import com.oms.event.entity.EventEntity;
import com.oms.event.exception.EventNotFoundException;
import com.oms.event.model.EventDTO;
import com.oms.event.model.EventResponse;
import com.oms.event.repository.EventRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventDTO addEvent(EventDTO eventDTO) {
        Date currentSysDate = new Date();
        eventDTO.setCreatedDate(currentSysDate);
        eventDTO.setLastModifiedDate(currentSysDate);
        EventEntity eventEntityCreated = eventRepository.insert(convertToEventEntity(eventDTO));

        return convertToEventDTO(eventEntityCreated);
    }

    private EventEntity convertToEventEntity(EventDTO eventDTO) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventName(eventDTO.getEventName()).setOrderId(eventDTO.getOrderId()).setStatus(eventDTO.getStatus())
                .setCreatedDate(eventDTO.getCreatedDate()).setLastModifiedDate(eventDTO.getLastModifiedDate());
        return eventEntity;
    }

    private EventDTO convertToEventDTO(EventEntity eventEntity) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(eventEntity.getId()).setEventName(eventEntity.getEventName()).setOrderId(eventEntity.getOrderId()).setStatus(eventEntity.getStatus())
                .setCreatedDate(eventEntity.getCreatedDate()).setLastModifiedDate(eventEntity.getLastModifiedDate());
        return eventDTO;
    }

    public EventResponse searchEventsByOrderId(String id) {
        EventResponse eventResponse = new EventResponse();
        List<EventDTO> eventDTOS = new ArrayList<EventDTO>();
        List<EventEntity> eventEntityCreated = eventRepository.findByOrderId(id);
        eventEntityCreated.forEach(eventEntity -> {
            eventDTOS.add(convertToEventDTO(eventEntity));
        });
        eventResponse.setEventDTOS(eventDTOS);
        return eventResponse;
    }

    public void deleteEventsByOrderId(String id) {
        List<EventEntity> eventEntityCreated = eventRepository.findByOrderId(id);
        if (CollectionUtils.isEmpty(eventEntityCreated))
            throw new EventNotFoundException("No Events Found For Given Order Id : " + id);
        eventRepository.deleteByOrderId(id);
    }

}
