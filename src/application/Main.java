package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Game;
import util.Gloabal;
import util.Gloabal.Controllers;
import util.Gloabal.R;
import view.BottomViewController;
import view.LoginController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.fxml.FXMLLoader;

/**
 * La classe {@code Main} stabilisce il punto di ingresso dell'applicazione.
 * @author Cianci Giuseppe Pio
 */
public class Main extends Application {

	/** Lo stage contenente la schermata di gioco */
	public static Stage gameStage;

	/** Lo stage contenente la schermata di 'login' */
	private static Stage loginStage;

	/** la scena di login*/
	private Scene loginScene;

	/** la scena di gioco */
	private Scene gameScene;

	/** Il gioco in sè */
	private static Game game;

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		gameStage = primaryStage;
		loginStage = new Stage();
		loginStage.initStyle(StageStyle.TRANSPARENT);			//Rendo trasparenti e non decorati gli stage
		primaryStage.initStyle(StageStyle.TRANSPARENT);


		try {
			/* Applico l'icona all'applicazione */
			gameStage.getIcons().add(new Image(R.APP_ICON_URI));
			loginStage.getIcons().add(new Image(R.APP_ICON_URI));

			// Carico i font scelti per l'interfaccia grafica
			Font.loadFont(Gloabal.R.BOLD_FONT_URI, 24);
			Font.loadFont(Gloabal.R.MEDIUM_FONT_URI, 14);
			Font.loadFont(Gloabal.R.REGULAR_FONT_URI, 14);
			Font.loadFont(Gloabal.R.LIGHT_FONT_URI, 18);
			Font.loadFont(Gloabal.R.MONOSPACE_FONT_URI, 18);

			/* Carico la RootView del gioco */
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RootView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			gameScene = new Scene(root);
			loadBottomView(root).setApplication(this);				//Carico la bottom view
			gameScene.setFill(Color.TRANSPARENT);					//Setto lo sfondo della scena a trasparente

			/* Carico la LoginView */
			loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
			Parent parent = loader.load();
			((LoginController) loader.getController()).setApplication(this);
			loginScene = new Scene(parent);
			loginScene.setFill(Color.TRANSPARENT);					//Setto lo sfondo della scena a trasparente

			/* Creo i Drag Manager necessari a gestire il drag & drop delle finestre non decorate */
			new DragWindowManager(loginStage, loginScene);
			new DragWindowManager(primaryStage, gameScene);

		} catch(Exception e) { e.printStackTrace(); }

		gameInitializer();
		setLoginView();
	}

	/**
	 * Inizializza il gioco
	 * <p> Inizializza il gioco nella prima fase di avvio dell'applicazione, controllando lo stato delle view utilizzzate</p>
	 */
	private void gameInitializer(){
		if(game == null)
			game = new Game();

		game.init();
		if(Controllers.bottomViewController == null) throw new IllegalStateException("La bottom view non è stata inizializzata!");
		if(Controllers.rootView == null) throw new IllegalStateException("La root view non è stata inizializzata!");
		Controllers.bottomViewController.setGame(game);
	}

	/**
	 * Carica la {@code BottomView} del gioco
	 * <p> Viene caricata la porzione bassa della view adibita alla gestione dei comandi dell'utente </p>
	 *
	 * @param root il BorderPane dove la view varrà aggiunta in basso
	 * @return il controller della {@code BottomView}
	 * @throws IOException Segnala un errore nella lettura del file {@code .fxml} che descrive la view.
	 */
	private BottomViewController loadBottomView(BorderPane root) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BottomView.fxml"));
        root.setBottom(loader.load());
        return loader.getController();
	}

	/**
	 * Mostra la schermata di login
	 * <p> Permette di mostrare a schermo lo stage e la scena di login nascondendo eventualmente quella di gioco.</p>
	 */
	public void setLoginView() {
		gameStage.hide();

		loginStage.setScene(loginScene);
		loginStage.centerOnScreen();
		loginStage.show();
	}

	/**
	 * Mostra la schermata di giovo
	 * <p> Permette di mostrare a schermo lo stage e la scena di gioco nascondendo eventualmente quella di login.</p>
	 */
	public void setGameView(){
		loginStage.hide();

		gameStage.setScene(gameScene);
		gameStage.setResizable(false);
		gameStage.setTitle("Swarm Optimization - Progetto programmazione III   [Giuseppe Cianci Pio]");
		gameStage.centerOnScreen();
		gameStage.show();
	}


	/**
	 * Il metodo Main non viene utilizzato per il lancio dell'applicazione.
	 * <p> Esso è definito soltanto per motivi di compatibilita' con alcuni IDE che non sono in grado di creare un louncher
	 * Javafx nello JAR creato. </p>
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
