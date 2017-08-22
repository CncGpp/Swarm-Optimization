package model.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 * la classe {@code Rank} gestisce i punteggi dei giocatori.
 * <p> I punteggi sono memorizzati su file quindi la classe si occupa della lettura e scrittura su tale file.
 * In oltre la classe fornisce un punto di accesso alla classifica, infatti tutti gli attributi sono statici </p>
 */
public class Rank {
	private static File f;
	private static ObservableList<PlayerScore> observableList_rank;
	private static SortedList<PlayerScore> sortedList;

	/**
	 * Crea una nuova istanza di Rank
	 * <p> La prima istanza creata leggerà la classifica dal file riempiendo la lista dei punteggi </p>
	 * @param path the path
	 */
	public Rank(String path){
		if(observableList_rank != null) return;											//La lista va caricata solo la prima volta

		f = new File(path);
		if(!f.exists())
			try { f.createNewFile(); } catch (IOException e1) {e1.printStackTrace();}


		//Creo la collections observabele
		observableList_rank = FXCollections.observableArrayList();

		//La associo ad una lista ordinata
		sortedList = new SortedList<>(observableList_rank,
        		(PlayerScore a, PlayerScore b) -> Long.compare(a.getTime(), b.getTime())
        		);

		//LEGGO IL FILE AGGIORNANDO LA LISTA
		try (Scanner fs = new Scanner(f)){
			while(fs.hasNext())
			observableList_rank.add(new PlayerScore(fs.next(), fs.next(), fs.nextLong()));
		} catch (FileNotFoundException e) { e.printStackTrace(); }
	}


	public void submitScore(final PlayerScore ps){
		submitScore(ps.getName(), ps.getSurname(), ps.getTime());
	}

	public void submitScore(final String name, final String surname, final long time){

		try (PrintStream  fw = new PrintStream( new FileOutputStream(f,true) )){
			if(!f.exists()) f.createNewFile();
			fw.println(name + " " + surname + " " + time);
			fw.flush();
		} catch (IOException e) { e.printStackTrace(); }

		observableList_rank.add(new PlayerScore(name, surname, time));
	}

	public ObservableList<PlayerScore> getRankList(){
		return sortedList;
	}

}