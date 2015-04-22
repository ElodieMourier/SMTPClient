package smtpclient.commandes;

import java.io.BufferedReader;
import java.io.IOException;
import smtpclient.exceptions.MalformedResponseException;
import smtpclient.responses.Response;

public class RcptToCmd extends Commande {
    public RcptToCmd(String mail) {
        super(":");
        code = "RCPT TO";
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
