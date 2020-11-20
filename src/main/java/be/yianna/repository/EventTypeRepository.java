package be.yianna.repository;

import be.yianna.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType,Long> {
}
