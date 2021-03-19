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

public class HexagonDialog extends JDialog{


		private final JPanel pnlHexagon = new JPanel();
		private JTextField txtCentarX;
		private JTextField txtCentarY;
		private JTextField txtRadius;
		private Color colorIn;
		private Color colorOut;
		private boolean isOk;
		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			try {
				CircleDialog dialog = new CircleDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Create the dialog.
		 */
		public HexagonDialog() {
			setBounds(100, 100, 450, 300);
			getContentPane().setLayout(new BorderLayout());
			pnlHexagon.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(pnlHexagon, BorderLayout.CENTER);
			this.setModal(true);
			JLabel lblXCC = new JLabel("X koordinata centra");
			JLabel lblYCC = new JLabel("Y koordinata centra");
			JLabel lblDuinaPoluprenika = new JLabel("Du\u017Eina polupre\u010Dnika");
			txtCentarX = new JTextField();
			
			txtCentarX.setColumns(10);
			txtCentarY = new JTextField();
			
			txtCentarY.setColumns(10);
			txtRadius = new JTextField();
			txtRadius.setColumns(10);
			JButton btnBoja = new JButton("Boja unutra\u0161njosti");
			btnBoja.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					colorIn=JColorChooser.showDialog(null, "Izaberite boju", Color.WHITE);
				}
			});
			
			JButton btnBojaIvica = new JButton("Boja ivica");
			btnBojaIvica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					colorOut=JColorChooser.showDialog(null, "Izaberite boju", Color.BLACK);
				}
			});
			GroupLayout gl_pnlCircle = new GroupLayout(pnlHexagon);
			gl_pnlCircle.setHorizontalGroup(
				gl_pnlCircle.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCircle.createSequentialGroup()
						.addGap(51)
						.addGroup(gl_pnlCircle.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pnlCircle.createSequentialGroup()
								.addComponent(btnBoja)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnBojaIvica))
							.addGroup(gl_pnlCircle.createSequentialGroup()
								.addGroup(gl_pnlCircle.createParallelGroup(Alignment.LEADING)
									.addComponent(lblXCC)
									.addComponent(lblYCC)
									.addComponent(lblDuinaPoluprenika))
								.addGap(56)
								.addGroup(gl_pnlCircle.createParallelGroup(Alignment.LEADING)
									.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtCentarY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txtCentarX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(123, Short.MAX_VALUE))
			);
			gl_pnlCircle.setVerticalGroup(
				gl_pnlCircle.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlCircle.createSequentialGroup()
						.addGap(30)
						.addGroup(gl_pnlCircle.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblXCC)
							.addComponent(txtCentarX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_pnlCircle.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblYCC)
							.addComponent(txtCentarY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(34)
						.addGroup(gl_pnlCircle.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblDuinaPoluprenika)
							.addComponent(txtRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
						.addGroup(gl_pnlCircle.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnBoja)
							.addComponent(btnBojaIvica))
						.addGap(26))
			);
			pnlHexagon.setLayout(gl_pnlCircle);
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("OK");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								if(Integer.parseInt(getTxtRadius()) <= 0) {
									throw new Exception();
								} else {
									Integer.parseInt(getTxtCentarX());
									Integer.parseInt(getTxtCentarY());
									setIsOk(true);
									dispose();
								}
								
							}
							catch (NumberFormatException e1){
								JOptionPane.showMessageDialog(new JFrame(), 
										"Neispravan unos podataka.Proverite da li su sva polja popunjena brojnim vrednostima!!", "Greška", 
										JOptionPane.WARNING_MESSAGE);
							} 
							catch (Exception e1){
								JOptionPane.showMessageDialog(new JFrame(), 
										"Vrednost poluprecnika mora da bude pozitivna!", "Greška", 
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

		//Getters and setters
		public void setColorIn(Color colorIn) {
			this.colorIn=colorIn;
		}
		
		public Color getColorIn() {
			return this.colorIn;
		}
		public void setColorOut(Color colorOut) {
			this.colorOut = colorOut;
		}
		
		public Color getColorOut() {
			return this.colorOut;
		}
		public void setIsOk(boolean isOk) {
			this.isOk = isOk;
		}
		public boolean getisOk() {
			return isOk;
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
			this.txtCentarY.setText(txtCentarY);;
		}

		public String getTxtRadius() {
			return txtRadius.getText();
		}

		public void setTxtRadius(String txtRadius) {
			this.txtRadius.setText(txtRadius); ;
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
	

}
