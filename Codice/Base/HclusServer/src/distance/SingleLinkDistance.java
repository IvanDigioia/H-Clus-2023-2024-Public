package distance;

import clustering.*;
import data.*;
import exceptions.InvalidSizeException;

/**
 * Implementa la distanza tra cluster utilizzando il metodo Single Linkage.
 * In questo metodo, la distanza tra due cluster è definita come la distanza minima tra qualsiasi coppia di elementi,
 * ciascuno proveniente da un cluster diverso.
 */
public class SingleLinkDistance implements ClusterDistance {

	/**
	 * Calcola la distanza tra due cluster utilizzando il metodo Single Linkage.
	 *
	 * @param c1 il primo cluster.
	 * @param c2 il secondo cluster.
	 * @param d i dati utilizzati per calcolare le distanze tra esempi.
	 * @return la distanza minima tra i due cluster, calcolata come la distanza minima tra tutti i possibili accoppiamenti
	 *         di esempi nei due cluster.
	 */
	public double distance(Cluster c1, Cluster c2, Data d) {
		
		double min=Double.MAX_VALUE;
		
		for (var It1 = c1.iterator(); It1.hasNext();){
			Example e1 = d.getExample(It1.next());
			for (var It2 = c2.iterator(); It2.hasNext();){
				try{
					double distance= e1.distance(d.getExample(It2.next()));
					if (distance < min)
						min = distance;
				}
				catch (InvalidSizeException e){
					System.err.println(e.getMessage());
				}
			}
		}

		return min;
	}
}
