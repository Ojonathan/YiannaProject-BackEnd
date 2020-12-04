package be.yianna.rest;

import be.yianna.domain.Event;
import be.yianna.domain.EventType;
import be.yianna.service.EventService;
import be.yianna.service.EventTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin // possible to specify settings
@RequestMapping("/event_types")
public class EventTypeRestController {

    private EventTypeService eventTypeService;
    private EventService eventService;


    // Autowired not required for only one Constructor
    public EventTypeRestController(EventTypeService eventTypeService,
                                   EventService eventService
    ) {
        this.eventTypeService = eventTypeService;
        this.eventService = eventService;
    }

    // TODO to improve and make it compatible with pagging
    @GetMapping
    public List<EventType> findAll() {
        return eventTypeService.getAllEventTypes();
    }

    @GetMapping("/{idEventType}/events")
    public List<Event> findAllByEventType(@PathVariable("idEventType") Long idEventType){
        return eventService.getAllEventsByEventType(idEventType);
    }


    // Add new event type
    @PostMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addEventType(@RequestBody EventType eventType) {
        try{
            eventTypeService.addEventType(eventType);
            return new ResponseEntity<>("Success du l'ajout d'un type d'événement", HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<>("Erreur lors de l'ajout d'un type d'événement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // Only Admin can delete Event
    @DeleteMapping("/{id}" )
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {
        try{
            eventTypeService.deleteEventTypeById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception ex) {
            return new ResponseEntity<>("Erreur lors de la suppresion d'un type d'événement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
