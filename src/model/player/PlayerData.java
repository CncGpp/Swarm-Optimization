package model.player;

import java.util.ArrayList;

import util.Memento;

public class PlayerData {
	private static ArrayList<PlayerMemento> playerMementos = new ArrayList<>();
	private static PlayerScore currentPlayer = null;
	private static Memento currentMemento = null;

	public void loginPlayer(final PlayerScore playerScore){
		if(playerScore == null) return;
		currentPlayer = playerScore;
		findCurrentPlayerMemento();
	}

	public void logoutPlayer(){
		currentPlayer = null;
		currentMemento = null;
	}

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

	public void restoreCurrentMemento(){
		if(currentMemento != null) currentMemento.restoreMemento();
	}

	public PlayerScore getCurrentPlayer(){return currentPlayer;}
	public boolean isLogged(){return currentPlayer!=null;}
	public boolean thereIsAMemento(){ return currentMemento!=null;}

}
