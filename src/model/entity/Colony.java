package model.entity;


import model.game.AGame;
import model.map.AMap;
import util.Coord;

/**
 * Implementazione concreta della classe {@code AColony}
 * @see AColony
 */
public class Colony extends AColony{

	/**
	 * Istanzia una nuova colonia di bot.
	 *
	 * @param map l'istanza della mappa a cui la colonia deve appartenere.
	 * @param botCount il numero di bot che devono far parte della colonia.
	 * @param start l'entita' {@code AStart} da dove la colonia dovra' cominciare.
	 *
	 * @see AGame
	 * @see AStart
	 */
	public Colony(final AMap map, final int botCount, final AStart start) {
		super(map, botCount, start);
	}

	/* (non-Javadoc)
	 * @see model.entity.AColony#makeBot(util.Coord)
	 */
	@Override
	protected ABot makeBot(final Coord startPosition){ return new Bot(this, startPosition); }

	/* (non-Javadoc)
	 * @see model.entity.AColony#moveBot()
	 * Tutti i bot si muovono ad ogni cambio di stato
	 */
	@Override
	protected void moveBot() { this.getBots().forEach( b->b.move()); }

}
