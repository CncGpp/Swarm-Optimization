package application;

import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * La classe <code>DragWindowManager</code> gestisce lo spostamento degli stage non decorati.
 * <p>Essa permette di spostare uno stage non decorato con un drag & drop in qualsiasi posizione
 * della finestra.</p>
 *
 * @author Cianci Giuseppe Pio
 */
public class DragWindowManager {
	/** L'offset orizzontale dello spostamento*/
	private double xOffset;

	/** L'offset verticale dello spostamento*/
	private double yOffset;

	/**
	 * Istanzia un nuovo <code>DragWindowManager</code>.
	 *
	 * @param stage Lo stage che si vuole monitorare
	 * @param scene la scena attuale sullo stage monitorato
	 */
	public DragWindowManager(final Stage stage, final Scene scene) {
        //Uso una funzione lambda per gestire l'evento di pressione del mouse
        scene.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        //Uso una funzione lambda per gestire l'evento di trascinamento del mouse
        scene.setOnMouseDragged(event -> {
        	stage.setX(event.getScreenX() + xOffset);
        	stage.setY(event.getScreenY() + yOffset);
  });
}
}
