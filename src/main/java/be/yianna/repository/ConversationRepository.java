package be.yianna.repository;

import be.yianna.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Event,Long> {
}
