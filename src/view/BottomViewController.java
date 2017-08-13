package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Game;
import util.Gloabal.Controllers;
import util.Chronometer;
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


    /// METODI

    @FXML
    void startButtonHandle(MouseEvent event) {
    	System.out.println("Hai premuto start");

    	switch (g.getStatus()) {
			case NOTREADY:
				 Chronometer.set(0);
				 g.createNewGame();
				 break;
			case READY:
				 Chronometer.start();
				 g.startGame();
				 break;
			case RUNNING:
				 Chronometer.pause();
				 g.pauseGame();
				 break;
			case PAUSED:
				 Chronometer.start();
				 g.startGame();
				 break;
			case ENDED: System.out.println("Il gioco � terminato!"); break;
			default: break;
		}
    }

    @FXML
    void saveGame(MouseEvent event){
    	System.out.println("Hai premuto memento");
    	if(m == null) {
    		m = g.getMemento();
    		g.pauseGame();
    		//Get time
    		g.remove();
    		g.createNewGame();
    		g.add();
    	}
    	else {
    		g.pauseGame();
    		g.remove();
    		m.restoreMemento();
    		g.add();
    		//RestoreTime
    	}
    }

    @FXML
    void initialize() {
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'BottomView.fxml'.";
        Controllers.bottomViewController = this;
    }

    public void setGame(Game game){ this.g = game;}

}
