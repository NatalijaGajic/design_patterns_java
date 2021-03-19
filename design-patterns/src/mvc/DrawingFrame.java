package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class DrawingFrame extends JFrame {

	// panel na kom se iscrtavaju oblici - view
	// kontroler kome se prosledjuje klik da bi ga obradio
	
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	JToolBar toolBar = new JToolBar();
	JToolBar southToolbar = new JToolBar();
	JToggleButton tglBtnPoint = new JToggleButton("Point");
	JToggleButton tglBtnLine = new JToggleButton("Line");
	JToggleButton tglBtnCircle = new JToggleButton("Circle");
	JToggleButton tglBtnRectangle = new JToggleButton("Rectangle");
	JToggleButton tglBtnDonut = new JToggleButton("Donut");
	JToggleButton tglBtnModify = new JToggleButton("Modify");
	JToggleButton tglBtnSelect = new JToggleButton("Select");
	JToggleButton tglBtnDelete = new JToggleButton("Delete");
	JToggleButton tglBtnHexagon = new JToggleButton("Hexagon");
	JToggleButton tglBtnBringToBack = new JToggleButton("Bring to back");
	JToggleButton tglBtnRedo = new JToggleButton("Redo");
	JToggleButton tglBtnBringToTop = new JToggleButton("Bring to top");
	JToggleButton tglBtnUndo = new JToggleButton("Undo");
	JToggleButton tglBtnToFront = new JToggleButton("To front");
	JToggleButton tglBtnToBack = new JToggleButton("To back");
	JToggleButton tglBtnSave = new JToggleButton("Save");
	JToggleButton tglBtnSaveFile = new JToggleButton("Save file");
	JToggleButton tglBtnOpen = new JToggleButton("Open");
	JToggleButton tglBtnOpenFile = new JToggleButton("Open file");
	JToggleButton tglBtnNextLine = new JToggleButton("Next line");
	JButton btnAreaColor = new JButton("");
	JButton btnBorderColor = new JButton("");
	JList<Object> jlistOfCommands = new JList<Object>();
	DefaultListModel<Object> dlm = new DefaultListModel<Object>();

	private DrawingView view = new DrawingView();
	private DrawingController controller;
	private final JScrollPane scrollPane = new JScrollPane();
	
	// u konstruktoru se postavlja listener na view (panel)

	public DrawingFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setTitle("IT17-2017 Natalija Gajic");
		setBounds(10, 10, 1920, 1080);
		
		jlistOfCommands.setModel(dlm);
		// TODO setuj i lista se nalazi u kontroleru
		scrollPane.setViewportView(jlistOfCommands);
		
		buttonGroup.add(tglBtnPoint);
		toolBar.add(tglBtnPoint);

		buttonGroup.add(tglBtnLine);
		toolBar.add(tglBtnLine);

		buttonGroup.add(tglBtnCircle);
		toolBar.add(tglBtnCircle);
		
		buttonGroup.add(tglBtnDonut);
		toolBar.add(tglBtnDonut);

		buttonGroup.add(tglBtnRectangle);
		toolBar.add(tglBtnRectangle);
		
		toolBar.add(tglBtnHexagon);
		buttonGroup.add(tglBtnHexagon);
		
		buttonGroup.add(tglBtnSelect);
		toolBar.add(tglBtnSelect);
		
		tglBtnModify.setEnabled(false);
		buttonGroup.add(tglBtnModify);
		toolBar.add(tglBtnModify);
		tglBtnModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.clickedModify();
			}
		});


		tglBtnDelete.setEnabled(false);
		buttonGroup.add(tglBtnDelete);
		tglBtnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.clickedDelete();
			}
		});
		toolBar.add(tglBtnDelete);
		
		tglBtnToBack.setEnabled(false);
		toolBar.add(tglBtnToBack);
		buttonGroup.add(tglBtnToBack);
		tglBtnToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.clickedToBack();
			}
		});
		
		tglBtnBringToBack.setEnabled(false);
		toolBar.add(tglBtnBringToBack);
		buttonGroup.add(tglBtnBringToBack);
		tglBtnBringToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.clickedBringToBack();
			}
		});
		
		tglBtnToFront.setEnabled(false);
		toolBar.add(tglBtnToFront);
		buttonGroup.add(tglBtnToFront);
		tglBtnToFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.clickedToFront();
			}
		});
		
		tglBtnBringToTop.setEnabled(false);
		toolBar.add(tglBtnBringToTop);
		buttonGroup.add(tglBtnBringToTop);
		tglBtnBringToTop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.clickedBringToTop();
			}
		});
		
		tglBtnUndo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.clickedUndo();
			}
		});
		
		tglBtnUndo.setEnabled(false);
		toolBar.add(tglBtnUndo);
		buttonGroup.add(tglBtnUndo);
		tglBtnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.clickedRedo();
			}
		});
		
		tglBtnRedo.setEnabled(false);
		toolBar.add(tglBtnRedo);
		buttonGroup.add(tglBtnRedo);
		this.view.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				controller.mouseClickedOnPanel(e);
			}
		});
	
	toolBar.add(tglBtnOpenFile);
	buttonGroup.add(tglBtnOpenFile);
	tglBtnOpenFile.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			controller.clickedOpenFile();
		}
	});
	
	toolBar.add(tglBtnNextLine);
	buttonGroup.add(tglBtnNextLine);
	tglBtnNextLine.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			controller.clickedNextLine();
		}
	});
		
		
	toolBar.add(tglBtnSaveFile);
	buttonGroup.add(tglBtnSaveFile);
	tglBtnSaveFile.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			controller.clickedSaveFile();
		}
	});
		
			
			toolBar.add(tglBtnOpen);
			buttonGroup.add(tglBtnOpen);
			tglBtnOpen.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					controller.clickedOpen();
				}
			});
		
		//getContentPane().add(view, BorderLayout.CENTER);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		view.setBounds(10, 40, 5000, 5000);
		view.setBackground(Color.BLUE);
		contentPane.add(view);
		
		GroupLayout gl_panel = new GroupLayout(view);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 1273, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 651, Short.MAX_VALUE)
		);
		view.setLayout(gl_panel);
		southToolbar.add(btnAreaColor);
		
		btnAreaColor.setBackground(Color.WHITE);
		buttonGroup.add(btnAreaColor);
		btnAreaColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//boja unutrasnjosti
				controller.clickedAreaColor();
			}
		});
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(view);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		toolBar.add(tglBtnSave);
		buttonGroup.add(tglBtnSave);
		tglBtnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.clickedSave();
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("Area color");
		southToolbar.add(lblNewLabel_1);
		southToolbar.add(btnBorderColor);
		
		btnBorderColor.setBackground(Color.BLACK);
		buttonGroup.add(btnBorderColor);
		btnBorderColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//boja unutrasnjosti
				controller.clickedBorderColor();
			}
		});
		
		
		contentPane.add(southToolbar, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("Border color");
		southToolbar.add(lblNewLabel);
		
		southToolbar.add(scrollPane);

	}
	
	public void addToDLM(Object o) {
		dlm.addElement(o);
	}
	

	// Getters and setters
	public DrawingView getView() {
		return view;
	}

	public void setView(DrawingView view) {
		this.view = view;
	}

	public DrawingController getController() {
		return controller;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public DefaultListModel<Object> getDefaultListModel() {
		return dlm;
	}
	
	public boolean getTglBtnPoint() {
		return tglBtnPoint.isSelected();
	}

	public boolean getTglBtnLine() {
		return tglBtnLine.isSelected();
	}

	public boolean getTglBtnRectangle() {
		return tglBtnRectangle.isSelected();
	}

	public boolean getTglBtnCircle() {
		return tglBtnCircle.isSelected();
	}

	public boolean getTglBtnDonut() {
		return tglBtnDonut.isSelected();
	}

	public boolean getTglBtnSelected() {
		return tglBtnSelect.isSelected();
	}

	public boolean getTglBtnModify() {
		return tglBtnModify.isSelected();
	}

	public boolean getTglBtnDelete() {
		return tglBtnDelete.isSelected();
	}
	
	public boolean getTglBtnHexagon() {
		return tglBtnHexagon.isSelected();
	}
	
	public boolean getTglBtnUndo() {
		return tglBtnUndo.isSelected();
	}
	
	public boolean getTglBtnRedo() {
		return tglBtnRedo.isSelected();
	}
	
	public JToggleButton getBtnUndo() {
		return this.tglBtnUndo;
	}

	public JToggleButton getBtnRedo() {
		return this.tglBtnRedo;
	}

	public JToggleButton getTglBtnSave() {
		return tglBtnSave;
	}
	

	
	public JButton getBtnAreaColor() {
		return btnAreaColor;
	}
	
	public JButton getBtnBorderColor() {
		return btnBorderColor;
	}
	
	public JToggleButton getBtnModify() {
		return tglBtnModify;
	}
	
	public JToggleButton getBtnDelete() {
		return tglBtnDelete;
	}
	
	public JToggleButton getBtnToFront() {
		return tglBtnToFront;
	}
	public JToggleButton getBtnToBack() {
		return tglBtnToBack;
	}
	
	public JToggleButton getBtnBringToTop() {
		return tglBtnBringToTop;
	}
	
	public JToggleButton getBtnBringToBack() {
		return tglBtnBringToBack;
	}
	
}
