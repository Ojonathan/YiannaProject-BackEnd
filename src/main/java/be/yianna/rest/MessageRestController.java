package be.yianna.rest;

import be.yianna.domain.ChatNotification;
import be.yianna.domain.Message;
import be.yianna.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.Optional;

@RestController
@CrossOrigin // possible to specify settings
public class MessageRestController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("Message received: " + message);
        return "Hello, " + message + "!";
    }


    @MessageMapping("/chat/{eventId}") // envia notificacion de mensaje
    //@SendToUser("/queue/messages")
    public void processMessage(@Payload Message message,
                               @DestinationVariable("eventId") Long eventId) {
        // si ya existe el chatId se lo envia, sino se crea uno nuevo (en el caso de una nueva conversacion)
        String chatId = messageService
                .getChatId(message.getSenderName(), message.getRecipientName(), eventId, true);

        // set chatId
        message.setChatId(chatId);

        // save message in the database
        Message saved = messageService.save(message);

        System.out.println("Message Saved ....");
        //return "Message Saved ....";

        // Respond to user with a notification
        // (message receiver, message destination , object to send)
        /*messagingTemplate.convertAndSend(
                "/user/" + message.getRecipientName() + "/queue/messages",
                new ChatNotification(
                        saved.getIdMessage(),
                        saved.getSenderId(),
                        saved.getSenderName()));*/
        messagingTemplate.convertAndSendToUser(
                message.getRecipientName(),
                "/queue/messages",
                new ChatNotification(
                        saved.getIdMessage(),
                        saved.getSenderName()));
    }

    // get total number of messages revieved for a conversation
    @GetMapping("/messages/{senderName}/{recipientName}/count")
    public ResponseEntity<Long> countNewMessagesConversation(@PathVariable String senderName, @PathVariable String recipientName) {
        return ResponseEntity
                .ok(messageService.countNewMessagesConversation(senderName, recipientName));
    }

    // get total number of messages revieved for a conversation
    @GetMapping("/messages/{recipientName}/count")
    public ResponseEntity<Long> countNewMessagesTotal(@PathVariable String recipientName) {
        return ResponseEntity
                .ok(messageService.countNewMessagesTotal(recipientName));
    }

    // get all messages by Conversation(by eventId, senderId, recipientId)
    @GetMapping("/messages/{eventId}/{senderName}/{recipientName}")
    public ResponseEntity<?> findChatMessages ( @PathVariable Long eventId,
                                                @PathVariable String senderName,
                                                @PathVariable String recipientName
                                                ) {
        return ResponseEntity
                .ok(messageService.findChatMessages(senderName, recipientName, eventId));
    }

    // get a specific message and change its status value to DELIVERED
    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable Long id) {
        try{
            Message msg = messageService.findById(id);
            return new ResponseEntity<Message>(msg, HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<String>("Le message n'as pas été trouvé : " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
