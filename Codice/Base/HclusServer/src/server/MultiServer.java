package server;

import java.io.IOException;
import java.lang.*;

import java.net.*;

/**
 * Classe Main. Si occupa di avviare il programma e creare la socket per l'ascolto del client
 */
public class MultiServer {
    private static int port=8080; //porta del src.server
    //volendo potremmo anche aggiungere un attributo per localhost, ma non è richiesto
    /**
     * Istanzia un oggetto di tipo MultiServer
     * @param args argomenti in input
     */
    public static void main(String[] args){
        MultiServer multiServer=new MultiServer(port);
    }

    /**
     * Costruttore di classe. Inizializza la porta e invoca run()
     * @param port porta su cui si mette in ascolto il src.server
     */
    public MultiServer(int port) {
        if(port<1024){
            System.out.println("WARNING:you are using an already used (well-known) port. there could be issues from now on");
            this.port=port;
        }else if (port==0){
            System.out.println("Uso la porta standard 8080");
        }else
            this.port=port;
        try {
            run();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Avvia il src.server con una porta standard 8080 e su indirizzo localhost
     * @throws IOException in caso di errori all'apertura della socket
     */
    private void run() throws IOException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        ServerSocket s = new ServerSocket(port);
        System.out.println("Started: " + s);
        try {
            while (true) {
                // Si blocca finché non si verifica una connessione:
                System.out.println("Client Connected: Connection started");
                Socket socket = s.accept();
                try {
                    new ServerOneClient(socket);
                } catch (IOException e) {
                    // Se fallisce chiude il socket,
                    // altrimenti il thread la chiuderà:
                    socket.close();
                }
            }
        } finally {
            s.close();
        }
    }
}