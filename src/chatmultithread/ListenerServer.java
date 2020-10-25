/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmultithread;


import java.util.*;
/**
 * IL LISTENER SERVER VIENE ADOPERATO COME SINGOLO ORECCHIO/IMBUTO DAL 
 * SERVER. OGNI SINGOLA PORTA VERSO IL CLIENTE è ISTANZIATA COME SINGOLO THREAD
 * @author Dany
 */
public class ListenerServer extends Thread{
    private String risposta;                                                         //messaggio dal client
    private HashMap<String, Cliente> clienti;                                        //Vettore di tutti i client
    private HashMap<String, Cliente> altriclienti = new HashMap();                   //Vettore di tutti i client meno il cliente alla quale appartiene questo listener
    private String nome;                                                             //nome del client di questo Listener
    
    /**
     * Costruttore di classe dell'ascoltatore
     * @param clienti
     * @param nome 
     */      
    public ListenerServer(HashMap<String, Cliente> clienti, String nome){
        this.nome = nome;
        this.clienti = clienti;
        reGetUtenti(clienti);
    }
    /**
     * Viene utilizzato anche nel costruttore per pulire il codice ma ha la 
     * funzione di aggiornare l'Hashmap di questa classe
     * @param clienti nuovo Hashmap aggiornato
     * riporta alla classe un hashmap privo dell'elemento alla quale questa
     * classe appartiene
     */
    public void reGetUtenti(HashMap<String, Cliente> clienti){ 
        altriclienti.putAll(clienti);
        altriclienti.remove(nome);
    }
    /**
     * "Ascolta" se ci sono nuovi messaggi da questo utente e, in caso, li 
     * invia a tutti gli "altriclienti"
     * 
     */
    public void run(){  
        while(true){
            try{
                risposta = clienti.get(nome).getDati_dal_client().readLine();
                for(String s : altriclienti.keySet()){
                    altriclienti.get(s).getDati_al_client().writeBytes((clienti.get(nome).getNome()+ ": " + risposta) + '\n');
                }
            }catch(Exception e){
                System.out.println("@ messaggio illeggibile");
            }
        }
    }
}
