package be.yianna.service;

import be.yianna.domain.Event;
import be.yianna.domain.User;
import be.yianna.repository.EventRepository;
import be.yianna.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            // Récupérer le Cient offrant
            User user = userRepo.findByUsername(username);

            // Ajouter le nouveau evenement à la liste des evenements de l'utilisateur
            List<Event> eventsuser = user.getEvents();
            eventsuser.add(event);
            user.setEvents(eventsuser);

            // Ajouter l'utilisateur qui a publié l'evenement
            event.setUser(user);

            // Synchroniser les objets persistants
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
            // Récupérer l'utilisateur par son username
            User user = userRepo.findByUsername(username);

            // Récupérer tous les evenements d'un utilisateur
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
