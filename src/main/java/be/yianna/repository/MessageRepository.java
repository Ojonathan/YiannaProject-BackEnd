package be.yianna.repository;

import be.yianna.domain.Conversation;
import be.yianna.domain.Message;
import be.yianna.domain.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//TODO Documentation
public interface MessageRepository extends JpaRepository<Message, Long> {
    long countBySenderNameAndRecipientNameAndStatus(
            String senderName, String recipientName, MessageStatus status);

    //long countByConversationAndStatus(Conversation conversation, MessageStatus status);

    long countByRecipientNameAndStatus(
            String recipientName, MessageStatus status);

    //List<Message> findByChatId(String chatId);

    Message findByIdMessage(Long idMessage);

    @Transactional // By default (readOnly = false)
    @Modifying
    @Query("update Message u set u.status = :status where u.senderName = :senderName and u.recipientName = :recipientName")
    void updateMessages(@Param(value = "status") MessageStatus status,
                        @Param(value = "senderName") String senderName,
                        @Param(value = "recipientName") String recipientName
                        );

    @Transactional // By default (readOnly = false)
    @Modifying
    @Query("update Message u set u.status = :status where u.conversation.idConversation = :idConversation and u.recipientName = :recipientName")
    void updateMessagesByConversation(@Param(value = "status") MessageStatus status,
                        @Param(value = "idConversation") String idConversation,
                        @Param(value = "recipientName") String recipientName
    );

    //Message findTopByChatId(String chatId);

    //Message findDistinctByChatId(String chatId);

    //@Query("select u.username from User u inner join u.events ar where ar.idEvent = :idEvent")
    List<Message> findAllByConversation(Conversation conversation);
}
