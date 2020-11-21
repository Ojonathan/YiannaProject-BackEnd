package be.yianna.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_role;

    @Column(unique = true)
    private String role;

    // @JsonIgnore
    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<User> users;

    public Role() {
	}

    public Role(String role, Set<User> users) {
		this.role = role;
		this.users = users;
	}

	public long getId_role() {
        return id_role;
    }

    public void setId_role(long id) {
        this.id_role = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
