package com.Googol.frontend;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutionException;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.google.gson.Gson;

import googol.AdminInfoDTO;
import googol.ClientCB;

public class RealTimeContent extends UnicastRemoteObject implements ClientCB {

  public RealTimeContent() throws RemoteException {
    super();
  }

  @Override
  public void notify(AdminInfoDTO adminInfo) throws RemoteException {

    adminInfo.getTopSearch().forEach(entry -> {
      System.out.println(entry.getKey() + " " + entry.getValue());
    });

    Gson gson = new Gson();

    RealTimeContentDTO realTimeContentDTO = new RealTimeContentDTO(adminInfo);

    String res = gson.toJson(realTimeContentDTO);
    System.out.println(res);

    WebSocketClient webSocketClient = new StandardWebSocketClient();
    WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
    stompClient.setMessageConverter(new StringMessageConverter());
    // stompClient.setTaskScheduler(taskScheduler);

    String url = "ws://127.0.0.1:8080/websocket";
    StompSessionHandler sessionHandler = new MyStompSessionHandler();
    try {
      StompSession session = stompClient.connectAsync(url, sessionHandler).get();

      System.out.println("New session established: " + session.getSessionId());

      session.send("/app/updateAdminInfo", res);
      session.disconnect();

    } catch (InterruptedException | ExecutionException e) {
      System.out.println("Erro ao conectar com o websocket");
      e.printStackTrace();
    }

    // ClientWebsockets clientWebsockets = new ClientWebsockets();
    // clientWebsockets.messageSend();

    // ToDo: send res to frontend

  }

}
