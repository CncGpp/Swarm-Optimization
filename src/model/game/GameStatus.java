package model.game;

/**
 * l'enum {@code GameStatus} definisce i possibili stati in cui si puo' trovare il gioco.
 */
public enum GameStatus {
	/** L'istanza di gioco � stata appena creata e le sue variabili non sono state inizializzate. */
	NOTREADY(),
	/** Il gioco � stato inizializzato ed � pronto a cominciare.*/
	READY(),
	/** Il gioco � in fase di esecuzione e si sta svolgendo */
	RUNNING(),
	/** Il gioco � stato messo in pausa */
	PAUSED(),
	/** Il gioco � terminato */
	ENDED();
}