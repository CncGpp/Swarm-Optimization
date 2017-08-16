package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Game;
import util.Gloabal;
import view.BottomViewController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		BottomViewController bottomViewController = null;

		try {
			Font.loadFont(Gloabal.R.BOLD_FONT_URI, 24);
			Font.loadFont(Gloabal.R.MEDIUM_FONT_URI, 14);
			Font.loadFont(Gloabal.R.REGULAR_FONT_URI, 14);
			Font.loadFont(Gloabal.R.LIGHT_FONT_URI, 18);
			Font.loadFont(Gloabal.R.MONOSPACE_FONT_URI, 18);


	        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/view/RootView.fxml"));
	        BorderPane root = (BorderPane) rootLoader.load();

	        //CARICO LA UI DELL'APP
	        bottomViewController = loadBottomView(root);

	        Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Swarm Optimization - Progetto programmazione III   [Giuseppe Cianci Pio]");
			//primaryStage.show();

		} catch(Exception e) { e.printStackTrace(); }


		//*****      QUI CI STA LA MIA APP    ******
		Game g = new Game();
		g.newGame();

		if(bottomViewController != null) bottomViewController.setGame(g);

		//Una volta caricata tutta l'app mostro lo stage.
		stage.show();
	}

	private BottomViewController loadBottomView(BorderPane root) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BottomView.fxml"));
        root.setBottom(loader.load());
        return loader.getController();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
