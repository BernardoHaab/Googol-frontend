package websockets;

import org.springframework.boot.rsocket.server.RSocketServer;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientWebsockets {
    List<Transport> transports;
    SockJsClient sockJsClient;
    WebSocketStompClient stompClient;
    StompSessionHandler sessionHandler;

    public boolean messageSend (HashMap<String, Component> components, List<Searched> seaches){
        try {
            StompSession session = null;
            session = stompClient.connect("ws://localhost:8080/stats-ws", sessionHandler).get();
            session.send("/app/stats-update", new StatsMessage(components, seaches));
            session.disconnect();
            return true;
        } catch (InterruptedException | ExecutionException | IllegalStateException | MessageDeliveryException e) {
            return false;
        }
    }

    static class MyStompSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            session.subscribe("/stats/message", new StompFrameHandler(){
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return String.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    System.out.println("Received message: " + payload);
                }
            });
        }
    }

    public ClientWebsockets() {
        transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        sockJsClient = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        sessionHandler = new MyStompSessionHandler();
    }
}