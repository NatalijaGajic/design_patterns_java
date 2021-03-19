package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LineDialog extends JDialog {

	private final JPanel pnlLine = new JPanel();
	private JTextField txtStartX;
	private JTextField txtStartY;
	private JTextField txtEndX;
	private JTextField txtEndY;
	private boolean isOk;
	private Color colorLine;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LineDialog dialog = new LineDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LineDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		pnlLine.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setModal(true);
		getContentPane().add(pnlLine, BorderLayout.CENTER);
		JLabel lblXKoordinataPoetne = new JLabel("X koordinata po\u010Detne ta\u010Dke ");
		JLabel lblYKoordinataPoetne = new JLabel("Y koordinata po\u010Detne ta\u010Dke");
		JLabel lblXKoordinataKrajnje = new JLabel("X koordinata krajnje ta\u010Dke");
		JLabel lblYKoordinataKranje = new JLabel("Y koordinata krajnje ta\u010Dke");
		
		txtStartX = new JTextField();
		txtStartX.setColumns(10);
		txtStartY = new JTextField();
		txtStartY.setColumns(10);
		txtEndX = new JTextField();
		txtEndX.setColumns(10);
		txtEndY = new JTextField();
		txtEndY.setColumns(10);
		
		JButton btnNewButton = new JButton("Izaberi boju");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorLine = JColorChooser.showDialog(null, "Izaberite boju linije", null);

			}
		});
		GroupLayout gl_pnlLine = new GroupLayout(pnlLine);
		gl_pnlLine.setHorizontalGroup(
			gl_pnlLine.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLine.createSequentialGroup()
					.addGap(53)
					.addGroup(gl_pnlLine.createParallelGroup(Alignment.LEADING)
						.addComponent(lblXKoordinataPoetne)
						.addComponent(lblYKoordinataPoetne)
						.addComponent(lblXKoordinataKrajnje)
						.addComponent(lblYKoordinataKranje))
					.addGap(46)
					.addGroup(gl_pnlLine.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton)
						.addComponent(txtEndY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtEndX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtStartY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtStartX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(94, Short.MAX_VALUE))
		);
		gl_pnlLine.setVerticalGroup(
			gl_pnlLine.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLine.createSequentialGroup()
					.addGap(26)
					.addGroup(gl_pnlLine.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXKoordinataPoetne)
						.addComponent(txtStartX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlLine.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYKoordinataPoetne)
						.addComponent(txtStartY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlLine.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXKoordinataKrajnje)
						.addComponent(txtEndX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlLine.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYKoordinataKranje)
						.addComponent(txtEndY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(btnNewButton)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		pnlLine.setLayout(gl_pnlLine);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//kad se klikne ok
						try {
							Integer.parseInt(getTxtStartX());
							Integer.parseInt(getTxtStartY());
							Integer.parseInt(getTxtEndX());
							Integer.parseInt(getTxtEndY());
							setIsOk(true);
							dispose();
						}
						catch(NumberFormatException e1){
							JOptionPane.showMessageDialog(new JFrame(), 
									"Neispravan unos podataka.Proverite da li su sva polja popunjena brojnim vrednostima!", "Greška", 
									JOptionPane.WARNING_MESSAGE);
						}
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public String getTxtStartX() {
		return txtStartX.getText();
	}

	public void setTxtStartX(String txtStartX) {
		this.txtStartX.setText(txtStartX);
	}
	
	public String getTxtStartY() {
		return txtStartY.getText();
	}

	public void setTxtStartY(String txStartY) {
		this.txtStartY.setText(txStartY);
	}
	
	public String getTxtEndX() {
		return txtEndX.getText();
	}

	public void setTxtEndX(String txtEndX) {
		this.txtEndX.setText(txtEndX);
	}

	public String getTxtEndY() {
		return txtEndY.getText();
	}

	public void setTxtEndY(String txtEndY) {
		this.txtEndY.setText(txtEndY);
	}


	public boolean isOk() {
		return isOk;
	}

	public void setIsOk(boolean isOk) {
		this.isOk = isOk;
	}

	public Color getColorLine() {
		return colorLine;
	}

	public void setTxtStartXEditable(boolean b)
	{
		this.txtStartX.setEditable(b);
	}
	
	public void setTxtStartYEditable(boolean b)
	{
		this.txtStartY.setEditable(b);
	}
	
	public void setTxtEndYXEditable(boolean b)
	{
		this.txtEndY.setEditable(b);
	}
	
	public void setTxtEndXEditable(boolean b)
	{
		this.txtEndX.setEditable(b);
	}
	
	public void setColorLine(Color colorLIne) {
		this.colorLine = colorLine;
	}
	
}
