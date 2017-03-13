package hello;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
public class GreetingController {

	private final RabbitTemplate rabbitTemplate;
    
	private TemplateEngine templateEngine;
	
	public GreetingController(RabbitTemplate rabbitTemplate) {
		super();
		this.rabbitTemplate = rabbitTemplate;
	}

    @MessageMapping("/hello")
    public void greeting(HelloMessage message) throws Exception {
    	
    	final Context ctx = new Context();
    	ctx.setVariable("message", message.getName());
    	
    	String htmlContent = this.templateEngine.process("templates/sent.xml", ctx);
        rabbitTemplate.convertAndSend("sms-queue", htmlContent);
    }

}
