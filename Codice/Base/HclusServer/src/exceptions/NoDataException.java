package exceptions;
/**
 * Eccezione lanciata quando non ci sono dati disponibili per un'operazione.
 */
public class NoDataException extends Exception{
    /**
     * Costruisce un'eccezione di assenza di dati con un messaggio specifico.
     *
     * @param message il messaggio di errore associato all'eccezione.
     */
    public NoDataException(String message) {
        super(message);
    }
}
