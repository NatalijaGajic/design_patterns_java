package mvc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import geometry.Shape;

public class DrawingModel implements Serializable {

	private List<Shape> shapes = new ArrayList<Shape>();

	// Getters and setters
	public List<Shape> getShapes() {
		return shapes;
	}
	
	public void add(Shape shape) {
		shapes.add(shape);
	}
	
	public void remove(Shape shape) {
		shapes.remove(shape);
	}
	public void add(int index, Shape shape) {
		shapes.add(index, shape);
	}

	public int indexOf(Shape shape) {
		return shapes.indexOf(shape);
	}
	
	public int size() {
		return shapes.size();
	}
}
