/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatmultithread;

import java.io.*;
import java.net.*;

/**
 * "L'orecchio del client" il quale identifica le informazioni inviate dal 
 * server e le rimanda alla classe d'interfaccia principale
 * @author dany
 */
public class ListenerClient extends Thread{
    BufferedReader ricevi;
    SchermataClient display;
    String risposta;
    /**
     * Costruttore di Listener usato per captare i messaggi in arrivo
     * @param ricezione come ricettore di messaggi
     * @param display  per scrivere i messaggi su schermo
     */
    public ListenerClient(BufferedReader ricezione, SchermataClient display){
        ricevi = ricezione;
        this.display = display;
    }
    /**
     * Adoperando un thread si avrà la possibilità di scrivere mentre si aspettano messaggi
     */
    public void run(){
        while(true){
            try{
                risposta = ricevi.readLine();
            }catch(Exception e){
                display.MessaggioinArrivo(" @ messaggio illeggibile");
            }
            display.MessaggioinArrivo(risposta);
        }
    }
}
