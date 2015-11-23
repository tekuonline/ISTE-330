
/**
 * ISTE 330 Part 3
 * Contributor: Tek, Qiaoran, Chanvi
 * Professor: Micheal Floeser
 * A class for Public Search Window
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import Presentation.*;

public class PublicSearch extends JFrame implements MenuListener, ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	PaperDatabase paperDb = new PaperDatabase();
	
	// Menu bar attributes
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnFile = new JMenu("File");
	private JMenuItem mntmLogin = new JMenuItem("Login");
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
	private JLabel lblHello;
	
	
	// Search result area attributes
	private JPanel jPanelSearchArea = new JPanel();
   private JTextArea txtResultList = new JTextArea();
   private JPanel jPanelbottomButton = new JPanel();
   private JButton btnSearch = new JButton("Search");
   private JButton btnClear = new JButton("Clear");
   private JButton btnLogin = new JButton("Login");
   private final JSeparator separator = new JSeparator();

   /**
    * Main Method
    */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PublicSearch frame = new PublicSearch();
					frame.setVisible(true);
					//frame.initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create the frame.
	public PublicSearch() {
		
		setTitle("Public Search Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 355);
		 setSize(900,500);
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
		paperDb.connect("teku", "Test123");
		lblHello = new JLabel("Hello! "+ paperDb.getRole("tn2089@rit.edu"));
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
		txtKeyword.setPreferredSize(new Dimension(150,20));
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
		
      btnSearch.addActionListener(this);    	
    	jPanelbottomButton.add(btnSearch);
		btnClear.addActionListener(this);		
		jPanelbottomButton.add(btnClear);
		btnLogin.addActionListener(this);		
		jPanelbottomButton.add(btnLogin);
		//txtTitle.addMouseListener(this);

	
		txtResultList.setWrapStyleWord(true);
		txtResultList.setLineWrap(true);
		txtResultList.add(new JScrollBar());
		txtResultList.setText("ResultShowsHere");
		contentPane.add(txtResultList, BorderLayout.CENTER);
		//txtKeyword.setColumns(10);
	
	}
	//perform action when clear, search or login button is clicked
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == ("Reset") || ae.getActionCommand() == "Clear") {
			txtKeyword.setText("");
			txtTitle.setText("");
			txtAuthor.setText("");
			txtResultList.setText("");		
			}
		else if(ae.getActionCommand() =="Search") {
			String authorName = txtAuthor.getText();
			paperDb.connect("teku", "Test123");
			System.out.println(authorName);
			String paper = paperDb.searchPapersbyAuthor(authorName);
			txtResultList.setText(paper);
			
		
		}
//		else if(ae.getActionCommand() == "Clear") {
//			Login lg = new Login();
//			lg.setVisible(true);			
//		}
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

}
