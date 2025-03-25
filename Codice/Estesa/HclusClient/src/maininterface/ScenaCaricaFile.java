package maininterface;

import server.ServerException;
import connessione.Connessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.awt.*;
import java.io.File;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Controller per la schermata di caricamento da file.
 */
public class ScenaCaricaFile {

    private static Connessione conn;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    @FXML
    private Button Esegui;

    @FXML
    private Button Esplora;

    @FXML
    private Button Indietro;

    @FXML
    private TextField NomeFile;

    @FXML
    private TextArea risultati;

    @FXML
    private Button Pulisci;

    /**
     * Metodo l'esecuzione del programma dedicato al Carica da file tramite gli input dell'utente.
     * @param event click del bottone "Esegui".
     */
    @FXML
    void EseguiFile(ActionEvent event) {
        try {
            String file = this.NomeFile.getText(); // Ottengo il nome del file
            in = conn.getConnectionInput(); // Ottengo la connessione per il client
            out = conn.getConnectionOutput(); // Ottengo la connessione per il server
            out.writeObject(2); // Per riferimento, funzione MainTest.storeFromFile()
            out.writeObject(file);
            String resultString = null;
            Object res = in.readObject(); // Restituisce OK se la lettura da file è andata a buon fine
            if (res instanceof String) {
                resultString = (String) res;
            } else if (res instanceof ServerException) {
                throw (ServerException) res;
            }

            if (!"OK".equals(resultString)) {
                this.risultati.setText("");

                // Aggiungi l'alert per segnalare che il file non è stato trovato
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("File Non Trovato");
                alert.setHeaderText("Errore di File");
                alert.setContentText("Il file '" + file + "' non è stato trovato. Controlla la cartella 'File memorizzati' per verificare la sua esistenza.");
                alert.showAndWait();

            } else {
                this.risultati.setText("");
                risultati.appendText("File Output:\n" + (String) in.readObject()); // Mostra a video il risultato
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Problema dal server");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } catch (ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oops");
            alert.setHeaderText("Classe non trovata");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        } catch (ServerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(e.getOriginClass());
            alert.setHeaderText("Messaggio di errore dal server");
            alert.setContentText(e.getMessage());
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // Aggiusta la grandezza della schermata in base al messaggio
            alert.showAndWait();
        }
    }


    /**
     * Metodo l'esplorazione della cartella 'File memorizzati'.
     * @param event click del bottone "Esplora cartella".
     */
    @FXML
    void esploraCartella(ActionEvent event) { //funzione di apertura della cartella 'File memorizzati'
        String currentDirectory = System.getProperty("user.dir"); //ottengo la directory dell'utente
        String targetDir = "H-Clus-23-24"; //arrivo fino alla cartella 'H-Clus-23-24' e mi fermo
        //questa operazione prende il percorso della directory corrente, individua l'ultima occorrenza di una specifica directory
        int endIndex = currentDirectory.lastIndexOf(targetDir) + targetDir.length();
        //all'interno di endIndex restituiscono una nuova stringa (desiredDir) contenente tutto il percorso fino alla fine di questa directory
        String desiredDir = currentDirectory.substring(0, endIndex);
        String folderPath = desiredDir + File.separator + "File memorizzati"; //entro nella cartella 'File memorizzati'
        File folder = new File(folderPath);
        try {
            Desktop.getDesktop().open(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo la pulizia del pannello dei risultati.
     * @param event click del bottone "Pulisci pannello".
     */
    @FXML
    void pulisciPannello(ActionEvent event){
        this.risultati.setText("");
    } //sostituisce tutto il testo del pannello degli output con un carattere vuoto

    /**
     * Metodo ritornare alla scena principale ovvero 'Scenauno'.
     * @param event click del bottone "Indietro".
     */
    @FXML
    void tornaScena(ActionEvent event) {
        Controller cs=new Controller(conn);
        try{
            cs.scenaUno(event);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo per passare la connessione alla classe attuale.
     * @param conn la connessione da impostare.
     */
    void setConnessione(Connessione conn){this.conn=conn;}

}