package view.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.player.PlayerScore;
import model.player.Rank;
import util.Gloabal.Controllers;
import util.Gloabal.R;

public class RankController {

	private Rank ranks;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane_rank;

    @FXML
    private ListView<PlayerScore> listView_rank; // Value injected by FXMLLoader
    private ObservableList<PlayerScore> observableList_rank = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        assert pane_rank != null : "fx:id=\"pane_rank\" was not injected: check your FXML file 'RankView.fxml'.";
        assert listView_rank != null : "fx:id=\"listView_rank\" was not injected: check your FXML file 'RankView.fxml'.";
        Controllers.rankController = this;
        this.hide();

        //CARICO LA CLASSIFICA
        ranks = new Rank(R.RANK_URI);
        observableList_rank = ranks.getRankList();


        //IMPOSTO GLI ITEM NELLA LISTA DELLA CLASSIFICA
        listView_rank.setItems(observableList_rank);
        listView_rank.setCellFactory(listView_rank -> new PlayerListViewCell());	//Cell factory
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