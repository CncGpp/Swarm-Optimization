package view;

import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
import util.Stage;
import view.gui.PlayerLoginDialog;

public class BottomViewController {

	private Game g;
	PlayerData playerData = new PlayerData();
	private Stage stage = new Stage();

    @FXML
    private ImageView startButton;

    @FXML
    private ImageView loginButton;

    @FXML
    private ImageView settingButton;

    @FXML
    private Label stageLabel;

    /// METODI
    @FXML
    void startButtonHandle(MouseEvent event) {
    	if(!playerData.isLogged() && !loginButtonHandler()) return;

    	switch (g.getStatus()) {
			case NOTREADY:	initializeNewGame(); break;
			case READY: 	startGame(); break;
			case RUNNING:	pauseGame(); break;
			case PAUSED:	startGame(); break;
			case ENDED: 	initializeNewGame(); break;
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

    private void initializeNewGame(){
    	this.pauseGame();
		this.stage = new Stage(0);
		g.newGame(this.stage);
		Chronometer.set(0);
		stageLabel.setText( 1 + stage.getStageNumber() + "");
    }
    private void initializeGameStage(){
		this.pauseGame();
		g.newGame(this.stage);
		stageLabel.setText( 1 + this.stage.getStageNumber() + "");
    }
    private void startGame(){
		 Chronometer.start();
		 g.startGame();
		 startButton.setImage(new Image(Gloabal.R.PAUSE_ICON_URI));
    }
    private void pauseGame(){
		 Chronometer.pause();
		 g.pauseGame();
		 startButton.setImage(new Image(Gloabal.R.START_ICON_URI));
    }

    public void stageEnded(){
    	if(!stage.nextStage()){
    		System.out.println("Il gioco è finito");
    		playerData.getCurrentPlayer().setTime(Chronometer.getTotalTime());
    		Controllers.rankController.submitScore(playerData.getCurrentPlayer());
    		startButton.setImage(new Image(Gloabal.R.RESTART_ICON_URI));
    	} else {
    		initializeGameStage();
    	}
    }

    private boolean logoutScene(){
		Alert al = new Alert(AlertType.CONFIRMATION);						//Creo un alert di conferma
		al.setTitle("Cambio giocatore");
		al.setHeaderText("Vuoi realmente cambiare giocatore?");
		al.setContentText("La partita attuale verrà salvatae potrà essere ripristinata al prossimo login.");
		al.setGraphic(new ImageView(Gloabal.R.CHANGE_ICON_URI));

		Optional<ButtonType> result = al.showAndWait();						//Verifico la scelta
		if(result.get() == ButtonType.CANCEL) return true;					//Se non si vuole proseguire con il cambio ritorno

		playerData.getCurrentPlayer().setTime(Chronometer.getTotalTime());
		playerData.addMemento(g.getMemento());

		this.initializeNewGame();

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
    		if(playerData.thereIsAMemento()) restoreMementoScene();

    		loginButton.setImage(new Image(Gloabal.R.CHANGE_ICON_URI));
    		return true;
    	}
    	return false;
    }

    private void restoreMementoScene(){
		Alert al = new Alert(AlertType.CONFIRMATION);						//Creo un alert di conferma
		al.setTitle("Ripristino partita");
		al.setHeaderText("Bentornato " + playerData.getCurrentPlayer().getName() + "!");
		al.setContentText("Vuoi ripristinare lo stato dell'ultima partita?");
		al.setGraphic(new ImageView(Gloabal.R.CHANGE_ICON_URI));

		Optional<ButtonType> result = al.showAndWait();						//Verifico la scelta
		if(result.get() == ButtonType.CANCEL) return ;						//Se non si vuole proseguire con il ripristino ritorno


    	playerData.restoreCurrentMemento();									//Altrimenti procedo al ripristino
 		Chronometer.set(playerData.getCurrentPlayer().getTime());
    }

}
