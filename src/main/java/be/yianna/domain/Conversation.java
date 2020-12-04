package be.yianna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Conversation {
    @Id
    private String idConversation;

    @ManyToOne
    @JsonIgnore
    private Event event;

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;

    private String senderName;
    private String recipientName;
}