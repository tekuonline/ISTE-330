/**
 * <h1>Paper Database Data</h1>
 * The Paper Database program implements an application that
 * provides a secure, simple and functional user interface to
 * browse, update, delete, insert new article instances to the
 * back end database. 
 * <p>
 * <b> Edit Window Class </b> is a class that provides admin and faculty the screen to 
 * edit the searched paper and save the paper
 * It implements the frame screen for edit window and performs actions based on action listeners
 * 
 * @author  Chanvi Kotak, Tek Nepal, Qiaoran Li
 * @version 1.0
 * @since 2015-12-16 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField txtTitle;
	public JTextField txtpaperId;
	public JTextField txtcitation;
	public JTextArea txaAbstract;
	private PaperDatabase paperDb = new PaperDatabase();
	
/**
 * This methods creates dialog for edit page
 */
	public EditWindow() {
		setTitle("Edit Window");
		setBounds(100, 100, 585, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		bottomPanel.add(btnCancel);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		bottomPanel.add(btnSave);
		
		
		JPanel topPanel = new JPanel();

		topPanel.setLayout(new GridLayout(3,2));
		contentPane.add(topPanel, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Title");
		topPanel.add(lblTitle);
		
		txtTitle = new JTextField();
		topPanel.add(txtTitle);
		txtTitle.setText("");
		txtTitle.setColumns(10);
		
		JLabel lblKeyword_1 = new JLabel("Citation");
		topPanel.add(lblKeyword_1);
		
		txtcitation = new JTextField();
		txtcitation.setText("");
		topPanel.add(txtcitation);
		txtcitation.setColumns(10);
		
		
		txtpaperId = new JTextField();
		txtpaperId.setText("");
		topPanel.add(txtpaperId);
		txtpaperId.setColumns(10);
		txtpaperId.setVisible(false);
		
		JLabel lblAbstract = new JLabel("Abstract:");
		topPanel.add(lblAbstract);

		
		txaAbstract = new JTextArea();
		contentPane.add(txaAbstract, BorderLayout.CENTER);
		txaAbstract.setPreferredSize(new Dimension(200, 250));
		
	}
/*This method allows the admin and faculty to edit and save the paper
 * (non-Javadoc)
 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == ("Cancel")) {
		dispose();
		}
		else if(ae.getActionCommand() =="Save") {
			paperDb.connect();
			String title = txtTitle.getText().trim();
			String citation = txtcitation.getText().trim();
			String abst = txaAbstract.getText().trim();
			String id = (txtpaperId.getText().trim());
			System.out.println(title +" " + citation + " "+ abst);
			ArrayList<String> list = new ArrayList<String>();
			list.add(title);
			list.add(abst);
			list.add(citation);
			list.add(id);
			String sql = "UPDATE papers SET title = ?, abstract = ?, citation = ? WHERE id = ?";
			if (paperDb.setData(sql, list)){
				JOptionPane.showMessageDialog(null, "Updated Paper " + title);
				this.dispose();
			}
			else{
				JOptionPane.showMessageDialog(null, "Could not Update paper");
			}
		}
	}

}
