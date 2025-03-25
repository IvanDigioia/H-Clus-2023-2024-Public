package server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import clustering.ClusterDistance;
import data.*;
import distance.*;
import exceptions.DatabaseConnectionException;
import exceptions.EmptySetException;
import exceptions.MissingNumberException;
import exceptions.NoDataException;

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
     * @param s socket su cui il src.server riceve la connessione
     * @throws IOException in caso di errori con l'invio/ricezione di messaggi
     */
    public ServerOneClient(Socket s) throws IOException{
        this.socket=s;
        in=new ObjectInputStream(socket.getInputStream());
        out=new ObjectOutputStream(socket.getOutputStream()); //come da manuale dalla prof, da vedere se funzionano davvero
        start();
    }

    /**
     * Esegue il ciclo principale del thread, gestendo le richieste e le operazioni
     * provenienti dal client attraverso il socket.
     * Gestisce diverse opzioni in base ai comandi ricevuti, tra cui:
     * Opzione 0: Carica i dati da una tabella specificata e verifica la loro esistenza.
     * Opzione 1: Esegue il clustering gerarchico sui dati utilizzando il tipo di distanza specificato (Single Link o Average Link).
     * Opzione 2: Carica e visualizza la struttura del dendrogramma salvato precedentemente.
     *
     * Gestisce le eccezioni di I/O e le eccezioni specifiche del server, inviando messaggi di errore al client
     * e chiudendo la connessione quando necessario.
     */
    @Override
    public void run(){
        ClusterDistance distance;
        int option=4;
        String fileName=null;
        int k=0;
        int link=0;
        Data data;
        while(!socket.isClosed()){
            try{
                option=(int) in.readObject();
            }catch(IOException e){ //brutto
                try{
                    out.writeObject(e.getMessage());
                }catch(IOException e1){
                    option=3; //lo manda alla chiusura della socket
                }
            }catch(ClassNotFoundException e){
                try{
                    out.writeObject(e.getMessage());
                }catch(IOException e1){
                    System.out.println("IOException");
                }
            }
            switch(option) {
                case 0:
                    try {
                        fileName = (String) in.readObject();
                        if (!fileName.equals("exampletab")) //controllo molto arificiale, sarebbe da controllare come espellere l'errore da src.data()
                            throw new IOException("[404] La tabella " + fileName + " non esiste");
                        data = new Data(fileName); //gusto per espellere l'errore e controllare se la tabella esiste
                        //controlli per vedere se esiste!!
                        out.writeObject("OK");
                    } catch (ClassNotFoundException e) {
                        try {
                            out.writeObject(e.getMessage());
                        } catch (IOException e1) {
                            System.out.println("IOException");
                        }
                    } catch (
                            Exception e) { //raccoglie: SQLException,IOException, DatabaseConnectionException, EmptySetException
                        try {
                            throw new ServerException(e);
                        } catch (ServerException se) {
                            try {
                                out.writeObject(se.getMessage());
                            } catch (IOException e1) {
                            }
                        }
                    }

                    break;
                case 1: //ricevo 1
                    try {
                        fileName = (String) in.readObject();

                        k = (int) in.readObject(); //ricevo la profondità

                        this.dendrogram = new HierachicalClusterMiner(k);

                        link = (int) in.readObject(); //ricevo il single o averrage link distance
                        if (link == 1) {
                            distance = new SingleLinkDistance();
                        } else {
                            distance = new AverageLinkDistance();
                        }

                        data = new Data(fileName);
                        dendrogram.mine(data, distance);

                        out.writeObject("OK");

                        String tempString=dendrogram.toString(data);
                        out.writeObject(tempString);

                        dendrogram.salva(fileName);

                    } catch (ClassNotFoundException e) {
                        try {
                            out.writeObject(e.getMessage());
                        } catch (IOException e1) {
                            System.out.println("IOException");
                        }
                    } catch (
                            Exception e) { //raccogle: SQLException,IOException, DatabaseConnectionException, EmptySetException, OutOfRangeSampleSize
                        try {
                            throw new ServerException(e);
                        } catch (ServerException se) {
                            try {
                                out.writeObject(se.getMessage());
                            } catch (IOException e1) {
                            }
                        }
                    }
                    break;
                case 2:
                    try {
                        fileName = (String) in.readObject();
                        data=new Data(fileName);
                        this.dendrogram = new HierachicalClusterMiner(fileName);
                        String tempString = dendrogram.toString(data);
                        out.writeObject("OK");
                        out.writeObject(tempString);

                    } catch (IOException | ClassNotFoundException e) {
                        try {
                            throw new ServerException(e);
                        } catch (ServerException se) {
                            try {
                                out.writeObject(se.getMessage());
                            } catch (IOException e1) {
                            }
                        }
                    } catch (DatabaseConnectionException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (EmptySetException e) {
                        throw new RuntimeException(e);
                    } catch (MissingNumberException e) {
                        throw new RuntimeException(e);
                    } catch (NoDataException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    try{
                        socket.close();
                    }catch(IOException e){
                        System.out.println("Errore durante la chiusura della socket. Forzatura chiusura...");
                    }//non ho un finally perchè da specifiche non posso espellere l'eccezione fuori dalla funzione
                    break;
            }

        }

    }

}