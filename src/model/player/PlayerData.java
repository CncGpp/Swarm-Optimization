package model.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.game.AGame;
import model.game.AGame.AGameMemento;

/**
 * la classe {@code PlayerData} gestisce i dati dei giocatori memorizzati e il giocatore che attualmente sta giocando
 * <p> La classe fornisce un punto di accesso alle sue strutture dati interne che sono statiche </p>
 */
public class PlayerData {

	/** Il giocatore correntemente loggato */
	private static PlayerScore currentPlayer = null;

	/** Il memento del giocatore loggato */
	private static AGameMemento currentMemento = null;

	/** Il percorso di default per il salvataggio dei mementi*/
	private static final String savePath = "save/";

	static {
		final File saveDir = new File(savePath);
		if(!saveDir.exists()) saveDir.mkdir();
	}

	/**
	 * La funzione effettua il login del player
	 * <p> Una volta effettuato il login si ricercano eventuali mementi presenti nella lista e li si ripristinano</p>
	 * @param playerScore the player score
	 */
	public void loginPlayer(final PlayerScore playerScore){
		if(playerScore == null) return;
		currentPlayer = playerScore;
		findCurrentPlayerMemento();
	}

	/**
	 * Effettua il logout del player
	 */
	public void logoutPlayer(){
		currentPlayer = null;
		currentMemento = null;
	}

	/**
	 * Aggiunge il memento relativo al player corrente nella lista dei mementi.
	 *
	 * @param memento the memento
	 */
	public void addMemento(final AGameMemento memento){
		if(currentPlayer == null) return;
		if(memento == null) return;

		final File f = new File(savePath + "/" + currentPlayer.getSurname() + currentPlayer.getName());
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f)) ) {
			out.writeObject( new PlayerMemento(memento, currentPlayer));
		} catch (IOException e) {
			System.err.println("C'è stato un problema durante il salvataggio del memento: \n" + f.getPath());
			e.printStackTrace();
		}
	}

	/**
	 * Ricerca un eventuale memento salvato e lo memorizza
	 */
	private void findCurrentPlayerMemento(){
		if(currentPlayer == null) return;

		final File f = new File(savePath + "/" + currentPlayer.getSurname() + currentPlayer.getName());
		if(!f.exists()) return;

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f)) ) {
			final PlayerMemento playerMemento = (PlayerMemento) in.readObject();
			currentPlayer = playerMemento.getPlayerScore();
			currentMemento = playerMemento.getMemento();
			in.close();
			f.delete();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("C'è stato un problema durante il caricamento del memento: \n" + f.getPath());
			e.printStackTrace();
		}
	}

	/**
	 * Ripristina il memento corrente
	 */
	public void restoreCurrentMemento(AGame game){
		if(currentMemento != null) game.restoreMemento(currentMemento);
	}

	/**
	 * Gets the current player.
	 * @return the current player
	 */
	public PlayerScore getCurrentPlayer(){return currentPlayer;}

	/**
	 * Checks if is logged.
	 * @return true, if is logged
	 */
	public boolean isLogged(){return currentPlayer!=null;}

	/**
	 * There is A memento.
	 * @return true, if successful
	 */
	public boolean thereIsAMemento(){ return currentMemento!=null;}

}
