package model.map;

import util.Gloabal.C;

public class RaisedTile extends Tile {

	private final double height;

	public RaisedTile(double tileSize, TileType tileType, final double height) {
		super(tileSize, tileType);
		this.height = height;
		draw();
	}

	@Override
	public double getWeight() { return height; }

	@Override
	void draw() {
		super.draw();
		this.setFill(C.RAISED_COLOR.deriveColor(0,0.4,0.3,height));
	}
}
