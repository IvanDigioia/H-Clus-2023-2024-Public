package data;

import java.util.*;

import exceptions.InvalidSizeException;

/**
 * Rappresenta un esempio con una serie di attributi numerici.
 * La classe implementa l'interfaccia {@link Iterable} per consentire l'iterazione sugli attributi.
 */
public class Example implements Iterable<Double> {
    private List<Double> example;

    /**
     * Crea un nuovo esempio vuoto.
     * Inizializza la lista degli attributi come una {@link LinkedList}.
     */
    public Example() {
        example = new LinkedList<>();
    }

    /**
     * Restituisce un iteratore sui valori dell'esempio.
     *
     * @return un iteratore per i valori numerici dell'esempio.
     */
    public Iterator<Double> iterator() {
        return example.iterator();
    }

    /**
     * Restituisce il numero di attributi dell'esempio.
     *
     * @return il numero di attributi.
     */
    public int getLength() {
        return example.size();
    }

    /**
     * Aggiunge un valore numerico all'esempio.
     *
     * @param v il valore da aggiungere.
     */
    public void add(Double v) {
        example.add(v);
    }

    /**
     * Restituisce il valore dell'attributo all'indice specificato.
     *
     * @param index l'indice dell'attributo.
     * @return il valore dell'attributo.
     */
    Double get(int index) {
        return example.get(index);
    }

    /**
     * Calcola la distanza euclidea tra questo esempio e un altro esempio.
     *
     * @param newE l'altro esempio con cui calcolare la distanza.
     * @return la distanza euclidea tra i due esempi.
     * @throws InvalidSizeException se le dimensioni degli esempi non corrispondono.
     */
    public double distance(Example newE) throws InvalidSizeException {
        if (this.getLength() != newE.getLength())
            throw new InvalidSizeException("Example size mismatch.");

        double distance = 0;
        for (Iterator<Double> It1 = this.iterator(), It2 = newE.iterator(); It1.hasNext() && It2.hasNext(); ){
            distance += Math.pow(It1.next() - It2.next(), 2);
        }
        
        return distance;
    }

    /**
     * Restituisce una rappresentazione in formato stringa dell'esempio.
     *
     * @return una stringa che rappresenta i valori dell'esempio.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Double value : example) {
            str.append(value.toString()).append(" ");
        }
        return str.toString().trim();
    }


}