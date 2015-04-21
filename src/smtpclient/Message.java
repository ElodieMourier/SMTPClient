package smtpclient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Message {
    private String subject;
    private String from;
    private List<String> to;
    private String content;
    private GregorianCalendar dateMess;

    public Message() {
        subject = "";
        from = "";
        to = new ArrayList<>();
        content = "";
        
        dateMess = new GregorianCalendar();
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
    
    /**
     * Envoyer la commande sur le flux de sortie en paramètre
     *
     * @param output : flux sortie
     * @throws IOException
     */
    public void send(BufferedWriter output) throws IOException {
        //output.write("Date: "+dateMess.+"\r\n"); //date
        
        //TO
        for(String t : to)
            output.write("To: "+t+"\r\n");
        
        output.write("From: "+from+"\r\n"); //FROM
        output.write("Subject: "+subject+"\r\n"); //SUBJECT
        
        output.write("\r\n"); //vide
        
        output.write(content);
        if (content.endsWith("\r\n."))
            content += "#";
        if (content.endsWith("\r\n.\r\n"))
            content = content.substring(0, content.length()-1)+"#";
         
        output.write("\r\n.\r\n"); //FFIN
        output.flush();
    }
}
