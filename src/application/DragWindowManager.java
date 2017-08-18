package application;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class DragWindowManager {
	private double xOffset;
	private double yOffset;

	public DragWindowManager(final Stage stage, final Scene scene) {
        //Lambda mouse event handler
        scene.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        //Lambda mouse event handler
        scene.setOnMouseDragged(event -> {
        	stage.setX(event.getScreenX() + xOffset);
        	stage.setY(event.getScreenY() + yOffset);
  });
}
}
