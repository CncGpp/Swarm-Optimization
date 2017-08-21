package model.map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * La classe {@code Pheromone} modella il ferormone presente ad una certa posizione della mappa.
 */
public class Pheromone extends Rectangle{

	/** Il valore del ferormone */
	private DoubleProperty pheromoneValue = new SimpleDoubleProperty(0);

	/**
	 * istanzia un nuovo Phromone
	 * @param tileSize la dimensione del tile della mappa
	 */
	public Pheromone(final double tileSize) {
		super(tileSize,tileSize);

		pheromoneValue.addListener(
				(observer, oldValue, newValue) -> { this.setOpacity(pheromoneValue.get());}
		);

		this.setOpacity(pheromoneValue.get());
		this.setFill(Color.RED);
	}


	/* 								+-----------------------------------------------------+
	 * 								|        METODI DI MANIPOLAZIONE DEL FERORMONE        |
	 * 								+-----------------------------------------------------+      	             */

	/**
	 * Gets the pheromone value.
	 * @return the pheromone value
	 */
	public double getPheromoneValue(){ return pheromoneValue.get(); }

	/**
	 * Sets the pheromone value.
	 * @param value the new pheromone value
	 */
	public void setPheromoneValue(final double value){ pheromoneValue.set(value); }

	/**
	 * Evaporate pheromone.
	 * @param scalar the scalar
	 */
	public void evaporatePheromone(final double scalar){ pheromoneValue.set(getPheromoneValue() * scalar); }

	/**
	 * Drop pheromone.
	 * @param amount the amount
	 */
	public void dropPheromone(final double amount) { pheromoneValue.set(getPheromoneValue() + amount); }
}

