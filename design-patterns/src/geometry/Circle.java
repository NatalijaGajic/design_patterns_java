package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends AreaShape implements Cloneable{

	private int radius;
	private Point center;
	
	public Circle() {
		this.center = new Point();
	}
	
	public Circle(Point center, int radius) {
		this.radius = radius;
		this.center = center;
	}
	
	public Circle(int radius, Point center, boolean selected) {
		this(center,radius);
		setSelected(selected);
	}
	
	//CONTAINS
	public boolean contains (int x, int y) {
		return center.distance(x, y) <= radius;
	}
	
	public boolean contains (Point p) {
		return center.distance(p.getX(), p.getY()) <= radius;
	}
	
	//EQUALS
	public boolean equals (Object o) {
		if (o instanceof Circle) {
			Circle c = (Circle)o;
			return (c.center.equals(center) && c.radius == radius && c.getAreaColor().equals(getAreaColor())
					&& c.getBorderColor().equals(getBorderColor()) && c.isSelected() == isSelected());
		} else {
			return false;
		}
	}
	
	public void draw(Graphics g) {
		///pogledaj dodeljivanje boje
		if(getAreaColor() != null) {
			g.setColor(getAreaColor());
			//oval boji krug koji je opisan u kvardat koji nastaje levom tackom i sirina visina se proseldjuju 
			g.fillOval(this.getCenter().getX()-this.getRadius(), this.getCenter().getY()-this.getRadius(), this.getRadius()*2, this.getRadius()*2);
		}
		if(getBorderColor() != null)
			g.setColor(getBorderColor());
		else
			g.setColor(Color.BLACK);
		
			
		g.drawOval(this.getCenter().getX() - this.getRadius(), this.getCenter().getY() - this.getRadius(), this.getRadius()*2, this.getRadius()*2);
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getCenter().getX() + getRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - radius - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() + getRadius() -3, 6, 6);
			g.drawRect(this.getCenter().getX()  - 3, this.getCenter().getY() - getRadius() -3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() - 3, 6, 6);
		
		}
	}
	
	public void moveBy(int x, int y) {
		center.moveBy(x, y);
	}
	
	public int compareTo(Object o) {
		if (o instanceof Circle) {
		 return (this.radius - ((Circle) o).getRadius());
		}
		else
			return 0;
	}
	
	public double area() {
		return radius*radius*Math.PI;
	}
	
	
	//Getters and setters
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}

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
		return "Circle:(" + this.getCenter().getX()+","+this.getCenter().getY()+") "
				+ "R:"+this.getRadius()+", BC("+outr+","+outg+","+outb+"), "
						+ "FC("+inr+","+ing+","+inb+"), " + selected;
	}

	@Override
	public void setFields(Shape shape) {
		if(shape instanceof Circle) {
			Circle getCircle = (Circle)shape;
			this.getCenter().setX(getCircle.getCenter().getX());
			this.getCenter().setY(getCircle.getCenter().getY());
			this.setRadius(getCircle.getRadius());
			this.setSelected(getCircle.isSelected());
			this.setBorderColor(getCircle.getBorderColor());
			this.setAreaColor(getCircle.getAreaColor());		
		}
		
	}
	
	public Circle clone() {
		Circle newCircle = new Circle();
		newCircle.setFields(this);
		return newCircle;
	}

}
