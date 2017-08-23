package view;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import model.entity.Entity;
import model.map.PheromoneLayer;
import model.map.TileLayer;
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

    public void addNode(final Entity entity){ entityMapController.add(entity.getNode());}
    public void removeNode(final Entity entity){ entityMapController.remove(entity.getNode());}

    public void addNode(final TileLayer tileLayer){ tileMapController.add(tileLayer.getNode()); }
    public void removeNode(final TileLayer tileLayer){ tileMapController.remove(tileLayer.getNode()); }

    public void addNode(final PheromoneLayer pheromoneLayer){ pheromoneMapController.add(pheromoneLayer.getNode()); }
    public void removeNode(final PheromoneLayer pheromoneLayer){ pheromoneMapController.remove(pheromoneLayer.getNode()); }
}
