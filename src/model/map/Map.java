package model.map;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javafx.scene.Node;
import model.game.AGame;
import model.game.Stage;
import util.Coord;
import util.Global.Controllers;
import util.Global.R;

/**
 * Implementazione concreta della classe {@code AMap}
 * <p> La classe implementa delle strutture dati e sottoclassi per modellare la mappa e il feromone in modo da implementare
 * i metodi astratti della superclasse </p>
 */
public class Map extends AMap{

	/** Il layer della mappa contenente il ferormone */
	private PheromoneLayer pheromoneLayer;

	/** Il layer della mappa contenente i tile */
	private TileLayer tileLayer;

	/**
	 * Istanzia una nuova mappa
	 *
	 * @param game il gioco di appartenenza della mappa
	 * @param stage lo stage relativo alla mappa da istanziare
	 */
	// COSTRUTTORI
	public Map(final AGame game, final Stage stage) {
		super(game, stage);
	}

	/* (non-Javadoc)
	 * @see model.map.AMap#loadMap(model.AGame, model.Stage)
	 */
	@Override
	protected void loadMap(final AGame game, final Stage stage){
		loadTileMap(game, stage.getStagePath());
		pheromoneLayer = new PheromoneLayer(this);
	}


	/* 								+--------------------------------------------------------+
	 * 								|        METODI IL FERORMONE SULLA MAPPA DI GIOCO        |
	 * 								+--------------------------------------------------------+      	             */
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
	@Override
	public TileType getTileTypeAt(int row, int col) { return tileLayer.getTileAt(row, col).getTileType();}

	/**
	 * Carica la mappa dei tile da file
	 *
	 * @param game l'stanza di game a cui la mappa deve appartenere
	 * @param stagePath il path del file contenente la mappa
	 */
	private void loadTileMap(final AGame game, String stagePath){
		try(Scanner s = new Scanner(R.CLASSLOADER.getResourceAsStream(stagePath)).useLocale(Locale.ITALIAN)){
			// Leggo le dimensioni della mappa
			setDimensions(s.nextInt(), s.nextInt(), s.nextInt());

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
							game.addManhole(this, new Coord(i, j));
							break;
					case 3: tt = TileType.START;
							game.setStart(this, new Coord(i, j));
							break;
					case 4: tt = TileType.END;
							game.addEnd(this, new Coord(i, j));
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

	/* (non-Javadoc)
	 * @see model.map.AMap#getWeight(util.Coord, util.Coord)
	 */
	@Override
	public double getWeight(Coord from, Coord to) {
		final double peso = (from.getRow() == to.getRow() || from.getCol() == to.getCol()) ? 1.0d : 1.4142135623d;
		final Weighable wFrom = tileLayer.getTileAt(from.getRow(), from.getCol());
		final Weighable wTo = tileLayer.getTileAt(to.getRow(), to.getCol());

		return peso + (wTo.getWeight() - wFrom.getWeight())/peso;
	}

	/* (non-Javadoc)
	 * @see model.Drawable#getNode()
	 */
	@Override
	public Node getNode() { return null; }

	/* (non-Javadoc)
	 * @see model.Drawable#addNode()
	 */
	@Override
	public void addNode(){
		tileLayer.addNode();
		pheromoneLayer.addNode();
		Controllers.rootView.setSize(getRows()*getTileSize(), getCols()*getTileSize());
	}

	/* (non-Javadoc)
	 * @see model.Drawable#removeNode()
	 */
	@Override
	public void removeNode(){ tileLayer.removeNode(); pheromoneLayer.removeNode(); }

}












