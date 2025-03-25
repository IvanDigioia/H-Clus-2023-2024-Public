package clustering;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

import data.*;


/**
 * Classe che gestisce il cluster
 */
public class Cluster implements Iterable<Integer>, Cloneable, Serializable {

	private Set<Integer> clusteredData=new TreeSet<>();

	//aggiungere l'indice di un campione al cluster

	/**
	 * Aggiunge un nuovo elemento al cluster.
	 *
	 * @param id l'identificatore dell'elemento da aggiungere al cluster.
	 */
	public void addData(int id){
		clusteredData.add(id);	
	}

	/**
	 * Restituisce la dimensione del cluster, ovvero il numero di elementi in esso contenuti.
	 *
	 * @return il numero di elementi presenti nel cluster.
	 */
	public Iterator<Integer> iterator(){
		return clusteredData.iterator();
	}
	
	public int getSize() {
		return clusteredData.size();
	}

	/**
	 * Crea e restituisce una copia del cluster corrente.
	 *
	 * @return un nuovo oggetto Cluster che è una copia dell'oggetto corrente.
	 */
	public Object Clone()
	{
		Cluster c2 = new Cluster();
		c2.clusteredData = new TreeSet<>(this.clusteredData);
		return c2;
	}

	/**
	 * Crea un nuovo cluster che è la fusione del cluster corrente con un altro cluster specificato.
	 *
	 * @param c il cluster da unire con il cluster corrente.
	 * @return un nuovo Cluster risultante dalla fusione dei due cluster.
	 */
	Cluster mergeCluster (Cluster c)
	{
		Cluster newC=new Cluster();
		for (var It = clusteredData.iterator(); It.hasNext();){
			newC.addData(It.next());
		}
		for (var It = c.clusteredData.iterator(); It.hasNext();){
			newC.addData(It.next());
		}
		return newC;
		
	}

	/**
	 * Restituisce una rappresentazione in formato stringa del cluster corrente.
	 *
	 * @return una stringa contenente tutti gli elementi del cluster separati da una virgola.
	 */
	public String toString()
	{		
		String str="";
		for (var It = clusteredData.iterator(); It.hasNext(); ){
			str += It.next() + ",";
		}
		return str;	
	}

	/**
	 * Restituisce una rappresentazione in formato stringa del cluster corrente utilizzando i dati forniti.
	 *
	 * @param data l'oggetto Data che contiene gli esempi utilizzati per rappresentare gli elementi del cluster.
	 * @return una stringa con gli elementi del cluster rappresentati secondo i dati forniti.
	 */
	String toString(Data data){
		String str="";
		for (var It = clusteredData.iterator(); It.hasNext();){
			str += "<" + data.getExample(It.next()) + ">";
		}
		return str;
		
	}
	


}
