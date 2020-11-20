package be.yianna.repository;

import be.yianna.domain.Event;
import be.yianna.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//TODO Documentation
public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findAllByUser(User user);
}
