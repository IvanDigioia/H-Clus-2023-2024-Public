package clientmain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import keyboardinput.Keyboard;

/**
 * La classe {@code MainTest} è un client che si connette a un server tramite socket e interagisce con esso
 * per caricare e gestire un dendrogramma. Fornisce un'interfaccia utente testuale per selezionare e
 * eseguire diverse operazioni, tra cui il caricamento di un dendrogramma da un file e l'apprendimento
 * di un dendrogramma da un database.
 *
 * Le principali operazioni supportate sono:
 * Caricare un dendrogramma da un file esistente.
 * Apprendere un dendrogramma da un database e salvarlo su file.
 *
 * La connessione al server avviene utilizzando un socket e due stream di input/output per la comunicazione.
 *
 */
public class MainTest {

	/**
	 * @param args
	 */
	private ObjectOutputStream out;
	private ObjectInputStream in; // stream con richieste del client

	/**
	 * Costruttore della classe {@code MainTest}.
	 * Inizializza una connessione socket al server specificato dall'indirizzo IP e dalla porta forniti.
	 *
	 * @param ip L'indirizzo IP del server a cui connettersi.
	 * @param port La porta del server a cui connettersi.
	 * @throws IOException Se si verifica un errore durante la creazione della connessione socket o degli stream.
	 */
public MainTest(String ip, int port) throws IOException {
		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port); //Port
		System.out.println(socket);

		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream()); // stream con richieste del client
	}

	/**
	 * Mostra un menu per scegliere un'opzione tra il caricamento di un dendrogramma da file o l'apprendimento
	 * di un dendrogramma da un database.
	 *
	 * @return L'opzione selezionata dall'utente.
	 */
	private int menu() {
		int answer;
		System.out.println("Scegli una opzione");
		do {
			System.out.println("(1) Carica Dendrogramma da File");
			System.out.println("(2) Apprendi Dendrogramma da Database");
			System.out.print("Risposta:");
			answer = Keyboard.readInt();
		} while (answer <= 0 || answer > 2);
		return answer;
	}


	/*
	* MENU PER MAIN CICLICO
	* */
	/*
	private int menu() {
    	int answer;
    	System.out.println("Scegli una opzione");
    	do {
        	System.out.println("(1) Carica Dendrogramma da File");
        	System.out.println("(2) Apprendi Dendrogramma da Database");
        	System.out.println("(3) Chiudi programma")
        	System.out.print("Risposta:");
        	answer = Keyboard.readInt();
        	if (answer == Integer.MIN_VALUE) {
        	    System.out.println("Errore: input non valido, per favore inserisci un numero intero.");
        	}
    	} while (answer <= 0 || answer > 3);
    	return answer;
	}

	*/

	/**
	 * Invia il nome della tabella al server e gestisce la risposta. Se la tabella esiste, carica i dati dal server.
	 *
	 * @throws IOException Se si verifica un errore durante la comunicazione con il server.
	 * @throws ClassNotFoundException Se il tipo dell'oggetto ricevuto dal server non può essere trovato.
	 */
	private void loadDataOnServer() throws IOException, ClassNotFoundException {
		boolean flag = false;
		do {
			System.out.println("Nome tabella:");
			String tableName = Keyboard.readString();
			out.writeObject(0);
			out.writeObject(tableName);
			String risposta = (String) (in.readObject());
			if (risposta.equals("OK"))
				flag = true;
			else System.out.println(risposta);

		} while (flag == false);
	}

	/**
	 * Carica un dendrogramma salvato su file specificato dal server e stampa la rappresentazione del dendrogramma.
	 *
	 * @throws IOException Se si verifica un errore durante la comunicazione con il server.
	 * @throws ClassNotFoundException Se il tipo dell'oggetto ricevuto dal server non può essere trovato.
	 */
	private void loadDedrogramFromFileOnServer() throws IOException, ClassNotFoundException {
		out.writeObject(2);
		System.out.println("Inserire il nome dell'archivio (comprensivo di estensione):");
		String fileName = Keyboard.readString();
		out.writeObject(fileName);
		String risposta = (String) (in.readObject());
		if (risposta.equals("OK"))
			System.out.println(in.readObject()); // stampo il dendrogramma che il server mi sta inviando
		else
			System.out.println(risposta); // stampo il messaggio di errore
	}

	/**
	 * Esegue il clustering gerarchico sul server utilizzando i dati e le impostazioni specificati dall'utente.
	 * Il dendrogramma risultante viene salvato su un file specificato.
	 *
	 * @throws IOException Se si verifica un errore durante la comunicazione con il server.
	 * @throws ClassNotFoundException Se il tipo dell'oggetto ricevuto dal server non può essere trovato.
	 */
	private void mineDedrogramOnServer() throws IOException, ClassNotFoundException {
		out.writeObject(1); //mando 1

		System.out.println("Introdurre la profondita' del dendrogramma");
		int depth = Keyboard.readInt();
		out.writeObject(depth); //mando la profondità

		int dType = -1;
		do {
			System.out.println("Distanza: single-link (1), average-link (2):");
			dType = Keyboard.readInt();
		} while (dType <= 0 || dType > 2);
		out.writeObject(dType); //mando il link

		String risposta = (String) (in.readObject()); //ricevo l'OK
		if (risposta.equals("OK")) {
			System.out.println(in.readObject()); // stampo il dendrogramma che il server mi sta inviando
			System.out.println("Inserire il nome dell'archivio (comprensivo di estensione):");
			String fileName = Keyboard.readString();
			out.writeObject(fileName);
		} else
			System.out.println(risposta); // stampo il messaggio di errore
	}

	/**
	 * Punto di ingresso principale per l'applicazione. Stabilisce una connessione al server e gestisce
	 * le operazioni di caricamento e clustering del dendrogramma in base alla selezione dell'utente.
	 *
	 * @param args Argomenti della linea di comando: ip e porta del server.
	 */
	public static void main(String[] args) {
		String ip=args[0];
		int port = Integer.valueOf(args[1]).intValue();
		MainTest main=null;
		try{
			main=new MainTest(ip,port);

			main.loadDataOnServer();
			int scelta=main.menu();
			if(scelta==1)
				main.loadDedrogramFromFileOnServer();
			else
				main.mineDedrogramOnServer();
		}
		catch (IOException |ClassNotFoundException  e){
			System.out.println(e);
			return;
		}
	}

	/*
	* VERSIONE ALTERNATIVA DEL MAIN: Questo è un main ciclico,
	* il main passato nel laboratorio 5 si conclude con la scelta tra il caricamento via file o database,
	* non sapendo se mi è concesso modificare il main ne faccio uan versione alternativa qui.
	* Per usare questo main bisogna rimuovere dal commento il menu() con la terza opzione e commentare il main sopra
	* L'unico caso di uscita nel main ciclico è inserire 3 alla prima scelta.
	* */
	/*
	public static void main(String[] args) {
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		MainTest main = null;

		try {
			main = new MainTest(ip, port);
			main.loadDataOnServer();

			int scelta;
			do {
				scelta = main.menu();
				if (scelta == 1) {
					main.loadDedrogramFromFileOnServer();
				} else if (scelta == 2) {
					main.mineDedrogramOnServer();
				}
			} while (scelta != 3);

		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e);
		}
		return;
	}
	*/
}


