package view.gui;

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
import util.Gloabal.Controllers;
import util.Gloabal.Settings;

public class SettingController {

	private BooleanProperty changedSettings = new SimpleBooleanProperty(false);
	private List<ColonyStrategy> strategyData;

    @FXML
    private AnchorPane pane_setting;

    @FXML
    private Spinner<Integer> botCountSpinner;

    @FXML
    private ChoiceBox<String> strategySelector;

    @FXML
    private TextArea descriptionText;

    @FXML
    private Button confirmButton;

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

        ///// Inizializzo la Choice Box
        strategySelector.setItems( FXCollections.observableArrayList(
        		"Ant-System", "Arctan Ant-System", "Decreasing Path Ant-System", "Linear Decreasing Path Ant-System",
        		"True Random"
        		));

        strategySelector.getSelectionModel().select(1);
        this.setStrategy(1);

        strategySelector.getSelectionModel().selectedIndexProperty().addListener( (observable, oldValue, newValue) -> {
        	this.setStrategy(newValue);
        	changedSettings.set(true);
        });

        //// Inizializzo lo spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100, Settings.BOT_NUMBER);
        botCountSpinner.setValueFactory(valueFactory);
        botCountSpinner.valueProperty().addListener((observer, oldValue, newValue)->{
        		if(newValue == null) return;
        		changedSettings.set(true);
        });

        /// Inizializzo il bottone di conferma
        changedSettings.addListener((obs, oldValue, newValue) ->{
        	confirmButton.setDisable(!newValue.booleanValue());
        });
        confirmButton.setDisable(true);
    }

    private void setStrategy(final Number index){
    	descriptionText.setText(strategyData.get(index.intValue()).getStrategyDescriprion());
    	Settings.COLONY_STRATEGY = strategyData.get(index.intValue()).makeStrategy();
    }

    public SettingController() {
    	strategyData = Arrays.asList(new AS(), new AtanAS(), new DPAS(), new LDPAS(), new TRAS());
	}

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
    	pane_setting.setDisable(!pane_setting.isDisabled());
    }
}
