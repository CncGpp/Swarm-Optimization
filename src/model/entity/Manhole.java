package model.entity;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import model.map.AMap;
import util.Coord;
import util.Global.C;

/**
 * Implementazione concreta della classe {@code AManhole}.
 * */
public class Manhole extends AManhole{

	/**
	 * Istanzia una nuova Botola
	 *
	 * @param map la mappa di gioco
	 * @param row l'indice di riga della coordinata y
	 * @param col l'indice di riga della coordinata x
	 */
	public Manhole(final AMap map, final int row, final int col) {
		super(map, new Coord(row, col));
	}

	@Override
	protected Node makeNode(AMap map) {
		final Rectangle r = new Rectangle(map.getTileSize(),map.getTileSize());
		r.setFill(C.MANHOLE_COLOR);
		r.relocate(getCol() * map.getTileSize(),  getRow() * map.getTileSize());
		return r;
	}

}