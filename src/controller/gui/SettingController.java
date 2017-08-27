package controller.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import strategy.*;
import util.Global.Controllers;
import util.Global.Settings;
/**
 * Classe controller del menù delle impostazioni
 * */
public class SettingController {

	/** Property che tiene traccia di eventuali modifiche alle impostazioni per abilitare il pulsante di conferma*/
	private BooleanProperty changedSettings = new SimpleBooleanProperty(false);

	/** Data delle strategie disponibili*/
	private List<ColonyStrategy> strategyData;

    @FXML private AnchorPane pane_setting;
    @FXML private Spinner<Integer> botCountSpinner;
    @FXML private ChoiceBox<String> strategySelector;
    @FXML private TextArea descriptionText;
    @FXML private Button confirmButton;

    @FXML
    void confirmButtonHandler(MouseEvent event) {
		Settings.BOT_NUMBER = botCountSpinner.getValue();
		Controllers.bottomViewController.initializeNewGame();
		changedSettings.set(false);
    }

    @FXML
    void initialize() {
        assert pane_setting != null : "fx:id=\"settingPane\" was not injected: check your FXML file 'SettingView.fxml'.";
        Controllers.settingController = this;
        this.hide();

        ///// INIZIALIZZO LA CHOICE BOX
        final ArrayList<String> names = new ArrayList<>();
        strategyData.forEach( name -> names.add(name.getStrategyName()) );
        strategySelector.setItems( FXCollections.observableArrayList(names) );

        strategySelector.getSelectionModel().select(3);
        this.setStrategy(3);

        strategySelector.getSelectionModel().selectedIndexProperty().addListener( (observable, oldValue, newValue) -> {
        	this.setStrategy(newValue);
        	changedSettings.set(true);
        });

        //// INIZIALIZZO LO SPINNER
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100, Settings.BOT_NUMBER);
        botCountSpinner.setValueFactory(valueFactory);
        botCountSpinner.valueProperty().addListener((observer, oldValue, newValue)->{
        		if(newValue == null) return;
        		changedSettings.set(true);
        });

        /// INIZIALIZZO IL BOTTONE DI CONFERMA
        changedSettings.addListener((obs, oldValue, newValue) ->{
        	confirmButton.setDisable(!newValue.booleanValue());
        });
        confirmButton.setDisable(true);
    }

    private void setStrategy(final Number index){
    	descriptionText.setText(strategyData.get(index.intValue()).getStrategyDescriprion());
    	Settings.COLONY_STRATEGY = strategyData.get(index.intValue()).makeStrategy();
    }

    /** Costruttore*/
    public SettingController() {
    	strategyData = Arrays.asList(new OAS(), new AS(), new ACO(), new AtanAS(), new DPAS(), new LDPAS(), new RAS(), new TRAS());
	}


    /* METODI PER MOSTRARE/NASCONDERE/DISABILITARE IL MENU' */
    public void show(){
    	pane_setting.setVisible(true);
    	pane_setting.setDisable(false);
    }
    public void hide(){
    	pane_setting.setVisible(false);
    	pane_setting.setDisable(true);
    }
    public void toggle(){
    	pane_setting.setVisible(!pane_setting.isVisible());
    	pane_setting.setDisable(!pane_setting.isDisable());
    }

    public void enableSetting(){
    	botCountSpinner.setDisable(false);
    	strategySelector.setDisable(false);
    }

    public void toggleSetting(){
    	botCountSpinner.setDisable(!botCountSpinner.isDisable());
    	strategySelector.setDisable(!strategySelector.isDisable());
    }
}
