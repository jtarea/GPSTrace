package com.otitan.bigdata.tracelocation.service.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class GpsTraceWebSocketHandler extends TextWebSocketHandler {

    //public static final Logger logger = LoggerFactory.getLogger(GpsTraceWebSocketHandler.class);

    @Autowired
    private BroadcastHandler broadcastHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("Adding session :" + session.getId());
        broadcastHandler.add(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("Removing session :" + session.getId());
        broadcastHandler.remove(session);
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("GOT INIT MESSAGE: " + message.getPayload());
    }

}
