package model.player;

import java.util.ArrayList;

import util.Memento;

/**
 * la classe {@code PlayerData} gestisce i dati dei giocatori memorizzati e il giocatore che attualmente sta giocando
 * <p> La classe fornisce un punto di accesso alle sue strutture dati interne che sono statiche </p>
 */
public class PlayerData {

	/** la lista dei Mementi dei giocatori che hanno effettuato il logout */
	private static ArrayList<PlayerMemento> playerMementos = new ArrayList<>();

	/** Il giocatore correntemente loggato */
	private static PlayerScore currentPlayer = null;

	/** Il memento del giocatore loggato */
	private static Memento currentMemento = null;

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
	 * Aggiunge il memento relativo al plater corrente nella lista dei mementi.
	 *
	 * @param memento the memento
	 */
	public void addMemento(final Memento memento){
		if(currentPlayer == null) return;
		if(memento == null) return;

		//Se già c'è nella lista lo sostituisco con il nuovo
		for (PlayerMemento playerMemento : playerMementos) {
			if(playerMemento.getPlayerScore().equals(currentPlayer)){
				playerMementos.remove(playerMemento);
				break;
			}
		}
		//Altrimenti lo aggiungo
		playerMementos.add(new PlayerMemento(memento, currentPlayer));
	}

	/**
	 * Ricerca un eventuale memento salvato e lo memorizza
	 */
	private void findCurrentPlayerMemento(){
		if(currentPlayer == null) return;
		for (PlayerMemento playerMemento : playerMementos) {
			if(playerMemento.getPlayerScore().equals(currentPlayer)) {
				currentPlayer = playerMemento.getPlayerScore();
				currentMemento = playerMemento.getMemento();
				playerMementos.remove(playerMemento);
				return;
			}
		}
	}

	/**
	 * Ripristina il memento corrente
	 */
	public void restoreCurrentMemento(){
		if(currentMemento != null) currentMemento.restoreMemento();
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
