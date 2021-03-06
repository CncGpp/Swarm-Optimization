package model.map;

import javafx.scene.CacheHint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * La classe {@code Tile} modella una singola cella della mappa
 * <p> Il tipo della cella pu� variare e dipende dalla mappa stessa</p>
 */
class Tile extends Rectangle implements Weighable{

	/** Il tipo del tile */
	private final TileType tileType;

	/**
	 * Istanzia un nuovo tile
	 *
	 * @param tileSize the tile size
	 * @param tileType the tile type
	 */
	public Tile(final double tileSize, final TileType tileType) {
		super(tileSize,tileSize);
		this.tileType = tileType;
		this.setCache(true);
		this.setCacheHint(CacheHint.SPEED);
		draw();
	}

	/**
	 * Gets the tile type.
	 * @return the tile type
	 */
	public TileType getTileType() {return tileType;}

	/**
	 * Disegna il tile
	 */
	protected void draw(){
		this.setFill(tileType.getTileColor());
		if(tileType == TileType.WALL) return;
		this.setStroke(Color.GAINSBORO);
		this.setStrokeWidth(1);
	}
}
