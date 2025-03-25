package database;

import data.*;

import exceptions.EmptySetException;
import exceptions.DatabaseConnectionException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe gestisce l'accesso e il recupero dei dati da una tabella di un database.
 * Utilizza un'istanza di {@link DbAccess} per eseguire query sul database.
 */
public class TableData {
    private DbAccess db;

    /**
     * Crea un'istanza di {@code TableData} con una connessione al database fornita.
     *
     * @param db l'oggetto {@link DbAccess} che gestisce la connessione al database.
     */
    public TableData(DbAccess db){ this.db=db; }

    /**
     * Recupera un elenco di transazioni distinte dalla tabella specificata nel database.
     *
     * @return una lista di oggetti {@link Example} che rappresentano le transazioni distinte.
     * @throws SQLException se si verifica un errore durante l'esecuzione della query.
     * @throws EmptySetException se il result set è vuoto e non contiene dati.
     * @throws DatabaseConnectionException se si verifica un errore di connessione al database.
     */
    public List<Example> getDistinctTransazioni() throws SQLException, EmptySetException, DatabaseConnectionException {
        List<Example> result = new ArrayList<>();
        String tableName = "exampletab"; // Nome fisso della tabella

        String query = "SELECT DISTINCT * FROM " + tableName;

        try (Statement s = db.getConnection().createStatement();
             ResultSet r = s.executeQuery(query)) {

            // Verifica se il ResultSet è vuoto
            if (!r.next()) {
                throw new EmptySetException();
            }

            // Elabora il ResultSet
            do {
                Example temp = new Example();
                temp.add(Double.valueOf(r.getInt(1))); // colonna X1
                temp.add(Double.valueOf(r.getInt(2))); // colonna X2
                temp.add(Double.valueOf(r.getInt(3))); // colonna X3
                result.add(temp);
            } while (r.next());

        } catch (SQLException e) {
            System.err.println("Errore durante l'esecuzione della query: " + e.getMessage());
            throw e; // Rilancia l'eccezione per la gestione a livello superiore
        }

        return result;
    }



}
