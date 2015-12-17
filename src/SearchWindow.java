
/**
 * ISTE 330 Part 3
 * @author  Tek, Qiaoran, Chanvi
 * Professor: Micheal Floeser
 * A class for Admin Search Window
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class SearchWindow extends JFrame implements MenuListener, ActionListener, TableModelListener {

	public int selectedPaperID;
	public ArrayList<String> selectedList = new ArrayList<String>();

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PaperDatabase paperDb = new PaperDatabase();
	// Menu bar attributes
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnFile = new JMenu("File");
	private JMenuItem mntmLogout = new JMenuItem("Logout");
	private JMenuItem mntmReset = new JMenuItem("Reset");
	private JMenuItem mntmExit = new JMenuItem("Exit");
	private JMenu mnHelp = new JMenu("Help");
	private JMenuItem mntmAbout = new JMenuItem("About");
	private JMenuItem mntmHowToUse = new JMenuItem("How to use -->");
	private JMenuItem mntmEmail = new JMenuItem("Email us!");
	Login login;

	// search control attributes
	private JTextField txtKeyword;
	private JTextField txtTitle;
	private JTextField txtAuthor;
	private JLabel lblKeyword = new JLabel("Keyword:");
	private JLabel lblAuthor = new JLabel("Author:");
	private JLabel lblTitle = new JLabel("Title:");
	private JLabel lblMessage = new JLabel();

	// title panel attributes
	private JPanel jPanelTitle = new JPanel();
	private JLabel lblWelcome = new JLabel("Welcome to Paper Database!");
	private JLabel lblHello = new JLabel("Hello! User");

	// Search result area attributes
	private JPanel jPanelSearchArea = new JPanel();
	private JPanel jPanelbottomButton = new JPanel();
	private JTable tableResult = new JTable();
	private DefaultTableModel model = new DefaultTableModel();
	private JButton btnSearch = new JButton("Search");
	private JButton btnClear = new JButton("Clear");
	private JButton btnLogout = new JButton("Logout");
	private JButton btnAdd = new JButton("Add Paper");
	private JButton btnUpdate = new JButton("Update");
	private JButton btnDelete = new JButton("Delete");
	private JButton btnuser = new JButton("Add User");
	private JButton btnlogin = new JButton("Login");

	// Create the frame.
	public SearchWindow() {

		setTitle("Paper Search Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 355);
		setSize(900, 500);
		// }

		// public void initialize(){

		/**
		 * Menu bar content
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
		mntmAbout.addActionListener(this);
		mnHelp.add(mntmHowToUse);
		mntmHowToUse.addActionListener(this);
		mnHelp.add(mntmEmail);
		mntmEmail.addActionListener(this);

		/**
		 * Main panel with in the frame
		 */
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		/**
		 * North panel called jPanelTitle contains title and greeting message
		 * for Paper users
		 */
		contentPane.add(jPanelTitle, BorderLayout.NORTH);
		jPanelTitle.setLayout(new GridLayout(2, 1));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWelcome.setForeground(Color.red);
		jPanelTitle.add(lblWelcome);
		lblHello.setHorizontalAlignment(SwingConstants.TRAILING);
		jPanelTitle.add(lblHello);

		/**
		 * West panel: search area
		 */

		contentPane.add(jPanelSearchArea, BorderLayout.WEST);
		jPanelSearchArea.setLayout(new GridLayout(4, 2));

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
		lblMessage.setForeground(Color.RED);
		jPanelSearchArea.add(lblMessage);

		/**
		 * Bottom button panel
		 */
		contentPane.add(jPanelbottomButton, BorderLayout.SOUTH);

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
		btnuser.addActionListener(this);
		jPanelbottomButton.add(btnuser);
		jPanelbottomButton.add(btnlogin);
		btnlogin.addActionListener(this);
		jPanelbottomButton.add(btnlogin);
		isPublic();

		model = new DefaultTableModel(null, new String[] { "Select", "ID", "Title", "Abstract", "Citation" }) {
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			public Class getColumnClass(int c) {
				switch (c) {
				case 0:
					return Boolean.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				default:
					return String.class;
				}
			}
		};

		tableResult = new JTable(model);
		tableResult.getColumn("ID").setPreferredWidth(0);
		tableResult.getColumn("ID").setMinWidth(0);
		tableResult.getColumn("ID").setWidth(0);
		tableResult.getColumn("ID").setMaxWidth(0);
		TableColumnModel colMdl = tableResult.getColumnModel();
		// put data into the String
		colMdl.getColumn(0).setPreferredWidth(5);
		// colMdl.getColumn(1).setPreferredWidth(60);
		tableResult.setRowHeight(40);
		// tableResult.setAutoResizeMode(ABORT);
		JScrollPane scrollPane = new JScrollPane(tableResult);
		tableResult.setFillsViewportHeight(true);
		tableResult.getTableHeader().setReorderingAllowed(false);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		tableResult.getModel().addTableModelListener(this);

	}

	// perform action when clear, search or login button is clicked
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == ("Reset") || ae.getActionCommand() == "Clear") {
			txtKeyword.setText("");
			txtTitle.setText("");
			txtAuthor.setText("");
			clearTable();
		}

		else if (ae.getActionCommand() == "Search") {
			clearTable();
			String authorName = txtAuthor.getText().trim();
			String title = txtTitle.getText().trim();
			String keyWords = txtKeyword.getText().trim();

			paperDb.connect();
			ArrayList<String> column = new ArrayList<String>();
			ArrayList<String> values = new ArrayList<String>();

			if (!authorName.isEmpty()) {
				column.add("person.fname");
				values.add(txtAuthor.getText().trim());
				//System.out.println(txtAuthor.getText().trim());
			}
			if (!title.isEmpty()) {
				column.add("title");
				values.add(txtTitle.getText().trim());
				//System.out.println(txtTitle.getText().trim());
			}
			if (!keyWords.isEmpty()) {
				column.add("keyword");
				values.add(txtKeyword.getText().trim());
				//System.out.println(txtKeyword.getText().trim());
			}
			if (authorName.isEmpty() && keyWords.isEmpty() && title.isEmpty()) {
				column.add("person.fname");
				values.add(txtAuthor.getText().trim());
			}
			
			paperDb.bigList.clear();
			paperDb.bigList.clear();

			paperDb.fetch(column, values);
			if (paperDb.bigList.size() > 0) {
				for (int i = 0; i < paperDb.bigList.size(); i++) {
					ArrayList<String> small = paperDb.bigList.get(i);
					model.addRow(new Object[] { false, small.get(0), small.get(1), small.get(2), small.get(3) });
				}
			} else {
				lblMessage.setText("No record found.");
			}

		} else if (ae.getActionCommand() == "Login") {
			login = new Login(this);
			this.setVisible(true);
			login.setVisible(true);
		} else if (ae.getActionCommand().equalsIgnoreCase("Exit")) {
			System.exit(0);
		} else if (ae.getActionCommand().equalsIgnoreCase("How to use -->")) {
			openPage();
		}
		 else if (ae.getActionCommand().equalsIgnoreCase("Email us!")) {
				Sendmail();
				
//			try {
//				Runtime.getRuntime().exec("hh.exe ..\\help\\pd.chm");
//			} catch (IOException ioe) {
//				ioe.printStackTrace();
//				System.out.println(
//						"CHM file only works on Windows Machines!\n" 
//								+ "Please use the CHM viewer Mac to see the help file");
//			}
		} else if (ae.getActionCommand().equalsIgnoreCase("About")) {

			JOptionPane.showMessageDialog(null, "Paper Search Database " + "By Group 11\n " + "\u00a9 2015 \n"
					+ "Chanvi Kotak\n" + "Qiaoran Li\n" + "Tek Nepal\n");
		} else if (ae.getActionCommand().equals("Delete")) {
			if(JOptionPane.showConfirmDialog(null,"Are you sure you want to delete " + selectedList.get(1) ) == 0){
				if (paperDb.deletePapers(selectedPaperID)) {
						JOptionPane.showMessageDialog(null, "Deleted paper "+ selectedList.get(1));
						clearTable();
				}
			}
		} else if (ae.getActionCommand().equals("Add Paper")) {
			AddWindow ed = new AddWindow();
			setVisible(true);
			ed.setVisible(true);

		}
		else if (ae.getActionCommand().equals("Logout")) {
			isPublic();

		}
		else if (ae.getActionCommand().equalsIgnoreCase("Add user")) {
			AddUser aduser = new AddUser();
			this.setVisible(true);
			aduser.setVisible(true);
		} else if (ae.getActionCommand().equalsIgnoreCase("Update")) {
			paperDb.smallList.clear();
			paperDb.bigList.clear();
			EditWindow ed = new EditWindow();
			ed.txtpaperId.setText(selectedList.get(0));
			ed.txtTitle.setText(selectedList.get(1));
			ed.txtcitation.setText(selectedList.get(3));
			ed.txaAbstract.setText(selectedList.get(2));
			ed.txaAbstract.setWrapStyleWord(true);
			ed.txaAbstract.setLineWrap(true);
			setVisible(true);
			ed.setVisible(true);
		}
	}

	// event listener for table
	// active when table detects changes in cells
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		if (column > -1) {
			Object data = model.getValueAt(row, column);
			selectedList.clear();
			if ((boolean) data == true) {
				selectedPaperID = Integer.parseInt(model.getValueAt(row, column + 1).toString());
				selectedList.add(model.getValueAt(row, column + 1).toString());
				selectedList.add(model.getValueAt(row, column + 2).toString());
				selectedList.add(model.getValueAt(row, column + 3).toString());
				selectedList.add(model.getValueAt(row, column + 4).toString());
				for (int i = 0; i < selectedList.size(); i++) {
					//System.out.println(selectedList.get(i));

				}
			} else {
				selectedPaperID = -1;
			}
		}
	}

	private void clearTable() {
		int rows = model.getRowCount();
		lblMessage.setText("");
		for (int i = rows - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

	public void isAdmin() {
		btnUpdate.setEnabled(true);

		btnDelete.setEnabled(true);

		btnLogout.setEnabled(true);

		btnuser.setEnabled(true);

		btnAdd.setEnabled(true);
		
		btnlogin.setVisible(false);
		
		lblHello.setText("Hello " +login.userName());
		lblHello.setForeground(Color.red);
	}

	public void isFaculty() {
		
		btnUpdate.setEnabled(true);

		btnDelete.setEnabled(true);

		btnLogout.setEnabled(true);

		btnuser.setEnabled(false);

		btnAdd.setEnabled(true);
		
		lblHello.setText("Hello " +login.userName());
		lblHello.setForeground(Color.red);
	}

	private void isPublic() {
		btnUpdate.setEnabled(false);

		btnDelete.setEnabled(false);

		btnLogout.setEnabled(false);

		btnuser.setEnabled(false);

		btnAdd.setEnabled(false);
		
		btnlogin.setEnabled(true);
		
		btnlogin.setVisible(true);
		
		lblHello.setText("Hello User");
		
		lblHello.setForeground(Color.red);
	}
	
	public void isStudent() {
		btnUpdate.setEnabled(false);

		btnDelete.setEnabled(false);

		btnLogout.setEnabled(false);

		btnuser.setEnabled(false);

		btnAdd.setEnabled(false);
		
		btnlogin.setEnabled(true);
		
		btnlogin.setVisible(true);

		lblHello.setText("Hello " +login.userName());
		lblHello.setForeground(Color.red);
	}

	/**
	 * Opens the help website from help menu
	 */
	public void openPage(){
		String htmlFilePath = "help/index.html"; // path to help file
		File htmlFile = new File(htmlFilePath);
		        // open the default web browser for the HTML page
		try {
			Desktop.getDesktop().open(htmlFile);
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {	
			e.printStackTrace();
		}

	}
	
	public void Sendmail(){
		//String htmlFilePath = "help/index.html"; // path to help file
		//File htmlFile = new File(htmlFilePath);
		        // open the default web browser for the HTML page
		String message = "mailto:np.teku@gmail.com?subject=MessagefromPaperDB&body=Help!"; 
		URI uri = URI.create(message); 
		try {
			Desktop.getDesktop().mail(uri);
		} catch (IOException e) {	
			e.printStackTrace();
		}

	}
	/**
	 * Opens the help online website from help menu commented for now!
	 */
	
//	public void openPage(){
//	       try {
//	         String url = "https://teknepal.com/paperHelp/";
//	         java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
//	       }
//	       catch (java.io.IOException e) {
//	           System.out.println(e.getMessage());
//	       }
//	   
//	}


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
