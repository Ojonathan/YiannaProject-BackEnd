package be.yianna.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class TypeEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_type;

    @Column(unique = true)
    private String type;

    @OneToMany(mappedBy = "type")
    private List<Event> events;
}
