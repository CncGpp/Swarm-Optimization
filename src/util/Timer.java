package util;


public abstract class Timer {
	private static long startTime = 0;
	private static long totalTime = 0;
	private static boolean paused = false;

	public static void reset(){					//Resetta il timer in modo da misuarare un nuovo tempo
		totalTime = 0;
		startTime = 0;
		paused = false;
	}

	public static void set(long milliseconds){
		totalTime = milliseconds;
		paused = false;
	}

	public static void start(){					//Fa cominciare la misurazione
		paused = false;
		startTime = System.currentTimeMillis();
	}

	private static long time(){					//Effettua la misurazione del tempo trascorso dall'ultimo start...
		if(paused || startTime == 0) start();	//Se il Timer era in pausa o non è stato avviato lo avvia e poi misura.
		return System.currentTimeMillis() - startTime;
	}

	public static void pause(){					//Mette in pausa il timer, salvano il tempo ottenuto fino ad ora
		totalTime += time();
		paused = true;
	}

	public static long getElapsedTime(){		//Permette di ottenere il tempo totale del timer.
		return time() + totalTime;
	}
}