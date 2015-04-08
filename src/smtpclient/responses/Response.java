package smtpclient.responses;

import smtpclient.exceptions.MalformedResponseException;

public class Response {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(String data) throws MalformedResponseException {
        //Analyser
        this.parse(data);
    }

    /**
     * Découper une réponse en code + message
     *
     * @param data : ligne de réponse provenant du serveur
     * @return reponse
     * @throws MalformedResponseException
     */
    public Response parse(String data) throws MalformedResponseException {
        if (data.matches("[0-9]{3}.+")) {
            code = Integer.valueOf(data.replaceAll("^([0-9]{3}).+$", "$1"));
            message = data.replaceAll("^[0-9]{3}(.+)$", "$1").trim(); //récupérer le reste
            return this;
        } else {
            throw new MalformedResponseException();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Response) {
            Response r = (Response) obj;
            return r.code == this.code;
        }
        return false;
    }

    @Override
    public String toString() {
        return code+" "+message+"\r\n";
    }
}
