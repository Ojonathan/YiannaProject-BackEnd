package be.yianna.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotification {
    private Long idChatNotif;
    private String idConversation;
    private String senderName;
}
