package be.yianna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Conversation {
    @Id
    @Column(unique = true)
    private String idConversation;

    private String senderName;
    private String recipientName;

    @ManyToOne
    @JsonIgnore
    private Event event;

    @OneToMany(mappedBy = "conversation")
    @JsonIgnore
    private List<Message> messages;

    public Conversation(String idConversation){
        this.idConversation = idConversation;
    }
}