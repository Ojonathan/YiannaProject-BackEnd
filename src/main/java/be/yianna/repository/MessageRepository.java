package be.yianna.repository;

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
    long countBySenderIdAndRecipientIdAndStatus(
            Long senderId, Long recipientId, MessageStatus status);

    List<Message> findByChatId(String chatId);

    Message findByIdMessage(Long idMessage);

    @Transactional // By default (readOnly = false)
    @Modifying
    @Query("update Message u set u.status = :status where u.senderId = :senderId and u.recipientId = :recipientId")
    void updateMessages(@Param(value = "status") MessageStatus status,
                        @Param(value = "senderId") Long senderId,
                        @Param(value = "recipientId") Long recipientId
                        );

    Message findDistinctByChatId(String chatId);

    boolean existsDistinctByChatId(String chatId);
}
