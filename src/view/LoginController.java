package view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.player.PlayerData;
import model.player.PlayerScore;
import util.Chronometer;
import util.Gloabal;

public class LoginController {

	private Main application;
	public void setApplication(final Main application){ this.application = application;}

	private PlayerData playerData =  new PlayerData();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private void loginButtonHandler(MouseEvent event) {
    	PlayerScore ps = new PlayerScore(nameField.getText(), surnameField.getText(), 0);
    	playerData.loginPlayer(ps);
    	application.setGameView();
    	if(playerData.thereIsAMemento()) restoreMementoScene();
    }

    @FXML
    void initialize() {
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert surnameField != null : "fx:id=\"surnameField\" was not injected: check your FXML file 'LoginView.fxml'.";
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
