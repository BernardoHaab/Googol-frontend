package websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ControllerWebsocket {
    public ControllerWebsocket() {
        System.out.println("ControllerWebsocket created");
    }
    @MessageMapping("/stats-update")
    @SendTo("/stats/message")
    public String updateStats(String message) {
        System.out.println("Received message: " + message);
        return message;
    }
}