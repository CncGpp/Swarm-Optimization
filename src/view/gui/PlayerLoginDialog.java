package view.gui;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import model.player.PlayerScore;
import util.Gloabal.R;

public class PlayerLoginDialog extends Dialog< PlayerScore >{

	public PlayerLoginDialog(){
		this.setTitle("Inserisci i tuoi dati");
		this.setHeaderText("Inserisci il tuo nome e cognome!");

		this.setGraphic(new ImageView(R.USER_ICON_URI));
		this.initStyle(StageStyle.UTILITY);

		//Definisco i bottoni necessari
		ButtonType confirmButtonType = new ButtonType("Conferma", ButtonData.OK_DONE);
		this.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

		// Definisco la UI
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 100, 10, 10));

		TextField name = new TextField();
		name.setPromptText("Inserisci il tuo nome.");
		TextField surname = new TextField();
		surname.setPromptText("Inserisci il tuo cognome.");

		grid.add(new Label("Nome:"), 0, 0);
		grid.add(name, 1, 0);
		grid.add(new Label("Cognome:"), 0, 1);
		grid.add(surname, 1, 1);

		// Ricerco il bottone li login nella UI e lo disabilito momentaneamente
		Node confirmButton = this.getDialogPane().lookupButton(confirmButtonType);
		confirmButton.setDisable(true);

		// I due campi non devono essere vuoti! quindi se lo sono disabilito il login con questi listener
		name.textProperty().addListener((observable, oldValue, newValue) -> {
		    confirmButton.setDisable(newValue.trim().isEmpty() || surname.getText().isEmpty());
		});
		surname.textProperty().addListener((observable, oldValue, newValue) -> {
		    confirmButton.setDisable(newValue.trim().isEmpty() || name.getText().isEmpty());
		});

		//Aggiungo quindi il content
		this.getDialogPane().setContent(grid);

		// RequPossizione il cursore sul primo text field
		Platform.runLater(() -> name.requestFocus());

		//Converto il risultato in un oggetto PlayerScore
		this.setResultConverter(dialogButton -> {
		    if (dialogButton == confirmButtonType) {
		        return new PlayerScore( fix(name.getText()), fix(surname.getText()),0l);
		    }
		    return null;
		});

	}

	//Rimuove caratteri speciali come spazi e tab dalla stringa
	private String fix(String s){
		String ss = s.replaceAll("\\s+","");
		return ss;
	}
}