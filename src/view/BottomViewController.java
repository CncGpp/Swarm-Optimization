package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.GameStatus;
import util.Gloabal.Controllers;
import util.Memento;

public class BottomViewController {


	private Game g;
	private Memento m = null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView startButton;

    @FXML
    private Label chronometer;

    @FXML
    void startButtonHandle(MouseEvent event) {
    	System.out.println("Hai premuto start");
    	if(g.getStatus() == GameStatus.PAUSED) g.startGame(); else g.pauseGame();
    }

    @FXML
    void saveGame(MouseEvent event){
    	System.out.println("Hai premuto memento");
    	if(m == null) {
    		m = g.getMemento();
    		g.pauseGame();
    		g.remove();
    		g.createNewGame();
    		g.add();
    	}
    	else {
    		g.pauseGame();
    		g.remove();
    		m.restoreMemento();
    		g.add();
    	}
    }

    @FXML
    void initialize() {
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'BottomView.fxml'.";
        Controllers.bottomViewController = this;
    }

    public void setGame(Game game){ this.g = game;}

    @Deprecated
    public void updateTimer(final long milliseconds){
        	int s = (int) ((milliseconds / 1000) % 60);
        	int m = (int) ((milliseconds / 1000) / 60);
        	int ms = (int) (milliseconds % 1000);

        	chronometer.setText(String.format("+%02d:%02d.%03d", m,s,ms));
    }
}
