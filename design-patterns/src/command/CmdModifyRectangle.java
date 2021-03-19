package command;

import java.io.Serializable;

import geometry.Rectangle;
import geometry.Shape;
//TODO izbrisi initial state, setuj original state u konstruktoru, u tostring
//umesto initial, original
public class CmdModifyRectangle implements Command, Serializable, CmdModify {
	
	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle originalState = new Rectangle();;
	
	public CmdModifyRectangle(Rectangle oldRect, Rectangle newRect) {
		this.oldState = oldRect;
		this.newState = newRect;
		originalState.setFields(oldState);
	}
	
	@Override
	public void execute() {
		originalState.setFields(oldState);
		oldState.setFields(newState);
		
	}

	@Override
	public void unexecute() {
		oldState.setFields(originalState);
	}
	
	
	
	public String toString() {
		return "Modified "+originalState.toString()+" to "+ newState.toString();
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
		if(o instanceof CmdModifyRectangle) {
			if(((CmdModifyRectangle)o).getOldState().equals(getOldState())&&
					((CmdModifyRectangle)o).getNewState().equals(getNewState())
				) {
				return true;
			} 
		}
		//jer se samo napravi komanda, tj. dodele oldState i newState
		return false;
	}
	
	//Getters and setters
	public Rectangle getNewState() {
		return newState;
	}
	
	public Shape getOldState() {
		return oldState;
	}
}
