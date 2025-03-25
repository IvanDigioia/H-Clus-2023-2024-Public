package maininterface;

import connessione.Connessione;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe controller per gestire le interfacce utente dell'applicazione.
 */
public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private static Connessione conn;

    /**
     * Metodo che permette di visualizzare la prima schermata dell'interfaccia
     * @param event click del bottone "torna indietro"
     * @throws IOException Eccezione sollevata se si verifica un errore durante il caricamento della schermata.
     */
    void scenaUno(ActionEvent event) throws IOException{
        root=FXMLLoader.load(getClass().getResource("/resources/Scenauno.fxml")); //carica prima scena
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root); //prima scena
        stage.setResizable(true);
        scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm()); //carica file css
        stage.setTitle("H-clus GUI");
        stage.setScene(scene); //imposta scena
        stage.show(); //mostra scena
        scene.getRoot().requestFocus();
    }

    /**
     * Metodo per visualizzare la prima schermata dell'interfaccia all'avvio del programma.
     * @param primaryStage Lo stage iniziale dell'applicazione.
     * @throws IOException Eccezione sollevata se si verifica un errore durante il caricamento della schermata.
     */
    void scenaUno(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Scenauno.fxml")); //caricare scena principale
        root= fxmlLoader.load();
        ScenaUno controllerScenaUno=fxmlLoader.<ScenaUno>getController();
        controllerScenaUno.setConnessione(conn); //per mandare la connessione dentro ScenaUno
        scene= new Scene(root);
        stage=primaryStage;
        stage.setResizable(true);
        stage.setTitle("H-clus GUI");
        stage.setScene(scene); //impostare la scena attuale
        scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm()); //caricare il css
        stage.show();
    }

    /**
     * Metodo che permette di visualizzare la seconda schermata dell'interfaccia.
     * @param event click del bottone "Carica dati".
     * @throws IOException Eccezione sollevata se si verifica un errore durante il caricamento della schermata.
     */
    void scenaCarica(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Scenacarica.fxml")); //carica scena per salvare
        root=fxmlLoader.load();
        ScenaCarica controllerScenaCarica=fxmlLoader.<ScenaCarica>getController();
        controllerScenaCarica.setConnessione(conn); //per mandare la connessione dentro ScenaCarica
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(true);
        scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm()); //carica file css
        stage.setScene(scene); //imposta scena
        stage.show(); //mostra scena
        scene.getRoot().requestFocus();
    }


    /**
     * Metodo che permette di visualizzare la seconda schermata dell'interfaccia.
     * @param event click del bottone "Carica dati da file".
     * @throws IOException Eccezione sollevata se si verifica un errore durante il caricamento della schermata.
     */
    void scenaCaricaFile(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/Scenacaricafile.fxml")); //carica scena carica file
        root=fxmlLoader.load();
        ScenaCaricaFile controllerScenaCaricaFile=fxmlLoader.<ScenaCaricaFile>getController();
        controllerScenaCaricaFile.setConnessione(conn); //per mandare la connessione dentro ScenaCaricaFile
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(true);
        scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm()); //carica file css
        stage.setScene(scene); //imposta scena
        stage.show(); //mostra scena
        scene.getRoot().requestFocus();
    }

    /**
     * Costruttore per il controller.
     * @param conn La connessione utilizzata per comunicare con il server.
     */
    public Controller(Connessione conn) {this.conn=conn;}

}
