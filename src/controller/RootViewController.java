package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import model.EntityDrawable;
import model.PheromoneDrawable;
import model.TileDrawable;
import util.Global.Controllers;
import view.map.EntityMapController;
import view.map.PheromoneMapController;
import view.map.TileMapController;

public class RootViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane_rootView;

    @FXML
    private StackPane stackPane_layers;

    @FXML private TileMapController tileMapController;
    @FXML private PheromoneMapController pheromoneMapController;
    @FXML private EntityMapController entityMapController;

    @FXML
    private void exitButtonHandler(MouseEvent event){
    	Platform.exit();
    	System.exit(0);
    }

    @FXML
    void initialize() {
        assert borderPane_rootView != null : "fx:id=\"borderPane_rootView\" was not injected: check your FXML file 'RootView.fxml'.";
        assert stackPane_layers != null : "fx:id=\"stackPane_layers\" was not injected: check your FXML file 'RootView.fxml'.";

        Controllers.rootView = this;
    }

    public void setSize(final double height, final double width){
    	tileMapController.setSize(height, width);
    	pheromoneMapController.setSize(height, width);
    	entityMapController.setSize(height, width);
    	Main.centerGameStage();
    }

    public void addNode(final EntityDrawable entityDrawable){ entityMapController.add(entityDrawable.getNode());}
    public void removeNode(final EntityDrawable entityDrawable){ entityMapController.remove(entityDrawable.getNode());}

    public void addNode(final TileDrawable tileDrawable){ tileMapController.add(tileDrawable.getNode()); }
    public void removeNode(final TileDrawable tileDrawable){ tileMapController.remove(tileDrawable.getNode()); }

    public void addNode(final PheromoneDrawable pheromoneDrawable){ pheromoneMapController.add(pheromoneDrawable.getNode()); }
    public void removeNode(final PheromoneDrawable pheromoneDrawable){ pheromoneMapController.remove(pheromoneDrawable.getNode()); }
}
