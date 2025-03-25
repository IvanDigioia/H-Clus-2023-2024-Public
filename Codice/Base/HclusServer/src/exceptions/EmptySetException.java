package exceptions;
/**
 * Eccezione lanciata quando si tenta di eseguire un'operazione su un dataset vuoto.
 */
public class EmptySetException extends Exception{

    /**
     * Costruisce un'eccezione che indica che il dataset è vuoto.
     * Il messaggio predefinito dell'eccezione è "[!] DataSet vuoto: Operazione illegale su un dataset vuoto".
     */
    public EmptySetException() {
        super("[!] DataSet vuoto: Operazione illegale su un dataset vuoto");
    }
}
