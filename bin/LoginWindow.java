import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;

public class LoginWindow {

	private JFrame frame;

	public LoginWindow(PrimeSearch ps) {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel LoginBanner = new JPanel();
		LoginBanner.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		JLabel LoginText = new JLabel("Faculty Login");
		LoginText.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 20));
		LoginBanner.add(LoginText);
		frame.getContentPane().add(LoginBanner, BorderLayout.NORTH);
		
		JPanel jpLoginButtons = new JPanel();
		frame.getContentPane().add(jpLoginButtons, BorderLayout.SOUTH);
		
		JButton btnLogin = new JButton("Login");
		jpLoginButtons.add(btnLogin);
		
		JButton btnCancel = new JButton("Cancel");
		jpLoginButtons.add(btnCancel);
		
		JPanel jpLoginPanel = new JPanel();
		frame.getContentPane().add(jpLoginPanel, BorderLayout.CENTER);
		jpLoginPanel.setLayout(new BorderLayout(0, 0));
		frame.setVisible(true);
	}

}
