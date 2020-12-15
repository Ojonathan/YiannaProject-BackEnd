package be.yianna.rest;

import be.yianna.domain.AuthorEvent;
import be.yianna.domain.ChatNotification;
import be.yianna.domain.Conversation;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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


    @MessageMapping("/chat/{eventId}")
    //@SendToUser("/queue/messages")
    public void processMessage(@Payload Message message,
                               @DestinationVariable("eventId") Long eventId) {
        // si la conversacion no existe se la crea y se envia el objeto creado
        // si la conversacion ya existe entonces se envia el objeto
        Conversation conversation = messageService
                .getConversation(message.getSenderName(), message.getRecipientName(), eventId);

        message.setConversation(conversation);

        // save message in database
        Message saved = messageService.save(message);

        System.out.println("New Message Saved ....");
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
                        saved.getConversation().getIdConversation(),
                        saved.getSenderName()));
    }

    // get total number of messages revieved for a conversation
    /*@GetMapping("/messages/{senderName}/{recipientName}/count")
    public ResponseEntity<Long> countNewMessagesConversation(@PathVariable String senderName, @PathVariable String recipientName) {
        return ResponseEntity
                .ok(messageService.countNewMessagesConversation(senderName, recipientName));
    }*/

    // get total number of messages revieved for a conversation
    @GetMapping("/messages/user/{recipientName}/count")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Long> countNewMessagesTotal(@PathVariable String recipientName) {
        return ResponseEntity
                .ok(messageService.countNewMessagesTotal(recipientName));
    }

    @GetMapping("/conversations/user/{userName}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> getUserConversations(@PathVariable String userName) {
        return ResponseEntity
                .ok(messageService.getUserConversations(userName));
    }

    @GetMapping("/conversations/event/{idEvent}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> getConversationEventTitle(@PathVariable("idEvent") Long idEvent){
        try {
            String EventTitle = messageService.getConversationEventTitle(idEvent);
            return (EventTitle == null)?
                    new ResponseEntity<>(HttpStatus.NOT_FOUND):
                    new ResponseEntity<String>(EventTitle, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<String>("Erreur lors de la recuperation du titre de l'evenement pour une conversation : " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // get all messages by Conversation(by eventId, senderId, recipientId)
    /*@GetMapping("/messages/{eventId}/{senderName}/{recipientName}")
    public ResponseEntity<?> findChatMessages ( @PathVariable Long eventId,
                                                @PathVariable String senderName,
                                                @PathVariable String recipientName
                                                ) {
        return ResponseEntity
                .ok(messageService.findChatMessages(senderName, recipientName, eventId));
    }*/

    @GetMapping(value = "/conversations/{idConversation}/messages")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> findChatMessages( @PathVariable String idConversation,
                                               @RequestParam("recipientName")String recipientName) {
        return ResponseEntity
                .ok(messageService.findChatMessages(idConversation, recipientName));
    }

    // get a specific message and change its status value to DELIVERED
    @GetMapping("/messages/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> findMessage ( @PathVariable Long id) {
        try{
            Message msg = messageService.findById(id);
            return new ResponseEntity<Message>(msg, HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<String>("message not found : " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
