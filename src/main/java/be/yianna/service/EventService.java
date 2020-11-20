package be.yianna.service;

import be.yianna.domain.Event;

import java.util.List;

public interface EventService {
    void addEvent(String username, Event event);

    List<Event> getAllEvents();
    List<Event> getAllEventsForUser(String username);

    void deleteEventById(Long id);
}