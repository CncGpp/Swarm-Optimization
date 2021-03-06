package model.entity;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import model.map.AMap;
import util.Coord;
import util.Global.C;

/**
 * Implementazione concreta della classe {@code AStart}.
 * */
public class Start extends AStart{

	public Start(final AMap map, final int row, final int col) {
		super(map, new Coord(row, col));
	}

	@Override
	protected Node makeNode(AMap map) {
		final Rectangle r = new Rectangle(map.getTileSize(),map.getTileSize());
		r.setFill(C.START_COLOR);
		r.relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());
		return r;
	}

}
