package be.yianna.repository;

import be.yianna.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO Documentation
public interface EventTypeRepository extends JpaRepository<EventType,Long> {
}
