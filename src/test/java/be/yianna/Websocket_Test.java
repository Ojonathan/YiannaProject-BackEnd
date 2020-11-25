package be.yianna;
import be.yianna.YiannaprojectApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= YiannaprojectApplication.class)
public class Websocket_Test {

    static final String WEBSOCKET_URI = "ws://localhost:8080/ws";
    static final String WEBSOCKET_SUBSCRIBE = "/topic/greetings";
    static final String WEBSOCKET_SEND = "/app/hello";

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;

    @BeforeEach
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(
                asList(new WebSocketTransport(new StandardWebSocketClient()))));
    }

    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {
        //StompSessionHandler sessionHandler = new CustomStompSessionHandler();
        //StompSession stompSession = stompClient
        //        .connect(loggerServerQueueUrl, sessionHandler)
        //        .get();

        StompSession session = stompClient
                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter(){})
                .get(1, SECONDS);

        session.subscribe(WEBSOCKET_SUBSCRIBE, new DefaultStompFrameHandler());

        String message = "MESSAGE TEST";
        session.send(WEBSOCKET_SEND, message.getBytes());


        String result = blockingQueue.poll(1, SECONDS);
        System.out.println("recibido del servidor ... " + result);

        assertEquals(("Hello, " + message +"!"), result);
    }

    // clase que sirve para recuperar en contenido de la subscripcion
    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}
