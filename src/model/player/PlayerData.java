package model.player;

import java.util.ArrayList;

import util.Memento;

public class PlayerData {
	private static ArrayList<PlayerMemento> playerMementos = new ArrayList<>();
	private static PlayerScore currentPlayer = null;

	public void loginPlayer(final PlayerScore playerScore){
		if(playerScore == null) return;
		currentPlayer = playerScore;
		restoreCurrentMemento();
	}

	public void logoutPlayer(){currentPlayer = null;}

	public void addMemento(final Memento memento){
		if(currentPlayer == null) return;
		if(memento == null) return;

		//Se già c'è nella lista lo sostituisco con il nuovo
		for (PlayerMemento playerMemento : playerMementos) {
			if(playerMemento.getPlayerScore().equals(currentPlayer)) playerMemento.setMemento(memento);
			return;
		}
		//Altrimenti lo aggiungo
		playerMementos.add(new PlayerMemento(memento, currentPlayer));
	}

	private void restoreCurrentMemento(){
		if(currentPlayer == null) return;
		for (PlayerMemento playerMemento : playerMementos) {
			if(playerMemento.getPlayerScore().equals(currentPlayer)) playerMemento.getMemento().restoreMemento();
			playerMementos.remove(playerMemento);
			return;
		}
	}

	public PlayerScore getCurrentPlayer(){return currentPlayer;}
	public boolean isLogged(){return currentPlayer!=null;}

}
