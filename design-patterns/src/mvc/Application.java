package mvc;


import javax.swing.JFrame;


public class Application {

	public static void main(String[] args) {
		System.out.println("Dobrodošli na vežbe iz predmeta Dizajnerski obrasci.");
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame();
		frame.getView().setModel(model);
		DrawingController controller = new DrawingController(model, frame);
		frame.setController(controller);
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		System.out.println(frame.getBtnUndo().getText());
		JToggleButtonObserver tbo = new JToggleButtonObserver(frame);
		tbo.addJToggleButton(frame.getBtnDelete());
		tbo.addJToggleButton(frame.getBtnModify());
		tbo.addJToggleButton(frame.getBtnBringToBack());
		tbo.addJToggleButton(frame.getBtnBringToTop());
		tbo.addJToggleButton(frame.getBtnToBack());
		tbo.addJToggleButton(frame.getBtnToFront());
		controller.getCollectionOfSelectedShapes().addObserver(tbo);
		/*String stringRectModify = "Deleted Line:SP(370,296) EP(381,286) BC(255,51,51), selected";
		String[] splitsRectModify = stringRectModify.split("[, =():]");
		for (int i = 0; i < splitsRectModify.length; i++) {
			System.out.println(i+" :"+splitsRectModify[i]);
		}*/
		 
	}

}
