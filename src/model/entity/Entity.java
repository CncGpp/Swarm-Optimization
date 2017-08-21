package model.entity;

import javafx.scene.Node;
import model.Drawable;
import model.map.AMap;
import util.Coord;
import util.Gloabal.Controllers;

/**
 * La classe <code>Entity</code> rappresenta in maniera astratta un'entita' che si trova sulla mappa di gioco.
 * <p>Ogni entita' a questo livello è caratterizzata solamente da un {@code Node} usato per la sua rappresentazione e dalla
 * posizione {@code Coord} sulla mappa dove si trova.</p>
 *
 * @author Cianci Giuseppe Pio
 * @see AMap
 * @see Coord
 * @see Node
 * @see Drawable
 * */
public abstract class Entity implements Drawable{

	/** Elemento grafico utilizzato per la rappresentazione dell'entità*/
	protected Node node;

	/** Le coordinate, intese come indici di riga e colonna, dell'entità sulla mappa*/
	private Coord coordinate = new Coord(-1, -1);

	/**
	 * Istanzia una nuova entita'.
	 *
	 * @param map la mappa sulla quale l'entità deve essere creata
	 * @param coordinate le coordinate dell'entità sulla mappa
	 * @see AMap
	 * @see Coord
	 */
	public Entity(final AMap map, final Coord coordinate) {
		this.setCoordinate(coordinate);
		node = makeNode(map);
	}


	/* 									+--------------------------------------+
	 * 									|        METODI GETTER & SETTERS       |
	 * 									+--------------------------------------+          	                          */

	/**
	 * Metodo getter per ottenere le coordinate dell'entità
	 * @return le coordinate
	 * */
	public Coord getCoordinate(){ return coordinate; }

	/**
	 * Metodo setter per settare le coordinate dell'entità
	 * @param coordinate le coordinate
	 * */
	public void setCoordinate(final Coord coordinate) {this.coordinate = coordinate;}

	/**
	 * Metodo setter per settare le coordinate mediante indice di riga e colonna dell'entità
	 * @param row l'indice di riga (coordinata y)
	 * @param col l'indice di colonna (coordinata x)
	 * */
	public void setCoordinate(final int row, final int col){
		this.coordinate.setRow(row);
		this.coordinate.setCol(col);
	}

	/**
	 * Metodo getter per ottenere la coordinata y, o indice di riga dell'entità
	 * @return l'indice di riga (coordinata y)
	 * */
	public int getRow(){ return coordinate.getRow(); }

	/**
	 * Metodo getter per ottenere la coordinata x, o indice di colonna dell'entità
	 * @return l'indice di colonna (coordinata x)
	 * */
	public int getCol(){ return coordinate.getCol(); }

	/**
	 * Controlla eventuali intersezioni.
	 * <p> Verifica se l'entità corrente si interseca con un'entità data in input. </p>
	 *
	 * @param e l'entita' con la quale si vuole effettuare la verifica.
	 * @return true, se c'è un'intersezione.
	 */
	public boolean isIntersect(final Entity e){
		return this.getRow() == e.getRow() && this.getCol() == e.getCol();
	}

	/**
	 * Factory method per la costruzione del Nodo
	 *
	 * @param map la mappa di appartenenza
	 * @return il nodo creato
	 */
	protected abstract Node makeNode(final AMap map);

	@Override
	public Node getNode() {return node; }

	@Override
	public void addNode(){ Controllers.rootView.addNode(this); }

	@Override
	public void removeNode(){ Controllers.rootView.removeNode(this);}
}
