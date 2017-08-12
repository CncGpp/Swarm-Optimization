package model.map;

import java.util.Scanner;

import model.Game;
import model.entity.End;
import model.entity.Start;
import util.Gloabal.Controllers;
import util.Gloabal.R;

public class Map {
	private int rows;
	private int cols;
	private double tileSize;

	private final Game game;

	private PheromoneLayer pheromoneLayer;
	private TileLayer tileLayer;

	// COSTRUTTORI
	public Map(final Game game) {
		this.game = game;
		loadMap(R.STAGE_LIST.get(0));
	}

	// METODI GETTER & SETTER
	public int getRows(){ return rows; }
	public int getCols(){ return cols; }
	public double getTileSize(){ return tileSize; }

	public void setDimensions(final int rows, final int cols, final double tileSize){
		//TODO: aggiungere controlli
		this.rows = rows;
		this.cols = cols;
		this.tileSize = tileSize;
	}

	//MODIFICATORI DEL FERORMONE
	public double getPheromoneAt(final int row, final int col){ return pheromoneLayer.getPheromoneAt(row, col);}
	public void setPheromoneAt(final int row, final int col, final double value){ pheromoneLayer.setPheromoneAt(row, col, value); }
	public void evaporatePheromoneAt(final int row, final int col, final double scalar){ pheromoneLayer.evaporatePheromoneAt(row, col, scalar);}
	public void dropPheromoneAt(final int row, final int col, final double amount){pheromoneLayer.dropPheromoneAt(row, col, amount);}

	public void setPheromone(final double value){ pheromoneLayer.setPheromone(value); }
	public void evaporatePheromone(final double scalar){ pheromoneLayer.evaporatePheromone(scalar); }
	public void dropPheromone(final double amount){ pheromoneLayer.dropPheromone(amount); }

	//MODIFICATORI DEI TILE
	public TileType getTileTypeAt(int row, int col) { return tileLayer.getTileAt(row, col).getTileType();}

	public void add(){ tileLayer.add(); pheromoneLayer.add();}
	public void remove(){ tileLayer.remove(); pheromoneLayer.remove(); }


	public void loadMap(String stagePath){
		Scanner s = new Scanner(R.CLASSLOADER.getResourceAsStream(stagePath));

		// Leggo le dimensioni della mappa
		setDimensions(s.nextInt(), s.nextInt(), s.nextDouble());

		// Creo la mappa dei tile e la mappa del feromone
		tileLayer = new TileLayer(this);
		pheromoneLayer = new PheromoneLayer(this);

		// Leggo dunque la mappa posizione per posizione
		TileType tt;
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++){
				switch (s.nextInt()) {
				case 0: tt = TileType.FREE; break;
				case 1: tt = TileType.WALL; break;
				case 2: tt = TileType.MANHOLE; break;
				case 3: tt = TileType.START;
						game.setStart(new Start(this, i, j));
						break;
				case 4: tt = TileType.END;
						game.addEnd(new End(this, i, j));
						break;
				default: tt = TileType.WALL; break;
				}
				tileLayer.setTileAt(i, j, new Tile(tileSize, tt));
		}
		s.close();
		//QUESTO SEZONDO ME VA NELLA FUNZIONE DI DRAW!
		Controllers.tileMap.setSize(rows*tileSize, cols*tileSize);
	}

}












