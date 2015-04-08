package smtpclient.commandes;

import java.io.BufferedReader;
import java.io.IOException;
import smtpclient.exceptions.MalformedResponseException;
import smtpclient.responses.Response;

public class HeloCmd extends Commande {
    public HeloCmd(String domain) {
        super();
        
        code = "HELO";
        parameters = new String[1];
        setDomain(domain);
    }
    
    public void setDomain(String domain)
    {
        parameters[0] = domain;
    }
    
    @Override
    public Boolean traitementRep(BufferedReader input) throws MalformedResponseException, IOException {
        Response r = this.response(input); //attendre une réponse
        
        return r.getCode() == 250;
    }
}
