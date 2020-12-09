package be.yianna.repository;

import be.yianna.domain.Conversation;
import be.yianna.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation,Long> {

    boolean existsByIdConversation(String conversation);
    Conversation findByIdConversation(String conversation);

    boolean existsBySenderNameOrRecipientName(String senderName, String recipientName);
    List<Conversation> findAllBySenderNameOrRecipientName(String senderName, String recipientName);

    @Query("select u.username from User u inner join u.events ar where ar.idEvent = :idEvent")
    String getConversationEvent(@Param(value = "idEvent") Long idEvent);
    
}
