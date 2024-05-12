package com.Googol.frontend;

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

  @Override
  public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    // ...
    System.out.println("New session established: " + session.getSessionId());
    // session.send("/app/updateAdminInfo", "payload");
  }
}