package view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import util.Gloabal.Controllers;

public class ChronometerController {

    @FXML
    private Label minutes;

    @FXML
    private Label seconds;

    @FXML
    private Label milliseconds;

    @FXML
    void initialize() { Controllers.chronometerController = this; }

    public void updateTimer(final long milliseconds){
    	int s = (int) ((milliseconds / 1000) % 60);
    	int m = (int) ((milliseconds / 1000) / 60);
    	int ms = (int) (milliseconds % 1000);

    	this.milliseconds.setText(String.format("%03d", ms));
    	this.seconds.setText(String.format("%02d", s));
    	this.minutes.setText(String.format("%02d", m));
    }

}
