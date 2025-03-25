package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exceptions.DatabaseConnectionException;

/**
 * Gestisce l'accesso al DB per la lettura dei dati di training
 * @author Map Tutor
 *
 */
public class DbAccess {

	private String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private  String DBMS = "jdbc:mysql";
    private  String SERVER = "localhost";
    private  String DATABASE = "MapDB";
    private  String PORT = "3306";
    private  String USER_ID = "MapUser";
    private  String PASSWORD = "map";

    private Connection conn;

    /**
     * Inizializza la connessione al src.database.
     *
     * @throws DatabaseConnectionException Eccezione lanciata se la connessione al src.database fallisce.
     */
    public void initConnection() throws DatabaseConnectionException
    {
        try {
            Class.forName(DRIVER_CLASS_NAME); //crea eccezione qui
        } catch(ClassNotFoundException e) {
            System.out.println("[!] Driver not found: " + e.getMessage());
            throw new DatabaseConnectionException(e.toString());
        }
        String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch(SQLException e) {

            throw new DatabaseConnectionException(e.toString());
        }
    }

    /**
     * Restituisce la connessione al src.database.
     *
     * @return Connessione al src.database.
     * @throws DatabaseConnectionException Eccezione lanciata se la connessione al src.database fallisce.
     */
    public Connection getConnection() throws DatabaseConnectionException{
        this.initConnection();
        return conn;
    }

    /**
     * Chiude la connessione al src.database.
     *
     * @throws SQLException Eccezione lanciata se si verifica un errore durante la chiusura della connessione.
     */
    public void closeConnection() throws SQLException {
        conn.close();
    }

}
