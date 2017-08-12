package model.map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pheromone extends Rectangle{

	private DoubleProperty pheromoneValue = new SimpleDoubleProperty(0);

	public Pheromone(final double tileSize) {
		super(tileSize,tileSize);

		pheromoneValue.addListener(
				(observer, oldValue, newValue) -> { this.setOpacity(pheromoneValue.get());}
				);

		draw();
	}

	void draw(){
		this.setOpacity(pheromoneValue.get());
		this.setFill(Color.RED);
	}

	// METODI GETTER & SETTER
	public double getPheromoneValue(){ return pheromoneValue.get(); }
	public void setPheromoneValue(final double value){ pheromoneValue.set(value); }

	// METODI DI MANIPOLAZIONE DEL FERORMONE
	public void evaporatePheromone(final double scalar){ pheromoneValue.set(getPheromoneValue() * scalar); }
	public void dropPheromone(final double amount) { pheromoneValue.set(getPheromoneValue() + amount); }
}

