package geometry;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
//
public abstract class Shape implements Moveable, Comparable, Serializable {

	private boolean selected;
	private Color borderColor;
	
	public Shape() {
		
	}
	
	public Shape(boolean selected) {
		this.selected = selected;
	}
	
	//public abstract boolean equals(Object o);
	
	 public abstract void draw(Graphics g);

	
	public abstract boolean contains(int x, int y);
	public abstract void setFields(Shape shape);
	
	public boolean isSelected() {
		return this.selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
}
