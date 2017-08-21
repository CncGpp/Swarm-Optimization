package strategy;

import model.map.AMap;
/**
 * La funzione da chiamare al momento della prima inizializzazione del ferormone
 * */
public interface PheromoneInitializer {
	public void initialize(final AMap map);
}
