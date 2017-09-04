package model.game;

/**
 * l'enum {@code GameStatus} definisce i possibili stati in cui si puo' trovare il gioco.
 */
public enum GameStatus {
	/** L'istanza di gioco è stata appena creata e le sue variabili non sono state inizializzate. */
	NOTREADY(),
	/** Il gioco è stato inizializzato ed è pronto a cominciare.*/
	READY(),
	/** Il gioco è in fase di esecuzione e si sta svolgendo */
	RUNNING(),
	/** Il gioco è stato messo in pausa */
	PAUSED(),
	/** Il gioco è terminato */
	ENDED();
}