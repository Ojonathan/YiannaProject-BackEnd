package be.yianna.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMessage;

    private String chatId;
    private Long senderId;
    private Long recipientId;
    private String senderName;
    private String recipientName;

    private String content;
    private LocalDateTime timestamp;

    @Enumerated(EnumType.ORDINAL)
    private MessageStatus status; //enum in DataBase

    @ManyToOne
    private Event event;
}
