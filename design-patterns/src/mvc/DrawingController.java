package mvc;
//TODO proveri sta se desava kada se sacuva prazan fajl
import java.awt.Color;
//ako se ne prosledjuje komandi onda provera pre uexecute i unexecute (zbog undo,redo, da li je u komandi
//original state tj. new state selected ako jeste treba da se izbaci/ubaci u listu )
//TODO izbrisi initial state, dovoljno original; to string se poziva pre execute u cmdExecute, pa treba 
// da se original uzme u kontruktoru, ako je to izvodljivo
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import command.CmdAdd;
import command.CmdChangeLayer;
import command.CmdDelete;
import command.CmdDeleteAll;
import command.CmdModify;
import command.CmdModifyCircle;
import command.CmdModifyDonut;
import command.CmdModifyHexagon;
import command.CmdModifyLine;
import command.CmdModifyPoint;
import command.CmdModifyRectangle;
import command.Command;
import dialogs.CircleDialog;
import dialogs.DonutDialog;
import dialogs.HexagonDialog;
import dialogs.LineDialog;
import dialogs.PointDialog;
import dialogs.RectangleDialog;
import geometry.Circle;
import geometry.Donut;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;

//TODO indexOf za komande ne moze, pogledaj executeCommand i clickedUndo za objasnjenje

//TODO 25.12. undoredo pogledaj sta se desava
public class DrawingController {

	private DrawingModel model;
	private DrawingFrame frame;
	private Point clickedPoint;
	private ArrayList<Command> commandList = new ArrayList<Command>();
	private CollectionOfSelectedShapes selectedShapes = new CollectionOfSelectedShapes();
	//private ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
	private Command commandPointer;
	private int commandPointerIndex = -2;
	private Shape selectedShape;
	private Color borderColor = Color.BLACK;
	private Color areaColor = Color.WHITE;
	private ArrayList<String> stringCommandsFromFile = new ArrayList<String>();
	private ArrayList<String> stringCommandsToWriteToFile = new ArrayList<String>();
	private int readLineFromFile = 0;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
	}

	// odavde ce se pozivati repaint() nad view-om
	public void mouseClickedOnPanel(MouseEvent e) {
		if(frame.getTglBtnPoint()) {
			PointDialog pd = new PointDialog();
			pd.setTxtCoordX(Integer.toString(e.getX()));
			pd.setTxtCoordY(Integer.toString(e.getY()));
			pd.setTxtCoordXEditable(false);
			pd.setTxtCoordYEditable(false);
			pd.setVisible(true);
			if(pd.getIsOk())
			{
				clickedPoint = new Point();
				// mora da se uvek napravi nova inace se samo menjaju vrednosti nad referencom na istu tacku
				clickedPoint.setX(e.getX());
				clickedPoint.setY(e.getY());
				clickedPoint.setBorderColor(getBorderColor(pd.getColor()));
				CmdAdd cmd = new CmdAdd(clickedPoint, model);
				 executeCommand(cmd);
			}
			clickedPoint = null;
			
		} else if(frame.getTglBtnLine()) {
			
			if(clickedPoint == null) {
				clickedPoint = new Point(e.getX(),e.getY());
//				JOptionPane.showMessageDialog(new JFrame(), "Odaberite drugu tacku linije", "Obavestenje", JOptionPane.PLAIN_MESSAGE);
			}//ukoliko ne postoji onda se dodeljuje klik, u suprotnom, znaci da vec postoji prva tacka:
			else  {
				Line l = new Line(clickedPoint, new Point(e.getX(), e.getY()));
				LineDialog ld = new LineDialog();
				ld.setTxtStartXEditable(false);
				ld.setTxtStartYEditable(false);
				ld.setTxtEndXEditable(false);
				ld.setTxtEndYXEditable(false);
				ld.setTxtStartX(Integer.toString(l.getStartPoint().getX()));
				ld.setTxtStartY(Integer.toString(l.getStartPoint().getY()));
				ld.setTxtEndX(Integer.toString(l.getEndPoint().getX()));
				ld.setTxtEndY(Integer.toString(l.getEndPoint().getY()));
				ld.setVisible(true);
				if(ld.isOk()) {					
					l.setBorderColor(getBorderColor(ld.getColorLine()));				
					CmdAdd cmd = new CmdAdd(l, model);
					 executeCommand(cmd);
				}
				//postavlja se na null, pri sledecem pokusaju da se napravi linija treba nova tacka
				clickedPoint = null;
				
			}
			
		} else if(frame.getTglBtnCircle()) {
			
			Point center = new Point(e.getX(), e.getY());
			CircleDialog cd = new CircleDialog();
			cd.setTxtCentarX(Integer.toString(center.getX()));
			cd.setTxtCentarY(Integer.toString(center.getY()));
			cd.setTxtCentarXEditable(false);
			cd.setTxtCentarYEditable(false);
			cd.setVisible(true);
			if(cd.getisOk()) {
				int radius = Integer.parseInt(cd.getTxtRadius());	
				Circle c = new Circle(center, radius);
				c.setAreaColor(getAreaColor(cd.getColorIn()));
				c.setBorderColor(getBorderColor(cd.getColorOut()));
				CmdAdd cmd = new CmdAdd(c, model);
				 executeCommand(cmd);
			}
			clickedPoint = null;

		} else if(frame.getTglBtnRectangle()) {
			Point upperLeft = new Point(e.getX(), e.getY());
			RectangleDialog rd = new RectangleDialog();
			rd.setTxtXCoordinate(Integer.toString(upperLeft.getX()));
			rd.setTxtYCoordinate(Integer.toString(upperLeft.getY()));
			rd.setTxtCoordXEditable(false);
			rd.setTxtCoordYEditable(false);
			rd.setVisible(true);
			if(rd.isOk()) {
				int height = Integer.parseInt(rd.getTxtHeight());
				int width = Integer.parseInt(rd.getTxtWidth());
				Rectangle rect = new Rectangle(upperLeft, width, height);
				rect.setAreaColor(getAreaColor(rd.getColorIn()));
				rect.setBorderColor(getBorderColor(rd.getColorOut()));
				CmdAdd cmd = new CmdAdd(rect, model);
				 executeCommand(cmd);
			}
			clickedPoint = null;
		} else if(frame.getTglBtnDonut()) {
			Point center = new Point(e.getX(),e.getY());
			DonutDialog dd = new DonutDialog();
			dd.setTxtCentarX(Integer.toString(center.getX()));
			dd.setTxtCentarY(Integer.toString(center.getY()));
			dd.setTxtCentarXEditable(false);
			dd.setTxtCentarYEditable(false);
			dd.setVisible(true);
			if(dd.isOk()) {
				int radius = Integer.parseInt(dd.getTxtRadius());
				int innerRadius = Integer.parseInt(dd.getTxtInnerRadius());
				Donut d = new Donut(center, radius, innerRadius);
				d.setBorderColor(getBorderColor(dd.getColorOut()));
				d.setAreaColor(getAreaColor(dd.getColorIn()));
				CmdAdd cmd = new CmdAdd(d, model);
				executeCommand(cmd);
			}
			clickedPoint = null;
			
		} else if (frame.getTglBtnHexagon()) {
			Point center = new Point(e.getX(), e.getY());
			HexagonDialog hd = new HexagonDialog();
			hd.setTxtCentarX(Integer.toString(center.getX()));
			hd.setTxtCentarY(Integer.toString(center.getY()));
			hd.setTxtCentarXEditable(false);
			hd.setTxtCentarYEditable(false);
			hd.setVisible(true);
			if(hd.getisOk()) {
				int radius = Integer.parseInt(hd.getTxtRadius());	
				HexagonAdapter hex = new HexagonAdapter(center, radius);
				hex.setAreaColor(getAreaColor(hd.getColorIn()));
				hex.setBorderColor(getBorderColor(hd.getColorOut()));
				CmdAdd cmd = new CmdAdd(hex, model);
				 executeCommand(cmd);
			}
			clickedPoint = null;
		} else if (frame.getTglBtnSelected()) {
			
			//ako je kliknuo na neko drugo dugme posle odabira prve tacke, posle kad izabere liniju opet trazi prvu
			//TODO pogledaj da li jos negde treba
			clickedPoint = null;
			//treba da postavimo koji je objekat selektovan, selektovacemo poslednjeg na kojeg naidjemo u listi 
			// da mu pripada tacka, jer je on na najvisem nivou
			Iterator <Shape> iterator = model.getShapes().iterator();
			while(iterator.hasNext()) {
				Shape shapeFromList = (iterator.next());			
				if(shapeFromList.contains(e.getX(), e.getY())){
					this.selectedShape = shapeFromList; 
				}
			}
			if(selectedShape != null) {
				if(!selectedShape.isSelected()) {	
					modifySelected(selectedShape, true);
				} else {
					modifySelected(selectedShape, false);
				}	
				selectedShape = null;
			} 
		
			
		} 
	
		System.out.println("Click uspesno prosledjen" + e.getY() + e.getY());
		//frame.repaint();
	}

	private Color getBorderColor(Color color) {
		if(color!=null) {
			frame.getBtnBorderColor().setBackground(color);
			this.borderColor = color;
		}
		return borderColor;
		
	}
	
	private Color getAreaColor(Color color) {
		if(color!=null) {
			frame.getBtnAreaColor().setBackground(color);
			this.areaColor=color;
		}
		return areaColor;
	}
	
	private void modifySelected(Shape selectedShape, boolean setSelected) {
		if(selectedShape instanceof Point) {
			Point newPoint = new Point();
			newPoint.setFields(((Point)selectedShape));
			newPoint.setSelected(setSelected);
			CmdModifyPoint cmd = new CmdModifyPoint(((Point)selectedShape),newPoint);
			addRemoveSelectedExecute(cmd);
			executeCommand(cmd);
		} else if (selectedShape instanceof Line) {
			Line newLine = new Line();
			newLine.setFields(((Line)selectedShape));
			newLine.setSelected(setSelected);
			CmdModifyLine cmd = new CmdModifyLine(((Line)selectedShape), newLine);
			addRemoveSelectedExecute(cmd);
			executeCommand(cmd);
		} else if(selectedShape instanceof Donut) {
			Donut newDonut = new Donut();
			newDonut.setFields(((Donut)selectedShape));			
			newDonut.setSelected(setSelected);
			CmdModifyDonut cmd = new CmdModifyDonut(((Donut)selectedShape), newDonut);
			addRemoveSelectedExecute(cmd);
			executeCommand(cmd);
		} else if (selectedShape instanceof Rectangle) {
			Rectangle newRect = new Rectangle();
			newRect.setFields(((Rectangle)selectedShape));			
			newRect.setSelected(setSelected);
			CmdModifyRectangle cmd = new CmdModifyRectangle(((Rectangle)selectedShape),newRect);
			addRemoveSelectedExecute(cmd);
			executeCommand(cmd);
		} else if (selectedShape instanceof Circle) {
			Circle newCircle = new Circle();
			newCircle.setFields(((Circle)selectedShape));			
			newCircle.setSelected(setSelected);
			CmdModifyCircle cmd = new CmdModifyCircle(((Circle)selectedShape), newCircle);
			addRemoveSelectedExecute(cmd);
			executeCommand(cmd);
		} else if (selectedShape instanceof HexagonAdapter) {
			HexagonAdapter newHex = new HexagonAdapter();
			newHex.setFields(((HexagonAdapter)selectedShape));	
			newHex.setSelected(setSelected);
			CmdModifyHexagon cmd = new CmdModifyHexagon(((HexagonAdapter)selectedShape),newHex);
			addRemoveSelectedExecute(cmd);
			executeCommand(cmd);
		}
		
	}

	public void clickedUndo() {
		if (commandPointerIndex > -2) {
			if(commandPointer != null && commandPointerIndex > -2 ) {
				String command = "Undo " + this.commandPointer.toString();
				addRemoveSelectedUnexecute(commandPointer);
				stringCommandsToWriteToFile.add(command);
				frame.addToDLM(command);
				this.commandPointer.unexecute();
				//int index = 0;
				//int index = this.commandList.indexOf(commandPointer);
				//problem jer mozemo da imamo vise komandi npr. cmdchangelayer nad istim oblikom sa indeksa 0 na 1
				//equals nailazi na prvu i pomera pokazivac na nju, umesto na onu koja se desila posle
				/*Iterator<Command> it = commandList.iterator();
				int i = 0;
				while(it.hasNext()) {
					if(it.next().equals(commandPointer)) {
						index=i;
						System.out.println("index je: "+index);
					}
					i++;
				}*/
				if(commandPointerIndex != 0) {
					commandPointer = this.commandList.get(commandPointerIndex - 1);
					commandPointerIndex--;
				} else {
					// ako je undo na prvoj komandi
					frame.getBtnUndo().setEnabled(false);
					commandPointer = null;
					commandPointerIndex--;
				}
				frame.repaint();
				if(!frame.getBtnRedo().isEnabled()) {
					frame.getBtnRedo().setEnabled(true);
				}
			}

		}
				
	}

	public void addRemoveSelectedExecute(Command commandPointer) {
		if(commandPointer instanceof CmdModifyCircle || commandPointer instanceof CmdModifyRectangle
				|| commandPointer instanceof CmdModifyDonut || commandPointer instanceof CmdModifyLine
				|| commandPointer instanceof CmdModifyHexagon || commandPointer instanceof CmdModifyPoint) {
			if(((CmdModify)commandPointer).redo()!=null) {
				if((Boolean)((CmdModify)commandPointer).redo()==true) {
					selectedShapes.add(((CmdModify)commandPointer).getOldState());
				} else {
					selectedShapes.remove(((CmdModify)commandPointer).getOldState());
				}
			}
		}else if(commandPointer instanceof CmdDelete) {
			selectedShapes.remove(((CmdDelete)commandPointer).getShape());
		} else if (commandPointer instanceof CmdDeleteAll) {
			Iterator<CmdDelete> it = ((CmdDeleteAll) commandPointer).getListOfDeleteCommands().iterator();
			while(it.hasNext()) {
				selectedShapes.remove(it.next().getShape());
			}
		}
	}
	
	public void addRemoveSelectedUnexecute(Command commandPointer) {
		if(commandPointer instanceof CmdModifyCircle || commandPointer instanceof CmdModifyRectangle
				|| commandPointer instanceof CmdModifyDonut || commandPointer instanceof CmdModifyLine
				|| commandPointer instanceof CmdModifyHexagon || commandPointer instanceof CmdModifyPoint) {
			if(((CmdModify)commandPointer).undo()!=null) {
				if((Boolean)((CmdModify)commandPointer).undo()==true) {
					selectedShapes.add(((CmdModify)commandPointer).getOldState());
				} else {
					selectedShapes.remove(((CmdModify)commandPointer).getOldState());
				}
			}
		}else if(commandPointer instanceof CmdDelete) {
			selectedShapes.add(((CmdDelete)commandPointer).getShape());
		} else if (commandPointer instanceof CmdDeleteAll) {
			Iterator<CmdDelete> it = ((CmdDeleteAll) commandPointer).getListOfDeleteCommands().iterator();
			while(it.hasNext()) {
				selectedShapes.add(it.next().getShape());
			}
		}
	}
	
	public void clickedRedo() {
		if (commandPointerIndex > -2 ) {
		// mora jer jer je commandPointer null i kad nema komanda 
			if(commandPointer == null) {
				// ako je undo bio na prvoj komandi
				commandPointer = commandList.get(0);
				commandPointerIndex = 0;
				addRemoveSelectedExecute(commandPointer);	
				String command = "Redo " + this.commandPointer.toString();
				stringCommandsToWriteToFile.add(command);
				frame.addToDLM(command);
				commandPointer.execute();
				if(commandList.size() == 1) {
					//ako postoji samo jedna komanda
					frame.getBtnRedo().setEnabled(false);
				}
			} else {
				//int index = 0;
				//int index = this.commandList.indexOf(commandPointer);
				//problem jer mozemo da imamo vise komandi npr. cmdchangelayer nad istim oblikom sa indeksa 0 na 1
				//equals nailazi na prvu i pomera pokazivac na nju, umesto na onu koja se desila posle
				/*Iterator<Command> it = commandList.iterator();
				int i = 0;
				while(it.hasNext()) {
					if(it.next().equals(commandPointer)) {
						index=i;
						System.out.println("index je: "+index);
					}
					i++;
				}*/
				if(commandPointerIndex < this.commandList.size() - 1) {
					commandPointer = commandList.get(commandPointerIndex + 1);
					addRemoveSelectedExecute(commandPointer);	
					String command = "Redo " + this.commandPointer.toString();
					stringCommandsToWriteToFile.add(command);
					frame.addToDLM(command);
					commandPointer.execute();
					commandPointerIndex++;
				} 
				else if(commandPointerIndex + 1 == commandList.size() - 1) {
					frame.getBtnRedo().setEnabled(false);
					commandPointerIndex++;
				} else if(commandPointerIndex == commandList.size() - 1) {
					//kada nema vise komandi za redo, izvrsena poslednja komanda u nizu
					frame.getBtnRedo().setEnabled(false);
				}
				
			}
			frame.repaint();
			if(!frame.getBtnUndo().isEnabled()) {
				frame.getBtnUndo().setEnabled(true);
			}
		}
		//problem da se proverava da je samo null jer je na pocetku aplikacije null
		
		
	}

	public void clickedDelete() {
		// set clickedPoint null
		if(selectedShapes.size() != 0) {
			if(selectedShapes.size() == 1) {
				if(JOptionPane.showConfirmDialog(new JFrame(), 
						"Da li ste sigurni da želite da obrišete selektovani oblik?","Potvrda",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				CmdDelete cmd = new CmdDelete(selectedShapes.get(0), model);
				executeCommand(cmd);
				selectedShapes.removeAll();
				}				
			} else {
				if(JOptionPane.showConfirmDialog(new JFrame(), 
						"Da li ste sigurni da želite da obrišete selektovane oblike?","Potvrda",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				Iterator<Shape> it = selectedShapes.iterator();
				CmdDeleteAll cmdAll = new CmdDeleteAll();
				while(it.hasNext()) {
					CmdDelete cmd = new CmdDelete(it.next(), model);
					cmdAll.add(cmd);
				}
				executeCommand(cmdAll);
				selectedShapes.removeAll();
				}				
			}
		} else {
			// nema selektovanih
		}
		
	}

	public void clickedBringToBack() {
		System.out.println("clickedBringToBack");
		System.out.println(selectedShapes.size());
		if(selectedShapes.size() == 1) {
			System.out.println("One selected shape");
			int index = model.getShapes().indexOf(selectedShapes.get(0));
			if(index != 0) {
				int newIndex = 0; 
				CmdChangeLayer cmd = new CmdChangeLayer(selectedShapes.get(0), model, newIndex);
				executeCommand(cmd);
			}
		} else {
			System.out.println("Selektovano vise od jednog objekta");
			//selektovano je vise od jednog objekta
		}
	}

	public void clickedBringToTop() {
		System.out.println();
		if(selectedShapes.size() == 1) {
			int index = model.getShapes().indexOf(selectedShapes.get(0));
			if(index != model.getShapes().size() - 1) {
				int newIndex = model.getShapes().size() - 1; 
				CmdChangeLayer cmd = new CmdChangeLayer(selectedShapes.get(0), model, newIndex);
				executeCommand(cmd);
			}
		} else {
			//selektovano je vise od jednog objekta
			System.out.println("Selektovano vise od jednog objekta");
		}
	}

	public void clickedToBack() {
		if(selectedShapes.size() == 1) {
			System.out.println("One selected shape");
			int index = model.getShapes().indexOf(selectedShapes.get(0));
			if(index != 0) {
				int newIndex = index - 1; 
				CmdChangeLayer cmd = new CmdChangeLayer(selectedShapes.get(0), model, newIndex);
				executeCommand(cmd);
			}
		} else {
			System.out.println("Selektovano vise od jednog objekta");
			//selektovano je vise od jednog objekta
		}
		
	}

	public void clickedToFront() {
		if(selectedShapes.size() == 1) {
			int index = model.getShapes().indexOf(selectedShapes.get(0));
			if(index != model.getShapes().size() - 1) {
				int newIndex = index + 1; 
				CmdChangeLayer cmd = new CmdChangeLayer(selectedShapes.get(0), model, newIndex);
				executeCommand(cmd);
			}
		} else {
			//selektovano je vise od jednog objekta
		}
	}
	
	public void executeCommand(Command cmd) {
		if(commandPointerIndex == -2) {
			//prva komanda 
			commandPointerIndex = 0;
		} else {
			//int index = this.commandList.indexOf(commandPointer);
			//problem jer mozemo da imamo vise komandi npr. cmdchangelayer nad istim oblikom sa indeksa 0 na 1
			//equals nailazi na prvu i pomera pokazivac na nju, umesto na onu koja se desila posle
			/*Iterator<Command> it = commandList.iterator();
			int i = 0;
			while(it.hasNext()) {
				if(it.next().equals(commandPointer)) {
					index=i;
					System.out.println("index je: "+index);
				}
				i++;
			}*/
			if(commandPointerIndex < commandList.size() - 1) {
				int index = 0;
				int iterator = commandPointerIndex;
				index = commandPointerIndex + 1;
				int sizeOfList = commandList.size();
				while(iterator < sizeOfList - 1) {
					commandList.remove(index);
					iterator++;
				}
				//nova komanda se dodaje posle undo, pointer nije na poslednjoj komandi
				
			}
			commandPointerIndex++;
		}
		
		String command = cmd.toString();
		stringCommandsToWriteToFile.add(command);
		frame.addToDLM(cmd);
		cmd.execute();
		commandList.add(cmd);
		commandPointer = cmd;
		if(!frame.getBtnUndo().isEnabled()) {
			frame.getBtnUndo().setEnabled(true);
		}
		System.out.println(this.model.getShapes().size());
		frame.repaint();
	}


	public void clickedModify() {
		if(selectedShapes.size() == 1) {
			
			if (selectedShapes.get(0) instanceof Point) {
				Point p = (Point) selectedShapes.get(0);
				PointDialog pd = new PointDialog();
				pd.setTxtCoordX(Integer.toString(p.getX()));
				pd.setTxtCoordY(Integer.toString(p.getY()));
				pd.setColor(p.getBorderColor());
				pd.setVisible(true);
				if (pd.getIsOk()) {
					Point newPoint = new Point();
					newPoint.setX(Integer.parseInt(pd.getTxtCoordX()));
					newPoint.setY(Integer.parseInt(pd.getTxtCoordY()));
					newPoint.setBorderColor(pd.getColor());
					newPoint.setSelected(true);
					CmdModifyPoint cmd = new CmdModifyPoint(p, newPoint);
					addRemoveSelectedExecute(cmd);
					executeCommand(cmd);
				}
			} else if (selectedShapes.get(0) instanceof Line) {
				Line l = (Line) selectedShapes.get(0);
				LineDialog ld = new LineDialog();
				ld.setTxtStartX(Integer.toString(l.getStartPoint().getX()));
				ld.setTxtStartY(Integer.toString(l.getStartPoint().getY()));
				ld.setTxtEndX(Integer.toString(l.getEndPoint().getX()));
				ld.setTxtEndY(Integer.toString(l.getEndPoint().getY()));
				ld.setColorLine(l.getBorderColor());
				ld.setVisible(true);
				if (ld.isOk()) {
					Line newLine = new Line();
					newLine.setStartPoint(new Point(Integer.parseInt(ld.getTxtStartX()),
							Integer.parseInt(ld.getTxtStartY())));
					newLine.setEndPoint(new Point(Integer.parseInt(ld.getTxtEndX()),
							Integer.parseInt(ld.getTxtEndY())));
					newLine.setBorderColor(ld.getColorLine());
					newLine.setSelected(true);
					CmdModifyLine cmd = new CmdModifyLine(l, newLine);
					addRemoveSelectedExecute(cmd);
					executeCommand(cmd);
				}
			} else if (selectedShapes.get(0) instanceof Rectangle) {
				Rectangle r = (Rectangle) selectedShapes.get(0);
				RectangleDialog rd = new RectangleDialog();
				rd.setTxtXCoordinate((Integer.toString(r.getUpperLeftPoint().getX())));
				rd.setTxtYCoordinate(Integer.toString(r.getUpperLeftPoint().getY()));
				rd.setTxtHeight(Integer.toString(r.getHeight()));
				rd.setTxtWidth(Integer.toString(r.getWidth()));
				rd.setColorIn(r.getAreaColor());
				rd.setColorOut(r.getBorderColor());
				rd.setVisible(true);
				if (rd.isOk()) {
					Rectangle newRect = new Rectangle();
					newRect.setUpperLeftPoint(new Point(Integer.parseInt(rd.getTxtXCoordinate()),
							Integer.parseInt(rd.getTxtYCoordinate())));
					newRect.setHeight(Integer.parseInt(rd.getTxtHeight()));
					newRect.setWidth(Integer.parseInt(rd.getTxtWidth()));
					newRect.setAreaColor(rd.getColorIn());
					newRect.setBorderColor(rd.getColorOut());
					newRect.setSelected(true);
					CmdModifyRectangle cmd = new CmdModifyRectangle(r, newRect);
					addRemoveSelectedExecute(cmd);
					executeCommand(cmd);
				}
			} else if (selectedShapes.get(0) instanceof Donut) {
				Donut d = (Donut) selectedShapes.get(0);
				DonutDialog dd = new DonutDialog();
				dd.setTxtCentarX(Integer.toString(d.getCenter().getX()));
				dd.setTxtCentarY(Integer.toString(d.getCenter().getY()));
				dd.setTxtInnerRadius(Integer.toString(d.getInnerRadius()));
				dd.setTxtRadius(Integer.toString(d.getRadius()));
				dd.setColorIn(d.getAreaColor());
				dd.setColorOut(d.getBorderColor());
				dd.setVisible(true);
				if(dd.isOk()) {
					Donut newDonut = new Donut();
					newDonut.setCenter(new Point(Integer.parseInt(dd.getTxtCentarX()),Integer.parseInt(dd.getTxtCentarY())));
					newDonut.setRadius(Integer.parseInt(dd.getTxtRadius()));
					newDonut.setInnerRadius(Integer.parseInt(dd.getTxtInnerRadius()));
					newDonut.setBorderColor(dd.getColorOut());
					newDonut.setAreaColor(dd.getColorIn());
					newDonut.setSelected(true);
					CmdModifyDonut cmd = new CmdModifyDonut(d, newDonut);
					addRemoveSelectedExecute(cmd);
					executeCommand(cmd);
				}
			} else if (selectedShapes.get(0) instanceof Circle) {
				Circle c = (Circle) selectedShapes.get(0);
				//mora posle donuta jer ulazi u if i ako je donut jer je donut insanca kruga 
				CircleDialog cd = new CircleDialog();
				cd.setTxtCentarX(Integer.toString(c.getCenter().getX()));
				cd.setTxtCentarY(Integer.toString(c.getCenter().getY()));
				cd.setTxtRadius(Integer.toString(c.getRadius()));
				cd.setColorIn(c.getAreaColor());
				cd.setColorOut(c.getBorderColor());
				cd.setVisible(true);
				if (cd.getisOk()) {
					Circle newCircle = new Circle(); 
					newCircle.setCenter(new Point(Integer.parseInt(cd.getTxtCentarX()),
							Integer.parseInt(cd.getTxtCentarY())));
					newCircle.setRadius(Integer.parseInt(cd.getTxtRadius()));
					newCircle.setAreaColor(cd.getColorIn());
					newCircle.setBorderColor(cd.getColorOut());
					newCircle.setSelected(true);
					CmdModifyCircle cmd = new CmdModifyCircle(c, newCircle);
					addRemoveSelectedExecute(cmd);
					executeCommand(cmd);
				}
			} else if (selectedShapes.get(0) instanceof HexagonAdapter) {
				HexagonAdapter h = (HexagonAdapter) selectedShapes.get(0);
				HexagonDialog hd = new HexagonDialog();
				hd.setTxtCentarX(Integer.toString(h.getCenter().getX()));
				hd.setTxtCentarY(Integer.toString(h.getCenter().getY()));
				hd.setTxtRadius(Integer.toString(h.getRadius()));
				hd.setColorIn(h.getAreaColor());
				hd.setColorOut(h.getBorderColor());
				hd.setVisible(true);
				if(hd.getisOk()) {
					Point center = new  Point(Integer.parseInt(hd.getTxtCentarX()),
							Integer.parseInt(hd.getTxtCentarY()));
					int radius = Integer.parseInt(hd.getTxtRadius());
					HexagonAdapter newHex = new HexagonAdapter(center, radius); 
					newHex.setAreaColor(hd.getColorIn());
					newHex.setBorderColor(hd.getColorOut());
					newHex.setSelected(true);
					CmdModifyHexagon cmd = new CmdModifyHexagon(h, newHex);
					addRemoveSelectedExecute(cmd);
					executeCommand(cmd);
				}

			}
			
		} else {
			System.out.println("Selektovano vise od jednog oblika ili nijedan");
		}
		
	}

	public void clickedSave() {
		//TODO skontaj da li je potrebno ovo da radis
		/*Iterator <Shape> it = selectedShapes.getSelectedShapes().iterator();
		while(it.hasNext()) {
			Shape selectedShape = (it.next());			
			selectedShape.setSelected(false);
			}
		selectedShapes.removeAll();*/
		//problem jer izgledaju da su selektovani a u stvari nisu u listi 
		SaveSerializedDrawing ssd = new SaveSerializedDrawing(this);
		SavingManager sm = new SavingManager(ssd);
		sm.save();
		
	}
	

	public void clickedSaveFile() {
		SaveCommandsToTextFile scttf = new SaveCommandsToTextFile(this);
		SavingManager sm = new SavingManager(scttf);
		sm.save();
	}

	public void clickedNextLine() {
		if(readLineFromFile < stringCommandsFromFile.size()) {
			makeCommand(stringCommandsFromFile.get(readLineFromFile));
			readLineFromFile++;
			System.out.println("NEXT");
		}
	}
	

	public void clickedOpen() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("C:/Users/Natalija/Documents"));
		fc.setDialogTitle("Choose a file");
		//fc.setFileFilter(new FileTypeFilter(".bin", "File"));
		int result = fc.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String filePath = file.getPath();
			try {
				//ArrayList<Shape> shapesFromFile = SerializableCommandList.readFromFile(filePath);
				 ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath));
		    	 @SuppressWarnings("unchecked")
				ArrayList<Shape> shapesFromFile = (ArrayList<Shape>) objectInputStream.readObject();
				Iterator<Shape> it = shapesFromFile.iterator();
				while(it.hasNext()) {
					model.add(it.next());
				}
				//TODO dodato zatvaranje stream-a
				objectInputStream.close();
				frame.repaint();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}


	public void clickedOpenFile() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("C:/Users/Natalija/Documents"));
		fc.setDialogTitle("Choose a file");
		//fc.setFileFilter(new FileTypeFilter(".bin", "File"));
		int result = fc.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String filePath = file.getPath();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(filePath));
				String line = reader.readLine();
				while(line != null) {
					stringCommandsFromFile.add(line);
					System.out.println(line);
					line = reader.readLine();
				}
				//TODO dodato zatvaranje reader-a
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void clickedBorderColor() {
		Color colorOfBorder = JColorChooser.showDialog(null, "Izaberite boju", Color.BLACK);
		if(colorOfBorder!=null) {
			borderColor=colorOfBorder;
		}
		frame.getBtnBorderColor().setBackground(borderColor);
	}

	public void clickedAreaColor() {
		Color colorOfArea = JColorChooser.showDialog(null, "Izaberite boju", Color.BLACK);
		if(colorOfArea!=null) {
			areaColor=colorOfArea;
		}
		frame.getBtnAreaColor().setBackground(areaColor);
	}
	
	private void makeCommand(String line) {
		System.out.println("make command");
		String[] splits = line.split("[, =():]");
		System.out.println(splits[0]);
		if(splits[0].equals("Added")) {
			Command cmd = makeAddCommand(splits);
			executeCommand(cmd);
		} else if (splits[0].equals("Modified")) {
			Command cmd = makeModifyCommand(splits);
			executeCommand(cmd);
		} else if (splits[0].equals("Deleted")) {
			String[] deleteCommands = line.split(";");
			if(deleteCommands.length > 1) {
				CmdDeleteAll cmd = new CmdDeleteAll();
				for(int i=0; i<deleteCommands.length;i++) {
					System.out.println("delete command:" + deleteCommands[i]);
					String[] splitsForCmdDelete = deleteCommands[i].split("[, =():]");
					CmdDelete cmdDelete = (CmdDelete)makeDeleteCommand(splitsForCmdDelete);
					cmd.add(cmdDelete);
				}
				executeCommand(cmd);
			} else {
				Command cmd = makeDeleteCommand(splits);
				executeCommand(cmd);
			}
			
		} else if(splits[0].equals("Moved")) {
			Command cmd = makeChangeLayerCommand(splits);
			executeCommand(cmd);
		} else if(splits[0].equals("Undo")){
			clickedUndo();
		} else if(splits[0].equals("Redo")){
			clickedRedo();
		}
	}
	
	private Command makeChangeLayerCommand(String[] splits) {
		switch(splits[1]) {
			case "Rectangle":{
				Rectangle rect = makeRectangle(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(rect)){
						rect = (Rectangle) shapeFromList;
						break;
					}
				}
				int index = Integer.parseInt(splits[27]);
				 Command cmd = new CmdChangeLayer(rect, this.model, index);
				
				return cmd;
				}
			case "Hexagon":{
				
				HexagonAdapter hex = makeHexagon(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(hex)){
						hex = (HexagonAdapter) shapeFromList;
						break;
					}
				}
				int index = Integer.parseInt(splits[24]);
				 Command cmd = new CmdChangeLayer(hex, this.model, index);
				return cmd;
			}
			case "Donut": {
				
				Donut donut = makeDonut(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(donut)){
						donut = (Donut) shapeFromList;
						break;
					}
				}
				int index = Integer.parseInt(splits[27]);
				 Command cmd = new CmdChangeLayer(donut, this.model, index);		
				return cmd;
			}
			case "Circle": {
				
				Circle circle = makeCircle(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(circle)){
						circle = (Circle) shapeFromList;
						break;
					}
				}
				int index = Integer.parseInt(splits[24]);
				 Command cmd = new CmdChangeLayer(circle, this.model, index);
				return cmd;
			}
			case "Line":{
				
				Line l = makeLine(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(l)){
						l = (Line) shapeFromList;
						break;
					}
				}
				int index = Integer.parseInt(splits[19]);
				 Command cmd = new CmdChangeLayer(l, this.model, index);
				 return cmd;
			}
			case "Point":{	
				Point point = makePoint(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(point)){
						point = (Point) shapeFromList;
						break;
					}
				}
				
				int index = Integer.parseInt(splits[14]);
				 Command cmd = new CmdChangeLayer(point, this.model, index);
				 return cmd;
			}
		}
		return null;
	}

	private Command makeDeleteCommand(String[] splits) {
		switch(splits[1]) {
			case "Rectangle":{
				Rectangle rect = makeRectangle(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(rect)){
						rect = (Rectangle) shapeFromList;
						break;
					}
				}
				Command cmd = new CmdDelete(rect, this.model);
				return cmd;
				}
			case "Hexagon":{
				
				HexagonAdapter hex = makeHexagon(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(hex)){
						hex = (HexagonAdapter) shapeFromList;
						break;
					}
				}
				Command cmd = new CmdDelete(hex, this.model);
				return cmd;
			}
			case "Donut": {
				
				Donut donut = makeDonut(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(donut)){
						donut = (Donut) shapeFromList;
						break;
					}
				}
				Command cmd = new CmdDelete(donut, this.model);
				return cmd;
			}
			case "Circle": {
				
				Circle circle = makeCircle(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(circle)){
						circle = (Circle) shapeFromList;
						break;
					}
				}
				Command cmd = new CmdDelete(circle, this.model);
				return cmd;
			}
			case "Line":{
				
				Line l = makeLine(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(l)){
						l = (Line) shapeFromList;
						break;
					}
				}
				Command cmd = new CmdDelete(l, this.model);
				return cmd;
			}
			case "Point":{
				
				Point point = makePoint(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(point)){
						point = (Point) shapeFromList;
						break;
					}
				}
				
				Command cmd = new CmdDelete(point, this.model);
				return cmd;
			}
		}
		return null;
	}
	
	private Command makeModifyCommand(String[] splits) {
		switch(splits[1]) {
			case "Rectangle":{
					System.out.println("rectangle case in modified");
					Rectangle oldRect = makeRectangle(splits, 0);
					
					Iterator <Shape> iterator = model.getShapes().iterator();
					while(iterator.hasNext()) {
						Shape shapeFromList = (iterator.next());			
						if(shapeFromList.equals(oldRect)){
							System.out.println("found oldRect in shapeFromList");
							oldRect = (Rectangle) shapeFromList;
							break;
						}
						
					}
					
					Rectangle newRect = makeRectangle(splits, 25);
					 Command cmd = new CmdModifyRectangle(oldRect, newRect);
					 addRemoveSelectedExecute(cmd);
					 return cmd;
					
			}
			case "Hexagon": {
					
					HexagonAdapter oldHex = makeHexagon(splits, 0);
		
					Iterator <Shape> iterator = model.getShapes().iterator();
					while(iterator.hasNext()) {
						Shape shapeFromList = (iterator.next());			
						if(shapeFromList.equals(oldHex)){
							oldHex = (HexagonAdapter) shapeFromList;
							break;
						}
					}
					HexagonAdapter newHex = makeHexagon(splits, 22);
					 Command cmd = new CmdModifyHexagon(oldHex, newHex);
					 addRemoveSelectedExecute(cmd);
					return cmd;
				}
			case "Donut": {
				
				Donut oldDonut = makeDonut(splits, 0);
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(oldDonut)){
						oldDonut = (Donut) shapeFromList;
						break;
					}
				}
				Donut newDonut = makeDonut(splits, 25);
				System.out.println(newDonut.isSelected());
				 Command cmd = new CmdModifyDonut(oldDonut, newDonut);
				 addRemoveSelectedExecute(cmd);
				return cmd;
			}
			case "Circle":{
				
				Circle oldCircle = makeCircle(splits, 0);
	
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(oldCircle)){
						oldCircle = (Circle) shapeFromList;
						break;
					}
				}
				Circle newCircle = makeCircle(splits, 22);
				 Command cmd = new CmdModifyCircle(oldCircle, newCircle);
				 addRemoveSelectedExecute(cmd);
				 return cmd;
			}
			case "Line":{
			
				Line oldLine = makeLine(splits, 0);
				
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(oldLine)){
						oldLine = (Line) shapeFromList;
						break;
					}
				}
				Line newLine = makeLine(splits, 17);
				 Command cmd = new CmdModifyLine(oldLine, newLine);
				 addRemoveSelectedExecute(cmd);
				return cmd;
				
			}
			case "Point":{
				
				Point oldPoint = makePoint(splits, 0);
				
				Iterator <Shape> iterator = model.getShapes().iterator();
				while(iterator.hasNext()) {
					Shape shapeFromList = (iterator.next());			
					if(shapeFromList.equals(oldPoint)){
						oldPoint = (Point) shapeFromList;
						break;
					}
				}
				Point newPoint = makePoint(splits, 12);
				 Command cmd = new CmdModifyPoint(oldPoint, newPoint);
				 addRemoveSelectedExecute(cmd);
				return cmd;
			}
			
		}
		return null;
		
	}

	private Command makeAddCommand(String[] splits) {
		switch(splits[1]) {
			case "Rectangle":{
				System.out.println("rectangle case");
				Rectangle rect = makeRectangle(splits, 0);
				Command cmd = new CmdAdd(rect, this.model);
				return cmd;				}
			case "Hexagon":{
				HexagonAdapter hex = makeHexagon(splits, 0);
				Command cmd = new CmdAdd(hex, model);
				return cmd;
			}
			case "Donut": {
				Donut donut = makeDonut(splits, 0);
				Command cmd = new CmdAdd(donut, model);
				return cmd;			}
			case "Circle": {
				Circle circle = makeCircle(splits, 0);
				Command cmd = new CmdAdd(circle, model);
				return cmd;
			}
			case "Line":{
				Line l = makeLine(splits, 0);
				Command cmd = new CmdAdd(l, model);
				return cmd;			}
			case "Point":{
				Point point = makePoint(splits, 0);
				Command cmd = new CmdAdd(point, model);
				 return cmd;
			}
		}
		return null;
		
	}


	private Rectangle makeRectangle(String[] splits, int diff) {
		int x = Integer.parseInt(splits[3 + diff]);
		int y = Integer.parseInt(splits[4 + diff]);
		Point p = new Point(x, y);
		int w = Integer.parseInt(splits[7 + diff]);
		int h = Integer.parseInt(splits[10 + diff]);
		Color border = new Color(Integer.parseInt(splits[13 + diff]),Integer.parseInt(splits[14 + diff]),Integer.parseInt(splits[15 + diff]));
		Color fill = new Color(Integer.parseInt(splits[19 + diff]),Integer.parseInt(splits[20 + diff]),Integer.parseInt(splits[21 + diff]));
		boolean selected;
		if(splits[24 + diff].equals("selected")) {
			selected = true;
		} else {
			selected = false;
		}
		Rectangle rect = new Rectangle(p, h, w, selected);
		rect.setBorderColor(border);
		rect.setAreaColor(fill);
		return rect;
		
	}
	
	private HexagonAdapter makeHexagon(String[] splits, int diff) {
		int x = Integer.parseInt(splits[3 + diff]);
		int y = Integer.parseInt(splits[4 + diff]);
		Point center = new Point(x, y);
		int radius = Integer.parseInt(splits[7 + diff]);
		Color border = new Color(Integer.parseInt(splits[10 + diff]),Integer.parseInt(splits[11 + diff]),Integer.parseInt(splits[12 + diff]));
		Color fill = new Color(Integer.parseInt(splits[16 + diff]),Integer.parseInt(splits[17 + diff]),Integer.parseInt(splits[18 + diff]));
		boolean selected;
		if(splits[21 + diff].equals("selected")) {
			selected = true;
		} else {
			selected = false;
		}
		HexagonAdapter hex = new HexagonAdapter(center, radius);
		hex.setAreaColor(fill);
		hex.setBorderColor(border);
		hex.setSelected(selected);
		return hex;
	}
	
	private Donut makeDonut(String[] splits, int diff) {
		int x = Integer.parseInt(splits[3 + diff]);
		int y = Integer.parseInt(splits[4 + diff]);
		Point center = new Point(x, y);
		int outerRadius = Integer.parseInt(splits[7 + diff]);
		int innerRadius = Integer.parseInt(splits[10 + diff]);
		Color border = new Color(Integer.parseInt(splits[13 + diff]),Integer.parseInt(splits[14 + diff]),Integer.parseInt(splits[15 + diff]));
		Color fill = new Color(Integer.parseInt(splits[19 + diff]),Integer.parseInt(splits[20 + diff]),Integer.parseInt(splits[21 + diff]));
		boolean selected;
		if(splits[24 + diff].equals("selected")) {
			selected = true;
		} else {
			selected = false;
		}
		Donut donut;
		donut = new Donut(center, outerRadius, innerRadius);
		donut.setAreaColor(fill);
		donut.setBorderColor(border);
		donut.setSelected(selected);
		System.out.println(donut.toString());
		return donut;
		
	}
	
	private Circle makeCircle (String[] splits, int diff) {
		int x = Integer.parseInt(splits[3 + diff]);
		int y = Integer.parseInt(splits[4 + diff]);
		Point center = new Point(x, y);
		int outRadius = Integer.parseInt(splits[7 + diff]);
		Color border = new Color(Integer.parseInt(splits[10 + diff]),Integer.parseInt(splits[11 + diff]),Integer.parseInt(splits[12 + diff]));
		Color fill = new Color(Integer.parseInt(splits[16 + diff]),Integer.parseInt(splits[17 + diff]),Integer.parseInt(splits[18 + diff]));
		boolean selected;
		if(splits[21 + diff].equals("selected")) {
			selected = true;
		} else {
			selected = false;
		}
		Circle circle = new Circle(center, outRadius);
		circle.setAreaColor(fill);
		circle.setBorderColor(border);
		circle.setSelected(selected);
		return circle;
	}
	
	private Line makeLine(String[] splits, int diff) {
		int x = Integer.parseInt(splits[3 + diff]);
		int y = Integer.parseInt(splits[4 + diff]);
		int x1 = Integer.parseInt(splits[7 + diff]);
		int y1 = Integer.parseInt(splits[8 + diff]);
		Point startPoint = new Point(x, y);
		Point endPoint = new Point(x1, y1);
		Color border = new Color(Integer.parseInt(splits[11 + diff]),Integer.parseInt(splits[12 + diff]),Integer.parseInt(splits[13 + diff]));
		boolean selected;
		if(splits[16 + diff].equals("selected")) {
			selected = true;
		} else {
			selected = false;
		}	
		Line l = new Line(startPoint, endPoint);
		l.setSelected(selected);
		l.setBorderColor(border);
		return l;
	}
	
	private Point makePoint(String[] splits, int diff) {
		int x = Integer.parseInt(splits[3+ diff]);
		int y = Integer.parseInt(splits[4+ diff]);
		Color border = new Color(Integer.parseInt(splits[7 + diff]),Integer.parseInt(splits[8 + diff]),Integer.parseInt(splits[9 + diff]));
		boolean selected;
		if(splits[11 + diff].equals("selected")) {
			selected = true;
		} else {
			selected = false;
		}	
		Point point = new Point(x, y);
		point.setSelected(selected);
		point.setBorderColor(border);
		return point;
	}
	
	// Getters and setters
	public ArrayList<Command> getCommandList() {
		return commandList;
	}

	public void setCommandList(ArrayList<Command> commandList) {
		this.commandList = commandList;
	}
	
	public Command getCommandPointer() {
		return commandPointer;
	}

	public void setCommandPointer(Command commandPointer) {
		this.commandPointer = commandPointer;
	}
	
	public CollectionOfSelectedShapes getCollectionOfSelectedShapes() {
		return selectedShapes;
	}
	
	public ArrayList<String> getStringCommandsToWriteToFile (){
		return stringCommandsToWriteToFile;
	}
	
	public DrawingModel getModel() {
		return model;
	}
}
