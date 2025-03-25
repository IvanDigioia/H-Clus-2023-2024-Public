package clustering;

import data.*;

import java.io.Serializable;

/**
 * La classe ClusterSet rappresenta un insieme di cluster, consentendo operazioni come
 * l'aggiunta di cluster, la fusione dei cluster più vicini e la rappresentazione dei cluster come stringa.
 */
public class ClusterSet implements Serializable {

	/** Array di cluster che compongono il ClusterSet. */
	private Cluster C[];
	/** Indice dell'ultimo cluster aggiunto nel ClusterSet. */
	private int lastClusterIndex=0;

	/**
	 * Costruttore della classe ClusterSet che inizializza un insieme di cluster con una dimensione specificata.
	 *
	 * @param k il numero di cluster che il ClusterSet deve contenere.
	 */
	public ClusterSet(int k){
		C=new Cluster[k];
	}

	/**
	 * Aggiunge un cluster al ClusterSet se non è già presente, per evitare duplicati.
	 *
	 * @param c il cluster da aggiungere al ClusterSet.
	 */
	public void add(Cluster c){
		for(int j=0;j<lastClusterIndex;j++)
			if(c==C[j]) // to avoid duplicates
				return;
		C[lastClusterIndex]=c;
		lastClusterIndex++;
	}

	/**
	 * Restituisce il cluster situato all'indice specificato nel ClusterSet.
	 *
	 * @param i l'indice del cluster da restituire.
	 * @return il cluster all'indice specificato.
	 */
	Cluster get(int i){
		return C[i];
	}


	/**
	 * Esegue la fusione dei due cluster più vicini nel ClusterSet in base alla distanza specificata
	 * e restituisce un nuovo ClusterSet aggiornato.
	 *
	 * @param distance l'oggetto ClusterDistance utilizzato per calcolare la distanza tra i cluster.
	 * @param data l'oggetto Data contenente i dati necessari per il calcolo della distanza.
	 * @return un nuovo ClusterSet risultante dalla fusione dei due cluster più vicini.
	 */
	public ClusterSet mergeClosestClusters(ClusterDistance distance, Data data){
		if (C.length == 1) return this;

		double min=Double.MAX_VALUE;
		int lastI = 0, lastJ = 0;

		for (int i = 0; i < C.length; i++) for ( int j = 0; j < C.length; j++){
			if (i>=j) continue;
			double d = distance.distance(this.get(i), this.get(j), data);
			if (d < min){
				min = d;
				lastI = i;
				lastJ = j;
			}
		}

		Cluster merged = this.get(lastI).mergeCluster(this.get(lastJ));
		ClusterSet newClusterSet = new ClusterSet(C.length - 1);
		for (int i = 0; i < C.length; i++){
			if (i == lastI){
				newClusterSet.add(merged);
			}
			else if (i != lastI && i != lastJ){
				newClusterSet.add(this.get(i));
			}
		}

		return newClusterSet;
	}

	/**
	 * Restituisce una rappresentazione in formato stringa del ClusterSet.
	 *
	 * @return una stringa che rappresenta tutti i cluster contenuti nel ClusterSet.
	 */
	public String toString(){
		String str="";
		for(int i=0;i<C.length;i++){
			if (C[i]!=null){
				str+="cluster"+i+":"+C[i]+"\n";
		
			}
		}
		return str;
		
	}

	/**
	 * Restituisce una rappresentazione in formato stringa del ClusterSet utilizzando i dati forniti.
	 *
	 * @param data l'oggetto Data contenente gli esempi utilizzati per rappresentare gli elementi del cluster.
	 * @return una stringa con i cluster rappresentati secondo i dati forniti.
	 */
	String toString(Data data){
		String str="";
		for(int i=0;i<C.length;i++){
			if (C[i]!=null){
				str+="cluster"+i+":"+C[i].toString(data)+"\n";
		
			}
		}
		return str;
		
	}

}
