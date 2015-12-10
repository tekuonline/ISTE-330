
/**
 * ISTE 330 Part 3
 * Contributor: Tek, Qiaoran, Chanvi
 * Professor: Micheal Floeser
 * A class for Public Search Window
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.JTable;

import Presentation.*;

public class PublicSearch extends JFrame implements MenuListener, ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	PaperDatabase paperDb = new PaperDatabase();
	Person p = new Person();
	
	
	// Menu bar attributes
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnFile = new JMenu("File");
	private JMenuItem mntmLogin = new JMenuItem("Login");
	private JMenuItem mntmReset = new JMenuItem("Reset");
	private JMenuItem mntmExit = new JMenuItem("Exit");
	private JMenu mnHelp = new JMenu("Help");
	private JMenuItem mntmAbout = new JMenuItem("About");
	private JMenuItem mntmHowToUse = new JMenuItem("How to use -->");
	private JScrollPane JScrollPane;
	private JList listResultBox = new JList();
	private DefaultListModel listModel = new DefaultListModel();
	 private JTable tableResult =new JTable();
	   private DefaultTableModel model = new DefaultTableModel(); 
	
	
 
	
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
	private JLabel lblHello;
	
	
	// Search result area attributes
	private JPanel jPanelSearchArea = new JPanel();
   private JTextArea txtResultList = new JTextArea();
   private JPanel jPanelbottomButton = new JPanel();
   private JButton btnSearch = new JButton("Search");
   private JButton btnClear = new JButton("Clear");
   private JButton btnLogin = new JButton("Login");
   private final JSeparator separator = new JSeparator();

	

	// Create the frame.
	public PublicSearch() {
		
		setTitle("Public Search Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 355);
		setSize(900,500);
		String name = p.getId();
		System.out.println("name: "+ name);
	//}
	
	//public void initialize(){

		/**
		 *  Menu bar content
		 */
		setJMenuBar(menuBar);		
		menuBar.add(mnFile);		
		mnFile.add(mntmLogin);
		mntmLogin.addActionListener(this);	
		mnFile.add(mntmReset);	
		mntmReset.addActionListener(this);	
		
		mnFile.add(separator);
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
		paperDb.connect();
		lblHello = new JLabel("Hello! "+ "user");
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
		txtKeyword.setPreferredSize(new Dimension(150,20));
		txtKeyword.setText("");
		jPanelSearchArea.add(txtKeyword);
        txtKeyword.setColumns(10);	
		
		jPanelSearchArea.add(lblTitle);
		txtTitle = new JTextField();
		txtTitle.setText("");
		jPanelSearchArea.add(txtTitle);
		
		txtTitle.setColumns(10);
		
		jPanelSearchArea.add(lblAuthor);
		txtAuthor = new JTextField();
		txtAuthor.setText("");
		jPanelSearchArea.add(txtAuthor);
		txtAuthor.setColumns(10);
		

		/**
        * Bottom button panel
        */      
		contentPane.add(jPanelbottomButton, BorderLayout.SOUTH);
		
		btnSearch.addActionListener(this);    	
    	jPanelbottomButton.add(btnSearch);
		btnClear.addActionListener(this);		
		jPanelbottomButton.add(btnClear);
		btnLogin.addActionListener(this);		
		jPanelbottomButton.add(btnLogin);
		//txtTitle.addMouseListener(this);

		  model = new DefaultTableModel(null, new String [] {"Select", "Title", "Abstract", "Citation" }) {
              /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class getColumnClass(int c) {
                switch (c) {
                  case 0: return Boolean.class;
                  case 1: return String.class;
                  case 2: return String.class;
                  case 3: return String.class;
                  default: return String.class;
                }   
              }};
              		
					tableResult = new JTable(model);
					TableColumnModel colMdl = tableResult.getColumnModel();
					// put data into the String
					colMdl.getColumn(0).setPreferredWidth(5);
					//colMdl.getColumn(1).setPreferredWidth(60);
					tableResult.setRowHeight(40);
					//tableResult.setAutoResizeMode(ABORT);
					JScrollPane scrollPane = new JScrollPane(tableResult);
					tableResult.setFillsViewportHeight(true);
					tableResult.getTableHeader().setReorderingAllowed(false);
					contentPane.add(scrollPane, BorderLayout.CENTER);
					
					
		//  contentPane.add(listResultBox, BorderLayout.CENTER);


		
		
//		txtResultList.setWrapStyleWord(true);
//		txtResultList.setLineWrap(true);
////	txtResultList.add(new JScrollBar());
//		txtResultList.setText("");
		//JScrollPane = new JScrollPane(listResultBox, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    //	contentPane.add(JScrollPane, BorderLayout.CENTER);
		//txtKeyword.setColumns(10);
	
	}
	//perform action when clear, search or login button is clicked
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == ("Reset") || ae.getActionCommand() == "Clear") {
			txtKeyword.setText("");
			txtTitle.setText("");
			txtAuthor.setText("");
			listResultBox.removeAll();
			clearTable();
		}
		
		else if(ae.getActionCommand() =="Search") {
			clearTable();
			String authorName = txtAuthor.getText().trim();
			String title = txtTitle.getText().trim();
			String keyWords = txtKeyword.getText().trim();
			
			paperDb.connect();
			ArrayList<String> searchPapersAll = null;
			if (authorName.equals("") && keyWords.equals("") && title.equals("")){
				 searchPapersAll.removeAll(searchPapersAll);
				 searchPapersAll.add("Please Narrow your search by some fields");
				 searchPapersAll.add("");
				 searchPapersAll.add("");
			}
			
			if (title.equals("") && authorName.equals("")){
				searchPapersAll = null;
				searchPapersAll = paperDb.searchPapersbyKeyWord(keyWords);
			}
			else if (title.equals("") && keyWords.equals("")){
				searchPapersAll = null;
				searchPapersAll = paperDb.searchPapersbyAuthor(authorName);
			}
			else if (authorName.equals("") && keyWords.equals("")){
				searchPapersAll = null;
				searchPapersAll = paperDb.searchPapersbyTitle(title);
			}
			else {
			searchPapersAll = null;
			searchPapersAll = paperDb.searchPapersAll(authorName, title, keyWords);
			}
//			txtResultList.setText("");
//			for(int i = 0; i < paperBytitle.size(); i++) {
//				  //System.out.println(paperBytitle.get(i)); 
//				 // txtResultList.append(paperBytitle.get(i)  + "\n");
//				}
//			for(int i = 0; i < paperByAuthor.size(); i++) {
//				  //System.out.println(paperByAuthor.get(i)); 
//				  //txtResultList.append(paperByAuthor.get(i)  + "\n");
//				}
//			for(int i = 0; i < paperByKeyword.size(); i++) {
//				  //System.out.println(paperByKeyword.get(i)); 
//				  //txtResultList.append(paperByKeyword.get(i)  + "\n");
//				}
			txtResultList.setText("");
			
			for(int i = 0; i < searchPapersAll.size(); i++) {
				 // System.out.println(searchPapersAll.get(i)); 
				  Object[] paperObj= new Object[]{false, searchPapersAll.get(0), searchPapersAll.get(1), searchPapersAll.get(2)};
				  System.out.println(paperObj); 
				  model.addRow(paperObj);
				}	
		}
		else if(ae.getActionCommand() == "Login") {
			 Login login = new Login();	
			 this.setVisible(true);
			 login.setVisible(true);	 
		}
		else if(ae.getActionCommand().equalsIgnoreCase("Exit")) {
			 System.exit(0); 
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
	public void mouseClicked(MouseEvent e) {
		txtTitle.setText("");
	}
	private void clearTable(){
		int rows = model.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
		   model.removeRow(i); 
		}
		}

}
