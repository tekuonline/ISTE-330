
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

public class PrimeSearch implements MenuListener, ActionListener {

	JFrame frmResearchPaperDatabase;
	private JMenuBar menubar;
	private JMenu mnFile;
	private JMenuItem mntmFacultyLogin;
	private JMenuItem mntmReset;
	private JMenuItem mntmExit;
   
   private JTextField jtfTitle;
   private JTextField jtfAuthor;
   private JTextField jtfWord;

	/**
	 * Launch the application.
	 */

		
	/**
	 * Create the application.
	 */
	public PrimeSearch() {
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
		
		JPanel jpTitleSearch = new JPanel();
		JLabel jlTitle = new JLabel("Title:     ");
		jpTitleSearch.add(jlTitle);
		jtfTitle = new JTextField(" Title          ");
		jpTitleSearch.add(jtfTitle);
		leftPanel.add(jpTitleSearch);

		JPanel jpAuthorSearch = new JPanel();
		JLabel jlAuthor = new JLabel("Author:   ");
		jpAuthorSearch.add(jlAuthor);
		jtfAuthor = new JTextField(" Author         ");
		jpAuthorSearch.add(jtfAuthor);
		leftPanel.add(jpAuthorSearch);

		JPanel jpWordSearch = new JPanel();
		JLabel jlWord = new JLabel("Keyword(s): ");
		jpWordSearch.add(jlWord);
		jtfWord = new JTextField(" Keyword(s)       ");
		jtfWord.setToolTipText("Separate multiple keywords by a comma.");
		jpWordSearch.add(jtfWord);
		leftPanel.add(jpWordSearch);
		
		JPanel jpWordSearch1 = new JPanel();
		JLabel jlWord1 = new JLabel("Keyword(s): ");
		jpWordSearch1.add(jlWord1);
		JTextField jtfWord1 = new JTextField(" Keyword(s)       ");
		jpWordSearch1.add(jtfWord);
		rightPanel.add(jpWordSearch);
		
		
		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		frmResearchPaperDatabase.getContentPane().add(leftPanel, BorderLayout.CENTER);
		
		
		
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