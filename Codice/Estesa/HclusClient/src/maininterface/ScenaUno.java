package maininterface;

import connessione.Connessione;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Classe rappresentante la prima schermata dell'applicazione
 */
public class ScenaUno {

    @FXML
    private Button CaricaDati;

    @FXML
    private Button CaricaFile;

    @FXML
    private Button Chiudi;

    private static Connessione conn;

    /**
     * Metodo avviare la scena 'ScenaCarica'.
     * @param event click del bottone "Carica dati".
     */
    @FXML
    void AvviaScenaCaricaDati(ActionEvent event) { //avvia la scena per caricare e salvare dei dati
        Controller cs=new Controller(conn);
        try{
            cs.scenaCarica(event);
        }catch(IOException e){
            System.out.println("[!] Error while loading javafx scene: "+e.getMessage());
        }
    }

    /**
     * Metodo avviare la scena 'ScenaCaricaFile'.
     * @param event click del bottone "Carica dati da file".
     */
    @FXML
    void AvviaScenaCaricaFile(ActionEvent event) { //avvia la scena per caricare dei dati da file
        Controller cs=new Controller(conn);
        try{
            cs.scenaCaricaFile(event);
        }catch(IOException e){
            System.out.println("[!] Error while loading javafx scene: "+e.getMessage());
        }
    }

    /**
     * Metodo chiude l'applicazione.
     * @param event click del bottone "Chiudi applicazione".
     */
    @FXML
    void Exit(ActionEvent event) {
        try{
            conn.disconnect();
        }catch (IOException e){ //non fa nulla, siamo chiudendo l'app
            System.out.println("Error");
        }
        Platform.exit();
    } //chiudi applicazione


    /**
     * Metodo per passare la connessione alla classe attuale.
     * @param conn la connessione da impostare.
     */
    void setConnessione(Connessione conn){this.conn=conn;}


}