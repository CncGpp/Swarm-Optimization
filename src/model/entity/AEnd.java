package model.entity;
import java.util.Observable;
import java.util.Observer;

import model.map.AMap;
import util.Coord;

/**
 * La classe {@code AEnd} modella l'uscita della mappa.
 * <p>La classe è essenzialmente un'entità che ha il compito di osservare i movimenti dei microbot, se essi entrano
 * in collisione con l'uscita allora sono riusciti a scappare dal labirinto della mappa.</p>
 */
public abstract class AEnd extends Entity implements Observer{

	/**
	 * Istanzia una nuova End
	 *
	 * @param map la mappa di gioco
	 * @param coordinate le coordinate dell'uscita
	 */
	public AEnd(final AMap map, final Coord coordinate) {
		super(map, coordinate);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(!(o instanceof AColony)) return;

		for(ABot b : ((AColony) o).getBots()) if(this.isIntersect(b)) { b.leaved(); }
	}


}
