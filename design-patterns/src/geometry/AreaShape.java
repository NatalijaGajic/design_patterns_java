package geometry;

import java.awt.Color;

public abstract class AreaShape extends Shape {

	private Color areaColor; 
	
	abstract double area();

	public Color getAreaColor() {
		return areaColor;
	}

	public void setAreaColor(Color areaColor) {
		this.areaColor = areaColor;
	}
	
}
