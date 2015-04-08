package smtpclient.commandes;

import java.io.BufferedReader;
import java.io.IOException;
import smtpclient.exceptions.MalformedResponseException;
import smtpclient.responses.Response;

/**
 * Commande pour quitter QUIT
 */
public class QuitCmd extends Commande {

    public QuitCmd() {
        code = "QUIT";
    }

    @Override
    public Boolean traitementRep(BufferedReader input) throws MalformedResponseException, IOException {
        Response r = this.response(input); //attendre une réponse
        
        return r.getCode() == 221;
    }
}
