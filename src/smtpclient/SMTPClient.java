 package smtpclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import smtpclient.commandes.DataCmd;
import smtpclient.commandes.HeloCmd;
import smtpclient.commandes.MailFromCmd;
import smtpclient.commandes.QuitCmd;
import smtpclient.commandes.RcptToCmd;
import smtpclient.exceptions.NoDestException;
import smtpclient.responses.Response;

public class SMTPClient {
    private final int mPortServ;
    private final String mAdrServ;
    private final Message mMess;
    
    private List<String> mListDest;
    private final String domain; 
    private List<String> listDestError; //liste destinataire incorrect lors de l'envoi
        
    public SMTPClient(Message m, int port, String domain) {
        mMess = m;
        mListDest = new ArrayList<>();
        this.domain = domain;
        
        //Verification destinataire pour ce domaine 
        for(String dest : m.getTo())
            if (dest.endsWith("@"+domain))
                mListDest.add(dest);
       
        listDestError = new ArrayList<>();
        
        mPortServ = port;
        mAdrServ = domain;
    }
    
    public boolean deposer() throws UnknownHostException, IOException, Exception
    {
        InetAddress iServ = InetAddress.getByName(mAdrServ);

        System.out.println("Resolution adresse ok"); 

        Socket sock = new Socket(iServ, mPortServ);

        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        System.out.println("Buffer ok");
        
        Response rep = new Response(input.readLine());
        
        if (rep.getCode() == 220)
        { //REP 220 
            try {
                //HELO domain    
                HeloCmd helo = new HeloCmd("mv.com");
                helo.send(output);
                
                if (helo.traitementRep(input)) 
                { //REP 250
                    //MAIL FROM <@adrMailClient>
                    MailFromCmd mf = new MailFromCmd(mMess.getFrom());
                    mf.send(output);
                    
                    //REP 250
                    if (mf.traitementRep(input))
                    {
                        listDestError.clear();
                        int nbDestNotFound = 0;
                        RcptToCmd rcpt;
                        for(String mail : mListDest)
                        {
                            rcpt = new RcptToCmd(mail);
                            rcpt.send(output);
                            
                            if(!rcpt.traitementRep(input))
                            {
                                nbDestNotFound++;
                                listDestError.add(mail);
                            }
                        }
                        
                        if (nbDestNotFound < mListDest.size())
                        {
                            DataCmd datacmd = new DataCmd();
                            datacmd.send(output);
                                    
                            if (datacmd.traitementRep(input))
                            {
                                mMess.send(output);
                                
                                Response r = new Response(input.readLine());
                                if (r.getCode() == 250)
                                {
                                    System.out.println("Mess deposé domain <"+domain+">");
                                } else {
                                    throw new Exception("Erreur domain <"+domain+"> : Réponse invalide du serveur pour la commande DATA");
                                }                                
                            } else {
                                throw new Exception("Erreur domain <"+domain+"> : Réponse invalide du serveur pour la commande DATA");
                            }
                        }/* else {
                            throw new Exception("Erreur domain <"+domain+"> : Aucun destinaire n'a été trouvé sur le serveur.");
                        }  */                     
                    } else {
                        throw new Exception("Erreur domain <"+domain+"> : Mail from incorrect");
                    }
                    
                }
            } catch (Exception ex) {
                QuitCmd quit = new QuitCmd();

                quit.send(output);
                quit.traitementRep(input);

                throw ex; //propagé l'exception
            }
        
        } else {
            throw new Exception("Erreur: Serveur occupé");
        }
        
        input.close();
        output.close();
        sock.close(); //STATE deconnecté
        
        return true;
    }
    
    public List<String> getDestError() {
        return listDestError;
    }
    
    public List<String> getListDest() {
        return mListDest;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Elodie Elodie Elodie Mourier");
        
        Message m = new Message();
        m.setContent("Hello, how are you today ?");
        m.setFrom("buckbunny@carotte.com");
        m.addTo("adrien.castex@134.214.117.61");
        
        try {
            SMTPClient client = new SMTPClient(m, 1024, "134.214.117.61");
            client.deposer();
        } catch (Exception ex) {
            Logger.getLogger(SMTPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
