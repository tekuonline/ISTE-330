
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

//import Presentation.*;

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
					
	
	
	}
	//perform action when clear, search or login button is clicked
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == ("Reset") || ae.getActionCommand() == "Clear") {
			txtKeyword.setText("");
			txtTitle.setText("");
			txtAuthor.setText("");
			clearTable();
		}
		
		else if(ae.getActionCommand() =="Search") {
			clearTable();
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
				  model.addRow(new Object[]{false, small.get(1), small.get(2), small.get(3)});
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
