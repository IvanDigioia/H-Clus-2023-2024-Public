package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exceptions.DatabaseConnectionException;;

/**
 * Rappresenta lo schema di una tabella in un database.
 * Fornisce informazioni sulle colonne della tabella, inclusi i nomi e i tipi di dati.
 */
public class TableSchema {
	private DbAccess db;

	/**
	 * Rappresenta una colonna nello schema della tabella.
	 * Contiene il nome e il tipo della colonna.
	 */
	public class Column{
		private String name;
		private String type;

		/**
		 * Crea un'istanza di {@code Column} con il nome e il tipo specificati.
		 *
		 * @param name il nome della colonna.
		 * @param type il tipo di dati della colonna.
		 */
		Column(String name,String type){
			this.name=name;
			this.type=type;
		}
		/**
		 * Restituisce il nome della colonna.
		 *
		 * @return il nome della colonna.
		 */
		public String getColumnName(){
			return name;
		}

		/**
		 * Verifica se il tipo della colonna è numerico.
		 *
		 * @return {@code true} se il tipo è "number", {@code false} altrimenti.
		 */
		public boolean isNumber(){
			return type.equals("number");
		}

		/**
		 * Restituisce una rappresentazione in formato stringa della colonna.
		 *
		 * @return una stringa che rappresenta il nome e il tipo della colonna.
		 */
		public String toString(){
			return name+":"+type;
		}
	}
	List<Column> tableSchema=new ArrayList<Column>();

	/**
	 * Crea un'istanza di {@code TableSchema} per una tabella specificata.
	 *
	 * @param db l'oggetto {@link DbAccess} che gestisce la connessione al database.
	 * @param tableName il nome della tabella di cui recuperare lo schema.
	 * @throws SQLException se si verifica un errore durante l'interazione con il database.
	 * @throws DatabaseConnectionException se si verifica un errore di connessione al database.
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException, DatabaseConnectionException{
		this.db=db;
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		
		
	
		 Connection con=db.getConnection();
		 DatabaseMetaData meta = con.getMetaData();
	     ResultSet res = meta.getColumns(null, null, tableName, null);
		   
	     while (res.next()) {
	         
	         if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
	        		 tableSchema.add(new Column(
	        				 res.getString("COLUMN_NAME"),
	        				 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        				 );
	         
		 }
	      res.close();

	}

		/**
	 	* Restituisce il numero di colonne nell schema della tabella.
	 	*
	 	* @return il numero di colonne.
	 	*/
		public int getNumberOfAttributes(){
			return tableSchema.size();
		}

		/**
		 * Restituisce la colonna all'indice specificato nello schema della tabella.
	 	*
	 	* @param index l'indice della colonna.
	 	* @return la colonna all'indice specificato.
	 	*/
		public Column getColumn(int index){
			return tableSchema.get(index);
		}

		
}

		     


