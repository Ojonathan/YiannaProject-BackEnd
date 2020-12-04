package be.yianna.service;

import be.yianna.domain.Event;
import be.yianna.domain.EventType;

import java.util.List;

public interface EventService {
    void addEvent(String username, Event event);

    List<Event> getAllEvents();
    List<Event> getAllEventsByEventType(Long idType);
    List<Event> getAllEventsForUser(String username);

    void deleteEventById(Long id);

    String getAuthor(Long idEvent);
}