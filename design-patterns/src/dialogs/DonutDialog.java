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
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DonutDialog extends JDialog {

	private final JPanel pnlDonut = new JPanel();
	private JTextField txtCentarX;
	private JTextField txtCentarY;
	private JTextField txtInnerRadius;
	private JTextField txtRadius;
	private Color colorIn;
	private Color colorOut;
	private boolean isOk;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DonutDialog dialog = new DonutDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DonutDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		pnlDonut.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setModal(true);
		getContentPane().add(pnlDonut, BorderLayout.CENTER);
		
		JLabel lblXCC = new JLabel("X koordinata centra");
		JLabel lblYCC = new JLabel("Y koordinata centra");
		JLabel lblInnerRadius = new JLabel("Unutra\u0161nji radius");
		JLabel lblOutterRadius = new JLabel("Spolja\u0161nji radius");
		
		txtCentarX = new JTextField();
		txtCentarX.setColumns(10);
		txtCentarY = new JTextField();
		txtCentarY.setColumns(10);
		txtInnerRadius = new JTextField();
		txtInnerRadius.setColumns(10);
		txtRadius = new JTextField();
		txtRadius.setColumns(10);
		
		JButton btnColorIn = new JButton("Boja unutra\u0161njosti");
		btnColorIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//boja unutrasnjosti
				colorIn = JColorChooser.showDialog(null, "Izaberite boju", Color.WHITE);
			}
		});
		
		JButton btnColorOut = new JButton("Boja ivica");
		btnColorOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//boja spolj.
				colorOut = JColorChooser.showDialog(null, "Izaberite boju", Color.BLACK);
			}
		});
		GroupLayout gl_pnlDonut = new GroupLayout(pnlDonut);
		gl_pnlDonut.setHorizontalGroup(
			gl_pnlDonut.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDonut.createSequentialGroup()
					.addGroup(gl_pnlDonut.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDonut.createSequentialGroup()
							.addGap(41)
							.addComponent(lblXCC))
						.addGroup(gl_pnlDonut.createSequentialGroup()
							.addGap(43)
							.addGroup(gl_pnlDonut.createParallelGroup(Alignment.LEADING)
								.addComponent(lblYCC)
								.addGroup(gl_pnlDonut.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblOutterRadius)
									.addComponent(lblInnerRadius)
									.addComponent(btnColorIn)))))
					.addGroup(gl_pnlDonut.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDonut.createSequentialGroup()
							.addGap(77)
							.addGroup(gl_pnlDonut.createParallelGroup(Alignment.LEADING)
								.addComponent(txtCentarY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtCentarX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlDonut.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnColorOut)))
					.addContainerGap(87, Short.MAX_VALUE))
		);
		gl_pnlDonut.setVerticalGroup(
			gl_pnlDonut.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDonut.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_pnlDonut.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXCC)
						.addComponent(txtCentarX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlDonut.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblYCC)
						.addComponent(txtCentarY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlDonut.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDonut.createSequentialGroup()
							.addComponent(lblInnerRadius)
							.addGap(18)
							.addComponent(lblOutterRadius))
						.addGroup(gl_pnlDonut.createSequentialGroup()
							.addComponent(txtInnerRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addGroup(gl_pnlDonut.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnColorIn)
						.addComponent(btnColorOut))
					.addGap(19))
		);
		pnlDonut.setLayout(gl_pnlDonut);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {

							Integer.parseInt(getTxtCentarX());
							Integer.parseInt(getTxtCentarY());
							if(Integer.parseInt(getTxtRadius()) < 0 || Integer.parseInt(getTxtInnerRadius()) < 0) {
								throw new Exception();
							} else {
								if(Integer.parseInt(getTxtRadius()) <= Integer.parseInt(getTxtInnerRadius())) {
									throw new Exception();
								} else {
									setOk(true);
								}
							}
							dispose();
						}
						catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(new JFrame(), "Neispravan unos podataka.Proverite da li su sva polja popunjena brojnim vrednostima!", "Greška", JOptionPane.WARNING_MESSAGE);

						} catch (Exception e1) {
							JOptionPane.showMessageDialog(new JFrame(), "Vrednosti poluprecnika moraju da budu pozitivne! Unutrasnji radius mora da bude manji od spoljasnjeg!", "Greška", JOptionPane.WARNING_MESSAGE);

						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	//Getters and setters
	public Color getColorIn() {
		return colorIn;
	}

	public void setColorIn(Color colorIn) {
		this.colorIn = colorIn;
	}

	public Color getColorOut() {
		return colorOut;
	}

	public void setColorOut(Color colorOut) {
		this.colorOut = colorOut;
	}

	public String getTxtCentarX() {
		return txtCentarX.getText();
	}

	public void setTxtCentarX(String txtCentarX) {
		this.txtCentarX.setText(txtCentarX);
	}

	public String getTxtCentarY() {
		return txtCentarY.getText();
	}

	public void setTxtCentarY(String txtCentarY) {
		this.txtCentarY.setText(txtCentarY);
	}

	public String getTxtInnerRadius() {
		return txtInnerRadius.getText();
	}

	public void setTxtInnerRadius(String txtInnerRadius) {
		this.txtInnerRadius.setText(txtInnerRadius);
	}

	public String getTxtRadius() {	
		return txtRadius.getText();
	}

	public void setTxtRadius(String txtRadius) {
		this.txtRadius.setText(txtRadius);
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}
	
	public void setTxtCentarXEditable(boolean b) {
		this.txtCentarX.setEditable(b);
	}
	
	public void setTxtCentarYEditable(boolean b) {
		this.txtCentarY.setEditable(b);
	}
	
	public void setTxtRadiusEditable(boolean b) {
		this.txtRadius.setEditable(b);
	}
	public void setTxtInnerRadiusXEditable(boolean b) {
		this.txtInnerRadius.setEditable(b);
	}

}
