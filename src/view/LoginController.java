package view;

import java.util.Collections;
import java.util.Optional;

import application.Main;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

	private final int animationDuration = 600;

    @FXML
    private ImageView aboutButton;

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
    void loginButtonKeyHandler(KeyEvent event) {
    	 if(event.getCode().equals(KeyCode.ENTER) && validate()){
    		 this.loginButtonHandler(null);
    	 }
    }

    @FXML
    private void aboutButtonHandler(MouseEvent event){
    	RotateTransition rt = new RotateTransition(Duration.millis(animationDuration), aboutButton);
    	HorizontalCollapse hc;
    	if(!isCollapsed){
    		rt.setByAngle(-180);
    		hc = new HorizontalCollapse(Duration.millis(animationDuration), aboutPane, 0);
    	} else {
    		hc = new HorizontalCollapse(Duration.millis(animationDuration), aboutPane, initialSize);
    		rt.setByAngle(180);
    	}

    	ParallelTransition pt = new ParallelTransition(rt,hc);
    	pt.play();
		isCollapsed = !isCollapsed;
    }

    @FXML
    private void exitButtonHandler(MouseEvent event){
    	Platform.exit();
    }

    @FXML
    void initialize() {
        assert aboutPane != null : "fx:id=\"aboutPane\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert loginPane != null : "fx:id=\"loginPane\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert surnameField != null : "fx:id=\"surnameField\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert aboutButton != null : "fx:id=\"aboutButton\" was not injected: check your FXML file 'LoginView.fxml'.";

        loginButton.setDisable(true);

        nameField.textProperty().addListener((obs, oldValue, newValue) ->{
        	 if(newValue.trim().length() == 0) this.setError(nameField); else removeError(nameField);
        	 loginButton.setDisable(!validate());
        });
        surnameField.textProperty().addListener((obs, oldValue, newValue) ->{
         if(newValue.trim().length() == 0) this.setError(surnameField); else removeError(surnameField);
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

    private void setError(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if(!styleClass.contains("error"))  styleClass.add("error");
    }
    private void removeError(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        styleClass.removeAll(Collections.singleton("error"));
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
