package model.map;

/**
 * L'interfaccia definisce la capacità di chi la implementa di essere "pesato"
 * <p> Per peso si intende un valore numerico che rappresenta un indice di bonta' o appetibilita' </p>
 * */
public interface Weighable {
	public default double getWeight(){ return 0;}
}
