/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmultithread;

/**
 * Classe vera e propria del server, gestisce le richieste, le porte e i clienti
 * connessi attraverso i thread
 * @author dany
 */
import java.io.*;
import java.net.*;
import java.util.*;
public class Server {
    ServerSocket socket_server = null;
    HashMap<String, Cliente> sockets_client = null;
    Vector<ListenerServer> listener = null;
    Cliente cliente_in_entrata = null;
    
    String messaggio_client = null;
    String risposta_server = null;
    /**
     * metodo di attesa della connessione del server
     */
    public void connetti(){
        try {
            /**
             * startaggio e monitoraggio delle risorse 
             */
            System.out.println("Server in esecuzione.");
            socket_server = new ServerSocket(3030);
            sockets_client = new HashMap();
            listener = new Vector();
        }catch (Exception e) {
            System.out.println("Errore nella connessione al server.");
        }
        while(true){
            try{
                /**
                 * operazione di attesa di richiesta del client
                 */
                System.out.println("Server in attesa del client.");
                cliente_in_entrata = new Cliente();
                //socket_client.add(new Cliente());
                cliente_in_entrata.setClient(socket_server.accept());
                /**
                 * Costruzione dei parametri di comunicazione col client
                 */
                System.out.println("Cliente in costruzione");
                cliente_in_entrata.setDati_client(
                    new BufferedReader(new InputStreamReader(cliente_in_entrata.getClient().getInputStream())), 
                    new DataOutputStream(cliente_in_entrata.getClient().getOutputStream()));
                /**
                 * Controllo dell'univocità
                 */
                System.out.println("Ultimi passaggi di costruzione");
                messaggio_client = cliente_in_entrata.dati_dal_client.readLine();
                String[] s = messaggio_client.split(",");
                System.out.println("1" + s[0]);
                cliente_in_entrata.superCostruttor(s[0], s[1], cliente_in_entrata.getClient().getInetAddress().toString());
                System.out.println("2");
                int prima = sockets_client.size();
                sockets_client.put(s[0], cliente_in_entrata);
                if(prima == sockets_client.size()){
                    System.out.println("Nome gia adoperato");
                    cliente_in_entrata.dati_al_client.writeBytes("@ATTENZIONE: il tuo nome e' gia stato usato" + '\n');
                    throw new Exception();
                }else{
                    System.out.println("Client accettato.");
                    /**
                     * aggiunta del client all'hashmap dei client
                     */
                    System.out.println("Ultimazione...aggiunta del nuovo cliente nella lista");
                    aggiungiUtente(s[0]);
                    System.out.println("Client connesso");
                    System.out.println(); 
                }
            }catch(Exception e){
                System.out.println("@Errore nella connessione del nuovo client");
                System.out.println();     
            }
        }
    }
    /**
     * aggiorna l'hashmap di tutti gli utenti in caso si aggiunga un nuovo utente 
     * @param clienti 
     */
    private void aggiornaUtenti(HashMap<String, Cliente> clienti){
            for(int i = 0; i < listener.size(); i++)
                listener.elementAt(i).reGetUtenti(clienti); 
    }
    /**
     * aggiungi un nuovo thread alla lista dei thread degli utenti
     * @param nomedelcliente 
     */
    private void aggiungiUtente(String nomedelcliente){
        listener.add(new ListenerServer(sockets_client, nomedelcliente));
        aggiornaUtenti(sockets_client);
        listener.elementAt(listener.size()-1).start();
    }
}
