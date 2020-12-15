package be.yianna.domain;

import be.yianna.domain.Authority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
//@Table(name = "USER")
public class User {

    /*@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUser;

    //@Column(name = "USERNAME", length = 50, unique = true)
    @Column(unique = true)
    private String username;

    //@Column(name = "PASSWORD", length = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    //@Column(name = "FIRSTNAME", length = 50)
    private String firstname;

    //@Column(name = "LASTNAME", length = 50)
    private String lastname;

    //@Column(name = "EMAIL", length = 50)
    private String email;

    private String description;

    private String avatar;

    private Integer age;

    //@Column(name = "ENABLED")
    private Boolean enabled;

    //@Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    @JsonIgnore
    private List<Authority> authorities;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Event> events;

    /*public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }*/

    public User() {
    }

    public User(String username, String password, String avatar, List<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.authorities = authorities;
        this.enabled = true;
    }

    public User(String username, String password, List<Authority> authorities, String description, int age) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.description = description;
        this.age = age;
        this.enabled = true;
    }
}