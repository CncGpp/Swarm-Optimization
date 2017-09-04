package model.map;

import javafx.scene.Group;
import javafx.scene.Node;
import model.TileDrawable;
import util.Global.Controllers;

/**
 * La classe {@code TileLayer} modella e gestisce la composizione della mappa
 * <p> La classe gestisce un'array 2D delle stesse dimensioni della mappa che mantiene informazioni circa la mappa</p>
 * */
class TileLayer implements TileDrawable{
	/** Il gruppo dove tutti i nodi Tile sono posti per poi essere rappresentato*/
	private Group group;

	/** Array bidimensionale di Tile della stessa dimensione della mappa*/
	private Tile[][] layer;

	/** Dimensione del tile sulla mappa*/
	private double tileSize;

	/**
	 * Istanzia un nuovo TileLayer
	 * @param map the map
	 */
	public TileLayer(final Map map) {
		layer = new Tile[map.getRows()][map.getCols()];
		group = new Group();
		tileSize = map.getTileSize();
	}

	/**
	 * Metodo setter che setta un Tile ad una determinata posizione sul TileLayer
	 * @param row the row
	 * @param col the col
	 * @param tile the tile
	 */
	public void setTileAt(final int row, final int col, final Tile tile){
		try { layer[row][col] = tile; }
		catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("La posizione del tile sulla mappa non è valida. (row,col):=(" + row + "," + col +")");
		}
		group.getChildren().add(tile);
		tile.relocate(col * tileSize, row * tileSize);
	}

	/**
	 * Metodo getter per ottenere il tile ad una determinata posizione dal TileLayer
	 * @param row the row
	 * @param col the col
	 * @return il tile
	 * */
	public Tile getTileAt(final int row, final int col){
		try{ return layer[row][col]; }
		catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("La posizione del tile sulla mappa non è valida. (row,col):=(" + row + "," + col +")");
		}
	}

	@Override
	public Node getNode() {return group;}
	@Override
	public void addNode(){ Controllers.rootView.addNode(this);}
	@Override
	public void removeNode(){ Controllers.rootView.removeNode(this); }
}
