package model.map;

import model.AGame;
import model.Drawable;
import model.Stage;
import util.Coord;

/**
 * La classe {@code AMap} modella in maniera astratta la mappa di gioco e il suo funzionamento
 */
public abstract class AMap implements Drawable{

	/** Il numero di righe della mappa*/
	private int rows;

	/** Il numero di colonne della mappa*/
	private int cols;

	/** La dimensione in pixel di un elemento quadrato della mappa */
	private double tileSize;

	/**
	 * Istanzia una nuova mappa
	 *
	 * @param game il gioco di appartenenza della mappa
	 * @param stage lo stage relativo alla mappa da istanziare
	 */
	public AMap(final AGame game, final Stage stage) {
		if(game == null) throw new IllegalArgumentException("Il game non può essere 'null");
		else if(stage == null) throw new IllegalArgumentException("Lo stage non può essere 'null");
		loadMap(game, stage);
	}

	/**
	 * Carica la mappa di gioco
	 *
	 * @param game il gioco di appartenenza della mappa
	 * @param stage lo stage relativo alla mappa da istanziare
	 */
	protected abstract void loadMap(final AGame game, final Stage stage);


	/* 									+--------------------------------------+
	 * 									|        METODI GETTER & SETTERS       |
	 * 									+--------------------------------------+          	                          */

	/**
	 * Gets the rows.
	 * @return the rows
	 */
	public int getRows(){return rows;}

	/**
	 * Gets the cols.
	 * @return the cols
	 */
	public int getCols(){return cols;}

	/**
	 * Gets the tile size.
	 * @return the tile size
	 */
	public double getTileSize(){ return tileSize; }

	/**
	 * Ritorna il peso di congiungimento fra due Coordinate della mappa
	 *
	 * @param from le coordinate di partenza
	 * @param to le coordinate di arrivo
	 * @return the weight
	 */
	public abstract double getWeight(final Coord from, final Coord to);

	/**
	 * Setta le dimensioni della mappa
	 *
	 * @param rows the rows
	 * @param cols the cols
	 * @param tileSize the tile size
	 */
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


	/* 								+--------------------------------------------------------+
	 * 								|        METODI IL FERORMONE SULLA MAPPA DI GIOCO        |
	 * 								+--------------------------------------------------------+      	             */

	/**
	 * Gets the pheromone at.
	 * @param row the row
	 * @param col the col
	 * @return the pheromone at
	 */
	public abstract double getPheromoneAt(final int row, final int col);

	/**
	 * Sets the pheromone at.
	 * @param row the row
	 * @param col the col
	 * @param value the value
	 */
	public abstract void setPheromoneAt(final int row, final int col, final double value);

	/**
	 * Evaporate pheromone at.
	 * <p> L'evaporazione è ottenuta moltiplicando il valore attuale per lo scalare in input </p>
	 * @param row the row
	 * @param col the col
	 * @param scalar the scalar
	 */
	public abstract void evaporatePheromoneAt(final int row, final int col, final double scalar);

	/**
	 * Drop pheromone at.
	 * @param row the row
	 * @param col the col
	 * @param amount the amount
	 */
	public abstract void dropPheromoneAt(final int row, final int col, final double amount);

	/**
	 * Setta il feromone per tutta la mappa
	 * @param value the new pheromone
	 */
	public abstract void setPheromone(final double value);

	/**
	 * Evapora il feromone per tutta la mappa.
	 * <p> L'evaporazione è ottenuta moltiplicando il valore attuale per lo scalare in input </p>
	 * @param scalar the scalar
	 */
	public abstract void evaporatePheromone(final double scalar);

	/**
	 * Deposita pheromone per tutta la mappa.
	 * @param amount the amount
	 */
	public abstract void dropPheromone(final double amount);

	/**
	 * Ritorna il tipo del tile ad una determinata posizione della mappa
	 * @param row the row
	 * @param col the col
	 * @return the tile type at
	 */
	public abstract TileType getTileTypeAt(int row, int col);
}
