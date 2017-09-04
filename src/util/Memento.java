package util;

import java.io.Serializable;

/**
 * L'interfaccia che una classe deve implementare per peremttere il salvataggio del suo stato
 * <p> Affinchè lo stato possa essere memorizzato il tipo generico deve estendere l'interfaccia {@code Serializble} </p>
 *
 * @param <T> Il tipo generico che corrisponde alla classe che si occuperà di salvare il memento
 */
public interface Memento<T extends Serializable> {

	/**
	 * Salva lo stato dell'applicazione ritornandolo attraverso un oggetto T
	 *
	 * @return il memento
	 */
	public T saveMemento();

	/**
	 * Ripristina lo stato dell'oggetto attraverso il memento T
	 *
	 * @param memento il memento salvato da ripristinare
	 */
	public void restoreMemento(T memento);
}
