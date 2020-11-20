package be.yianna.rest;

import be.yianna.domain.EventType;
import be.yianna.service.EventTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin // possible to specify settings
@RequestMapping("/event_types")
public class EventTypeRestController {

    private EventTypeService eventTypeService;

    // Autowired not required for only one Constructor
    public EventTypeRestController(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    // Add new event type
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addEventType(@RequestBody EventType eventType) {
        try{
            eventTypeService.addEventType(eventType);
            return new ResponseEntity<String>("Success du l'ajout d'un type d'événement", HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de l'ajout d'un type d'événement : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
