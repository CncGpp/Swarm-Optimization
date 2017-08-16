package model.map;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.Game;
import model.entity.End;
import model.entity.Manhole;
import model.entity.Start;
import util.Gloabal.Controllers;
import util.Gloabal.R;
import util.Stage;

public class Map {
	private int rows;
	private int cols;
	private double tileSize;

	private PheromoneLayer pheromoneLayer;
	private TileLayer tileLayer;

	// COSTRUTTORI
	public Map(final Game game, final Stage stage) {;
		loadTileMap(game, stage.getStagePath());
		pheromoneLayer = new PheromoneLayer(this);
	}

	// METODI GETTER & SETTER
	public int getRows(){ return rows; }
	public int getCols(){ return cols; }
	public double getTileSize(){ return tileSize; }

	private void setDimensions(final int rows, final int cols, final double tileSize){
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
	public double getPheromoneAt(final int row, final int col){ return pheromoneLayer.getPheromoneAt(row, col);}
	public void setPheromoneAt(final int row, final int col, final double value){ pheromoneLayer.setPheromoneAt(row, col, value); }
	public void evaporatePheromoneAt(final int row, final int col, final double scalar){ pheromoneLayer.evaporatePheromoneAt(row, col, scalar);}
	public void dropPheromoneAt(final int row, final int col, final double amount){pheromoneLayer.dropPheromoneAt(row, col, amount);}

	public void setPheromone(final double value){ pheromoneLayer.setPheromone(value); }
	public void evaporatePheromone(final double scalar){ pheromoneLayer.evaporatePheromone(scalar); }
	public void dropPheromone(final double amount){ pheromoneLayer.dropPheromone(amount); }

	//MODIFICATORI DEI TILE
	public TileType getTileTypeAt(int row, int col) { return tileLayer.getTileAt(row, col).getTileType();}

	public void add(){
		tileLayer.addNode();
		pheromoneLayer.addNode();

		Controllers.rootView.setSize(rows*tileSize, cols*tileSize);
	}
	public void remove(){ tileLayer.removeNode(); pheromoneLayer.removeNode(); }


	public void loadTileMap(final Game game, String stagePath){
		try(Scanner s = new Scanner(R.CLASSLOADER.getResourceAsStream(stagePath))){
			// Leggo le dimensioni della mappa
			setDimensions(s.nextInt(), s.nextInt(), s.nextDouble());

			// Creo la mappa dei tile
			tileLayer = new TileLayer(this);

			// Leggo dunque la mappa posizione per posizione
			TileType tt;
			for(int i = 0; i < rows; i++)
				for(int j = 0; j < cols; j++){
					switch (s.nextInt()) {
					case 0: tt = TileType.FREE; break;
					case 1: tt = TileType.WALL; break;
					case 2: tt = TileType.MANHOLE;
							game.addManhole(new Manhole(this, i, j));
							break;
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
		} catch(NoSuchElementException e){
			System.err.println("Errore durante la lettura della mappa: '" +  stagePath + "'");
			System.err.println("E' presente un errore nella struttura del file.");
			e.printStackTrace();
			System.exit(-1);
		} catch (NullPointerException e) {
			System.err.println("Errore durante l'apertura della mappa: '" +  stagePath + "'");
			System.err.println("La risorsa '" +  stagePath + "' non è stata trovata.");
			e.printStackTrace();
			System.exit(-2);
		}
	}


}












