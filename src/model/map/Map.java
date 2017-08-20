package model.map;

import java.util.NoSuchElementException;
import java.util.Scanner;
import javafx.scene.Node;
import model.AGame;
import model.Stage;
import model.entity.End;
import model.entity.Manhole;
import model.entity.Start;
import util.Coord;
import util.Gloabal.Controllers;
import util.Gloabal.R;

public class Map extends AMap{

	private PheromoneLayer pheromoneLayer;
	private TileLayer tileLayer;

	// COSTRUTTORI
	public Map(final AGame game, final Stage stage) {
		super(game, stage);
	}

	@Override
	protected void loadMap(final AGame game, final Stage stage){
		loadTileMap(game, stage.getStagePath());
		pheromoneLayer = new PheromoneLayer(this);
	}


	//MODIFICATORI DEL FERORMONE
	@Override
	public double getPheromoneAt(final int row, final int col){ return pheromoneLayer.getPheromoneAt(row, col);}
	@Override
	public void setPheromoneAt(final int row, final int col, final double value){ pheromoneLayer.setPheromoneAt(row, col, value); }
	@Override
	public void evaporatePheromoneAt(final int row, final int col, final double scalar){ pheromoneLayer.evaporatePheromoneAt(row, col, scalar);}
	@Override
	public void dropPheromoneAt(final int row, final int col, final double amount){pheromoneLayer.dropPheromoneAt(row, col, amount);}

	@Override
	public void setPheromone(final double value){ pheromoneLayer.setPheromone(value); }
	@Override
	public void evaporatePheromone(final double scalar){ pheromoneLayer.evaporatePheromone(scalar); }
	@Override
	public void dropPheromone(final double amount){ pheromoneLayer.dropPheromone(amount); }

	//MODIFICATORI DEI TILE
	@Override
	public TileType getTileTypeAt(int row, int col) { return tileLayer.getTileAt(row, col).getTileType();}


	private void loadTileMap(final AGame game, String stagePath){
		try(Scanner s = new Scanner(R.CLASSLOADER.getResourceAsStream(stagePath))){
			// Leggo le dimensioni della mappa
			setDimensions(s.nextInt(), s.nextInt(), s.nextDouble());

			// Creo la mappa dei tile
			tileLayer = new TileLayer(this);

			// Leggo dunque la mappa posizione per posizione
			TileType tt;
			for(int i = 0; i < getRows(); i++)
				for(int j = 0; j < getCols(); j++){
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
					case -1: tt = TileType.RAISED;
							tileLayer.setTileAt(i, j, new RaisedTile(getTileSize(), tt, s.nextDouble()));
							continue;
					default: tt = TileType.WALL; break;
					}
					tileLayer.setTileAt(i, j, new Tile(getTileSize(), tt));
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

	@Override
	public double getWeight(Coord from, Coord to) {
		final double peso = (from.getRow() == to.getRow() || from.getCol() == to.getCol()) ? 1 : 1.4142135623;
		final Weighable wFrom = tileLayer.getTileAt(from.getRow(), from.getCol());
		final Weighable wTo = tileLayer.getTileAt(to.getRow(), to.getCol());

		if(wFrom.getWeight() >= wTo.getWeight())
			return peso - wFrom.getWeight()/peso;
		else
			return peso + wTo.getWeight()/peso;
	}

	@Override
	public Node getNode() { return null; }

	@Override
	public void addNode(){
		tileLayer.addNode();
		pheromoneLayer.addNode();
		Controllers.rootView.setSize(getRows()*getTileSize(), getCols()*getTileSize());
	}
	@Override
	public void removeNode(){ tileLayer.removeNode(); pheromoneLayer.removeNode(); }
}












