package be.yianna.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorEvent {
    private String username;
    private String avatar;

    public AuthorEvent(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
    }
}
