package model.entity;

import java.util.ArrayList;

import strategy.ColonyStrategy;
import util.Coord;
import util.Path;
import util.Vertex;

/**
 * La classe <code>ABot</code> modella un microbot.
 * <p>Si tratta di un entità che rappresenta una formica nel algoritmo <tt>AS - Ant System</tt> ha il compito di uscire dalla mappa.
 * Ogni microbot fa parte di una colonia <code>AColony</code> che ne determina il comportamento, movimento e strategia.</p>
 * <p>Ciascun microbot mantiene informazioni sulla mappa di appartenenza, le celle visitate e il percorso effettuato ed
 * è indipendente dagli altri.</p>
 *
 * @author Cianci Giuseppe Pio
 * @see AMap
 * @see AColony
 * @see Entity
 * */
public abstract class ABot extends Entity {

	/** Colonia di appartenenza del bot. */
	protected AColony colony;

	/** Matrice di flag contenente i nodi GIA' visitati dal bot. */
	protected boolean visited[][];

	/** Il percorso effettuato dal microbot. */
	protected Path path = new Path();

	/**
	 * Istanzia un nuovo ABot.
	 *
	 * @param colony la colonia di appartenenza del bot
	 * @see AColony
	 */
	public ABot(final AColony colony) {
		this(colony, new Coord(-1,-1));
	}

	/**
	 * Istanzia un nuovo ABot.
	 *
	 * @param colony la colonia di appartenenza del bot
	 * @param coordinate le coordinate del bot sulla mappa
	 * @see AColony
	 * @see Coord*/
	public ABot(final AColony colony, final Coord coordinate){
		super(colony.getMap(), coordinate);
		this.setColony(colony);
	}

	/**
	 * Metodo setter permette di settare la colonia di appartenenza
	 * @param colony la colonia
	 * @see AColony */
	private void setColony(final AColony colony){
		if(colony == null) throw new IllegalArgumentException("La colonia non può essere 'null'");
		else this.colony = colony;
	}

	/**
	 * Permette di sapere se il bot ha visitato una determinata coordinata della mappa
	 * @param coord la coordinata de verificare
	 * @return true, se è stato visitato.
	 * @see Coord*/
	public boolean isVisited(final Coord coord){return visited[coord.getRow()][coord.getCol()];}

	/** Muove il bot sulla mappa alla prossima posizione
	 * @return true, se il movimento è riuscito*/
	public abstract boolean move();

	/** Muove il bot su una posizione specifica della mappa
	 * @param coord le coordinate dello spostamento
	 * @return true, se il movimento è riuscito
	 * @see Vertex */
	protected abstract boolean moveTo(final Vertex coord);


	/**
	 * La funzione permette di ottenere le possibili che si trovano in un 8-connesso.
	 * <p> tali direzioni verranno in seguito utilizzate per pianificare il movimento del bot. </p>
	 *
	 * @param newDirections è un parametro di <code>in/out</code> dove verranno ritornate le nuove direzioni, ovvero
	 * quelle praticabili e che nell'intorno non sono state ancora visitate dal bot.
	 * @param oldDirections è un parametro di <code>in/out</code> dove verranno ritornate le vecchie direzioni, ovvero
	 * quelle che nell'intorno sono state già visitate dal bot.
	 * @see Vertex
	 */
	public abstract void getNeighbors(ArrayList<Vertex> newDirections, ArrayList<Vertex> oldDirections);

	/**
	 * Permette al bot di abbandonare la mappa.
	 * <p> Tale funzione viene chiamata quando il bot riesce a trovare l'uscita. </p>
	 * <p> In questa fase viene effettuato l'offlineUpdate del feromone del'algoritmo <tt>AS - Ant System</tt>,
	 *  come descritto nella strategia della colonia. </p>
	 *
	 * @see AColony
	 * @see ColonyStrategy
	 */
	public abstract void leaved();
}
