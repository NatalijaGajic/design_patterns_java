package mvc;

public interface Subject {
	public void notifyObservers();
	public void addObserver(Observer o);
	public void removeObserver(Observer o);
}
