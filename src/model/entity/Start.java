package model.entity;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import model.map.Map;
import util.Coord;
import util.Gloabal.C;

public class Start extends Entity{

	Rectangle r;

	public Start(final Map map, final int row, final int col) {
		super(new Coord(row, col));
		r = new Rectangle(map.getTileSize(),map.getTileSize());
		r.setFill(C.START_COLOR);
		r.relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());
	}

	@Override
	public Node getNode(){ return r; }
}
