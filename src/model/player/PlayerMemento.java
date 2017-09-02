package model.player;

import java.io.Serializable;

import model.AGame.AGameMemento;

/**
 * La classe {@code PlayerMemento} Modella il salvataggio di stato di un player
 * <p> Il suo salvataggio comprende Il memento della partita e i suoi dati </p>
 * */
public class PlayerMemento implements Serializable{
	private static final long serialVersionUID = 6872986050880841128L;

	private AGameMemento memento;
	private PlayerScore playerScore;


	public PlayerMemento(AGameMemento memento, PlayerScore playerScore) {
		super();
		this.memento = memento;
		this.playerScore = playerScore;
	}

	public AGameMemento getMemento() {
		return memento;
	}
	public void setMemento(final AGameMemento memento){ this.memento = memento;}

	public PlayerScore getPlayerScore() {
		return playerScore;
	}


}
