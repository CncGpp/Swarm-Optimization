package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class RootViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane_rootView;

    @FXML
    private StackPane stackPane_layers;

    @FXML
    void initialize() {
        assert borderPane_rootView != null : "fx:id=\"borderPane_rootView\" was not injected: check your FXML file 'RootView.fxml'.";
        assert stackPane_layers != null : "fx:id=\"stackPane_layers\" was not injected: check your FXML file 'RootView.fxml'.";
    }

    public void addLayer(Pane layer){
    	stackPane_layers.getChildren().add(layer);
    }

}
