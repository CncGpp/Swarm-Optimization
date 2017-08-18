package view.gui;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import strategy.*;
import util.Gloabal.Controllers;
import util.Gloabal.Settings;

/* FIXME: Mettere un pulsante di conferma nelle impostazioni... così non devo ricgreare il gioco ogni volta*/

public class SettingController {

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
        	Controllers.bottomViewController.initializeNewGame();
        });

        //// Inizializzo lo spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100, Settings.BOT_NUMBER);
        botCountSpinner.setValueFactory(valueFactory);
        botCountSpinner.valueProperty().addListener((observer, oldValue, newValue)->{
        		Settings.BOT_NUMBER = newValue.intValue();
        		Controllers.bottomViewController.initializeNewGame();
        });
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
