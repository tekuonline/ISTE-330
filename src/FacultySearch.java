

/**
 * ISTE 330 Part 3
 * Contributor: Tek, Qiaoran, Chanvi
 * Professor: Micheal Floeser
 * A class for Faculty Search Window
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
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
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class FacultySearch extends JFrame implements MenuListener, ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PaperDatabase paperDb = new PaperDatabase();
	private Login login = new Login();
	private JScrollPane JScrollPane;
	
	
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
	private JLabel lblHello = new JLabel("Hello! " + "Faculty");
	
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
		setBounds(100, 100, 593, 355);
		setSize(900,500);
		
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
		lblWelcome.setForeground (Color.red);
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
		
		jPanelSearchArea.add(txtKeyword);
        txtKeyword.setColumns(10);	
		
		jPanelSearchArea.add(lblTitle);
		txtTitle = new JTextField();
		jPanelSearchArea.add(txtTitle);
		txtTitle.setColumns(10);
		
		jPanelSearchArea.add(lblAuthor);
		txtAuthor = new JTextField();
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
		
		txtResultList.setText("");
		JScrollPane = new JScrollPane(txtResultList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(JScrollPane, BorderLayout.CENTER);
		
	
	}
	
	//perform action when clear, search or login button is clicked
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == ("Reset") || ae.getActionCommand() == "Clear") {
			txtKeyword.setText("");
			txtTitle.setText("");
			txtAuthor.setText("");
			//listResultBox.removeAll();
			//clearTable();
		}
		
		else if(ae.getActionCommand() =="Search") {
			//clearTable();
			String authorName = txtAuthor.getText().trim();
			String title = txtTitle.getText().trim();
			String keyWords = txtKeyword.getText().trim();
			
			paperDb.connect();
			ArrayList<String> column = new ArrayList<String>();
			ArrayList<String> values = new ArrayList<String>();
			
			if(!authorName.isEmpty()){
				column.add("person.fname");
				values.add(txtAuthor.getText().trim());
				System.out.println(txtAuthor.getText().trim());
			}
			if(!title.isEmpty()){
				column.add("title");
				values.add(txtTitle.getText().trim());
				System.out.println(txtTitle.getText().trim());
			}
			if(!keyWords.isEmpty()){
				column.add("keyword");
				values.add(txtKeyword.getText().trim());
				System.out.println(txtKeyword.getText().trim());
			}
			paperDb.bigList.clear();
			paperDb.bigList.clear();
			
			paperDb.fetch(column, values);					
			for(int i = 0; i < paperDb.bigList.size(); i++) {
				  ArrayList<String> small = paperDb.bigList.get(i);
				 //model.addRow(new Object[]{false, small.get(1), small.get(2), small.get(3)});
				}	
		}
		else if(ae.getActionCommand().equalsIgnoreCase("Exit")) {
			 System.exit(0); 
		}
		else if(ae.getActionCommand() == "Logout") {
			dispose();
					
		}
		else if(ae.getActionCommand() == "Add") {
			 EditWindow ed = new EditWindow();	
			 setVisible(true);
			 ed.setVisible(true);
		}
		else if(ae.getActionCommand() == "Update") {
			 EditWindow ed = new EditWindow();	
			 setVisible(true);
			 ed.setVisible(true);
		}
		else if(ae.getActionCommand() == "Delete") {
			 EditWindow ed = new EditWindow();	
			 setVisible(true);
			 ed.setVisible(true);
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
