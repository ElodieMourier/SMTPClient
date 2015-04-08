package smtpclient.commandes;

import java.io.BufferedReader;
import java.io.IOException;
import smtpclient.exceptions.MalformedResponseException;
import smtpclient.responses.Response;

public class DataCmd extends Commande {

    public DataCmd() {
        super();
        code = "DATA";
    }

    @Override
    public Boolean traitementRep(BufferedReader input) throws MalformedResponseException, IOException, Exception {
       Response r = this.response(input); //attendre une réponse
        
       return r.getCode() == 354;
    }
    
    
}
