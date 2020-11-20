package be.yianna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long owner;
    private Long receiver;
    private String ownerName;
    private String receiverName;

    private String content;
    private LocalDateTime timestamp;

    @Enumerated(EnumType.ORDINAL)
    private MessageStatus status; //enum in DataBase

    @ManyToOne
    @JsonIgnore
    private Conversation conversation;
}
