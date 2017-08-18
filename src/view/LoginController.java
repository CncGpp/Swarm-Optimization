package view;

import java.util.Optional;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.player.PlayerData;
import model.player.PlayerScore;
import util.Chronometer;
import util.Gloabal;

public class LoginController {

	private Main application;
	public void setApplication(final Main application){ this.application = application;}

	private PlayerData playerData =  new PlayerData();

    @FXML
    private AnchorPane aboutPane;
    private double initialSize;
    private boolean isCollapsed = false;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private Button loginButton;

    @FXML
    private void loginButtonHandler(MouseEvent event) {
    	PlayerScore ps = new PlayerScore(nameField.getText(), surnameField.getText(), 0);
    	playerData.loginPlayer(ps);
    	application.setGameView();
    	if(playerData.thereIsAMemento()) restoreMementoScene();
    }

    @FXML
    private void aboutButtonHandler(MouseEvent event){
    	if(!isCollapsed){
    		HorizontalCollapse hc = new HorizontalCollapse(Duration.millis(800), aboutPane, 0);
    		hc.play();
    	} else {
    		HorizontalCollapse hc = new HorizontalCollapse(Duration.millis(800), aboutPane, initialSize);
    		hc.play();
    	}
		isCollapsed = !isCollapsed;
    }

    @FXML
    private void exitButtonHandler(MouseEvent event){
    	Platform.exit();
    }

    @FXML
    void initialize() {

        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert surnameField != null : "fx:id=\"surnameField\" was not injected: check your FXML file 'LoginView.fxml'.";
        loginButton.setDisable(true);

        nameField.textProperty().addListener((obs, oldValue, newValue) ->{
        	 loginButton.setDisable(!validate());
        });
        surnameField.textProperty().addListener((obs, oldValue, newValue) ->{
       	 loginButton.setDisable(!validate());
       });

        final Rectangle clipper = new Rectangle();
        clipper.setArcHeight(7);
        clipper.setArcWidth(7);
        aboutPane.setClip(clipper);
        aboutPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
        	clipper.setWidth(newValue.getWidth());
        	clipper.setHeight(newValue.getHeight());
        });

       initialSize = aboutPane.prefWidth(-1);
       aboutPane.setMaxWidth(0);
       aboutPane.setPrefWidth(0);
       aboutPane.setMinWidth(0);
       isCollapsed = true;

    }

    private boolean validate(){
    	if(nameField.getText().equals("")) return false;
    	if(surnameField.getText().equals("")) return false;
    	return true;
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
