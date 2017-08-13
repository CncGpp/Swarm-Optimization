package view.gui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.player.PlayerScore;
import util.Gloabal.R;

public class PlayerListViewCell extends ListCell<PlayerScore>{
    @FXML // fx:id="position"
    private Label position; // Value injected by FXMLLoader

    @FXML // fx:id="img_id"
    private ImageView img_id; // Value injected by FXMLLoader

    @FXML // fx:id="cognome_id"
    private Label cognome_id; // Value injected by FXMLLoader

    @FXML // fx:id="nome_id"
    private Label nome_id; // Value injected by FXMLLoader

    @FXML // fx:id="time_id"
    private Label time_id; // Value injected by FXMLLoader

    @FXML // fx:id="anchorPane_id"
    private AnchorPane anchorPane_id; // Value injected by FXMLLoader

    private FXMLLoader mLLoader;

	@Override
	protected void updateItem(PlayerScore player, boolean empty) {
		super.updateItem(player, empty);

	       if(empty || player == null) {
	            setText(null);
	            setGraphic(null);
	       } else {
	            if (mLLoader == null) {
	                mLLoader = new FXMLLoader(getClass().getResource("/view/gui/PlayerListViewCell.fxml"));
	                mLLoader.setController(this);

	                try { mLLoader.load(); } catch (IOException e) {
	                	System.err.println("Non è stato possibile caricare il file: " + "/PlayerListViewCell.fxml");
	                	e.printStackTrace();  return; }
	            }

	            // SETTO I VALORI DELLA CELLA
	            nome_id.setText(player.getName());
	            cognome_id.setText(player.getSurname());
	            position.setText(String.valueOf(this.getIndex() + 1));
	            time_id.setText(getTimeStringfromMills(player.getTime()));

	            // SETTO L'IMMAGINE AD HOC PER I PRIMI TRE CLASSIFICATI
	            switch (this.getIndex()) {
	            case 0: img_id.setImage(new Image(R.FIRST_ICON_URI)); break;
				case 1: img_id.setImage(new Image(R.SECOND_ICON_URI)); break;
				case 2: img_id.setImage(new Image(R.THIRD_ICON_URI)); break;
				default: img_id.setImage(null); break;
				}

	            setText(null);
	            setGraphic(anchorPane_id);
	       }

	}

    private String getTimeStringfromMills(long milliseconds){
    	int s = (int) ((milliseconds / 1000) % 60);
    	int m = (int) ((milliseconds / 1000) / 60);
    	int ms = (int) (milliseconds % 1000);

    	return String.format("+%02d:%02d.%03d", m,s,ms);
    }



}
