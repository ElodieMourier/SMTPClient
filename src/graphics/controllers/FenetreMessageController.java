/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphics.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import smtpclient.Message;
import smtpclient.SMTPClient;
import smtpclient.exceptions.NoDestException;

/**
 *
 * @author p1308391
 */
public class FenetreMessageController implements Initializable {
    private final static int port = 1024;
    
    @FXML
    private Button btnSend;
    @FXML
    private Button btnQuitter;
    @FXML
    private TextField inputSubject;
    @FXML
    private TextArea inputContent;
    @FXML
    private TextField inputTo;
    @FXML
    private TextField inputFrom;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonSendAction(ActionEvent event) {
        Stage mainStage = (Stage)(btnQuitter.getScene().getWindow());
        
        btnSend.setDisable(true);
        btnQuitter.setDisable(true);
        
        Message m = new Message();
        
        //Verifier subject
        String subject = inputSubject.getText().trim();
        if (subject.isEmpty())
            subject = "No subject";
        
        m.setSubject(subject);
        
        //Verifier contenu
        String content = inputContent.getText().trim();
        m.setContent(content);
            
        //Verifier from
        String from = inputFrom.getText().trim();
        if (!from.matches("[a-zA-Z\\.0-9\\-_]+@[a-zA-Z0-9\\-]+\\.[a-zA-Z0-9]+"))
        {
            JOptionPane.showMessageDialog(null, "L'adresse mail du from est incorrect!");
        } else {
            m.setFrom(from);
            
            //Verifier to
            String tos = inputTo.getText().trim();
            String[] tblTo = tos.split(";");
            if (tblTo.length > 0 && !tblTo[0].isEmpty())
            {
                List<String> listTo = new ArrayList<>(); //liste destinataire valide
                List<String> listDomain = new ArrayList<>(); //liste des domaines
                String destIncorrect = "";
                for (int i=0; i<tblTo.length; ++i) {
                    tblTo[i] = tblTo[i].trim();
                    if (tblTo[i].matches("[a-zA-Z\\.0-9\\-_]+@[a-zA-Z0-9\\-\\.]+"))
                    {
                        listTo.add(tblTo[i]);
                        
                        //Checker si domaine n'existe pas et l'ajouter à la liste des domaines
                        String domain = (tblTo[i].split("@"))[1];
                        boolean exist = false;
                        for (String d : listDomain)
                        {
                            if (d.contentEquals(domain))
                            {
                                exist = true;
                                break;
                            }
                        }
                        if (!exist)
                            listDomain.add(domain);
                    } else 
                        destIncorrect += "- "+tblTo[i]+"\r\n";
                }
                
                if (!destIncorrect.isEmpty())
                    JOptionPane.showMessageDialog(null, "Les destinataires suivants sont incorrect : \r\n"+destIncorrect);
                else {
                    if (listTo.size() > 0)
                    {
                        m.setTo(listTo); //Ajouter destinataire message

                        String destError = "";
                        boolean ok = false;
                        for (String domain : listDomain)
                        {
                            SMTPClient smtp = new SMTPClient(m, port, domain);
                            try {
                                smtp.deposer(); 

                                for (String de : smtp.getDestError())
                                    destError += "Destinaire incorrect : "+de+"\r\n";

                                if(!ok) ok = true;
                            } catch (Exception ex) {
                                //JOptionPane.showMessageDialog(null, ex.getMessage());

                                for (String de : smtp.getListDest())
                                    destError += "Destinaire incorrect : "+de+"\r\n";

                                ex.printStackTrace();
                            }
                        }

                        if (ok)
                        {
                            JOptionPane.showMessageDialog(null, "Message déposé !\r\n"+destError);
                        } else {
                            JOptionPane.showMessageDialog(null, "Le message n'a pas été envoyé.. aucun destinataire atteint !");
                        }
                    } else 
                        JOptionPane.showMessageDialog(null, "Aucun domaine valide, le message n'a pu être envoyé !");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Aucun destinataire renseigné !");
            }
        }
        
        btnSend.setDisable(false);
        btnQuitter.setDisable(false);
    }

    @FXML
    private void handleButtonQuitAction(ActionEvent event) {
        Stage mainStage = (Stage)(btnQuitter.getScene().getWindow());
        mainStage.close();
    }
    
}
