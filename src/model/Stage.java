package model;

import java.util.List;

import util.Global.R;

/**
 * La classe {@code Stage} modella lo stage (livello) attuale in cui si trova il gioco.
 * <p> La classe permette di tenere conto del livello in cui si trova attualmente il gioco e permette di passare al
 * successivo se disponibile.
 * Per livello si intende essenzialmente l'ordine di apparizione delle mappe di gioco. </p>
 */
public class Stage {

	/** La lista di tutti i livelli disponibili
	 * 	<p> Utilizzata per ottenere il numero di stage implementati nel gioco </p>
	 * */
	private final List<String> stageList = R.STAGE_LIST;

	/** Lo stage correnti in cui il gioco si trova */
	private int currStage = 0;

	/**
	 * Istanzia un nuovo Stage
	 * <p> Permette la creazione di un nuova istanza di Stage partendo dal livello fornito in input  </p>
	 * @param currStage lo stage dal quale si vuole partire
	 */
	public Stage(int currStage) {
		if(currStage < 0 || currStage >= stageList.size()) currStage = 0;
		this.currStage = currStage;
	}

	/**
	 * Istanzia un nuovo Stage partendo dal primo livello.
	 * @see Stage#Stage(int)
	 */
	public Stage() { this(0);}

	/**
	 * Gets the stage number.
	 * @return the stage number
	 */
	public int getStageNumber() {return currStage;}

	/**
	 * Metodo getter per ottenere il path relativo allo stage corrente
	 * @return il path dello stage corrente
	 */
	public String getStagePath() {return stageList.get(currStage);}

	/**
	 * Permette di avanzare, se possibile, allo stage successivo
	 * @return false lo stage corrente è l'ultimo disponibile.
	 */
	public boolean nextStage() {
		if(currStage + 1 == stageList.size()) return false;
		else currStage++;
		return true;
	}

}
