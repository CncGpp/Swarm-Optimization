package util;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import util.Gloabal.Controllers;
import view.gui.ChronometerController;

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
	    	while(! isPaused){
		    	_totalTime.set(getElapsedTime());
		    	try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace();}
	    	}
	    } } ).start();
	}

	public static void set(final long time){
		startTime = 0;
		totalTime = time;
		_totalTime.set(time);
		isPaused = false;
	}

	public static void pause(){									//Mette in pausa il timer, salvano il tempo ottenuto fino ad ora
		totalTime += time();
		isPaused = true;
	}

	private static long time(){									//Effettua la misurazione del tempo trascorso dall'ultimo start...
		if(isPaused || startTime == 0) start();					//Se il Timer era in pausa o non è stato avviato lo avvia e poi misura.
		return System.currentTimeMillis() - startTime;
	}

	public static long getElapsedTime(){
		return time() + totalTime;
	}
}
