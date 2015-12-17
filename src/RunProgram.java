import java.awt.EventQueue;


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
					SearchWindow frame = new SearchWindow();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
