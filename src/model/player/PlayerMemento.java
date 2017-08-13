package model.player;

import util.Memento;

public class PlayerMemento {
	private Memento memento;
	private PlayerScore playerScore;


	public PlayerMemento(Memento memento, PlayerScore playerScore) {
		super();
		this.memento = memento;
		this.playerScore = playerScore;
	}

	public Memento getMemento() {
		return memento;
	}
	public void setMemento(final Memento memento){ this.memento = memento;}

	public PlayerScore getPlayerScore() {
		return playerScore;
	}


}
