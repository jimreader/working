package hello;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

	private SimpMessagingTemplate template;

	
    public Receiver(SimpMessagingTemplate template) {
		super();
		this.template = template;
	}


	public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        this.template.convertAndSend("/topic/greetings", new TextMessage(message, "01332445566"));
    }

}
