package model.entity;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import model.map.AMap;
import model.map.TileType;
import util.Coord;
import util.Vertex;

/**
 * La classe {@code AManhole} modella le botole della mappa.
 * <p>La classe è essenzialmente un'entità che ha il compito di osservare i movimenti dei microbot, se essi entrano
 * in collisione con la botola allora vengono teletrasportati in un punto casuale della mappa</p>
 */
public abstract class AManhole extends Entity implements Observer{

	/**
	 * Istanzia una nuova Botola
	 *
	 * @param map la mappa di gioco
	 * @param coordinate le coordinate della botola
	 */
	public AManhole(final AMap map, final Coord coordinate) {
		super(map, coordinate);
	}

	/**
	 * Istanzia una nuova Botola
	 *
	 * @param map la mappa di gioco
	 * @param row l'indice di riga della coordinata y
	 * @param col l'indice di riga della coordinata x
	 */
	public AManhole(final AMap map, final int row, final int col) {
		this(map, new Coord(row, col));
	}

	/**
	 *  Teletrasporta un bot in una coordinata casuale della mappa
	 *  @param map la mappa di gioco
	 *  @param b il bot da teletrasportare
	 *  @return true, se il teletrasporto va a buon fine
	 * */
	private boolean teleport(final AMap map, final ABot b){
		final Random r = new Random();

		//Ho 20 possibilita' di trovare una cella libera per il teletrasporto.
		for(int i = 0; i < 20; i++){
			final int _row = r.nextInt(map.getRows());
			final int _col = r.nextInt(map.getCols());
			if(map.getTileTypeAt(_row, _col) != TileType.WALL) {
				b.moveTo( new Vertex(_row,_col, 0) );
				return true;
			}
		}
		return false;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(!(o instanceof AColony)) return;

		for(ABot b : ((AColony) o).getBots())
			if(this.isIntersect(b))
				this.teleport(((AColony)o).getMap(), b);
	}

}
