package controller;

import java.util.Optional;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.AGame;
import model.GameStatus;
import model.player.PlayerData;
import util.Global.Controllers;
import util.Chronometer;
import util.Global;

/**
 * La classe {@code BottomViewController} si occupa di gestire la BottowView.fxml
 * <p> Tramite questo controller si gestisce k'iterazione dell'utente con la GUI e con il gioco</p>
 * */
public class BottomViewController {

	/** L'istanza della nostra applicazione corrente in modo da mostrare/nascondere la schermata di gioco*/
	private Main application;
	public void setApplication(final Main application){this.application = application;}

	/** Il gioco che deve essere controllato da questo controller*/
	private AGame g;

	/** Punto di accesso ai dati dei giocatori*/
	PlayerData playerData = new PlayerData();

    @FXML private Pane rankSelection, infoSelection, settingSelection;
    @FXML private ImageView startButton;
    @FXML private ImageView loginButton;
    @FXML private ImageView settingButton;
    @FXML private Label stageLabel;

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
    	if(!this.logoutScene()) g.pause();
    }


    @FXML
    void initialize() {
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'BottomView.fxml'.";
        Controllers.bottomViewController = this;
    }

	/* 										+--------------------------------+
	 * 										|        METODI DI CLASSE        |
	 * 										+--------------------------------+          	                          */
    public void setGame(AGame game){ this.g = game;}
    /** Chiude tutte le 'finestre' aperte*/
    public void cloaseAll(){
    	Controllers.rankController.hide();
    	Controllers.infoController.hide();
    	Controllers.settingController.hide();
    	rankSelection.setVisible(false);
    	infoSelection.setVisible(false);
    	settingSelection.setVisible(false);
	}
    /** Effettua un toggle dello status delle impostazioni per abilitarle/disabilitarle*/
    private void toggleSettingStatus(){	Controllers.settingController.toggleSetting(); }

    /** Abilita le impostazioni*/
    private void enableSettingStatus(){ Controllers.settingController.enableSetting(); }

    /** Inizializza un nuovo gioco partendo dal primo stage*/
    public void initializeNewGame(){
    	this.pauseGame();
		g.init();
		Chronometer.set(0);
		stageLabel.setText( 1 + g.getStage().getStageNumber() + "");
		startButton.setImage(new Image(Global.R.START_ICON_URI));
		enableSettingStatus();
    }

    /** Inizializza un nuovo gioco ripartendo dallo stage corrente*/
    private void initializeGameStage(){
		this.pauseGame();
		g.restore();
		stageLabel.setText( 1 + g.getStage().getStageNumber() + "");
    }

    /** Avvia il gioco*/
    private void startGame(){
    	 cloaseAll();
    	 toggleSettingStatus();
		 Chronometer.start();
		 g.start();
		 startButton.setImage(new Image(Global.R.PAUSE_ICON_URI));
    }

    /** Mette in pausa il gioco*/
    private void pauseGame(){
		 Chronometer.pause();
		 g.pause();
		 startButton.setImage(new Image(Global.R.START_ICON_URI));
    }

    /** Metodo chiamato quando uno stage si è concluso, quando il gioco termina fa il submite dei punteggi*/
    public void stageEnded(){
    	toggleSettingStatus();

    	if(!g.getStage().nextStage()){
    		System.out.println("Il gioco è finito");
			Chronometer.pause();
    		playerData.getCurrentPlayer().setTime(Chronometer.getTotalTime());
    		Controllers.rankController.submitScore(playerData.getCurrentPlayer());
    		startButton.setImage(new Image(Global.R.RESTART_ICON_URI));
    		enableSettingStatus();
    	} else {
    		initializeGameStage();
    		this.startGame();
    	}
    }

    /** Gestisce il logout dell'utente mostrando un alert di conferma e ne salva*/
    private boolean logoutScene(){
		Alert al = new Alert(AlertType.CONFIRMATION);						//Creo un alert di conferma
		al.setTitle("Cambio giocatore");
		al.setHeaderText("Vuoi realmente cambiare giocatore?");
		al.setContentText("La partita attuale verrà salvatae potrà essere ripristinata al prossimo login.");
		al.setGraphic(new ImageView(Global.R.CHANGE_ICON_URI));

		Optional<ButtonType> result = al.showAndWait();						//Verifico la scelta
		if(result.get() == ButtonType.CANCEL) return true;					//Se non si vuole proseguire con il cambio ritorno...
																			//... Altrimenti inizio la procedura di logout
		if(g.getStatus() != GameStatus.ENDED){
			g.pause();
			playerData.getCurrentPlayer().setTime(Chronometer.getTotalTime());
			playerData.addMemento(g.getMemento());
		}

		this.initializeNewGame();

		playerData.logoutPlayer();											//effettuo il "logout"
		this.application.setLoginView();
		return false;
    }

}
