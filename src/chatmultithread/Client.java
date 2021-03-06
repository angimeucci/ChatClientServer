/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmultithread;
import java.io.*;
import java.util.*;
import java.net.*;
/**
 * Classe scheletro del cliente che consente la connessione e la scrittura del messaggio
 * @author Dany
 */
public class Client extends Utente{
    Scanner scanner = new Scanner(System.in);
    ListenerClient listen;
    SchermataClient display;
    
    String ip_server = "127.0.0.1";
    int dr_server = 3030;
    Socket socket;
    DataOutputStream invia;
    BufferedReader ricevi;
    /**
     * Usata dal main ChatMultiThread.java solo per far accedere alla schermata ad alcune componenti di cliente
     */
    public Client(){}
    /**
     * Costruttore della superclasse astratta utente
     * @param nome 
     * @param info 
     */
    public Client(String nome, String info){super.DeterminaUtente(nome,info);}
    /**
     * Metodo di connessione al server con invio delle credenziali utente per la certificazione di una connessione univoca
     * @return socket da inviare alla classe Listener
     */
    public Socket Connetti(){
        try {
            socket=new Socket(ip_server,dr_server);
            invia = new DataOutputStream(socket.getOutputStream());
            ricevi = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            try{
                String s = (super.getNome()+","+super.getInfo()+'\n');
                invia.writeBytes(s);
            }catch(Exception e){
                display.MessaggioinArrivo("@ Errore nell'invio delle informazioni personali");
                System.exit(1);}
        }
        
        catch(UnknownHostException e){display.MessaggioinArrivo("@Server: Host non riconosciuto.");}
        catch (Exception e) {display.MessaggioinArrivo("@Server: Errore durante la connessione.");}
        return socket;
    }
    /**
     * Metodo di comunicazione che collega le due attivita di lettura e scrittura
     */
    public void Comunica(SchermataClient display){
        this.display = display; 
        listen = new ListenerClient(ricevi, display);
        listen.start();
    }   
    /**
     * Metodo di scrittura dentro la classe Client per inviare dati al server, chiamato dalla classe display
     */
    public boolean Scrivi(String mex){
        String messaggio;
            try{
                invia.writeBytes(mex + '\n');
                return true;
                
            }catch(Exception e){
                display.MessaggioinArrivo("@Server: Errore nell'invio del messaggio");
                return false;
            }
    }
    
}
