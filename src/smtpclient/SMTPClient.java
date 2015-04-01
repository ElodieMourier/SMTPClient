 package smtpclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import smtpclient.exceptions.NoDestException;

public class SMTPClient {
    private final int mPortServ;
    private final String mAdrServ;
    
    private List<String> mListDest;
        
    public SMTPClient(Message m, int port, String domain) throws NoDestException {
        //Verification destinataire pour ce domaine 
        for(String dest : m.getTo())
            if (dest.endsWith("@"+domain))
                mListDest.add(dest);
        
        if (mListDest.isEmpty())
            throw new NoDestException(); //Aucun destinataire pour ce domaine
        
        mPortServ = port;
        mAdrServ = domain;
    }
    
    public void deposer() throws UnknownHostException, IOException
    {
        InetAddress iServ = InetAddress.getByName(mAdrServ);

        System.out.println("Resolution adresse ok"); 

        Socket sock = new Socket(iServ, mPortServ);

        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        System.out.println("Buffer ok");
        
        //REP 220 
        //HELO domain    
        
        //REP 250
        //MAIL FROM <@adrMailClient>
        
        //REP 250
        
        //Boucle RECPT TO <@destMail>
        //Si REP 250 ok sinon 550 mettre dans tableau user not found
        
        //DATA
        //REP 354            
        
        //REP 250
        //QUIT
        
        //REP 221
        //close()
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Elodie Elodie Elodie Mourier");
    }
}
