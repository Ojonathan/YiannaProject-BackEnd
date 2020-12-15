package be.yianna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAuthority;

    //@Column(name = "NAME", length = 50)
    @Enumerated(EnumType.STRING)
    private AuthorityName name;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;

    public Authority() {
    }

    public Authority(AuthorityName authority, List<User> users) {
        this.name = authority;
        this.users = users;
    }

    public Authority(AuthorityName authority) {
        this.name = authority;
    }
}