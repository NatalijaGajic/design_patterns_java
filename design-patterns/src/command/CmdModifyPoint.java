package command;

import java.io.Serializable;


import geometry.Point;
import geometry.Shape;

public class CmdModifyPoint implements Command, Serializable, CmdModify {

	private Point oldState;
	private Point newState;
	private Point originalState = new Point();

	
	public CmdModifyPoint(Point oldPoint, Point newPoint) {
		this.oldState = oldPoint;
		this.newState = newPoint;
		originalState.setFields(oldState);
	}
	
	@Override
	public void execute() {
		originalState.setFields(oldState);
		oldState.setFields(newState);
	}

	@Override
	public void unexecute() {
		oldState.setFields(originalState);	}
	
	public String toString() {
		return "Modified "+originalState.toString()+" to "+newState.toString();

	}

	public Object redo() {
		if(oldState.isSelected() != newState.isSelected()) {
			if(newState.isSelected()) {
				return true;
				//treba oldState dodati u listu
			} else {
				//treba oldState izbaciti iz liste
				return false;
			}
		}
		return null;
	}

	@Override
	public Object undo() {
		if(oldState.isSelected() != originalState.isSelected()) {
			if(originalState.isSelected()) {
				return true;
			} else {
				return false;
			}
		}
		return null;
	}
	public boolean equals(Object o) {
		if(o instanceof CmdModifyPoint) {
			CmdModifyPoint cmc = (CmdModifyPoint)o;
			if(cmc.getOldState().equals(getOldState()) && cmc.getNewState().equals(getNewState())) {
				return true;
			}
		}
		return false;
	}
	
	//Getters and setters
	public Shape getOldState() {
		return oldState;
	}
	
	public Shape getNewState() {
		return newState;
	}
}
