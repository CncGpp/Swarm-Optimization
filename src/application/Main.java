package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Game;
import util.Gloabal;
import view.BottomViewController;
import view.RootViewController;
import view.map.EntityMapController;
import view.map.PheromoneMapController;
import view.map.TileMapController;
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
	        RootViewController rootController = rootLoader.getController();

	        //CARICO LA UI DELL'APP
	        bottomViewController = loadBottomView(root);


	        //CARICO LA UI DEL GIOCO
	        loadTileMap(rootController);
	        loadPheromoneMap(rootController);
	        loadEntityMap(rootController);

	        Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();


		} catch(Exception e) {
			e.printStackTrace();
		}


		//*****      QUI CI STA LA MIA APP    ******
		Game g = new Game();
		g.newGame();
		//g.add();

		if(bottomViewController != null)
		bottomViewController.setGame(g);

	}

	private TileMapController loadTileMap(RootViewController rvc) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/map/TileMap.fxml"));
        rvc.addLayer(loader.load());
        return loader.getController();
	}

	private PheromoneMapController loadPheromoneMap(RootViewController rvc) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/map/PheromoneMap.fxml"));
        rvc.addLayer(loader.load());
        return loader.getController();
	}

	private EntityMapController loadEntityMap(RootViewController rvc) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/map/EntityMap.fxml"));
        rvc.addLayer(loader.load());
        return loader.getController();
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
