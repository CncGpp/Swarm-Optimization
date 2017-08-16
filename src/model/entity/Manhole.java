package model.entity;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import model.map.Map;
import util.Coord;
import util.Gloabal.C;

public class Manhole extends AManhole{

	public Manhole(final Map map, final int row, final int col) {
		super(map, new Coord(row, col));
	}

	@Override
	protected Node makeNode(Map map) {
		final Rectangle r = new Rectangle(map.getTileSize(),map.getTileSize());
		r.setFill(C.MANHOLE_COLOR);
		r.relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());
		return r;
	}

	public boolean teleport(final Map map, final ABot b){
		return super.teleport(map, b);
	}

}