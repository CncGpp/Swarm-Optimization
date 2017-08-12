package model.map;

import javafx.scene.Group;
import util.Gloabal.Controllers;

public class PheromoneLayer {
	private Group group;
	private Pheromone[][] layer;
	private double tileSize;

	public PheromoneLayer(final Map map) {
		layer = new Pheromone[map.getRows()][map.getCols()];
		group = new Group();
		tileSize = map.getTileSize();

		initPheromone(map);
	}

	private void initPheromone(final Map map){
		for(int i = 0; i < map.getRows(); i++)
			for(int j = 0; j < map.getCols(); j++){
				layer[i][j] = new Pheromone(tileSize);
				//TODO: if map.getTileAt(i,j) == muro continue;
				layer[i][j].relocate(j * tileSize, i * tileSize);
				group.getChildren().add(layer[i][j]);
			}
	}

	// METODI GETTER & SETTER E MODIFICATORI
	public double getPheromoneAt(final int row, final int col){ return layer[row][col].getPheromoneValue();}
	public void setPheromoneAt(final int row, final int col, final double value){ layer[row][col].setPheromoneValue(value);}
	public void evaporatePheromoneAt(final int row, final int col, final double scalar){layer[row][col].evaporatePheromone(scalar);}
	public void dropPheromoneAt(final int row, final int col, final double amount){layer[row][col].dropPheromone(amount);}

	public void setPheromone(final double value){
		for(Pheromone[] uu : layer) for(Pheromone u : uu) u.setPheromoneValue(value);
	}
	public void evaporatePheromone(final double scalar){
		for(Pheromone[] uu : layer) for(Pheromone u : uu) u.evaporatePheromone(scalar);
	}
	public void dropPheromone(final double amount){
		for(Pheromone[] uu : layer) for(Pheromone u : uu) u.dropPheromone(amount);
	}

	public void add(){ Controllers.pheromoneMap.add(group);}
	public void remove(){ Controllers.pheromoneMap.remove(group); }

}
