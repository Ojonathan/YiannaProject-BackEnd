package be.yianna.rest;

import be.yianna.domain.AuthorEvent;
import be.yianna.domain.Event;
import be.yianna.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
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


    // Add new event using Principal
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> addEvent(@RequestBody Event event,
                                      Principal user) {
        try{
            // Récupérer le client à partir du contexte de sécurité (via le raccourci principal) et lui ajouter l'offre
            String username = user.getName();

            eventService.addEvent(username,event);
            return new ResponseEntity<String>("Success du l'ajout d'un événement", HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de l'ajout d'un événement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // Add new event
    @PostMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> addEvent(@RequestBody Event event,
                                      @PathVariable("username") String username) {
        try{
            // Récupérer le client à partir du contexte de sécurité (via le raccourci principal) et lui ajouter l'offre
            //String username = user.getName();

            eventService.addEvent(username,event);
            return new ResponseEntity<String>("Success du l'ajout d'un événement", HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de l'ajout d'un événement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // Get all events using Principal or username PathVariable
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> getAllMyEvents(@PathVariable("username") String username, Principal user) {
        try {
            // Récupérer le client à partir du contexte de sécurité (via le raccourci principal) et lui ajouter l'offre
            //String me = user.getName();

            List<Event> events = eventService.getAllEventsForUser(username);
            return (events == null)?
                    new ResponseEntity<>(HttpStatus.NOT_FOUND):
                    new ResponseEntity<List<Event>>(events, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de la recuperation de mes evenements : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/{idEvent}/author")
    public ResponseEntity<?> getAuthor(@PathVariable("idEvent") Long idEvent){
        try {
            AuthorEvent author = eventService.getAuthor(idEvent);
            return (author == null)?
                    new ResponseEntity<>(HttpStatus.NOT_FOUND):
                    new ResponseEntity<AuthorEvent>(author, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de la recuperation de mon auteur : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

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
