/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smtpclient.exceptions;

/**
 * Exception pour réponse malformé
 */
public class MalformedResponseException extends Exception {

    public MalformedResponseException() {
    }

    @Override
    public String toString() {
        return "Erreur:Reponse incorrect du serveur";
    }

    @Override
    public String getMessage() {
        return this.toString();
    }
}
