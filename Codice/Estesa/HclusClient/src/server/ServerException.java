package server;

import java.io.Serializable;

/**
 * Eccezione controllata sollevata dal server e trasmessa al client tramite stream di connessione
 */
public class ServerException extends Exception implements Serializable {

    private String result;
    private String originClass;

    /**
     * Costruttore che inizializza l'eccezione con un messaggio specifico, e memorizza la classe d'origine dell'errore
     * @param e
     */
    ServerException(Exception e){
        super(e.getMessage());
        originClass=e.getClass().getName();
    }

    /**
     * Restitusice il nome dell'eccezione lanciata
     * @return
     */
    public String getOriginClass(){
        return originClass;
    }
}