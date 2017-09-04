package model.map;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import model.PheromoneDrawable;
import util.Global.Controllers;

/**
 * La classe {@code PheromoneLayer} modella e gestisce il ferormone presente sulla mappa.
 * <p> La classe gestisce un'array 2D delle stesse dimensioni della mappa che mantiene informazioni circa il valore di
 * ferormone</p>
 * */
class PheromoneLayer implements PheromoneDrawable{
	/** Il gruppo dove tutti i nodi Pheromone sono posti per poi essere rappresentato*/
	private Group group;

	/** Array bidimensionale di Pheromone della stessa dimensione della mappa*/
	private Pheromone[][] layer;

	/** Istanzia un nuovo PheromoneLayer
	 * @param map la mappa di appartenenza
	 * */
	public PheromoneLayer(final AMap map) {
		layer = new Pheromone[map.getRows()][map.getCols()];
		group = new Group();
		group.setCache(true);
		group.setCacheHint(CacheHint.SPEED);

		initPheromone(map);
	}

	/**
	 * Inizializza il layer di ferormone e aggiunge gli elementi al group
	 * */
	private void initPheromone(final AMap map){
		final double tileSize = map.getTileSize();
		for(int i = 0; i < map.getRows(); i++)
			for(int j = 0; j < map.getCols(); j++){
				layer[i][j] = new Pheromone(tileSize);

				if(map.getTileTypeAt(i, j) == TileType.WALL) continue;	//Non rappresento il ferormone sui muri!

				layer[i][j].relocate(j * tileSize, i * tileSize);
				group.getChildren().add(layer[i][j]);
			}
	}

	/* 								+-----------------------------------------------------+
	 * 								|        METODI DI MANIPOLAZIONE DEL FERORMONE        |
	 * 								+-----------------------------------------------------+      	             */
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

	@Override
	public Node getNode() {return group;}
	@Override
	public void addNode(){ Controllers.rootView.addNode(this);}
	@Override
	public void removeNode(){ Controllers.rootView.removeNode(this); }

}
