package view;

import java.util.Optional;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Game;
import model.Stage;
import model.player.PlayerData;
import util.Gloabal.Controllers;
import util.Chronometer;
import util.Gloabal;

public class BottomViewController {

	private Main application;
	public void setApplication(final Main application){this.application = application;}

	private Game g;
	PlayerData playerData = new PlayerData();
	private Stage stage = new Stage();

    @FXML
    private Pane rankSelection, infoSelection, settingSelection;

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
    	if(!playerData.isLogged()) return;

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
    void settingButtonHandler(MouseEvent event){
    	Controllers.rankController.hide();
    	rankSelection.setVisible(false);
    	Controllers.infoController.hide();
    	infoSelection.setVisible(false);

    	Controllers.settingController.toggle();
    	settingSelection.setVisible(!settingSelection.isVisible());
    }

    @FXML
    void rankButtonHandler(MouseEvent event) {
    	Controllers.infoController.hide();
    	infoSelection.setVisible(false);
    	Controllers.settingController.hide();
    	settingSelection.setVisible(false);

    	Controllers.rankController.toggle();
    	rankSelection.setVisible(!rankSelection.isVisible());
    }

    @FXML
    void infoButtonHandler(MouseEvent event){
    	Controllers.rankController.hide();
    	rankSelection.setVisible(false);
    	Controllers.settingController.hide();
    	settingSelection.setVisible(false);

    	Controllers.infoController.toggle();
    	infoSelection.setVisible(!infoSelection.isVisible());
    }

    @FXML
    private void loginButtonHandler(){
    	if(!this.logoutScene()) g.pauseGame();
    }


    @FXML
    void initialize() {
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'BottomView.fxml'.";
        Controllers.bottomViewController = this;
        /*
        //TODO: PER ORA L'HO DISABILITATO...
        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(-1);
        settingButton.setEffect(grayscale);
        */
    }

    ////////// METODI DI CLASSE ////////////////
    public void setGame(Game game){ this.g = game;}
    public void cloaseAll(){
    	Controllers.rankController.hide();
    	Controllers.infoController.hide();
    	Controllers.settingController.hide();
    	rankSelection.setVisible(false);
    	infoSelection.setVisible(false);
    	settingSelection.setVisible(false);
	}

    private void toggleSettingStatus(){

    	if(settingButton.isDisabled()){
    		settingButton.setDisable(false);
    		settingButton.setEffect(null);
    		return;
    	}

        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(-1);
        settingButton.setEffect(grayscale);
        settingButton.setDisable(true);
    }

    public void initializeNewGame(){
    	this.pauseGame();
		this.stage = new Stage(0);
		g.newGame(this.stage);
		Chronometer.set(0);
		stageLabel.setText( 1 + stage.getStageNumber() + "");
		//toggleSettingStatus();
    }
    private void initializeGameStage(){
		this.pauseGame();
		g.newGame(this.stage);
		stageLabel.setText( 1 + this.stage.getStageNumber() + "");
    }
    private void startGame(){
    	 cloaseAll();
    	 toggleSettingStatus();
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
    	toggleSettingStatus();
    	if(!stage.nextStage()){
    		System.out.println("Il gioco � finito");
    		playerData.getCurrentPlayer().setTime(Chronometer.getTotalTime());
    		Controllers.rankController.submitScore(playerData.getCurrentPlayer());
    		startButton.setImage(new Image(Gloabal.R.RESTART_ICON_URI));
    	} else {
    		initializeGameStage();
    		this.startGame();
    	}
    }

    private boolean logoutScene(){
		Alert al = new Alert(AlertType.CONFIRMATION);						//Creo un alert di conferma
		al.setTitle("Cambio giocatore");
		al.setHeaderText("Vuoi realmente cambiare giocatore?");
		al.setContentText("La partita attuale verr� salvatae potr� essere ripristinata al prossimo login.");
		al.setGraphic(new ImageView(Gloabal.R.CHANGE_ICON_URI));

		Optional<ButtonType> result = al.showAndWait();						//Verifico la scelta
		if(result.get() == ButtonType.CANCEL) return true;					//Se non si vuole proseguire con il cambio ritorno

		playerData.getCurrentPlayer().setTime(Chronometer.getTotalTime());
		playerData.addMemento(g.getMemento());

		this.initializeNewGame();

		startButton.setImage(new Image(Gloabal.R.START_ICON_URI));

		playerData.logoutPlayer();											//Altrimenti effettuo il "logout"
		this.application.setLoginView();
		return false;
    }

}
