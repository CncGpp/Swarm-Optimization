package view.map;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class TileMapController {

    @FXML private AnchorPane drawingPane;

    public void setSize(final double height, final double width){
    	drawingPane.setMinSize(width, height);
    	drawingPane.setPrefSize(height, width);
    	drawingPane.setMaxSize(width, height);
    	Main.gameStage.sizeToScene();
    }

    @FXML
    void initialize(){}

    /** METODI DI MANIPOLAZIONE DELLA UI **/
    public void add(final Node node){ drawingPane.getChildren().add(node); }
    public void remove(final Node node){ drawingPane.getChildren().remove(node); }
    public void clear(){drawingPane.getChildren().clear();}
}
