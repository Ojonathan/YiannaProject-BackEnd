package be.yianna.service;

import be.yianna.domain.Event;
import be.yianna.domain.User;
import be.yianna.repository.EventRepository;
import be.yianna.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepo;
    private UserRepository userRepo;

    public EventServiceImpl(UserRepository userRepo,
                            EventRepository eventRepo) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    @Override
    public void addEvent(String username, Event event) {
        try {
            // Retrieve the user
            User user = userRepo.findByUsername(username);

            // Add the new event to the user's list of events
            List<Event> eventsuser = user.getEvents();
            eventsuser.add(event);
            user.setEvents(eventsuser);

            // Add the user who published the event
            event.setUser(user);

            // Add the user who published the event
            eventRepo.save(event);
        } catch (Exception ex) {

        }
    }

    // TODO to improve and make it compatible with pagging
    @Override
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    @Override
    public List<Event> getAllEventsForUser(String username) {
        List<Event> eventsList = new LinkedList<>();
        try{
            // Retrieve the user by his username
            User user = userRepo.findByUsername(username);

            // Retrieve all of a user's events
            eventsList = eventRepo.findAllByUser(user);
        } catch (Exception ex) {

        }
        return eventsList;
    }

    @Override
    public void deleteEventById(Long id) {
        Optional<Event> resultat =  eventRepo.findById(id);
        if (resultat.isPresent()) {
            eventRepo.delete(resultat.get());
        }
    }
}
