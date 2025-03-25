package exceptions;
/**
 * Eccezione lanciata quando si verifica un errore di connessione al database.
 */
public class DatabaseConnectionException extends Exception{
    /**
     * Costruisce un'eccezione di connessione al database con un messaggio specifico.
     *
     * @param message il messaggio di errore associato all'eccezione.
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }

}
