

/**
 * ISTE 330 Part 3
 * Contributor: Tek, Qiaoran, Chanvi
 * Professor: Micheal Floeser
 * A class for Login Window
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Login extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtUsername;
	private JTextField txtPassword;
	private PaperDatabase paperDb = new PaperDatabase();
	private AdminSearch as = null;
	private String role1 = "";


	/**
	 * Create the dialog.
	 */
	public Login(AdminSearch asIn) {
		as = asIn;
		setTitle("Login Window");
		setLocationRelativeTo(null);
		setBounds(100, 100, 398, 146);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2,2));
		{
			JLabel lblUsername = new JLabel("Username:");
			contentPanel.add(lblUsername);
		}
		{
			txtUsername = new JTextField();
			contentPanel.add(txtUsername);
			txtUsername.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password:");
			contentPanel.add(lblPassword);
		}
		{
			txtPassword = new JPasswordField(10);
			contentPanel.add(txtPassword);
			txtPassword.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton loginButton = new JButton("Login");
				loginButton.setActionCommand("OK");
				buttonPane.add(loginButton);
				getRootPane().setDefaultButton(loginButton);
				loginButton.addActionListener(this);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
		
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == ("OK")){
			paperDb.connect();
			boolean connected = paperDb.authenticateUser(txtUsername.getText().trim(), txtPassword.getText().trim());
			if (!connected){
				JOptionPane.showMessageDialog(this,
					    "Invalid username or password",
					    "Login Failure",
					    JOptionPane.ERROR_MESSAGE);
			}
			else {
				String role = paperDb.getRole(txtUsername.getText());
				if(role.equalsIgnoreCase("faculty")){
					role1 = "Faculty";
					this.dispose();
					as.isFaculty();
					
					}
				else if (role.equalsIgnoreCase("admin")){
					role1 = "Admin";
					this.dispose();
					as.isAdmin();
					}
				else if (role.equalsIgnoreCase("Student")){
					role1 = "Student";
					this.dispose();
					as.isStudent();
					}
				else{
					this.dispose();
					}
					}
			txtUsername.setText("");
			txtPassword.setText("");
		}
		else if(ae.getActionCommand() =="Cancel") {
			dispose();		
		}
	}
	public String userName(){
		return txtUsername.getText().trim() + ", you are a " + role1;	
	}

}
