package model.player;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class PlayerScore{
	private final SimpleStringProperty surname;
	private final SimpleStringProperty name;
	private final SimpleLongProperty time;

	public PlayerScore(String name, String surname, long time) {
		super();
		this.surname = new SimpleStringProperty(surname);
		this.name = new SimpleStringProperty(name);
		this.time = new SimpleLongProperty(time);
	}

	public String getSurname() { return surname.get(); }
	public String getName() { return name.get(); }
	public long getTime() { return time.get(); }

	public void setTime(final long time){ this.time.set(time);}

	public boolean equals(final PlayerScore playerScore){
		if(this.getName().equals(playerScore.getName())
			&& this.getSurname().equals(playerScore.getSurname())) return true;
		else return false;
	}
}