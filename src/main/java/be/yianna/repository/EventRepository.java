package be.yianna.repository;

import be.yianna.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO Documentation
public interface EventRepository extends JpaRepository<Event,Long> {
}
