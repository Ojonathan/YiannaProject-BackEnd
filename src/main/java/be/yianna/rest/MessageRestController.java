package be.yianna.rest;

import be.yianna.domain.ChatNotification;
import be.yianna.domain.Message;
import be.yianna.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return "Hello, " + message + "!";
    }


    @MessageMapping("/chat") // envia notificacion de mensaje
    //@SendToUser("/queue/messages")
    @SendTo("/topic/greetings")
    public String processMessage(@Payload Message message) {
        // si ya existe el chatId se lo envia, sino se crea uno nuevo (en el caso de una nueva conversacion)
        String chatId = messageService
                .getChatId(message.getSenderId(),
                        message.getRecipientId(),
                        message.getEvent().getIdEvent(),
                        true);

        // indicar a message a que conversation pertenece
        message.setChatId(chatId);

        // guardar message en la base de datos
        Message saved = messageService.save(message);

        System.out.println("Mensaje Guardado ....");
        return "Mensaje Guardado ....";

        // Respond to user with a notification
        // (message receiver, message destination , object to send)
        /*messagingTemplate.convertAndSendToUser(
                message.getRecipientId().toString(),
                "/queue/messages",
                new ChatNotification(
                        saved.getIdMessage(),
                        saved.getSenderId(),
                        saved.getSenderName()));*/
    }

    // get total number of messages reveived
    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(@PathVariable Long senderId, @PathVariable Long recipientId) {
        return ResponseEntity
                .ok(messageService.countNewMessages(senderId, recipientId));
    }

    // get all message by eventId, senderId, recipientId
    @GetMapping("/messages/{eventId}/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable Long eventId,
                                                @PathVariable Long senderId,
                                                @PathVariable Long recipientId
                                                ) {
        return ResponseEntity
                .ok(messageService.findChatMessages(senderId, recipientId, eventId));
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
