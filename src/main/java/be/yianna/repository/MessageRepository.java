package be.yianna.repository;

import be.yianna.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO Documentation
public interface MessageRepository extends JpaRepository<Message, Long> {
}
