package util;

import controller.gui.ChronometerController;
import javafx.animation.AnimationTimer;
import util.Global.Controllers;
/**
 * La classe {@code Chronometer} modella il funzionamento di un cronometro
 * <p> La classe fornisce essenzialmente un punto di accesso condiviso al cronometro attraverso i suoi metodi
 * in oltre il tempo totale è aggiornato attraverso un'AnimationTimer </p>
 * */
public class Chronometer {
	private static ChronometerController controller;

	private static long startTime = 0;			//Tempo iniziale
	private static long totalTime = 0;			//Tempo trascorso in totale
	private static long _totalTime = 0;         //Tempo trascorso dall'ultima pausa
	private static boolean isPaused = false;

	static {
		controller = Controllers.chronometerController;
	}

	public static void start(){
		startTime = System.currentTimeMillis();
		isPaused = false;

		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if(isPaused) {
					_totalTime = getTotalTime();
					controller.updateTimer(_totalTime);
					return;
				}

				_totalTime = (getElapsedTime());
				controller.updateTimer(_totalTime);
			}
		};
		at.start();
	}

	public static void set(final long time){
		startTime = 0;
		totalTime = time;
		_totalTime = totalTime;
	}

	public static void pause(){									//Mette in pausa il timer, salvano il tempo ottenuto fino ad ora
		totalTime += time();
		isPaused = true;
	}

	private static long time(){									//Effettua la misurazione del tempo trascorso dall'ultimo start...
		if(isPaused || startTime == 0) start();					//Se il Timer era in pausa o non è stato avviato lo avvia e poi misura.
		return System.currentTimeMillis() - startTime;
	}

	public static long getElapsedTime(){						//Prende il tempo e continua
		return time() + totalTime;
	}

	public static long getTotalTime(){							//Prende solo il tempo
		return totalTime;
	}
}
