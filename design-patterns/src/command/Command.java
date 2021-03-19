package command;

import mvc.DrawingModel;

public interface Command  {

	void execute();
	void unexecute();
}
