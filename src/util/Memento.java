package util;

public interface Memento<T> {
	public T saveMemento();
	public void restoreMemento(T memento);
}
