package exceptions;
/**
 * Eccezione lanciata quando un numero richiesto è mancante o non valido.
 */
public class MissingNumberException extends Exception{
    /**
     * Costruisce un'eccezione di numero mancante con un messaggio specifico.
     *
     * @param message il messaggio di errore associato all'eccezione.
     */
    public MissingNumberException(String message) {
        super(message);
    }
}
