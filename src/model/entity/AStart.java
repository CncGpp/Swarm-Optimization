package model.entity;

import model.map.Map;
import util.Coord;

/**
 * La classe {@code AStart} modella l'entrata della mappa.
 * <p>La classe è essenzialmente funziona da marcatore di posizione per l'entrata della mappa</p>
 */
public abstract class AStart extends Entity {

	/**
	 * Istanzia una nuova Start
	 *
	 * @param map la mappa di gioco
	 * @param coordinate le coordinate dell'entità
	 */
	public AStart(final Map map, final Coord coordinate) {
		super(map, coordinate);
	}

	/**
	 * Istanzia una nuova Start
	 *
	 * @param map la mappa di gioco
	 * @param row l'indice di riga della coordinata y
	 * @param col l'indice di riga della coordinata x
	 */
	public AStart(final Map map, final int row, final int col) {
		this(map, new Coord(row,col));
	}

}
