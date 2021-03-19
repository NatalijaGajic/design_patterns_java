package command;

import geometry.Shape;

public interface CmdModify {
	public Object redo();
	public Object undo();
	public Shape getOldState();
}
