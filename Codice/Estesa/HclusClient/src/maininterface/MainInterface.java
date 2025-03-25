package maininterface;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import connessione.Connessione;

import java.io.IOException;
import java.util.List;

/**
 * Classe che avvia il controllore delle scene.
 */
public class MainInterface extends Application{

    /**
     * Metodo main per l'avvio dell'applicazione.
     * @param args Argomenti da passare alla connessione [IP_server] [porta_server]
     */
    public static void main(String[] args) {
        launch(args); //lancia applicazione
    }

    /**
     * Metodo per l'avvio della prima finestra dell'applicazione.
     * @param primaryStage Lo stage iniziale dell'applicazione.
     * @throws Exception Eccezione che può essere sollevata durante l'avvio dell'applicazione.
     */
    @Override
    public void start (Stage primaryStage) throws Exception {
        Parameters params=getParameters();
        List<String> listparam=params.getRaw(); //estrapoliamo gli argomenti in input alla lista
        try{
            Connessione c;
            if(listparam.size()==2)
                c=new Connessione(listparam.get(0),Integer.parseInt(listparam.get(1)));
            else c=new Connessione();

            Controller cs =new Controller(c);
            cs.scenaUno(primaryStage);
            primaryStage.setOnCloseRequest(windowEvent -> {
                try {
                    c.disconnect();
                } catch (IOException e) {
                    System.out.println("Error while closing socket");
                }
            });

        }catch(IOException e){
            System.out.println(e.getMessage());
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server not found");
            alert.setContentText("Verifica che il server sia stato già avviato e riapri l'applicazione");
            alert.showAndWait();
        }
    }
}
