package model.map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle implements Weighable{

	private final TileType tileType;

	public Tile(final double tileSize, final TileType tileType) {
		super(tileSize,tileSize);
		this.tileType = tileType;
		draw();
	}

	public TileType getTileType() {return tileType;}

	void draw(){
		this.setFill(tileType.getTileColor());
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(0.3);
	}
}
