/**
 * @author TekNepal,Tim O'Rourke, Qiaoran Li, Chanvi Kotak
 *
 */
public interface Authenticate {
	static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	static final String DATABASE_URL = "jdbc:mysql://teknepal.com:3306/ISTE330PAPER";

	public abstract boolean authenticateUser(String user, String password);
	
	
	
}