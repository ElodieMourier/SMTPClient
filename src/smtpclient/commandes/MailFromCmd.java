package smtpclient.commandes;

import java.io.BufferedReader;
import java.io.IOException;
import smtpclient.exceptions.MalformedResponseException;
import smtpclient.responses.Response;

public class MailFromCmd extends Commande {

    public MailFromCmd(String mail) {
        super(":");
        code = "MAIL FROM";
        parameters = new String[1];
        
        setMail(mail);
    }
    
    public void setMail(String mail)
    {
        parameters[0] = "<"+mail+">";
    }

    @Override
    public Boolean traitementRep(BufferedReader input) throws MalformedResponseException, IOException, Exception {
        Response r = this.response(input); //attendre une réponse
        
        return r.getCode() == 250;
    }
    
    
}
