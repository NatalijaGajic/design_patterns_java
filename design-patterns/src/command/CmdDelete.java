package command;

import java.io.Serializable;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdDelete implements Command, Serializable{

	private Shape shape;
	private DrawingModel model;
	
	private int index;
	
	public CmdDelete(Shape shape, DrawingModel model) {
		//TODO dodata linija ispod umesto u execute-u zbog deleteAll 

		this.shape = shape;
		this.model = model;
		this.index = this.model.indexOf(shape);
	}
	
	@Override
	public void execute() {
		System.out.println("index of shape:" + index);
		this.model.remove(shape);
	}

	@Override
	public void unexecute() {
		System.out.println("unexecute");
		//problem ako pri execute deleteall pokusa da stavi na indeks 1 a size je nula
		this.model.add(index, shape);
	}
	
	

	public String toString() {
		return "Deleted "+shape.toString();
		//ne fali indeks iz kog layera jer cuva indeks u komandi
	}
	
	
	public boolean equals(Object o) {
		if(o instanceof CmdDelete) {
			CmdDelete cd = (CmdDelete)o;
			if(cd.getShape().equals(getShape()) && cd.getIndex() == getIndex()) {
				return true;
			}
		}
		return false;
	}
	
	//Getters and setters

		public Shape getShape() {
			return shape;
		}

		public int getIndex() {
			return index;
		}
		
}
