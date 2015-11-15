
/**
 * @authors TekNepal,Tim O'Rourke, Qiaoran Li, Chanvi Kotak
 *
 */


import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.*;
import java.awt.event.*;
//import java.io.*;

public class TestGUI implements MenuListener, ActionListener {

	JFrame frmResearchPaperDatabase;
	private JMenuBar menubar;
	private JMenu mnFile;
	private JMenuItem mntmFacultyLogin;
	private JMenuItem mntmReset;
	private JMenuItem mntmExit;
	private JTextField jtfKeywords;
	private JTextField jtfTitle;
	private JTextField jtfAuthor;

	/**
	 * Launch the application.
	 */

		
	/**
	 * Create the application.
	 */
	public TestGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmResearchPaperDatabase = new JFrame();
		frmResearchPaperDatabase.setTitle("Research Paper Database");
		frmResearchPaperDatabase.setBounds(200, 200, 450, 300);
		frmResearchPaperDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmResearchPaperDatabase.setLocationRelativeTo(null);
		
		
		
		JPanel jpBanner = new JPanel();
		jpBanner.setBackground(Color.CYAN);
		frmResearchPaperDatabase.getContentPane().add(jpBanner, BorderLayout.NORTH);
		JLabel banner = new JLabel("Welcome to the Research Paper Database!");
		banner.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 20));
		jpBanner.add(banner);
		
		JPanel jpButtons = new JPanel();
		frmResearchPaperDatabase.getContentPane().add(jpButtons, BorderLayout.SOUTH);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		jpButtons.add(btnSearch);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(this);
		jpButtons.add(btnClear);
		
		JButton btnLogin = new JButton("Login...");
		btnLogin.addActionListener(this);
		jpButtons.add(btnLogin);
		
		
		JPanel rightPanel = new JPanel();
		
		
		
		
		
		
		
		
		JPanel leftPanel = new JPanel();
		
		
		
		
		
		
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		
		//JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		frmResearchPaperDatabase.getContentPane().add(leftPanel, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane();
		leftPanel.add(splitPane);
		
		JPanel Panelleft = new JPanel();
		splitPane.setLeftComponent(Panelleft);
		Panelleft.setPreferredSize( new Dimension(500,1000));
		Panelleft.setLayout(new GridLayout(3,2));
		
		JLabel lblKeywords = new JLabel("Keywords: ");
		Panelleft.add(lblKeywords);
		
		jtfKeywords = new JTextField();
		Panelleft.add(jtfKeywords);
		jtfKeywords.setColumns(10);
		
		JLabel lblAuthor = new JLabel("Author: ");
		Panelleft.add(lblAuthor);
		
		jtfAuthor = new JTextField();
		Panelleft.add(jtfAuthor);
		jtfAuthor.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title");
		Panelleft.add(lblTitle);
		
		jtfTitle = new JTextField();
		Panelleft.add(jtfTitle);
		jtfTitle.setColumns(10);
		
		JPanel panelRight = new JPanel();
		splitPane.setRightComponent(panelRight);
		panelRight.setLayout(new GridLayout(1,1));
		
		JTextArea jtaResult = new JTextArea(5,7);
		jtaResult.setText("This is a Test Area");
		//JScrollBar scrollBar = new JScrollBar();
		jtaResult.setLineWrap(true);
		panelRight.add(jtaResult);
		//panelRight.add(scrollBar);
		
		
		
		
		
		
		JMenuBar menuBar = new JMenuBar();
		frmResearchPaperDatabase.setJMenuBar(menuBar);	
			JMenu mnFile = new JMenu("File");
			menuBar.add(mnFile);
				JMenuItem mntmFacultyLogin = new JMenuItem("Faculty Login...");
				mntmFacultyLogin.addActionListener(this);
				mnFile.add(mntmFacultyLogin);
				JMenuItem mntmReset = new JMenuItem("Reset");
				mntmReset.addActionListener(this);
				mnFile.add(mntmReset);
				JMenuItem mntmExit = new JMenuItem("Exit");
				mntmExit.addActionListener(this);
				mnFile.add(mntmExit);		
			JMenu mnHelp = new JMenu("Help");
			menuBar.add(mnHelp);
				JMenuItem mntmAbout = new JMenuItem("About");
				mntmAbout.addActionListener(this);
				mnHelp.add(mntmAbout);
				JMenuItem mntmHowToUse = new JMenuItem("How to Use");
				mntmHowToUse.addActionListener(this);
				mnHelp.add(mntmHowToUse);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == ("Reset") || ae.getActionCommand() == "Clear") {
			jtfTitle.setText(" ");
			jtfAuthor.setText("");
			jtfWord.setText("");
		}
		else if(ae.getActionCommand() =="Exit") {
			System.exit(0);
		
		}
		else if(ae.getActionCommand() == "Login...") {
			frmResearchPaperDatabase.setVisible(false);
			LoginWindow lw = new LoginWindow(this);			
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