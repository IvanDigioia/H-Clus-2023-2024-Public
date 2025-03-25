package data;

import database.*;
import exceptions.*;

import java.sql.SQLException;
import java.util.*;

/**
 * La classe Data rappresenta un insieme di dati provenienti da una tabella di un database.
 * Gestisce il caricamento degli esempi e il calcolo delle distanze tra essi.
 */
public class Data {
	/** Lista di esempi (righe) che compongono i dati caricati. */
	List<Example> data = new ArrayList<>();
	/** Numero di esempi presenti nei dati. */
	int numberOfExamples;

	/** Mappa di attributi che descrivono le caratteristiche dei dati. */
	Map<String, Boolean> attributeMap;

	/** Schema della tabella */
	TableSchema tableSchema;

	/**
	 * Costruttore della classe Data che inizializza i dati caricandoli da una tabella specificata nel database.
	 *
	 * @param tableName il nome della tabella da cui caricare i dati.
	 * @throws NoDataException se non ci sono dati disponibili nella tabella specificata.
	 * @throws DatabaseConnectionException se si verifica un errore nella connessione al database.
	 * @throws SQLException se si verifica un errore durante l'interazione con il database.
	 * @throws EmptySetException se la tabella specificata è vuota.
	 * @throws MissingNumberException se manca un numero necessario durante il caricamento dei dati.
	 */
	public Data(String tableName) throws NoDataException, DatabaseConnectionException, SQLException, EmptySetException, MissingNumberException {
		attributeMap = new HashMap<>();
		DbAccess dbaccess = new DbAccess(); // Inizializza l'oggetto DbAccess

		try {
			dbaccess.initConnection(); // Apertura connessione al database

			// Crea l'istanza di TableSchema per la tabella specificata
			tableSchema = new TableSchema(dbaccess, tableName);

			// Popola attributeMap con gli attributi della tabella
			for (int i = 0; i < tableSchema.getNumberOfAttributes(); i++) {
				TableSchema.Column column = tableSchema.getColumn(i);
				// Usa Map per rappresentare il nome e il tipo dell'attributo
				attributeMap.put(column.getColumnName(), column.isNumber());
			}

			TableData dbcrawler = new TableData(dbaccess);
			ArrayList<Example> examplelist = new ArrayList<>(dbcrawler.getDistinctTransazioni());

			// Popola il set di dati con gli esempi ottenuti
			data.addAll(examplelist);

		} catch (SQLException e) {
			System.err.println("Errore SQL: " + e.getMessage());
			e.printStackTrace();
			throw new SQLException("Errore durante l'accesso al database", e);
		} catch (DatabaseConnectionException e) {
			System.err.println("Errore di connessione al database: " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (EmptySetException e) {
			System.err.println("Errore: Set vuoto trovato per la tabella: " + tableName);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("Errore generico durante l'inizializzazione dei dati: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Errore imprevisto durante l'inizializzazione dei dati", e);
		} finally {
			// Assicura la chiusura della connessione indipendentemente dal risultato
			try {
				dbaccess.closeConnection();
			} catch (SQLException e) {
				System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Restituisce il numero di esempi presenti nei dati.
	 *
	 * @return il numero di esempi.
	 */
	public int getNumberOfExample() {
		return data.size();
	}

	/**
	 * Restituisce un esempio specifico dato il suo indice.
	 *
	 * @param exampleIndex l'indice dell'esempio da restituire.
	 * @return l'esempio situato all'indice specificato.
	 */
	public Example getExample(int exampleIndex) {
		return data.get(exampleIndex);
	}

	/**
	 * Calcola la matrice delle distanze tra tutti gli esempi nei dati.
	 * Le distanze sono calcolate solo per la parte superiore della matrice.
	 *
	 * @return una matrice bidimensionale di double che rappresenta le distanze tra gli esempi.
	 */
	public double[][] distance() {

		double matrix[][] = new double[this.getNumberOfExample()][this.getNumberOfExample()];

		for (int i = 0; i < this.getNumberOfExample(); i++)
			for (int j = 0; j < this.getNumberOfExample(); j++) {
				try {
					double distance = this.getExample(i).distance(this.getExample(j));
					matrix[i][j] = i >= j ? 0 : distance;
				} catch (InvalidSizeException e) {
					System.err.println(e.getMessage());
				}
			}
		return matrix;
	}

	/**
	 * Restituisce una rappresentazione in formato stringa dei dati, mostrando tutti gli esempi.
	 *
	 * @return una stringa che rappresenta tutti gli esempi nei dati.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (var it = data.iterator(); it.hasNext(); ){
			sb.append(it.next()).append(" \n");
		}
		return sb.toString();
	}
}
