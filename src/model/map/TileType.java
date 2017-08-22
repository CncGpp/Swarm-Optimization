package model.map;

import javafx.scene.paint.Color;
import util.Global.C;

/**
 * La classe definisce i diversi tipi possibili di tile
 * */
public enum TileType {
	RAISED(-1, C.RAISED_COLOR),
	FREE(0, C.FREE_COLOR),
	WALL(1, C.WALL_COLOR),
	MANHOLE(2, C.MANHOLE_COLOR),
	START(3, C.START_COLOR),
	END(4, C.END_COLOR);


	private final int id;
	private final Color tileColor;

	private TileType(final int id, final Color tileColor) {
		this.id = id;
		this.tileColor = tileColor;
	}

	public int getId() {return id;}
	public Color getTileColor(){ return tileColor;}
}
