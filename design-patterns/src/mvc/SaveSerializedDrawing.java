package mvc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;


public class SaveSerializedDrawing implements Saving{

	DrawingController controller;
	
	public SaveSerializedDrawing(DrawingController controller) {
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
			String filePath = file.getPath();
			try {
				System.out.println("broj komandi koji se cuva:" + controller.getCommandList().size());
				//SerializableCommandList.writeToFile((ArrayList<Shape>) controller.getModel().getShapes(), filePath);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
				objectOutputStream.writeObject( controller.getModel().getShapes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}


}
