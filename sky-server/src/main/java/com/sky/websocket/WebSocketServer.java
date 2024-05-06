package com.sky.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket service
 */
@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    //store session object
    private static Map<String, Session> sessionMap = new HashMap();

    /**
     * the method to be called after successful connection
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("user end " + sid + " starts connection..");
        sessionMap.put(sid, session);
    }

    /**
     * the method to be called after successfully receiving client end message
     *
     * @param message client end message
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("receiving client: " + sid + "'s message: " + message);
    }

    /**
     * method to be called after closing connection
     *
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("connection closed: " + sid);
        sessionMap.remove(sid);
    }

    /**
     * group message
     *
     * @param message
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                //server sends message to clients
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}