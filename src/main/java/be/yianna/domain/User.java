package be.yianna.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_user;

    @Column(unique = true)
    private String username;

    // @JsonIgnore
    private String password;

    private String description;
    private int age;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Event> events;

    public User() {
	}

    public User(String username, String password, Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

    public User(String username, String password, String description, int age) {
        this.username = username;
        this.password = password;
        this.description = description;
        this.age = age;
    }
}
