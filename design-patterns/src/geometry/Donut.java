package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;

public class Donut extends Circle implements Cloneable{
	
	private int innerRadius;

	
	public Donut() {
		super();
	}
	
	public Donut(Point center, int radius,int innerRadius) {
			setRadius(radius);
			setCenter(center);
			this.innerRadius = innerRadius;	
	
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected){
		this(center, radius, innerRadius);
		setSelected(selected);
	}
	
	//CONTAINS
	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return (dFromCenter < getRadius() && dFromCenter > this.innerRadius);
	}
		
	
	public double area () {
		return getRadius()*getRadius()*Math.PI - innerRadius*innerRadius*Math.PI;
	}
	
	//EQUALS
	public boolean equals(Object o) {
		if (o instanceof Donut) {
			return ( (super.equals(((Circle)o)) && this.innerRadius == ((Donut)o).innerRadius)); 
				} 
	
		else {
				return false;
		}
	}
	
	public void draw(Graphics g) {
		
		System.out.println("Iscrtavanje donut-a");
		Graphics2D gr = (Graphics2D) g;
		Shape donut = (Area)createDonut(getCenter(), innerRadius, getRadius());
		if(getBorderColor() != null) {
			g.setColor(getBorderColor());
		} else {
			g.setColor(Color.BLACK);
		}
		gr.draw(donut);
		
		if(getAreaColor() != null) {
			g.setColor(getAreaColor());
			gr.fill(donut);
		} 
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(getCenter().getX() - 3, getCenter().getY() - 3, 6, 6);
			g.drawRect(this.getCenter().getX() + getInnerRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - getInnerRadius() - 3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX() - 3, this.getCenter().getY() + getInnerRadius() -3, 6, 6);
			g.drawRect(this.getCenter().getX()  - 3, this.getCenter().getY() - getInnerRadius() -3, 6, 6);
		}
	
	}

    private static Shape createDonut(Point center, int innerRadius, int outerRadius)
    {
        Ellipse2D outer = new Ellipse2D.Double(
            center.getX() - outerRadius, 
            center.getY() - outerRadius,
            2*outerRadius, 
            2*outerRadius);
        Ellipse2D inner = new Ellipse2D.Double(
            center.getX() - innerRadius, 
            center.getY() - innerRadius,
            2*innerRadius, 
            2*innerRadius);
        Area area = new Area(outer);
        area.subtract(new Area(inner));
        return area;
    }
    
	public void moveBy(int x, int y) {
		getCenter().moveBy(x, y);
	}

	public int compareTo (Object o) {
		if (o instanceof Donut) {
			return (int)(((Donut)o).area() - this.area());
		}
		else
			return 0;
	}

	//Getters and setters
	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
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
		return "Donut:("+this.getCenter().getX()+","+this.getCenter().getY()+")"
				+ " OR:"+this.getRadius()+", IR:"+this.getInnerRadius()+", "
						+ "BC("+outr+","+outg+","+outb+"), FC("+inr+","+ing+","+inb+"), "+selected;
	}
	
	public void setFields(Donut donut) {
		super.setFields(donut);
		this.setInnerRadius(donut.getInnerRadius());
	}
	
	public Donut clone() {
		Donut newDonut = new Donut();
		newDonut.setFields(this);
		return newDonut;
	}

}
