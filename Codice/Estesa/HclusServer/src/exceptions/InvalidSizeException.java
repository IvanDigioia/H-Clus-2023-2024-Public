package exceptions;
/**
 * Eccezione lanciata quando si verifica un errore relativo alla dimensione degli esempi durante il calcolo della distanza.
 */
public class InvalidSizeException extends Exception {
    /**
     * Costruisce un'eccezione di dimensione non valida con un messaggio specifico.
     *
     * @param message il messaggio di errore associato all'eccezione.
     */
    public InvalidSizeException(String message) {
        super(message);
    }
}