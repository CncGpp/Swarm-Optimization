package model;

import javafx.scene.Node;

/**
 * The Interface Drawable.
 * <p> Specifica la capacita' di un oggetto di essere disegnato aggiungendolo alla view della scena. </p>
 * @author Cianci Giuseppe Pio
 */
public interface Drawable {

	/**
	 * Ritorna il nodo da disegnare
	 * <p> Il nodo descrive e rappresenta la grafica dell'oggetto da disegnare.</p>
	 * @return the node
	 */
	public Node getNode();

	/**
	 * Aggiunge il nodo alla scena
	 */
	public void addNode();

	/**
	 * Rimuove il nodo dalla scena
	 */
	public void removeNode();
}
