package be.yianna.service;

import be.yianna.domain.Conversation;
import be.yianna.domain.Event;
import be.yianna.domain.Message;
import be.yianna.domain.MessageStatus;
import be.yianna.repository.ConversationRepository;
import be.yianna.repository.EventRepository;
import be.yianna.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private EventRepository eventRepository;

    public String getChatId(String senderName, String recipientName, Long eventId, boolean createIfNotExist) {
        // check if a chatId exists, otherwise use createIfNoExist to create new chatId
        String generatedChatId = String.format("%s_%s_%s", eventId, senderName, recipientName);
        if(messageRepository.existsDistinctByChatId(generatedChatId)){
            return messageRepository.findTopByChatId(generatedChatId).getChatId();
        } else {
            generatedChatId = String.format("%s_%s_%s", eventId, recipientName, senderName);
            if(messageRepository.existsDistinctByChatId(generatedChatId)){
                return messageRepository.findTopByChatId(generatedChatId).getChatId();
            } else {
                if(createIfNotExist) {
                    return generatedChatId;
                }
                return null;
            }
        }
    }

    public Message save(Message message) {
        message.setStatus(MessageStatus.RECEIVED);

        messageRepository.save(message);
        return message;
    }

    //public long countNewMessagesByConversation( senderName, String recipientName) {
    //
    //    return messageRepository.countByConversationAndStatus(,MessageStatus.RECEIVED)
    //            senderName, recipientName, MessageStatus.RECEIVED);
    //}

    public long countNewMessagesConversation(String senderName, String recipientName) {
        return messageRepository.countBySenderNameAndRecipientNameAndStatus(
                senderName, recipientName, MessageStatus.RECEIVED);
    }

    public long countNewMessagesTotal(String recipientName) {
        return messageRepository.countByRecipientNameAndStatus(
                recipientName, MessageStatus.RECEIVED);
    }

    public List<Message> findChatMessages(String senderName, String recipientName, Long eventId) {
        // why null ChatId ?
        String chatId = getChatId(senderName, recipientName, eventId ,false);
        //Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);

        // find messages using chatId
        List<Message> messages = messageRepository.findByChatId(chatId);//.orElse(new ArrayList<>());

        System.out.println("Found Messages ...." + messages.size());

        if(messages.size() > 0) {
            updateStatuses(senderName, recipientName, MessageStatus.DELIVERED);
        }

        return messages;
    }

    // Update status for all messages matching senderId and recipientId
    public void updateStatuses(String senderName, String recipientName, MessageStatus status) {
        messageRepository.updateMessages(status, senderName,recipientName);
    }

    // Search a message and change its status
    public Message findById(Long id) {
        Message msg = messageRepository.findByIdMessage(id);
        msg.setStatus(MessageStatus.DELIVERED);
        return messageRepository.save(msg);
    }
}
