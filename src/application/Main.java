package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Game;
import util.Gloabal;
import util.Gloabal.Controllers;
import view.BottomViewController;
import view.LoginController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static Stage stage;
	private static Stage loginStage;
	private static Game game;

	private Scene loginScene;
	private Scene gameScene;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		loginStage = new Stage();
		loginStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.initStyle(StageStyle.TRANSPARENT);


		try {
			Font.loadFont(Gloabal.R.BOLD_FONT_URI, 24);
			Font.loadFont(Gloabal.R.MEDIUM_FONT_URI, 14);
			Font.loadFont(Gloabal.R.REGULAR_FONT_URI, 14);
			Font.loadFont(Gloabal.R.LIGHT_FONT_URI, 18);
			Font.loadFont(Gloabal.R.MONOSPACE_FONT_URI, 18);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RootView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			gameScene = new Scene(root);
			loadBottomView(root).setApplication(this);
			gameScene.setFill(Color.TRANSPARENT);

			loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
			Parent parent = loader.load();
			((LoginController) loader.getController()).setApplication(this);
			loginScene = new Scene(parent);
			loginScene.setFill(Color.TRANSPARENT);


			new DragWindowManager(loginStage, loginScene);
			new DragWindowManager(primaryStage, gameScene);

		} catch(Exception e) { e.printStackTrace(); }

		gameInitializer();
		setLoginView();
	}

	private void gameInitializer(){
		if(game == null) {
			game = new Game();
			game.init();
		}
		game.init();
		if(Controllers.bottomViewController == null) throw new IllegalStateException("La view non è stata inizializzata!");
			Controllers.bottomViewController.setGame(game);
	}

	private BottomViewController loadBottomView(BorderPane root) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BottomView.fxml"));
        root.setBottom(loader.load());
        return loader.getController();
	}


	public void setLoginView() {
		stage.hide();

		loginStage.setScene(loginScene);
		loginStage.centerOnScreen();
		loginStage.show();
	}

	public void setGameView(){
		loginStage.hide();

		stage.setScene(gameScene);
		stage.setResizable(false);
		stage.setTitle("Swarm Optimization - Progetto programmazione III   [Giuseppe Cianci Pio]");
		stage.centerOnScreen();
		stage.show();
	}




	public static void main(String[] args) {
		launch(args);
	}
}
