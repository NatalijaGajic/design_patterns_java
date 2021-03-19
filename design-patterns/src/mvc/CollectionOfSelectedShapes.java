package mvc;

import java.util.ArrayList;
import java.util.Iterator;


import geometry.Shape;

public class CollectionOfSelectedShapes implements Subject {
	
	ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
	ArrayList<Observer> observers = new ArrayList<Observer>();


	@Override
	public void notifyObservers() {
		Iterator<Observer> it = observers.iterator();
		while(it.hasNext()) {
			it.next().update(selectedShapes.size());
		}
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);		
	}
	
	public ArrayList<Shape> getSelectedShapes(){
		return selectedShapes;
	}

	public void add(Shape shape) {
		selectedShapes.add(shape);
		notifyObservers();
	}
	
	public void remove(Shape shape) {
		selectedShapes.remove(shape);
		notifyObservers();
	}
	
	public void removeAll() {
		selectedShapes.removeAll(selectedShapes);
		notifyObservers();
	}
	
	public int size() {
		return selectedShapes.size();
	}
	
	public Shape get(int index) {
		return selectedShapes.get(index);
	}

	public Iterator<Shape> iterator() {
		
		return selectedShapes.iterator();
	}

	
	
}
