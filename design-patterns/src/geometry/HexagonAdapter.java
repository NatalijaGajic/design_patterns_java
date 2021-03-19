package geometry;

import java.awt.Color;
import java.awt.Graphics;


import hexagon.Hexagon;

public class HexagonAdapter extends AreaShape {

	private Hexagon hexagon = new Hexagon(0,0,0);
	private Point center = new Point();
	private int radius;
	
	
	public HexagonAdapter(Point center, int r) {
		this.center = center;
		this.radius = r;
		
		this.hexagon = new Hexagon(center.getX(), center.getY(), r);
		hexagon.setR(radius);
		hexagon.setX(center.getX());
		hexagon.setY(center.getY());
		hexagon.setBorderColor(getBorderColor());
		hexagon.setAreaColor(getAreaColor());
		hexagon.setSelected(isSelected());

		
	}
	
	public HexagonAdapter() {
	}

	@Override
	public void moveBy(int byX, int byY) {
		
	}
	
	@Override
	public int compareTo(Object o) {
		return 0;
		
	}

	@Override
	public void draw(Graphics g) {
		hexagon.setSelected(isSelected());
		if(this.getAreaColor()!= null) {
			hexagon.setAreaColor(this.getAreaColor());
		} else {
			Color color = new Color (0f,0f,0f,0f);
			hexagon.setAreaColor(color);
		}
		if (getBorderColor()!= null) {
			hexagon.setBorderColor(this.getBorderColor());
		} else {
			hexagon.setBorderColor(Color.BLACK);
		}
		hexagon.paint(g);
	}

	@Override
	public boolean contains(int x, int y) {		
		return hexagon.doesContain(x, y);
	}

	@Override
	double area() {
		return 0;
	}

	@Override
	/*public boolean equals(Object o) {
		hexagon.setR(radius);
		hexagon.setX(center.getX());
		hexagon.setY(center.getY());
		hexagon.setBorderColor(getBorderColor());
		hexagon.setAreaColor(getAreaColor());
		hexagon.setSelected(isSelected());
		System.out.println(hexagon.equals(o));
		return hexagon.equals(((Hexagon)o));
		//TODO equals za hexagon
		//return true;
	}*/
	
		public String toString() {
		int inr = this.getAreaColor().getRed();
		int ing = this.getAreaColor().getGreen();
		int inb = this.getAreaColor().getBlue();
		int outr = this.getBorderColor().getRed();
		int outg = this.getBorderColor().getGreen();
		int outb = this.getBorderColor().getBlue();
		String selected;
		if(this.isSelected()) {
			selected = "selected";
		} else {
			selected = "unselected";
		}
		return "Hexagon:("+this.getCenter().getX()+","+this.getCenter().getY()+") "
				+ "R:"+this.getRadius()+", BC("+outr+","+outg+","+outb+"), "
						+ "FC("+inr+","+ing+","+inb+"), "+selected;

	}
	
	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public Hexagon getHexagon() {
		return hexagon;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public void setFields(Shape shape){
		if(shape instanceof HexagonAdapter){
		HexagonAdapter getHexagon = (HexagonAdapter)shape;
		this.setAreaColor(getHexagon.getAreaColor());
		this.setBorderColor(getHexagon.getBorderColor());
		this.setSelected(getHexagon.isSelected());
		this.getCenter().setX(getHexagon.getCenter().getX());
		this.getCenter().setY(getHexagon.getCenter().getY());
		this.setRadius(getHexagon.getRadius());
		
		this.getHexagon().setAreaColor(getHexagon.getHexagon().getAreaColor());
		this.getHexagon().setBorderColor(getHexagon.getHexagon().getBorderColor());
		this.getHexagon().setR(getHexagon.getHexagon().getR());
		this.getHexagon().setX(getHexagon.getHexagon().getX());
		this.getHexagon().setY(getHexagon.getHexagon().getY());
		this.getHexagon().setSelected(getHexagon.getHexagon().isSelected());
		}
	}
	
	public HexagonAdapter clone(){
		HexagonAdapter newHex = new HexagonAdapter();
		newHex.setFields(this);
		return newHex;
	}


}
