

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
import java.util.Random;

import javax.swing.JCheckBox;

public class EditWindow extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField txtKeyword;
	private JTextField txtTitle;
	private JTextField txtKeyword_1;
	//private JEditorPane editorPane;
	private JTextArea editorPane = new JTextArea();
	private PaperDatabase paperDb = new PaperDatabase();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditWindow frame = new EditWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditWindow() {
		setTitle("Edit Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//JScrollPane scrollPane = new JScrollPane();
		//contentPane.add(scrollPane, BorderLayout.CENTER);
		
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
		JLabel lblKeyword = new JLabel("Keyword:");
		topPanel.add(lblKeyword);
		
		txtKeyword = new JTextField();
		txtKeyword.setText("");
		topPanel.add(txtKeyword);
		txtKeyword.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title");
		topPanel.add(lblTitle);
		
		txtTitle = new JTextField();
		topPanel.add(txtTitle);
		txtTitle.setText("");
		txtTitle.setColumns(10);
		
		JLabel lblKeyword_1 = new JLabel("Citation");
		topPanel.add(lblKeyword_1);
		
		txtKeyword_1 = new JTextField();
		txtKeyword_1.setText("");
		topPanel.add(txtKeyword_1);
		txtKeyword_1.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(200, 250));
		
		
		editorPane = new JTextArea();
		scrollPane.setViewportView(editorPane);
		
		
	}

	//public EditWindow(FacultySearch facultySearch) {
		// TODO Auto-generated constructor stub
	//}
	
	//perform action when clear, search or login button is clicked
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == ("Cancel")) {
		dispose();
		}
		else if(ae.getActionCommand() =="Save") {
			String r = paperDb.getRole();
			Random rand = new Random();
			int  n = rand.nextInt(50) + 1;
			String title = txtTitle.getText().trim();
			String citation = txtTitle.getText().trim();
			String abst = editorPane.getText().trim();
			String PaperStat = paperDb.insertPaper(n, title, abst, citation);
			System.out.println(PaperStat);
			System.out.println(r);
//			if(PaperStat.equalsIgnoreCase("Cannot add Duplicate Paper!"))	{
//				System.out.println("Cannot add duplicate paper!");
//			}
//			else if (PaperStat.equals("Paper Inserted")){
//				System.out.println("Paper Inserted");
//				
//			}
//			else if (PaperStat.equals("you are not authorized to add papers")){
//				
//				System.out.println("you are not authorized to add papers");
//			}
		}
	}

}
