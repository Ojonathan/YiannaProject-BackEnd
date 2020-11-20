package be.yianna.service;

import be.yianna.domain.Event;
import be.yianna.domain.EventType;
import be.yianna.repository.EventTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeServiceImpl implements EventTypeService{

    private EventTypeRepository eventTypeRepository;

    public EventTypeServiceImpl(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    public void addEventType(EventType eventType) {
        try {
            // Synchroniser les objets persistants
            eventTypeRepository.save(eventType);
        } catch (Exception ex) {

        }
    }

    @Override
    public List<Event> getAllEventTypes() {
        return null;
    }

    @Override
    public void deleteEventTypeById(Long id) {

    }
}
