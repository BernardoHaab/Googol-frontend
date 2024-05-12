package com.Googol.frontend.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class AdminController {

  private SimpMessagingTemplate template;

  @Autowired
  public AdminController(SimpMessagingTemplate template) {
    this.template = template;
  }

  @MessageMapping("/updateAdminInfo")
  @SendTo("/admin/updates")
  public String onMessage(String message) {
    System.out.println("ADMIN");
    System.out.println("Message received " + message);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } // simulated delay
    return message;
    // return new Message(HtmlUtils.htmlEscape(message.content()));
  }
}
