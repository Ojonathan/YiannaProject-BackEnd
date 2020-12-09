package be.yianna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUser;

    @Column(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String description;
    private Integer age;
    private String email;
    // can I include avatar and email

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Event> events;

    public User() {
	}

    public User(String username, String password, Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

    public User(String username, String password, Set<Role> roles, String description, int age) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.description = description;
        this.age = age;
    }
}
