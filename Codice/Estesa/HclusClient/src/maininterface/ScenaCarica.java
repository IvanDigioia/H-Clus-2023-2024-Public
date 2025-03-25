package maininterface;

import server.ServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.io.*;

import connessione.Connessione;

/**
 * Classe controller per gestire la schermata di caricamento dei dati da database.
 */
public class ScenaCarica{

    private static Connessione conn;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;


    @FXML
    private Button EseguiSalva;

    @FXML
    private Button Indietro;

    @FXML
    private TextField SA;

    @FXML
    private TextField Profondita;

    @FXML
    private TextField Nomefile;

    @FXML
    private TextArea risultati;

    @FXML
    private Button Pulisci;

    /**
     * Metodo l'esecuzione del programma dedicato al Carica tramite gli input dell'utente.
     * @param event click del bottone "Esegui".
     */
    @FXML
    void Esegui(ActionEvent event) {
        try{
            int link = Integer.parseInt(this.SA.getText()); //ottengo il tipo di link
            int P = Integer.parseInt(this.Profondita.getText()); //ottengo la prodondità
            String file = this.Nomefile.getText(); //ottengo il nome del file

            in=conn.getConnectionInput(); //ottengo la connessione per il client
            out=conn.getConnectionOutput(); //ottengo la connessione per il server

            out.writeObject(1); //entro nell'opzione 1

            out.writeObject(file); //mando il nome del file

            out.writeObject(P); //mando la profondità

            if (link == 1 || link == 2){ //mando il tipo di link
                out.writeObject(link);
            } else {
                throw new IllegalArgumentException("Valore di link non valido. Inserisci 1 o 2.");
            }

            Object res=in.readObject();

            String stringResult=null;
            if (res instanceof String)
                stringResult=(String) res;
            else if (res instanceof ServerException)
                throw (ServerException) res;
            if (stringResult.equals("OK")){
                risultati.setText("");
                risultati.appendText("Output: \n" + (String) in.readObject()); //mostra a video il risultato
            }

        }catch(IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Problema dal server");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }catch(ClassNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oops");
            alert.setHeaderText("Classe non trovata");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch(ServerException e){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(e.getOriginClass());
            alert.setHeaderText("Messaggio di errore dal server");
            alert.setContentText(e.getMessage());
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); //aggiunsta la grandezza della schemata in base al messaggio
            alert.showAndWait();
        }
        catch(NumberFormatException e){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Valori inseriti non conformi");
            alert.setContentText("Assicurati di aver inserito dei valori corretti");
            alert.showAndWait();
        }
        catch (IllegalArgumentException e) { // Gestisce l'errore per valori di link non validi
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore di Input");
        alert.setHeaderText("Valore di link non valido");
        alert.setContentText(e.getMessage());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
        }
    }

    /**
     * Metodo per pulire il pannello dei risultati.
     * @param event click del bottone "Pulisci pannello".
     */
    @FXML
    void pulisciPannello(ActionEvent event){
        this.risultati.setText(""); //sostituisce tutto il testo del pannello degli output con un carattere vuoto
    }

    /**
     * Metodo ritornare alla scena principale ovvero 'Scena1'.
     * @param event click del bottone "Indietro".
     */
    @FXML
    void tornaScena(ActionEvent event) {
        Controller cs=new Controller(conn);
        try{
            cs.scenaUno(event);
        }catch(IOException e){
            System.out.println("[!] Error while loading javafx scene: "+e.getMessage());
        }
    }

    /**
     * Metodo per passare la connessione alla classe attuale.
     * @param conn la connessione da impostare.
     */
    void setConnessione(Connessione conn){this.conn=conn;}

}
