package be.yianna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_type;

    @Column(unique = true)
    private String type;

    @OneToMany(mappedBy = "type")
    @JsonIgnore
    private List<Event> events;
}
