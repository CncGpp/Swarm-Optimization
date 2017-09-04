package model.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * La classe {@code Tile} modella una singola cella della mappa
 * <p> Il tipo della cella può variare e dipende dalla mappa stessa</p>
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
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(0.3);
	}
}
