

/**
 * ISTE 330 Part 3
 * Contributor: Tek, Qiaoran, Chanvi
 * Professor: Micheal Floeser
 * A class for Edit Window
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JCheckBox;

public class EditWindow extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//private JTextField txtKeyword;
	public JTextField txtTitle;
	public JTextField txtpaperId;
	public JTextField txtcitation;
	public JTextArea txaAbstract;
	private PaperDatabase paperDb = new PaperDatabase();
	private AdminSearch as = new AdminSearch();

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
	
	//perform action when clear, search or login button is clicked
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
