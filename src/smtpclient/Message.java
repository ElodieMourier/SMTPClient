package smtpclient;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private String subject;
    private String from;
    private List<String> to;
    private String content;

    public Message() {
        subject = "";
        from = "";
        to = new ArrayList<>();
        content = "";
    }

    public Message(String subject, String from, List<String> to, String content) {
        this.subject = subject;
        this.from = from;
        this.to = to;
        this.content = content;
    }
    
    public Message(String subject, String from)
    {
        this.subject = subject;
        this.from = from;
        to = new ArrayList<>();
        this.content = "";
    }
    
    public void addTo(String dest)
    {
        to.add(dest);
    }
    
    public String deleteTo(int i)
    {
        return to.remove(i);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }
}
