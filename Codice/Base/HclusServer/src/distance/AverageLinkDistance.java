package distance;

import clustering.*;
import data.*;
import exceptions.InvalidSizeException;

/**
 * Implementa una misura di distanza basata sul metodo dell'average link distance tra due cluster.
 * Calcola la distanza media tra tutti i possibili accoppiamenti di esempi tra due cluster.
 */
public class AverageLinkDistance implements ClusterDistance{

	/**
	 * Calcola la distanza media tra due cluster basata sulla distanza euclidea degli esempi che li compongono.
	 *
	 * @param c1 il primo cluster.
	 * @param c2 il secondo cluster.
	 * @param d l'oggetto {@link Data} contenente gli esempi utilizzati per calcolare la distanza.
	 * @return la distanza media tra i due cluster.
	 */
    public double distance(Cluster c1, Cluster c2, Data d) {
        double avg = 0;
		
		for (var It1 = c1.iterator(); It1.hasNext();){
			Example e1 = d.getExample(It1.next());
			for (var It2 = c2.iterator(); It2.hasNext();){
				try{
					double distance= e1.distance(d.getExample(It2.next()));
					avg += distance;
				}
				catch (InvalidSizeException e){
					System.err.println(e.getMessage());
				}
			}
		}

		return avg / (c1.getSize() * c2.getSize());
    }
    
}
