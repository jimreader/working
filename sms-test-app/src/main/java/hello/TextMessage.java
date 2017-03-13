package hello;

public class TextMessage {

    private String content;
    
    private String originator;
    
    private String recipient;

    public TextMessage() {
    }

    public TextMessage(String content) {
        this.content = content;
    }
    
    public TextMessage(String content, String originator) {
        this.content = content;
		this.originator = originator;
    }

    public String getContent() {
        return content;
    }

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

}
