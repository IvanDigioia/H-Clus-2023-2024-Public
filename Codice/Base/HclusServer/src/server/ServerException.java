package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Eccezione controllata sollevata dal src.server e trasmessa al client tramite stream di connessione
 */
public class ServerException extends Exception{
    String result;

    /**
     * Costruttore dell'eccezione. Genera un'eccezione del src.server basata sull'eccezione originale e registra il messaggio di errore della classe di origine.
     * @param e l'eccezione originale da cui creare l'eccezione del src.server
     */
    ServerException(Exception e){super(createMessage(e));}

    /**
     * Restitusice il messaggio di errore della eccezione di origine, in base al tipo di eccezione.
     * @return stringa che contiene il messaggio di errore
     */
    private static String createMessage(Exception e){
        StringBuilder sb=new StringBuilder();
        if (e instanceof SQLException)
            sb.append("[!] Operazione SQL illegale: \n").append(e.getMessage());
        if (e instanceof FileNotFoundException)
            sb.append("[404]").append(e.getMessage());
        else
            sb.append(e.getMessage()); //tutte le altre eccezioni hanno già un messaggio costruito
        return sb.toString();
    }
}