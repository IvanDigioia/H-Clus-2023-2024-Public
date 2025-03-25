package clustering;

import data.*;

import java.io.Serializable;

/**
 * La classe Dendrogram rappresenta una struttura gerarchica di cluster, organizzata in livelli,
 * utilizzata per visualizzare la suddivisione dei dati in cluster a diverse profondità.
 */
public class Dendrogram implements Serializable {
    /** Array di ClusterSet che rappresenta i livelli del dendrogramma. */
    private ClusterSet tree[];

    /**
     * Costruttore della classe Dendrogram che inizializza il dendrogramma con una profondità specificata.
     *
     * @param depth la profondità del dendrogramma, che determina il numero di livelli di ClusterSet.
     */
    public Dendrogram(int depth){
        tree = new ClusterSet[depth];
    }

    /**
     * Imposta un ClusterSet al livello specificato del dendrogramma.
     *
     * @param c il ClusterSet da impostare.
     * @param level il livello del dendrogramma in cui inserire il ClusterSet.
     */
    public void setClusterSet(ClusterSet c, int level) {
        tree[level] = c;
    }

    /**
     * Restituisce il ClusterSet situato al livello specificato del dendrogramma.
     *
     * @param level il livello del dendrogramma da cui restituire il ClusterSet.
     * @return il ClusterSet al livello specificato.
     */
    public ClusterSet getClusterSet(int level) {
        return tree[level];
    }

    /**
     * Restituisce la profondità del dendrogramma, cioè il numero di livelli che contiene.
     *
     * @return la profondità del dendrogramma.
     */
    public int getDepth() {
        return tree.length;
    }

    /**
     * Restituisce una rappresentazione in formato stringa del dendrogramma, mostrando i cluster
     * a ogni livello.
     *
     * @return una stringa che rappresenta i cluster del dendrogramma a ogni livello.
     */
    public String toString() {
        String v = "";
        for (int i = 0; i < tree.length; i++)
            v += ("level" + i + ":\n" + tree[i] + "\n");
        return v;
    }

    /**
     * Restituisce una rappresentazione in formato stringa del dendrogramma utilizzando i dati forniti,
     * mostrando i cluster a ogni livello.
     *
     * @param data l'oggetto Data contenente gli esempi utilizzati per rappresentare gli elementi del cluster.
     * @return una stringa che rappresenta i cluster del dendrogramma a ogni livello secondo i dati forniti.
     */
    public String toString(Data data) {
        String v = "";
        for (int i = 0; i < tree.length; i++)
            v += ("level" + i + ":\n" + tree[i].toString(data) + "\n");
        return v;
    }

    /**
     * Restituisce l'array di ClusterSet che compone il dendrogramma.
     *
     * @return un array di ClusterSet che rappresenta i livelli del dendrogramma.
     */
    public ClusterSet[] getT() {
        return tree;
    }
}


