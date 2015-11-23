

/**
 * ISTE 330 Part 3
 * Contributor: Tek, Qiaoran, Chanvi
 * Professor: Micheal Floeser
 * A class for Faculty Search Window
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import Presentation.EditWindow;

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
import javax.swing.JCheckBox;

public class FacultySearch extends JFrame implements MenuListener, ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	// Menu bar attributes
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnFile = new JMenu("File");
	private JMenuItem mntmLogout = new JMenuItem("Logout");
	private JMenuItem mntmReset = new JMenuItem("Reset");
	private JMenuItem mntmExit = new JMenuItem("Exit");
	private JMenu mnHelp = new JMenu("Help");
	private JMenuItem mntmAbout = new JMenuItem("About");
	private JMenuItem mntmHowToUse = new JMenuItem("How to use -->");
   
   //search control attributes
	private JTextField txtKeyword;
	private JTextField txtTitle;
	private JTextField txtAuthor;
   private JLabel lblKeyword = new JLabel("Keyword:");
   private JLabel lblAuthor = new JLabel("Author:");
   private JLabel lblTitle = new JLabel("Title:");	
	
	// title panel attributes
	private JPanel jPanelTitle = new JPanel();
	private JLabel lblWelcome = new JLabel("Welcome to Paper Database!");
	private JLabel lblHello = new JLabel("Hello! Professor Floeser");
	
	// Search result area attributes
	private JPanel jPanelSearchArea = new JPanel();
   private JTextArea txtResultList = new JTextArea();
	
	// button panel attributes
   private JCheckBox chckbxLimit = new JCheckBox("Limit to my articles only");
   private JPanel jPanelbottomButton = new JPanel();
   private JButton btnSearch = new JButton("Search");
   private JButton btnClear = new JButton("Clear");
   private JButton btnLogout = new JButton("Logout");
   private JButton btnAdd = new JButton("Add");
   private JButton btnUpdate = new JButton("Update");
   private JButton btnDelete = new JButton("Delete");

	// Create the frame.
	public FacultySearch() {
		setTitle("Faculty/Student Search Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 635, 355);


		
		/**
		 *  Menu bar content
		 */
		setJMenuBar(menuBar);		
		menuBar.add(mnFile);		
		mnFile.add(mntmLogout);
		mntmLogout.addActionListener(this);	
		mnFile.add(mntmReset);	
		mntmReset.addActionListener(this);	
		mnFile.add(mntmExit);
		mntmExit.addActionListener(this);	
		menuBar.add(mnHelp);		
		mnHelp.add(mntmAbout);		
		mnHelp.add(mntmHowToUse);
		
		
		/**
		 * Main panel with in the frame
		 */
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		/**
		 * North panel called jPanelTitle
		 * contains title and greeting message for Paper users	
		 */
		contentPane.add(jPanelTitle, BorderLayout.NORTH);
		jPanelTitle.setLayout(new GridLayout(2,1));		
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
		jPanelTitle.add(lblWelcome);		
		lblHello.setHorizontalAlignment(SwingConstants.TRAILING);
		jPanelTitle.add(lblHello);
		
		
		/**
        * West panel: search area
        */
		
		contentPane.add(jPanelSearchArea, BorderLayout.WEST);
		jPanelSearchArea.setLayout(new GridLayout(3,2));
		
		jPanelSearchArea.add(lblKeyword);
		txtKeyword = new JTextField();
		txtKeyword.setText("Keyword");
		jPanelSearchArea.add(txtKeyword);
        txtKeyword.setColumns(10);	
		
		jPanelSearchArea.add(lblTitle);
		txtTitle = new JTextField();
		txtTitle.setText("Title");
		jPanelSearchArea.add(txtTitle);
		txtTitle.setColumns(10);
		
		jPanelSearchArea.add(lblAuthor);
		txtAuthor = new JTextField();
		txtAuthor.setText("Author");
		jPanelSearchArea.add(txtAuthor);
		txtAuthor.setColumns(10);
		

		/**
        * Bottom button panel
        */      
		contentPane.add(jPanelbottomButton, BorderLayout.SOUTH);
		jPanelbottomButton.add(chckbxLimit);
		
        btnSearch.addActionListener(this);    	
    	jPanelbottomButton.add(btnSearch);
		btnClear.addActionListener(this);		
		jPanelbottomButton.add(btnClear);
		btnAdd.addActionListener(this);		
		jPanelbottomButton.add(btnAdd);
		btnUpdate.addActionListener(this);		
		jPanelbottomButton.add(btnUpdate);
		btnDelete.addActionListener(this);		
		jPanelbottomButton.add(btnDelete);
		btnLogout.addActionListener(this);		
		jPanelbottomButton.add(btnLogout);

	
		txtResultList.setWrapStyleWord(true);
		txtResultList.setLineWrap(true);
		txtResultList.add(new JScrollBar());
		txtResultList.setText("ResultShowsHere");
		contentPane.add(txtResultList, BorderLayout.CENTER);
		txtKeyword.setColumns(10);
	
	}
	
	//perform action when clear, search or login button is clicked
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals("Clear")) {
			txtTitle.setText("");
			txtAuthor.setText("");
			txtKeyword.setText("");
		}
		else if(ae.getActionCommand() =="Exit") {
			dispose();
		
		}
		else if(ae.getActionCommand() == "Login...") {
			//frmResearchPaperDatabase.setVisible(false);
			//LoginWindow lw = new LoginWindow(this);			
		}
		else if(ae.getActionCommand() == "Add") {
			 EditWindow ed = new EditWindow();	
             ed.setVisible(true);
			 setVisible(false);
		}
	}

	@Override
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuSelected(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
