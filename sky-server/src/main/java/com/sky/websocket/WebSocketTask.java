package com.sky.websocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Component
//public class WebSocketTask {
//    @Autowired
//    private WebSocketServer webSocketServer;
//
//    /**
//     * sent message to clients every 5s via WebSocket
//     */
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void sendMessageToClient() {
//        webSocketServer.sendToAllClient("this message is from server：" + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
//    }
//}
