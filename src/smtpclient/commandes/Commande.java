package smtpclient.commandes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import smtpclient.exceptions.MalformedResponseException;
import smtpclient.responses.Response;

/**
 * Commande en général code parametre1 parametre2 ...
 */
public abstract class Commande {

    protected String[] parameters;
    protected String code;
    protected Response lastRep;
    protected String separator;

    public Commande() {
        separator = " ";
    }
    
    public Commande(String separator) {
        this.separator = separator;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public String getCode() {
        return code;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
    
    /**
     * Envoyer la commande sur le flux de sortie en paramètre
     *
     * @param output : flux sortie
     * @throws IOException
     */
    public void send(BufferedWriter output) throws IOException {
        output.write(this.toString());
        output.flush();
        System.out.println(this); //TODO
    }

    /**
     * Wait for a response
     *
     * @param input : flux in
     * @return
     * @throws MalformedResponseException
     * @throws IOException
     */
    protected Response response(BufferedReader input) throws MalformedResponseException, IOException {
        lastRep = new Response(input.readLine());
        System.out.println(lastRep);
        return lastRep;
    }

    public Response getLastRep() {
        return lastRep;
    }

    /**
     * Traitement par défaut d'une réponse avec un flux d'entré et de sortie
     *
     */
    public Object traitementRep(BufferedReader input, BufferedWriter output) throws MalformedResponseException, IOException, Exception {
        return traitementRep(input); //Ne pas être obligé de redéfinir les 2 méthodes traitementRep
    }

    /**
     * Traitement par défaut d'une réponse avec un flux d'entré
     *
     */

    public Object traitementRep(BufferedReader input) throws MalformedResponseException, IOException, Exception {
        return false;
    }

    /**
     * @return "code parametre1 parametre2 .."
     */
    @Override
    public String toString() {
        String str = code + separator;
        if (parameters != null) {
            for (int i = 0; i < parameters.length; ++i) {
                str += parameters[i] + " ";
            }
            str = str.trim();
        }
        str += "\r\n";
        return str;
    }
}
