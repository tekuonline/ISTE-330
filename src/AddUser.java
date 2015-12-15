import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * 
 * @author tek, Qioaran,Chanvi
 *
 */
public class AddUser extends JFrame implements ActionListener {
	private PaperDatabase paperDb = new PaperDatabase();

	/**
	 * constants
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtFname;
	private JTextField txtLname;
	private JTextField txtUsername;
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JRadioButton rdbtnStudent;
	private JRadioButton rdbtnFaculty;
	private JRadioButton rdbtnAdmin;
	private String role;

	/**
	 * Adds the user to the database
	 */
	public AddUser() {

		setBounds(100, 100, 450, 300);
		JPanel MainPanel = new JPanel();
		getContentPane().add(MainPanel, BorderLayout.CENTER);
		MainPanel.setLayout(null);

		JLabel lblName = new JLabel("First Name:");
		lblName.setBounds(20, 68, 81, 16);
		MainPanel.add(lblName);

		JLabel lblNewLabel = new JLabel("Last Name:");
		lblNewLabel.setBounds(20, 96, 81, 16);
		MainPanel.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 438, 48);
		MainPanel.add(panel);

		JLabel lblPaperDatabaseAdd = new JLabel("Paper Database add a user ");
		lblPaperDatabaseAdd.setForeground(Color.red);
		panel.add(lblPaperDatabaseAdd);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(20, 119, 81, 16);
		MainPanel.add(lblUsername);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(20, 147, 61, 16);
		MainPanel.add(lblEmail);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(20, 175, 81, 16);
		MainPanel.add(lblPassword);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(42, 243, 117, 29);
		MainPanel.add(btnSave);
		btnSave.addActionListener(this);

		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(this);
		buttonCancel.setBounds(192, 243, 117, 29);
		MainPanel.add(buttonCancel);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(321, 243, 117, 29);
		MainPanel.add(btnClear);
		btnClear.addActionListener(this);

		txtFname = new JTextField();
		txtFname.setBounds(159, 63, 130, 26);
		MainPanel.add(txtFname);
		txtFname.setColumns(10);

		txtLname = new JTextField();
		txtLname.setBounds(159, 91, 130, 26);
		MainPanel.add(txtLname);
		txtLname.setColumns(10);

		txtUsername = new JTextField();
		txtUsername.setBounds(159, 114, 130, 26);
		MainPanel.add(txtUsername);
		txtUsername.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(159, 142, 130, 26);
		MainPanel.add(txtEmail);
		txtEmail.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(159, 170, 130, 26);
		MainPanel.add(txtPassword);

		ButtonGroup buttonGroup = new ButtonGroup();
		rdbtnStudent = new JRadioButton("Student");
		rdbtnStudent.setBounds(20, 203, 141, 23);
		buttonGroup.add(rdbtnStudent);
		MainPanel.add(rdbtnStudent);

		rdbtnFaculty = new JRadioButton("Faculty");
		rdbtnFaculty.setBounds(148, 203, 141, 23);
		buttonGroup.add(rdbtnFaculty);
		MainPanel.add(rdbtnFaculty);

		rdbtnAdmin = new JRadioButton("Admin");
		rdbtnAdmin.setBounds(297, 203, 141, 23);
		buttonGroup.add(rdbtnAdmin);
		MainPanel.add(rdbtnAdmin);
	}

	@Override
	/*
	 * Actions performed when clear and reset, save, cancel buttons
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("Reset") || e.getActionCommand().equalsIgnoreCase("Clear")) {
			txtFname.setText("");
			txtLname.setText("");
			txtUsername.setText("");
			txtEmail.setText("");
			txtPassword.setText("");
			rdbtnStudent.setSelected(false);
			rdbtnFaculty.setSelected(false);
			rdbtnAdmin.setSelected(false);

		} else if (e.getActionCommand().equalsIgnoreCase("Save")) {
			if (rdbtnAdmin.isSelected()) {
				role = "admin";
			} else if (rdbtnFaculty.isSelected()) {
				role = "faculty";
			} else if (rdbtnStudent.isSelected()) {
				role = "student";
			}
			paperDb.connect();
			if (txtFname.getText().trim().isEmpty() || txtLname.getText().trim().isEmpty()
					|| txtUsername.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()
					|| txtPassword.getText().trim().isEmpty() || role.equals(null)) {
				JOptionPane.showMessageDialog(null, "All Fields are required to create a user!");
			} else {
				if (paperDb.createUser(txtFname.getText().trim(), txtLname.getText().trim(),
						txtUsername.getText().trim(), txtEmail.getText().trim(), role, txtPassword.getText().trim())
						.equals("Created")) {
					JOptionPane.showMessageDialog(null, "user " + txtUsername.getText().trim() + " Created");
					this.dispose();
				} else if (paperDb.createUser(txtFname.getText().trim(), txtLname.getText().trim(),
						txtUsername.getText().trim(), txtEmail.getText().trim(), role, txtPassword.getText().trim())
						.equals("Duplicate")) {
					JOptionPane.showMessageDialog(null, "User " + txtUsername.getText().trim() + " Already Exists");
				}

				else {
					JOptionPane.showMessageDialog(null, "Could Not Create User" + txtUsername.getText().trim());
				}
			}
		}

		else if (e.getActionCommand() == "Cancel") {
			this.dispose();
		}

	}

}
