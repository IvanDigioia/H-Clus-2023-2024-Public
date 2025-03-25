package distance;

import clustering.*;
import data.*;
import exceptions.InvalidDepthException;

import java.io.*;

/**
 * Implementa un miner per il clustering gerarchico.
 * Gestisce la creazione e la manipolazione di un dendrogramma per il clustering.
 */
public class HierachicalClusterMiner implements Serializable {
	
	private Dendrogram dendrogram;

	/**
	 * Costruisce un oggetto {@link HierachicalClusterMiner} con un dendrogramma di una certa profondità.
	 *
	 * @param depth la profondità del dendrogramma.
	 */
	public HierachicalClusterMiner(int depth) {
		dendrogram= new Dendrogram(depth);
	}

	/**
	 * Esegue il mining dei dati per costruire un dendrogramma di clustering gerarchico.
	 *
	 * @param data i dati utilizzati per il clustering.
	 * @param distance la funzione di distanza tra cluster.
	 * @return null.
	 * @throws InvalidDepthException se la profondità del dendrogramma è maggiore del numero di esempi.
	 */
	public Object mine(Data data, ClusterDistance distance) throws InvalidDepthException{
		if (dendrogram.getDepth() > data.getNumberOfExample()) throw new InvalidDepthException("Invalid depth exception.");
		
		ClusterSet level0 = new ClusterSet(data.getNumberOfExample());
		for (int i = 0; i < data.getNumberOfExample(); i++){
			Cluster c = new Cluster();
			c.addData(i);
			level0.add(c);
		}
		dendrogram.setClusterSet(level0, 0);

		for (int level = 1; level < dendrogram.getDepth(); level++){
			dendrogram.setClusterSet(dendrogram.getClusterSet(level - 1).mergeClosestClusters(distance, data), level);
		}
		return null;
	}

	/**
	 * Restituisce una rappresentazione del dendrogramma come stringa.
	 *
	 * @return la rappresentazione del dendrogramma come stringa.
	 */
	public String toString() {
		return dendrogram.toString();
	}

	/**
	 * Restituisce una rappresentazione del dendrogramma come stringa utilizzando i dati forniti.
	 *
	 * @param data i dati utilizzati per la rappresentazione.
	 * @return la rappresentazione del dendrogramma come stringa.
	 */
	public String toString(Data data) {
		return dendrogram.toString(data);
	}

	/**
	 * Costruttore che carica un oggetto {@link HierachicalClusterMiner} da un file di serializzazione.
	 *
	 * @param fileName il nome del file da cui caricare l'oggetto.
	 * @throws FileNotFoundException se il file non viene trovato.
	 * @throws IOException se si verifica un errore di I/O durante la lettura del file.
	 * @throws ClassNotFoundException se la classe dell'oggetto serializzato non viene trovata.
	 */
	public HierachicalClusterMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		// Logica per inizializzare l'oggetto utilizzando il file
		loadHierachicalClusterMiner(fileName);
	}

	/**
	 * Carica un oggetto {@link HierachicalClusterMiner} da un file di serializzazione.
	 *
	 * @param fileName il nome del file da cui caricare l'oggetto.
	 * @return null.
	 * @throws FileNotFoundException se il file non viene trovato.
	 * @throws IOException se si verifica un errore di I/O durante la lettura del file.
	 * @throws ClassNotFoundException se la classe dell'oggetto serializzato non viene trovata.
	 */
	public HierachicalClusterMiner loadHierachicalClusterMiner(String fileName)
			throws FileNotFoundException, IOException, ClassNotFoundException {

		String currentDirectory = System.getProperty("user.dir");
		String targetDir = "H-Clus-23-24";
		int endIndex = currentDirectory.lastIndexOf(targetDir) + targetDir.length();
		String desiredDir = currentDirectory.substring(0, endIndex);
		String folderPath = desiredDir + File.separator + "File memorizzati";
		File folder = new File(folderPath);

		// Aggiungi il nome del file al percorso della cartella
		fileName = folder + File.separator + fileName;

		// Inizializza FileInputStream e ObjectInputStream all'interno di un blocco try-with-resources
		try (FileInputStream fileInput = new FileInputStream(fileName);
			 ObjectInputStream inputStream = new ObjectInputStream(fileInput)) {

			// Leggi l'oggetto Dendrogram dal file
			dendrogram = (Dendrogram) inputStream.readObject();
		} catch (FileNotFoundException e) {
			// Gestisci FileNotFoundException in modo specifico
			System.err.println("Errore: File non trovato: " + fileName);
			throw e;
		} catch (IOException e) {
			System.err.println("Errore: Si è verificato un errore di I/O durante la lettura del file: " + fileName);
			throw e;
		} catch (ClassNotFoundException e) {
			System.err.println("Errore: Impossibile trovare la classe di un oggetto serializzato.");
			throw e;
		}
		return null;
	}

	/**
	 * Salva l'oggetto {@link HierachicalClusterMiner} in un file di serializzazione.
	 *
	 * @param fileName il nome del file in cui salvare l'oggetto.
	 * @throws FileNotFoundException se il percorso del file specificato non può essere trovato o creato.
	 * @throws IOException se si verifica un errore di I/O durante la scrittura sul file.
	 */
	public void salva(String fileName) throws FileNotFoundException, IOException {

		String currentDirectory = System.getProperty("user.dir");
		String targetDir = "H-Clus-23-24";
		int endIndex = currentDirectory.lastIndexOf(targetDir) + targetDir.length();
		String desiredDir = currentDirectory.substring(0, endIndex);
		String folderPath = desiredDir + File.separator + "File memorizzati";
		File folder = new File(folderPath);

		fileName = folder + File.separator + fileName;

		try (FileOutputStream fileOutput = new FileOutputStream(fileName);
			 ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput)) {

			outputStream.writeObject(dendrogram);
		} catch (FileNotFoundException e) {
			System.err.println("Errore: Il percorso del file specificato non è stato trovato o non può essere creato:" + fileName);
			throw e;
		} catch (IOException e) {
			System.err.println("Errore: Si è verificato un errore di I/O durante la scrittura sul file:" + fileName);
			throw e;
		}
	}



}
