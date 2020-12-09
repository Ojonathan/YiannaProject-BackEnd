package be.yianna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEvent;

    private String name;
    private String description;
    private boolean carAvailable;

    @Lob
    private byte[] picture;
    // Can I include image and date

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    private EventType type;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private List<Conversation> conversations;

    public Event(String name, String description, boolean carAvailable){
        this.name = name;
        this.description = description;
        this.carAvailable = carAvailable;
    }


}
