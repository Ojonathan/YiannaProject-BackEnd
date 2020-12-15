package be.yianna.repository;

import be.yianna.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation,Long> {

    boolean existsByIdConversation(String conversation);
    Conversation findByIdConversation(String conversation);

    boolean existsBySenderNameOrRecipientName(String senderName, String recipientName);

    // List of conversation by User
    List<Conversation> findAllBySenderNameOrRecipientName(String senderName, String recipientName);

    @Query("select ar.name from Conversation u inner join u.event ar where ar.idEvent = :idEvent")
    String getConversationEventTitle(@Param(value = "idEvent") Long idEvent);
}
