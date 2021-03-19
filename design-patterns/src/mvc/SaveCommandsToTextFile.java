package mvc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.swing.JFileChooser;


public class SaveCommandsToTextFile implements Saving {

	DrawingController controller;
	public SaveCommandsToTextFile(DrawingController controller) {
		this.controller = controller;
	}

	@Override
	public void save() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("C:/Users/Natalija/Documents"));
		fc.setDialogTitle("Save a file");
		//fc.setFileFilter(new FileTypeFilter(".bin", "File"));
		int result = fc.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				PrintWriter writer = new PrintWriter(file);
				Iterator<String> it = controller.getStringCommandsToWriteToFile().iterator();
				while(it.hasNext()) {
					writer.println(it.next());
				}
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}

}
