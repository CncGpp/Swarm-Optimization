package model.map;

import model.AGame;
import model.Drawable;
import model.Stage;
import util.Coord;

public abstract class AMap implements Drawable{
	private int rows;
	private int cols;
	private double tileSize;

	public AMap(final AGame game, final Stage stage) {
		if(game == null) throw new IllegalArgumentException("Il Game non può essere 'null");
		else if(stage == null) throw new IllegalArgumentException("Lo stage non può essere 'null");
		loadMap(game, stage);
	}

	protected abstract void loadMap(final AGame game, final Stage stage);

	// GETTERS & SETTERS
	public int getRows(){return rows;}
	public int getCols(){return cols;}
	public double getTileSize(){ return tileSize; }
	public abstract double getWeight(final Coord from, final Coord to);
	protected void setDimensions(final int rows, final int cols, final double tileSize){
		try{
			if(rows <= 0) throw new IllegalArgumentException("Il numero di righe della mappa deve essere > 0. \t rows: "+ rows);
			if(cols <= 0) throw new IllegalArgumentException("Il numero di colonne della mappa deve essere > 0. \t cols: "+ cols);
			if(tileSize <= 0) throw new IllegalArgumentException("La dimensione del tile della mappa deve essere > 0. \t tileSize: "+ tileSize);

			this.rows = rows;
			this.cols = cols;
			this.tileSize = tileSize;

		} catch(IllegalArgumentException e){
			e.printStackTrace();
			System.exit(-3);
		}
	}

	//MODIFICATORI DEL FERORMONE
	public abstract double getPheromoneAt(final int row, final int col);
	public abstract void setPheromoneAt(final int row, final int col, final double value);
	public abstract void evaporatePheromoneAt(final int row, final int col, final double scalar);
	public abstract void dropPheromoneAt(final int row, final int col, final double amount);

	public abstract void setPheromone(final double value);
	public abstract void evaporatePheromone(final double scalar);
	public abstract void dropPheromone(final double amount);

	//MODIFICATORI DEI TILE
	public abstract TileType getTileTypeAt(int row, int col);
}
