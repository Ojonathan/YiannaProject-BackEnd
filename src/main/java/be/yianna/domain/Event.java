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
    private String place;
    private String picture;
    private boolean carAvailable;

    //@Lob
    //private byte[] picture;
    // Can I include image and date

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    private EventType type;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private List<Conversation> conversations;

    public Event(String name, String description, String place, String picture, boolean carAvailable){
        this.name = name;
        this.description = description;
        this.place = place;
        this.picture = picture;
        this.carAvailable = carAvailable;

    }


}
