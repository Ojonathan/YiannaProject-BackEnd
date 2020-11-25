package be.yianna.service;

import be.yianna.domain.Message;
import be.yianna.domain.MessageStatus;
import be.yianna.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public String getChatId(Long senderId, Long recipientId, Long eventId, boolean createIfNotExist) {
        // check if a chatId exists, otherwise use createIfNoExist to create new chatId
        String generatedChatId = String.format("%s_%s_%s", eventId, senderId, recipientId);
        if(messageRepository.existsDistinctByChatId(generatedChatId)){
            return messageRepository.findDistinctByChatId(generatedChatId).getChatId();
        } else {
            generatedChatId = String.format("%s_%s_%s", eventId, recipientId, senderId);
            if(messageRepository.existsDistinctByChatId(generatedChatId)){
                return messageRepository.findDistinctByChatId(generatedChatId).getChatId();
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

    public long countNewMessages(Long senderId, Long recipientId) {
        return messageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<Message> findChatMessages(Long senderId, Long recipientId, Long eventId) {
        // why null ChatId ?
        String chatId = getChatId(senderId, recipientId, eventId ,false);
        //Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);

        // find messages using chatId
        List<Message> messages = messageRepository.findByChatId(chatId);//.orElse(new ArrayList<>());

        System.out.println("Found Messages ...." + messages.size());

        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    // Update status for all messages matching senderId and recipientId
    public void updateStatuses(Long senderId, Long recipientId, MessageStatus status) {
        messageRepository.updateMessages(status, senderId,recipientId);
    }

    // Search a message and change its status
    public Message findById(Long id) {
        Message msg = messageRepository.findByIdMessage(id);
        msg.setStatus(MessageStatus.DELIVERED);
        return messageRepository.save(msg);
    }
}
