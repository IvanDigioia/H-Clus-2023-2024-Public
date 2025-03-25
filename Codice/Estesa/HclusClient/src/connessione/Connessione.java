package connessione;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Classe per gestire la connessione tra il client e il server.
 */
public class Connessione {
    private static Socket socket;
    private static String falloverIp="127.0.0.1"; //localhost
    private static int falloverPort=8080; //porta standard
    private static ObjectOutputStream out; // stream con output del server
    private static ObjectInputStream in ; // stream con richieste del client

    /**
     * Metodo per inizializzare la classe connessione con valori standard.
     * @throws IOException Se si verifica un errore durante l'inizializzazione della connessione.
     */
    public Connessione() throws IOException{
        InetAddress addr = InetAddress.getByName(falloverIp); //ip
        socket = new Socket(addr, falloverPort); //Port
        out = new ObjectOutputStream(socket.getOutputStream()); //stream con output del server
        in = new ObjectInputStream(socket.getInputStream()); //stream con richieste del client
    }

    /**
     * Inizializza la classe Connessione con l'indirizzo IP e la porta specificati.
     * @param ip L'indirizzo IP del server.
     * @param port La porta del server.
     * @throws IOException Se si verifica un errore durante l'inizializzazione della connessione.
     */
    public Connessione(String ip, int port) throws IOException{
        InetAddress addr = InetAddress.getByName(ip);
        socket = new Socket(addr,port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Metodo per restituire la connessione del client.
     * @return la connessione di input al server
     */
    public static ObjectInputStream getConnectionInput(){
        return in;
    }

    /**
     * Metodo per restituire la connessione del server.
     * @return la connessione di output al client
     */
    public static ObjectOutputStream getConnectionOutput(){
        return out;
    }

    /**
     * Chiude il socket di connessione.
     * @throws IOException Se si verifica un errore durante la chiusura del socket.
     */
    public void disconnect() throws IOException {
        if(socket.isConnected())
            socket.close();
    }
}
