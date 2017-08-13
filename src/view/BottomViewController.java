package view;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Game;
import model.player.PlayerData;
import model.player.PlayerScore;
import util.Gloabal.Controllers;
import util.Chronometer;
import util.Gloabal;
import util.Memento;
import util.Stage;
import view.gui.PlayerLoginDialog;

public class BottomViewController {

	private Game g;
	PlayerData playerData = new PlayerData();
	private Memento m = null;
	private Stage stage = new Stage();

    @FXML
    private ImageView startButton;

    @FXML
    private ImageView loginButton;

    @FXML
    private ImageView settingButton;

    /// METODI
    @FXML
    void startButtonHandle(MouseEvent event) {
    	if(!playerData.isLogged() && !loginButtonHandler()) return;

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
    	Controllers.infoController.hide();
    	Controllers.rankController.toggle();
    }

    @FXML
    void infoButtonHandler(MouseEvent event){
    	Controllers.rankController.hide();
    	Controllers.infoController.toggle();
    }

    @FXML
    private boolean loginButtonHandler(){
    	if(!playerData.isLogged())		//Se nessun player è loggato effettuo il login;
    		return loginScene();
    	 else							//Altrimenti il logout
    		return logoutScene();
    }


    @FXML
    void saveGame(MouseEvent event){
    	System.out.println("Hai premuto memento");
    	if(m == null) {
    		m = g.getMemento();
    		g.pauseGame();
    		//Get time
    		g.newGame();
    	}
    	else {
    		Controllers.tileMap.clear();
    		Controllers.entityMap.clear();
    		Controllers.pheromoneMap.clear();
    		m.restoreMemento();
    		g.pauseGame();
    		m = null;
    		//RestoreTime
    	}
    }

    @FXML
    void initialize() {
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'BottomView.fxml'.";
        Controllers.bottomViewController = this;

        //TODO: PER ORA L'HO DISABILITATO...
        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(-1);
        settingButton.setEffect(grayscale);
    }

    ////////// METODI DI CLASSE ////////////////
    public void setGame(Game game){ this.g = game;}
    public void cloaseAll(){
    	Controllers.rankController.hide();
    	Controllers.infoController.toggle();
	}

    public void stageEnded(){
    	//if gli stage sono finiti  -> submist score
    	// else crea un gioco con il prossimo stage ----<>>> Stesso il gioco dovrebbe andare al prossimo stage?
    	if(!stage.nextStage()){
    		System.out.println("Il gioco è finito");
    		playerData.getCurrentPlayer().setTime(Chronometer.getTotalTime());
    		Controllers.rankController.submitScore(playerData.getCurrentPlayer());
    	} else {
    		g.newGame(stage);
    	}
    }

    private boolean logoutScene(){
		Alert al = new Alert(AlertType.CONFIRMATION);						//Creo un alert di conferma
		al.setTitle("Cambio giocatore");
		al.setHeaderText("Vuoi realmente cambiare giocatore?");
		al.setContentText("La partita attuale verrà resettata");
		al.setGraphic(new ImageView(Gloabal.R.CHANGE_ICON_URI));

		Optional<ButtonType> result = al.showAndWait();						//Verifico la scelta
		if(result.get() == ButtonType.CANCEL) return true;					//Se non si vuole proseguire con il cambio ritorno

		Chronometer.set(0);
		stage = new Stage(0);
		g.pauseGame();
		g.remove();
		g.newGame(stage);

		startButton.setImage(new Image(Gloabal.R.START_ICON_URI));

		playerData.logoutPlayer();											//Altrimenti effettuo il "logout"
		loginButton.setImage(new Image(Gloabal.R.NOLOGIN_ICON_URI));
		return false;
    }

    private boolean loginScene(){
    	PlayerLoginDialog pid = new PlayerLoginDialog();
    	Optional<PlayerScore> player = pid.showAndWait();

    	if (player.isPresent() ){
    		playerData.loginPlayer( player.get() );
    		loginButton.setImage(new Image(Gloabal.R.CHANGE_ICON_URI));
    		return true;
    	}
    	return false;
    }

}
