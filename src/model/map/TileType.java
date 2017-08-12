package model.map;

import javafx.scene.paint.Color;

public enum TileType {
	FREE(0, Color.WHITE),
	WALL(1, Color.BLACK),
	MANHOLE(2, Color.DODGERBLUE),
	START(3, Color.BROWN),
	END(4, Color.LIMEGREEN);


	private final int id;
	private final Color tileColor;

	private TileType(final int id, final Color tileColor) {
		this.id = id;
		this.tileColor = tileColor;
	}

	public int getId() {return id;}
	public Color getTileColor(){ return tileColor;}
}
