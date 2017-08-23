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
import util.Global;
/**
 * La classe {@code LoginController} si occupa di gestire la LoginView.fxml
 * <p> Tramite questo controller si gestisce il login, la validazione dell'username e l'eventuale ripristino dello stato precedente</p>
 * */
public class LoginController {

	/** L'istanza della nostra applicazione corrente in modo da mostrare/nascondere la schermata di login*/
	private Main application;
	public void setApplication(final Main application){ this.application = application;}

	/** Punto di accesso ai dati dei giocatori*/
	private PlayerData playerData =  new PlayerData();

	/** Durata dell'animazione dell'aboutPane*/
	private final int animationDuration = 600;

    @FXML private ImageView aboutButton;
    @FXML private AnchorPane aboutPane;
    private double initialSize;
    private boolean isCollapsed = false;
    @FXML private AnchorPane loginPane;
    @FXML private TextField nameField;
    @FXML  private TextField surnameField;
    @FXML private Button loginButton;

    @FXML
    private void loginButtonHandler(MouseEvent event) {
    	PlayerScore ps = new PlayerScore(nameField.getText(), surnameField.getText(), 0);
    	playerData.loginPlayer(ps);
    	application.setGameView();
    	if(playerData.thereIsAMemento()) restoreMementoScene();
    }

    @FXML
    private void loginButtonKeyHandler(KeyEvent event) {
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

        /// AGGIUNGO I LISTENER AI TEXTFIELD PER CAMBIARE LO STILE QUANDO C'E' UN INPUT NON CORRETTO
        nameField.textProperty().addListener((obs, oldValue, newValue) ->{
        	 if(!validateInput(newValue)) this.setError(nameField); else removeError(nameField);
        	 loginButton.setDisable(!validate());
        });
        surnameField.textProperty().addListener((obs, oldValue, newValue) ->{
        	 if(!validateInput(newValue)) this.setError(surnameField); else removeError(surnameField);
	       	 loginButton.setDisable(!validate());
       });

        /// CREO UNA SHAPE CHE FUNGERA' DA CLIPPER PER 'TAGLIRARE' I BORDI DELL' aboutPane
        final Rectangle clipper = new Rectangle();
        clipper.setArcHeight(7);													//Setto i raggi di curvatura del bordo
        clipper.setArcWidth(7);
        aboutPane.setClip(clipper);													//Setto il clipper al pane
        aboutPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {	//Lego le dimensioni del clipper a quelle del pane
        	clipper.setWidth(newValue.getWidth());
        	clipper.setHeight(newValue.getHeight());
        });

        /// INIZIALIZZO L'ANIMAZIONE
       initialSize = aboutPane.prefWidth(-1);		//Ottengo le dimensioni iniziali del pane aperto
       aboutPane.setMaxWidth(0);					//Lo chiudo riducendo a 0 la larghezza
       aboutPane.setPrefWidth(0);
       aboutPane.setMinWidth(0);
       isCollapsed = true;

    }

    private boolean validate(){
    	return validateInput(nameField.getText()) && validateInput(surnameField.getText());
    }
    private boolean validateInput(final String s){
    	return (s != null) && s.matches("[A-Za-z0-9_]+");
    }

    private void setError(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if(!styleClass.contains("error"))  styleClass.add("error");
    }
    private void removeError(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        styleClass.removeAll(Collections.singleton("error"));
    }

    /**
     * Funzione che permette il ripristino dello stato
     * <p>Nel caso in cui ci sia un 'salvataggio' di una partita precedente viene mostrato un alert
     * che chiede al giocatore se vuole o meno ripristinare la partita</p>
     * */
    private void restoreMementoScene(){
		Alert al = new Alert(AlertType.CONFIRMATION);						//Creo un alert di conferma
		al.setTitle("Ripristino partita");
		al.setHeaderText("Bentornato " + playerData.getCurrentPlayer().getName() + "!");
		al.setContentText("Vuoi ripristinare lo stato dell'ultima partita?");
		al.setGraphic(new ImageView(Global.R.CHANGE_ICON_URI));

		Optional<ButtonType> result = al.showAndWait();						//Verifico la scelta
		if(result.get() == ButtonType.CANCEL) return ;						//Se non si vuole proseguire con il ripristino ritorno

    	playerData.restoreCurrentMemento();									//Altrimenti procedo al ripristino
 		Chronometer.set(playerData.getCurrentPlayer().getTime());
    }
}
