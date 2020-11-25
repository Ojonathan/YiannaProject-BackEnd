package be.yianna.service;

import be.yianna.domain.EventType;
import be.yianna.repository.EventTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventTypeServiceImpl implements EventTypeService{

    private EventTypeRepository eventTypeRepository;

    public EventTypeServiceImpl(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    public void addEventType(EventType eventType) {
        try {
            // Synchronize persistent objects
            eventTypeRepository.save(eventType);
        } catch (Exception ex) {

        }
    }

    // TODO to improve and make it compatible with pagging
    @Override
    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    @Override
    public void deleteEventTypeById(Long id) {
        Optional<EventType> resultat =  eventTypeRepository.findById(id);
        if (resultat.isPresent()) {
            eventTypeRepository.delete(resultat.get());
        }
    }
}
