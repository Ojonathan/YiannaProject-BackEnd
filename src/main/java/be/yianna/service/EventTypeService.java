package be.yianna.service;

import be.yianna.domain.EventType;

import java.util.List;

public interface EventTypeService {
    void addEventType(EventType eventType);
    List<EventType> getAllEventTypes();
    void deleteEventTypeById(Long id);
}
