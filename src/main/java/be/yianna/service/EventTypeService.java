package be.yianna.service;

import be.yianna.domain.Event;
import be.yianna.domain.EventType;

import java.util.List;

public interface EventTypeService {
    void addEventType(EventType eventType);
    List<Event> getAllEventTypes();
    void deleteEventTypeById(Long id);
}
