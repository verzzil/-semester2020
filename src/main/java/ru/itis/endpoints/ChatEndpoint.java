package ru.itis.endpoints;


import ru.itis.dto.Message;
import ru.itis.dto.coders.MessageDecoder;
import ru.itis.dto.coders.MessageEncoder;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(
        value = "/chat/{userId}",
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class}
)
public class ChatEndpoint {

    private Session session = null;
    private static Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.session.getUserProperties().put("id", userId);

        chatEndpoints.add(this);
    }

    @OnClose
    public void onClose(Session session) {
        chatEndpoints.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, Message msg) {
        chatEndpoints.forEach(endpoint -> {
            if (
                    endpoint.session.getUserProperties().get("id").equals(msg.getToUserId().toString()) ||
                            endpoint.session.getUserProperties().get("id").equals(msg.getFromUserId().toString())
            ) {
                try {
                    endpoint.session.getBasicRemote().sendObject(msg);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
