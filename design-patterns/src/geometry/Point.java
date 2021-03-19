package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape implements Cloneable {

	private int x;
	private int y;
	
	public Point() {
		
	}
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(int x, int y, boolean selected) {
		this(x, y);
		setSelected(selected);
	}
	
	
	//DISTANCE
	public double distance (int x, int y) {
		int dx=this.x - x;
		int dy=this.y - y;
		return Math.sqrt((double)(dx * dx + dy * dy));
	}
	
	//DA LI SADRZI TACKU
	public boolean contains(int x, int y) {
		if(this.distance(x, y) <= 3)
			return true;
		return false;
	}
	
	//EQUALS da li je tacka jednaka sa drugom 
	public boolean equals(Object o) {
		//prvo moramo da ispitamo da li je instanca tacka da bismo uporedjivali
		if(o instanceof Point) {
			//kastovanje 
			Point p = (Point)o;
			if(this.x == p.x && this.y == p.y ) {
				if(getBorderColor() != null) {
					//u slucaju ispitivanja tacke kao centra krofne ili start point, end point linije
					//getBorderColor() daje null jer nije postavljeno
					//TODO provera da li se jos negde to desava
					if(p.getBorderColor()!= null && p.getBorderColor().equals(getBorderColor()) 
							&& p.isSelected() == isSelected()) {
						return true;
					}else {
						return false;
					}
					
				}
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public void draw(Graphics g) {
		System.out.println("draw for point");
		if (getBorderColor() != null){
			g.setColor(getBorderColor());
		}			
		else {
			g.setColor(Color.BLACK);
		}
		System.out.println("u draw point x je "+x+" y je "+y);
		g.drawLine(this.x - 2, y, this.x + 2, y);
		g.drawLine(x, this.y - 2, x, this.y + 2);
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getX() - 3, this.y - 3, 6, 6);

		}
	}
	
	public void moveBy(int x, int y) {
		this.x+= x;
		this.y+= y;
	}
	
	public int compareTo (Object o) {
		if (o instanceof Point) {
			Point p = (Point)o;
			//mora castovanje distance vraca double
			return (int)(p.distance(0, 0) - this.distance(0, 0));
		}
		else
			return 0;
	}
	
	//Getters and setters
	public void setX(int x){
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
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
		return "Point:(" + this.getX()+","+this.getY()+") "
				+"BC("+outr+","+outg+","+outb+") "+ selected;
	}
	@Override
	public void setFields(Shape shape) {
		if(shape instanceof Point) {
			Point getPoint = (Point)shape;
			this.setX(getPoint.getX());
			this.setY(getPoint.getY());
			this.setBorderColor(getPoint.getBorderColor());
			this.setSelected(getPoint.isSelected());
		}
	}
	
	public Point clone() {
		Point newPoint = new Point();
		newPoint.setFields(this);
		return newPoint;
	}
	
}
