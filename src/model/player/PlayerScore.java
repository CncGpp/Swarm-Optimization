package model.player;

import java.io.Serializable;

public class PlayerScore implements Serializable{
	private static final long serialVersionUID = 1000452002723704423L;

	private String surname;
	private String name;
	private long time;

	public PlayerScore(String name, String surname, long time) {
		super();
		this.surname = surname;
		this.name = name;
		this.time = time;
	}

	public String getSurname() { return surname; }
	public String getName() { return name; }
	public long getTime() { return time; }

	public void setTime(final long time){ this.time= time;}

	public boolean equals(final PlayerScore playerScore){
		if(this.getName().equals(playerScore.getName())
			&& this.getSurname().equals(playerScore.getSurname())) return true;
		else return false;
	}
}