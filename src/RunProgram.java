/**
 * <h1>Paper Database</h1>
 * The Paper Database program implements an application that
 * provides a secure, simple and functional user interface to
 * browse, update, delete, insert new article instances to the
 * back end database. 
 * <p>
 * <b>Run Program </b> is a class that implements the
 * the JDBC connection using the paper database class and 
 * invokes the main application search window screen
 * 
 * @author  Chanvi Kotak, Tek Nepal, Qiaoran Li
 * @version 1.0
 * @since   2015-12-16 
 */

import java.awt.EventQueue;

/*
 * main program which creates an instance paperDatabase class 
 * runs the event queue to invoke main application search window
 */
public class RunProgram {

	private static PaperDatabase paperDb = new PaperDatabase();
	
	/**
	 * Main
	 * @param args
	 * @throws exception if connection fails or the event is not invoked.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					paperDb.connect();
					SearchWindow frame = new SearchWindow();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
