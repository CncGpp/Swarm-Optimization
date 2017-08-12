package model.map;

import javafx.scene.Group;
import util.Gloabal.Controllers;

public class TileLayer {
	private Group group;
	private Tile[][] layer;
	private double tileSize;

	public TileLayer(final Map map) {
		layer = new Tile[map.getRows()][map.getCols()];
		group = new Group();
		tileSize = map.getTileSize();
	}

	public void setTileAt(final int row, final int col, final Tile tile){
		//TODO: fare controlli
		layer[row][col] = tile;
		group.getChildren().add(tile);
		tile.relocate(col * tileSize, row * tileSize);
	}
	public Tile getTileAt(final int row, final int col){ return layer[row][col];}

	public void add(){ Controllers.tileMap.add(group);}
	public void remove(){ Controllers.tileMap.remove(group); }
}
