package view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Game;
import util.Gloabal.Controllers;
import util.Chronometer;
import util.Memento;
import util.Stage;

public class BottomViewController {

	private Game g;
	private Memento m = null;
	private Stage stage = new Stage();

    @FXML
    private ImageView startButton;

    /// METODI
    @FXML
    void startButtonHandle(MouseEvent event) {
    	System.out.println("Hai premuto start");

    	switch (g.getStatus()) {
			case NOTREADY:
				 Chronometer.set(0);
				 g.newGame(stage);
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
			case ENDED: System.out.println("Il gioco è terminato!"); break;
			default: break;
		}
    }

    @FXML
    void rankButtonHandler(MouseEvent event) {
    	Controllers.rankController.toggle();
    }

    @FXML
    void saveGame(MouseEvent event){
    	System.out.println("Hai premuto memento");
    	if(m == null) {
    		m = g.getMemento();
    		g.pauseGame();
    		//Get time
    		g.remove();
    		g.newGame();
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

    ////////// METODI DI CLASSE ////////////////
    public void setGame(Game game){ this.g = game;}

    public void stageEnded(){
    	//if gli stage sono finiti  -> submist score
    	// else crea un gioco con il prossimo stage ----<>>> Stesso il gioco dovrebbe andare al prossimo stage?
    	if(!stage.nextStage()){
    		System.out.println("Il gioco è finito");
    	} else {
    		g.remove();
    		g.newGame(stage);
    		g.add();
    	}
    }


}
