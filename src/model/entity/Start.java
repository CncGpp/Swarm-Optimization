package model.entity;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import model.map.Map;
import util.Coord;
import util.Gloabal.C;

public class Start extends AStart{

	public Start(final Map map, final int row, final int col) {
		super(map, new Coord(row, col));
	}

	@Override
	protected Node makeNode(Map map) {
		final Rectangle r = new Rectangle(map.getTileSize(),map.getTileSize());
		r.setFill(C.START_COLOR);
		r.relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());
		return r;
	}

}
