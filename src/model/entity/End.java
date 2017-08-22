package model.entity;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.map.AMap;
import model.map.Map;
import util.Coord;
import util.Global.C;

/**
 * Implementazione concreta della classe {@code AEnd}.
 * */
public class End extends AEnd{

	public End(final Map map, final Coord coordinate) {
		super(map,coordinate);
	}

	public End(final Map map, final int row, final int col){
		this(map, new Coord(row,col));
	}

	@Override
	protected Shape makeNode(final AMap map) {
		Shape s = new Rectangle(map.getTileSize(),map.getTileSize());
		s.setFill(C.END_COLOR);
		s.relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());
		return s;
	}

}
