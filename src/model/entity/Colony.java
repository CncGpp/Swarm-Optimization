package model.entity;


import model.map.AMap;
import strategy.*;
import util.Coord;

public class Colony extends AColony{

	public Colony(final AMap map, final int botCount, final AStart start) {
		super(map, botCount, start);
		this.setStrategy(new AtanAS());
	}

	//Factory method
	@Override
	protected ABot makeBot(final Coord startPosition){ return new Bot(this, startPosition); }

	//Definisce l'ordine e in che modo di devono muovere i bot
	//In questo caso tutti insieme
	@Override
	protected void moveBot() {
		for(ABot b : this.getBots()) b.move();
	}

}
