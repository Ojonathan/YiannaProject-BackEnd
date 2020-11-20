package be.yianna.rest;

import be.yianna.domain.Event;
import be.yianna.domain.User;
import be.yianna.repository.EventRepository;
import be.yianna.repository.UserRepository;
import be.yianna.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin // possible to specify settings
@RequestMapping("/events")
public class EventRestController {

    private EventService eventService;

    // Autowired not required for only one Constructor
    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    // TODO to improve and make it compatible with pagging
    @GetMapping
    public List<Event> findAll() {
        return eventService.getAllEvents();
    }

    // Add new event
    @PostMapping
    public ResponseEntity<?> addEvent(@RequestBody Event event, Principal user) {
        try{
            // Récupérer le client à partir du contexte de sécurité (via le raccourci principal) et lui ajouter l'offre
            String username = user.getName();
            eventService.addEvent(username,event);
            return new ResponseEntity<String>("Success du l'ajout d'un événement", HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de l'ajout d'un événement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // Get all events
    @GetMapping("/me")
    public ResponseEntity<?> getAllMyEvents(Principal user) {
        try {
            // Récupérer le client à partir du contexte de sécurité (via le raccourci principal) et lui ajouter l'offre
            String me = user.getName();

            List<Event> events = eventService.getAllEventsForUser(me);
            return (events == null)?
                    new ResponseEntity<>(HttpStatus.NOT_FOUND):
                    new ResponseEntity<List<Event>>(events, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de la recuperation de mes evenements : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // Only Admin can delete Event
    @DeleteMapping("/{id}" )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {
        try{
            eventService.deleteEventById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de la suppresion d'un événement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
