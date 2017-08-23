package util;

import controller.gui.ChronometerController;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import util.Global.Controllers;
/**
 * La classe {@code Chronometer} modella il funzionamento di un cronometro
 * <p> La classe fornisce essenzialmente un punto di accesso condiviso al cronometro attraverso i suoi metodi
 * in oltre il tempo totale è legato attreverso una property alla view di gioco in modo che possa mostrare il tempo. </p>
 * */
public class Chronometer {
	private static ChronometerController controller;

	private static long startTime = 0;
	private static long totalTime = 0;
	private static LongProperty _totalTime;
	private static boolean isPaused = false;

	static {
		_totalTime = new SimpleLongProperty(0);
		controller = Controllers.chronometerController;

		if(controller !=null)
		_totalTime.addListener( (observer, oldValue, newValue) -> {
			Platform.runLater(()->{ controller.updateTimer(newValue.longValue()); });
		} );
	}

	public static void start(){
		startTime = System.currentTimeMillis();
		isPaused = false;

	    ( new Thread() { public void run() {
	    	while( !isPaused ){
		    	_totalTime.set(getElapsedTime());
		    	try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace();}
	    	}
	    	_totalTime.set(getTotalTime());
	    } } ).start();
	}

	public static void set(final long time){
		startTime = 0;
		totalTime = time;
		_totalTime.set(time);
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
