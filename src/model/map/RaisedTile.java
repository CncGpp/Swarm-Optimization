package model.map;

import util.Global.C;

/**
 * TRappresenta un'estenzione della classe {@code Tile}.
 * <p>Il raised Tile è un tile rialzato dal suolo, dunque di più difficile raggiungimento per un Bot</p>
 */
class RaisedTile extends Tile {

	/** l'altezza del tile */
	private final double height;

	/**
	 * Instantiates a new raised tile.
	 *
	 * @param tileSize the tile size
	 * @param tileType the tile type
	 * @param height the height
	 */
	public RaisedTile(double tileSize, TileType tileType, final double height) {
		super(tileSize, tileType);
		this.height = height;
		draw();
	}

	/* (non-Javadoc)
	 * @see model.map.Weighable#getWeight()
	 */
	@Override
	public double getWeight() { return height; }

	/* (non-Javadoc)
	 * @see model.map.Tile#draw()
	 */
	@Override
	protected void draw() {
		super.draw();
		this.setFill(C.RAISED_COLOR.deriveColor(0.0,0.0,1.0d-height*0.8d,1.0));
		this.setStroke(C.RAISED_COLOR.deriveColor(0.0,0.0,1.0d-height*0.8d,1.0));
	}
}
