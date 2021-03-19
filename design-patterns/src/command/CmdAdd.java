package command;

//TODO equals za cmdAdd?
import java.io.Serializable;

import geometry.Shape;
import mvc.DrawingModel;
public class CmdAdd implements Command, Serializable {

	private Shape shape;
	private DrawingModel model;
	
	public CmdAdd () {
		
	}

	public CmdAdd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		System.out.println("execute in cmdAdd");
		System.out.println("shape: "+shape.toString());
		model.add(shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
	}
	
	public String toString() {
		return "Added "+shape.toString();
	}
		
}
