
/**
 * ISTE 330 Part 3
 * @author  Tek, Qiaoran, Chanvi
 * Professor: Micheal Floeser
 * A class for Edit Window
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

public class AddWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField txtTitle;
	public JTextField txtcitation;
	public JTextArea txaAbstract;
	private PaperDatabase paperDb = new PaperDatabase();
	private SearchWindow as = new SearchWindow();

	public AddWindow() {
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
        topPanel.setLayout(new GridLayout(3, 2));
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

		JLabel lblAbstract = new JLabel("Abstract:");
		topPanel.add(lblAbstract);

		txaAbstract = new JTextArea();
		contentPane.add(txaAbstract, BorderLayout.CENTER);
		txaAbstract.setPreferredSize(new Dimension(200, 250));

	}

	/*
	 * //perform action when clear, search or login button is clicked
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == ("Cancel")) {
			dispose();
		} else if (ae.getActionCommand() == "Save") {
			paperDb.connect();
			String title = txtTitle.getText().trim();
			String citation = txtcitation.getText().trim();
			String abst = txaAbstract.getText().trim();
			System.out.println(title + " " + citation + " " + abst);
			ArrayList<String> list = new ArrayList<String>();
			list.add(title);
			list.add(abst);
			list.add(citation);
			String sql = "INSERT INTO papers (title, abstract, citation) VALUES (?,?,?);";

			if (paperDb.setData(sql, list)) {
				JOptionPane.showMessageDialog(null, "Added Paper " + title);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Could not add paper");
			}
		}
	}

}
