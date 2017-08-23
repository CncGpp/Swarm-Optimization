package controller.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import util.Global.C;
import util.Global.Controllers;

public class InfoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane_rank;

    @FXML
    private Circle bot_figure;

    @FXML
    private Rectangle pheromone_figure;

    @FXML
    private Rectangle start_figure;

    @FXML
    private Rectangle end_figure;

    @FXML
    private Rectangle manhole_figure;

    @FXML
    void initialize() {
        assert pane_rank != null : "fx:id=\"pane_rank\" was not injected: check your FXML file 'InfoView.fxml'.";
        assert bot_figure != null : "fx:id=\"bot_figure\" was not injected: check your FXML file 'InfoView.fxml'.";
        assert pheromone_figure != null : "fx:id=\"pheromone_figure\" was not injected: check your FXML file 'InfoView.fxml'.";
        assert start_figure != null : "fx:id=\"start_figure\" was not injected: check your FXML file 'InfoView.fxml'.";
        assert end_figure != null : "fx:id=\"end_figure\" was not injected: check your FXML file 'InfoView.fxml'.";
        assert manhole_figure != null : "fx:id=\"manhole_figure\" was not injected: check your FXML file 'InfoView.fxml'.";
        this.hide();
        Controllers.infoController = this;

        bot_figure.setFill(C.MICROBOT_COLOR);
        start_figure.setFill(C.START_COLOR);
        end_figure.setFill(C.END_COLOR);
        manhole_figure.setFill(C.MANHOLE_COLOR);
    }

    public void show(){
    	pane_rank.setVisible(true);
    	pane_rank.setDisable(false);
    }
    public void hide(){
    	pane_rank.setVisible(false);
    	pane_rank.setDisable(true);
    }
    public void toggle(){
    	pane_rank.setVisible(!pane_rank.isVisible());
    	pane_rank.setDisable(!pane_rank.isDisabled());
    }
}
