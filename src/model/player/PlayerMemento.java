package model.player;

import util.Memento;

/**
 * La classe {@code PlayerMemento} Modella il salvataggio di stato di un player
 * <p> Il suo salvataggio comprende Il memento della partita e i suoi dati </p>
 * */
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
