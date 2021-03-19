package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape implements Cloneable {

	
	private Point startPoint; 
	private Point endPoint;
	
	public Line() {
		startPoint = new Point();
		endPoint = new Point();
	}
	
	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	
	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint,endPoint);
		super.setSelected(selected);
	}
	
	//EQUALS
	public boolean equals (Object o) {
		if(o instanceof Line) {
			Line l = (Line)o;
			if (l.getStartPoint().equals(startPoint) && l.getEndPoint().equals(endPoint)
					&& l.getBorderColor().equals(getBorderColor()) && l.isSelected() == isSelected()) {
				return true;
			} else {
				return false;
			}
		}
		else
			return false;
	}
	
	//CONTAINS
	public boolean contains(int x, int y) {
		
		if(startPoint.distance(x, y) + endPoint.distance(x,y) - length() <= 50)
			return true;
		else
			return false;
	}
	
	public double length() {
		return this.endPoint.distance(this.startPoint.getX(), this.endPoint.getY());
	}
	
	
	public Point middleOfLine() {
		int x = (startPoint.getX()+endPoint.getX())/2;
		int y = (startPoint.getY()+endPoint.getY())/2;
		Point p = new Point(x,y);
		return p;
	}

	public void draw(Graphics g) {
		System.out.println("iscrtavanje linije");
		if(getBorderColor() != null) {
			g.setColor(getBorderColor());
		}
		else
			g.setColor(Color.BLACK);
		g.drawLine(this.getStartPoint().getX(), this.getStartPoint().getY(), 
				this.getEndPoint().getX(), this.getEndPoint().getY());
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getStartPoint().getX() - 3, this.getStartPoint().getY() - 3, 6, 6);
			g.drawRect(this.getEndPoint().getX() - 3, this.getEndPoint().getY() - 3, 6, 6);
			g.drawRect(this.middleOfLine().getX() - 3, this.middleOfLine().getY() - 3, 6, 6);
		}
		
	}
	
	public void moveBy(int x, int y) {
		//VEC IMAMO REALIZOVANE METODE ZA TACKE
		startPoint.moveBy(x, y);
		endPoint.moveBy(x, y);
		
	}
	
	public int compareTo(Object o) {
		if (o instanceof Line) {
			return (int) (this.length() - ((Line) o).length());
		}
		else
			return 0;
	}
	
	//Getters and setters
	public Point getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	public Point getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	
	public String toString() {
		int outr = this.getBorderColor().getRed();
		int outg = this.getBorderColor().getGreen();
		int outb = this.getBorderColor().getBlue();
		String selected;
		if(this.isSelected()) {
			selected = "selected";
		} else {
			selected = "unselected";
		}
		return "Line:SP(" + this.getStartPoint().getX()+","+this.getStartPoint().getY()+") EP("+
				this.getEndPoint().getX()+","+this.getEndPoint().getY()+") "+ "BC("+outr+","+outg+","+outb+"), "
						+ selected;
	}

	@Override
	public void setFields(Shape shape) {
		if(shape instanceof Line) {
			Line getLine = (Line) shape;
			this.getStartPoint().setX(getLine.getStartPoint().getX());
			this.getStartPoint().setY(getLine.getStartPoint().getY());
			this.getEndPoint().setX(getLine.getEndPoint().getX());
			this.getEndPoint().setY(getLine.getEndPoint().getY());
			this.setSelected(getLine.isSelected());
			this.setBorderColor(getLine.getBorderColor());
		}
		
	}
	
	public Line clone() {
		Line newLine = new Line();
		newLine.setFields(this);
		return newLine;
	}

	
}
