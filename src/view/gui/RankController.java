package view.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import util.Gloabal.Controllers;

public class RankController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane_rank;

    @FXML
    private ListView<?> listView_rank;

    @FXML
    void initialize() {
        assert pane_rank != null : "fx:id=\"pane_rank\" was not injected: check your FXML file 'RankView.fxml'.";
        assert listView_rank != null : "fx:id=\"listView_rank\" was not injected: check your FXML file 'RankView.fxml'.";
        Controllers.rankController = this;
        this.hide();
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