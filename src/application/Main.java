package application;

import java.io.IOException;

import controller.BottomViewController;
import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.game.AGame;
import model.game.Game;
import util.Global;
import util.Global.Controllers;
import util.Global.R;
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
	private static Stage gameStage;

	/** Lo stage contenente la schermata di 'login' */
	private static Stage loginStage;

	/** la scena di login*/
	private Scene loginScene;

	/** la scena di gioco */
	private Scene gameScene;

	/** Il gioco in s� */
	private static AGame game;

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		//Ottengo il delay dalla linea di comando [per debug]
		if(!getParameters().getNamed().isEmpty()){
			final int delay = Integer.parseInt(getParameters().getNamed().get("delay"));
			if(delay > 0) Global.Settings.UPDATE_DELAY = delay;
		}

		gameStage = primaryStage;
		loginStage = new Stage();
		loginStage.initStyle(StageStyle.TRANSPARENT);			//Rendo trasparenti e non decorati gli stage
		primaryStage.initStyle(StageStyle.TRANSPARENT);


		try {
			/* Applico l'icona all'applicazione */
			gameStage.getIcons().add(new Image(R.APP_ICON_URI));
			loginStage.getIcons().add(new Image(R.APP_ICON_URI));

			// Carico i font scelti per l'interfaccia grafica
			Font.loadFont(Global.R.BOLD_FONT_URI, 24);
			Font.loadFont(Global.R.MEDIUM_FONT_URI, 14);
			Font.loadFont(Global.R.REGULAR_FONT_URI, 14);
			Font.loadFont(Global.R.LIGHT_FONT_URI, 18);
			Font.loadFont(Global.R.MONOSPACE_FONT_URI, 18);

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


		/*
		Path p = new Path();
		//p.addNode(new Vertex(0, 102, 2));
		 File file = new File("DIOCAN");
		 */
	     /*
		 if (file != null) {
	            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
	                out.writeObject(p);
	            } catch (Exception exc) {
	                exc.printStackTrace();
	            }
	         }
	     */
		 /*
		 if (file != null) {
	            try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(file))) {
	                p = (Path) out.readObject();
	            } catch (Exception exc) {
	                exc.printStackTrace();
	            }
	         }
		 System.out.println(p.getLenght());
		 */
	}

	public static AGame getGame(){ return game;}

	/**
	 * Inizializza il gioco
	 * <p> Inizializza il gioco nella prima fase di avvio dell'applicazione, controllando lo stato delle view utilizzzate</p>
	 */
	private void gameInitializer(){
		if(game == null)
			game = new Game();

		game.init();
		if(Controllers.bottomViewController == null) throw new IllegalStateException("La bottom view non � stata inizializzata!");
		if(Controllers.rootView == null) throw new IllegalStateException("La root view non � stata inizializzata!");
		Controllers.bottomViewController.setGame(game);
	}

	/**
	 * Carica la {@code BottomView} del gioco
	 * <p> Viene caricata la porzione bassa della view adibita alla gestione dei comandi dell'utente </p>
	 *
	 * @param root il BorderPane dove la view varr� aggiunta in basso
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
	/** Centra la finestra di gioco*/
	public static void centerGameStage(){gameStage.centerOnScreen();}

	/** Ridimensiona la finestra di gioco per contenere il gioco*/
	public static void resizeGameStage(){gameStage.sizeToScene();}

	/**
	 * Il metodo Main non viene utilizzato per il lancio dell'applicazione.
	 * <p> Esso � definito soltanto per motivi di compatibilita' con alcuni IDE che non sono in grado di creare un louncher
	 * Javafx nello JAR creato. </p>
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
