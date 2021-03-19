package command;

import java.io.Serializable;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdChangeLayer implements Command, Serializable {

	private Shape shape;
	private int oldIndex;
	private int newIndex;
	
	private DrawingModel model;
	
	public CmdChangeLayer(Shape shape, DrawingModel model, int newIndex) {
		this.oldIndex = model.getShapes().indexOf(shape);
		this.newIndex = newIndex;
		this.shape = shape;
		this.model = model;
	}
	
	@Override
	public void execute() {
		model.remove(shape);
		model.add(newIndex, shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		model.add(oldIndex, shape);
	}
	

	public String toString() {
		return "Moved "+shape.toString()+" to layer "+newIndex;
	}
	
	public boolean equals(Object o) {
		if(o instanceof CmdChangeLayer) {
			CmdChangeLayer ccl = (CmdChangeLayer)o;
			if(ccl.getShape().equals(getShape()) && ccl.getnewIndex() == getnewIndex()
					&& ccl.getOldIndex() == getOldIndex()) {
				return true;
			}
		}
		return false;
	}
	
	//Getters and setters
	public Shape getShape() {
		return shape;
	}
	
	public int getOldIndex() {
		return oldIndex;
	}
	
	public int getnewIndex() {
		return newIndex;
	}
}
