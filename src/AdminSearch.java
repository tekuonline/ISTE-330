
/**
 * ISTE 330 Part 3
 * Contributor: Tek, Qiaoran, Chanvi
 * Professor: Micheal Floeser
 * A class for Admin Search Window
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
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class AdminSearch extends JFrame implements MenuListener, ActionListener {

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
	private JScrollPane JScrollPane;
	

	// search control attributes
	private JTextField txtKeyword;
	private JTextField txtTitle;
	private JTextField txtAuthor;
	private JLabel lblKeyword = new JLabel("Keyword:");
	private JLabel lblAuthor = new JLabel("Author:");
	private JLabel lblTitle = new JLabel("Title:");

	// title panel attributes
	private JPanel jPanelTitle = new JPanel();
	private JLabel lblWelcome = new JLabel("Welcome to Paper Database!");
	private JLabel lblHello = new JLabel("Hello! Admin");

	// Search result area attributes
	private JPanel jPanelSearchArea = new JPanel();
	private JTextArea txtResultList = new JTextArea();
	private JPanel jPanelbottomButton = new JPanel();

	// Create the frame.
	public AdminSearch() {

		setTitle("Administrator Search Window");
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
		mnHelp.add(mntmHowToUse);

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
		jPanelSearchArea.setLayout(new GridLayout(3, 2));

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

		txtResultList.setWrapStyleWord(true);
		txtResultList.setLineWrap(true);
		txtResultList.add(new JScrollBar());
		txtKeyword.setColumns(10);
		txtResultList.setText("");
		JScrollPane = new JScrollPane(txtResultList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(JScrollPane, BorderLayout.CENTER);

	}

	private JButton btnSearch = new JButton("Search");
	private JButton btnClear = new JButton("Clear");
	private JButton btnLogout = new JButton("Logout");
	private JButton btnAdd = new JButton("Add Paper");
	private JButton btnUpdate = new JButton("Update");
	private JButton btnDelete = new JButton("Delete");
	private JButton btnuser = new JButton("Add User");
	
	

	// perform action when clear, search or login button is clicked
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand() == ("Reset") || ae.getActionCommand() == "Clear") {
			txtTitle.setText(" ");
			txtAuthor.setText("");
			txtKeyword.setText("");
			txtResultList.setText("");
		}

		else if (ae.getActionCommand() == "Exit") {
			System.exit(0);
		} 
		else if (ae.getActionCommand() == "Logout") {
			this.dispose();

		} 
		else if (ae.getActionCommand() == "Search") {
			String authorName = txtAuthor.getText().trim();
			String title = txtTitle.getText().trim();
			String keyWords = txtKeyword.getText().trim();

			paperDb.connect();
			ArrayList<String> searchPapersAll = null;

			if (title.equals("") && authorName.equals("")) {
				searchPapersAll = paperDb.searchPapersbyKeyWord(keyWords);
			} else if (title.equals("") && keyWords.equals("")) {
				searchPapersAll = paperDb.searchPapersbyAuthor(authorName);
			} else if (authorName.equals("") && keyWords.equals("")) {
				searchPapersAll = paperDb.searchPapersbyTitle(title);
			} else if (authorName.equals("") && keyWords.equals("") && title.equals("")) {
				txtResultList.append("Please Narrow your search by some fields" + "\n");
			} else {
				searchPapersAll = paperDb.searchPapersAll(authorName, title, keyWords);
			}
			// txtResultList.setText("");
			// for(int i = 0; i < paperBytitle.size(); i++) {
			// //System.out.println(paperBytitle.get(i));
			// // txtResultList.append(paperBytitle.get(i) + "\n");
			// }
			// for(int i = 0; i < paperByAuthor.size(); i++) {
			// //System.out.println(paperByAuthor.get(i));
			// //txtResultList.append(paperByAuthor.get(i) + "\n");
			// }
			// for(int i = 0; i < paperByKeyword.size(); i++) {
			// //System.out.println(paperByKeyword.get(i));
			// //txtResultList.append(paperByKeyword.get(i) + "\n");
			// }
			txtResultList.setText("");
			for (int i = 0; i < searchPapersAll.size(); i++) {
				System.out.println(searchPapersAll.get(i));
				txtResultList.append(searchPapersAll.get(i) + "\n");
			}

		} else if (ae.getActionCommand() == "Delete") {
			
		}
		else if (ae.getActionCommand() == "Add Paper") {
			EditWindow ed = new EditWindow();
			setVisible(true);
			ed.setVisible(true);

		} 
		else if (ae.getActionCommand().equalsIgnoreCase("Add user")) {
			AddUser aduser = new AddUser();
			this.setVisible(true);
			aduser.setVisible(true);
		}
		else if (ae.getActionCommand().equalsIgnoreCase("Update")) {
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
