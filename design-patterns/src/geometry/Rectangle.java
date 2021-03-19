package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends AreaShape implements Cloneable{

	private Point upperLeftPoint;
	private int height;
	private int width;
	
	 public Rectangle () {
		 this.upperLeftPoint = new Point();
	 }
	 
	public Rectangle(Point upperLeftPoint, int height, int width)  {
		this.upperLeftPoint = upperLeftPoint;
		setHeight(height);
		setWidth(width);
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width, boolean selected) {
		this(upperLeftPoint, height, width);
		setSelected(selected);
	}
	
	//CONTAINS
	public boolean contains (int x, int y) {
		//moze i da premasi levu tacku i ode u negativne vrednosti van pravougaonika pa mora i upperx<x
		return (upperLeftPoint.getX() <= x && upperLeftPoint.getY() <=y 
				&& (x <width + upperLeftPoint.getX()) && (y < upperLeftPoint.getY() + height));
	}
	
	public boolean contains (Point p) {
		return (upperLeftPoint.getX() <= p.getX() && upperLeftPoint.getY() <= p.getY() 
				&& (p.getX() < width + upperLeftPoint.getX()) && (p.getY() < upperLeftPoint.getY() + height));
	}
	
	//EQUALS
	public boolean equals(Object o) {
		if (o instanceof Rectangle) {
			Rectangle r = (Rectangle)o;
			return (r.getUpperLeftPoint().equals(getUpperLeftPoint()) &&
					r.getHeight() == height && r.getWidth() == width && r.getBorderColor().equals(getBorderColor())
							&& r.getAreaColor().equals(getAreaColor()));
		
		} else {
			return false;
		}
	}
	
	public double area() {
		return (height * width);
	
	}

	public void draw(Graphics g) {
		if(this.getAreaColor() != null) {
			g.setColor(getAreaColor());
			g.fillRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(),
					this.getWidth(), this.getHeight());
		}
		
		if(getBorderColor() != null) {
			g.setColor(getBorderColor());
		}
		else 
		g.setColor(Color.BLACK);			
		g.drawRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.width, this.getHeight());
		// g.fillRect(this.getUpperLeftPoint().getX(), this.getUpperLeftPoint().getY(), this.width, this.getHeight());
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() -3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() + width - 3, this.getUpperLeftPoint().getY() - 3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() - 3, this.getUpperLeftPoint().getY() + height - 3, 6, 6);
			g.drawRect(this.getUpperLeftPoint().getX() + width - 3, this.getUpperLeftPoint().getY() + height - 3, 6, 6);
			g.setColor(Color.BLACK);
		}
	}
	
	public void moveBy(int x, int y) {
		upperLeftPoint.moveBy(x, y);
		//dovoljno da se pomeri jedna, menjaju se sve jer se racunaju preko nje
	}
	
	public int compareTo(Object o) {
		if(o instanceof Rectangle) {
			return (int)(((Rectangle)o).area()-this.area());
		}
		else
			return 0;
	}

	//Getters and setters
	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}
	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height){
	
			this.height = height;
		
		
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
			this.width = width;
	
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
		return "Rectangle:(" + this.getUpperLeftPoint().getX()+","+this.getUpperLeftPoint().getY()+") "
				+ "W:"+this.getWidth()+", H:"+this.getHeight()+", BC("+outr+","+outg+","+outb+"), "
						+ "FC("+inr+","+ing+","+inb+"), "+selected;
	}

	@Override
	public void setFields(Shape shape) {
		if(shape instanceof Rectangle) {
			Rectangle getRect = (Rectangle)shape;
			this.getUpperLeftPoint().setX(getRect.getUpperLeftPoint().getX());
			this.getUpperLeftPoint().setY(getRect.getUpperLeftPoint().getY());
			try {
				this.setHeight(getRect.getHeight());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				this.setWidth(getRect.getWidth());
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.setSelected(getRect.isSelected());
			this.setBorderColor(getRect.getBorderColor());
			this.setAreaColor(getRect.getAreaColor());
		}
	}
	
	public Rectangle clone() {
		Rectangle newRect = new Rectangle();
		newRect.setFields(this);
		return newRect;
	}
}
