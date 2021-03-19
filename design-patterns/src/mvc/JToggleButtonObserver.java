package mvc;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JToggleButton;

public class JToggleButtonObserver implements Observer {

	ArrayList<JToggleButton> buttons = new ArrayList<JToggleButton>();
	DrawingFrame frame;
	
	public JToggleButtonObserver(DrawingFrame drawingFrame) {
		this.frame = drawingFrame;
	}

	
	public void addJToggleButton(JToggleButton btn) {
		buttons.add(btn);
	}
	
	@Override
	public void update(int numberOfSelected) {
		if(numberOfSelected == 0) {
			Iterator<JToggleButton> it = buttons.iterator();
			while(it.hasNext()) {
				it.next().setEnabled(false);
			}
		} else if (numberOfSelected == 1) {
			Iterator<JToggleButton> it = buttons.iterator();
			while(it.hasNext()) {
				JToggleButton jtb = it.next();
				if(jtb.equals(frame.getBtnModify())) {
					jtb.setEnabled(true);
				} else if (jtb.equals(frame.getBtnDelete())) {
					jtb.setEnabled(true);
				} else if(jtb.equals(frame.getBtnBringToBack()) || jtb.equals(frame.getBtnBringToTop())
							|| jtb.equals(frame.getBtnToFront()) || jtb.equals(frame.getBtnToBack())) {
					jtb.setEnabled(true);
				}
			}
		} else {
			Iterator<JToggleButton> it = buttons.iterator();
			while(it.hasNext()) {
				JToggleButton jtb = it.next();
				if(jtb.equals(frame.getBtnModify())) {
					jtb.setEnabled(false);
				} else if (jtb.equals(frame.getBtnDelete())) {
					jtb.setEnabled(true);
				} else if(jtb.equals(frame.getBtnBringToBack()) || jtb.equals(frame.getBtnBringToTop())
						|| jtb.equals(frame.getBtnToFront()) || jtb.equals(frame.getBtnToBack())) {
					jtb.setEnabled(false);
				}
			}
		}
		
		
	}
	
	
	

}
