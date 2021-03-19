package mvc;

import java.awt.Graphics;
import java.util.ListIterator;
import javax.swing.JPanel;

import geometry.Shape;

public class DrawingView extends JPanel {

	private DrawingModel model = new DrawingModel();
	
	public void paint(Graphics g) {
		ListIterator<Shape> it = this.model.getShapes().listIterator();
		while(it.hasNext()) {
			it.next().draw(g);
		}
	}

	// Getters and setters
	public DrawingModel getModel() {
		return model;
	}

	public void setModel(DrawingModel model) {
		this.model = model;
	}
}
