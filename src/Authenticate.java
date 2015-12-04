/**
 * @author TekNepal,Tim O'Rourke, Qiaoran Li, Chanvi Kotak
 *
 */
public interface Authenticate {
	static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	static final String DATABASE_URL = "jdbc:mysql://teknepal.com:3306/ISTE330PAPER";
	static final String user = "teku";
	static final String pass = "Test123";

	public abstract boolean authenticateUser(String user, String password);

}