package model.entity;

import model.map.Map;
import util.Coord;

public abstract class AStart extends Entity {

	public AStart(final Map map, final Coord coordinate) {
		super(map, coordinate);
	}
	public AStart(final Map map, final int row, final int col) {
		this(map, new Coord(row,col));
	}

}
