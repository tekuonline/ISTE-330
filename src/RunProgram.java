import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.event.MenuListener;

//import Presentation.*;

/**
 * @authors TekNepal, Qiaoran Li, Chanvi Kotak
 *
 */
public class RunProgram {

	private static PaperDatabase paperDb = new PaperDatabase();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					paperDb.connect();
					AdminSearch frame = new AdminSearch();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



}
