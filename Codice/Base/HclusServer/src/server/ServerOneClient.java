package server;

import java.io.*;
import java.net.*;

import clustering.ClusterDistance;
import data.*;
import distance.*;

/**
 * Classe che gestisce le connessioni con i client
 */
public class ServerOneClient extends Thread {
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    HierachicalClusterMiner dendrogram;

    //metodi

    /**
     * Costruttore di classe. Inizializza gli attributi socket, in e out. Avvia il thread.
     *
     * @param s socket su cui il src.server riceve la connessione
     * @throws IOException in caso di errori con l'invio/ricezione di messaggi
     */
    public ServerOneClient(Socket s) throws IOException {
        this.socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream()); //come da manuale dalla prof, da vedere se funzionano davvero
        start();
    }

    /**
     * Esegue il ciclo principale del thread, gestendo le richieste e le operazioni
     * provenienti dal client attraverso il socket.
     * Gestisce diverse opzioni in base ai comandi ricevuti, tra cui:
     * Opzione 0: Carica i dati da una tabella specificata e verifica la loro esistenza.
     * Opzione 1: Esegue il clustering gerarchico sui dati utilizzando il tipo di distanza specificato (Single Link o Average Link).
     * Opzione 2: Carica e visualizza la struttura del dendrogramma salvato precedentemente.
     * <p>
     * Gestisce le eccezioni di I/O e le eccezioni specifiche del server, inviando messaggi di errore al client
     * e chiudendo la connessione quando necessario.
     */
    @Override
    public void run() {
        ClusterDistance distance;
        int option = 0;
        String fileName = null;
        int k = 0;
        int link = 0;
        Data data;

        // Flag per interrompere il loop in modo sicuro
        boolean running = true;

        while (running && !socket.isClosed()) {
            try {
                option = (int) in.readObject();
            } catch (IOException e) {
                System.out.println("Client disconnected: " + e.getMessage());
                running = false; // Interrompe il loop in caso di disconnessione del client
                continue;
            } catch (ClassNotFoundException e) {
                try {
                    out.writeObject(e.getMessage());
                } catch (IOException e1) {
                    System.out.println("IOException while sending error message.");
                }
            }

            switch (option) {
                case 0:
                    try {
                        fileName = (String) in.readObject();
                        if (!fileName.equals("exampletab")) {
                            throw new IOException("[404] La tabella " + fileName + " non esiste");
                        }
                        data = new Data(fileName);
                        out.writeObject("OK");
                    } catch (ClassNotFoundException e) {
                        try {
                            out.writeObject(e.getMessage());
                        } catch (IOException e1) {
                            System.out.println("IOException");
                        }
                    } catch (Exception e) {
                        try {
                            throw new ServerException(e);
                        } catch (ServerException se) {
                            try {
                                out.writeObject(se.getMessage());
                            } catch (IOException e1) {
                                System.out.println("IOException while sending ServerException message.");
                            }
                        }
                    }
                    break;
                case 1:
                    try {
                        k = (int) in.readObject();
                        data = new Data(fileName);
                        this.dendrogram = new HierachicalClusterMiner(k);
                        link = (int) in.readObject();

                        if (link == 1) {
                            distance = new SingleLinkDistance();
                        } else if (link == 2) {
                            distance = new AverageLinkDistance();
                        } else {
                            throw new IllegalArgumentException("Errore: il tipo di link deve essere 1 (Single) o 2 (Average).");
                        }

                        dendrogram.mine(data, distance);
                        out.writeObject("OK");
                        String tempString = dendrogram.toString(data);
                        out.writeObject(tempString);
                        fileName = (String) in.readObject();
                        dendrogram.salva(fileName);
                        socket.close();

                    } catch (Exception e) {
                        try {
                            throw new ServerException(e);
                        } catch (ServerException se) {
                            try {
                                out.writeObject(se.getMessage());
                            } catch (IOException e1) {
                                System.out.println("IOException while sending ServerException message.");
                            }
                        }
                    }
                    break;
                case 2:
                    try {
                        fileName = (String) in.readObject();
                        data = new Data(fileName);
                        out.writeObject("OK");
                        this.dendrogram = new HierachicalClusterMiner(fileName);
                        String tempString = dendrogram.toString(data);
                        out.writeObject(tempString);
                        socket.close();
                    } catch (IOException | ClassNotFoundException e) {
                        try {
                            throw new ServerException(e);
                        } catch (ServerException se) {
                            try {
                                out.writeObject(se.getMessage());
                            } catch (IOException e1) {
                                System.out.println("IOException while sending ServerException message.");
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }

        // Chiudi il socket alla fine del ciclo
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing socket: " + e.getMessage());
        }
    }
}


