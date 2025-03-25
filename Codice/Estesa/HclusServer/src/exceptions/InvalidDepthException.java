package exceptions;
/**
 * Eccezione lanciata quando viene fornita una profondità non valida durante l'elaborazione di clustering gerarchico.
 */
public class InvalidDepthException extends Exception {
    /**
     * Costruisce un'eccezione di profondità non valida con un messaggio specifico.
     *
     * @param message il messaggio di errore associato all'eccezione.
     */
    public InvalidDepthException(String message) {
        super(message);
    }
}