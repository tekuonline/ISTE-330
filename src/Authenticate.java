/**
 * <h1>Paper Database Data</h1>
 * The Paper Database program implements an application that
 * provides a secure, simple and functional user interface to
 * browse, update, delete, insert new article instances to the
 * back end database. 
 * <p>
 * <b>Authenticate  Class </b> is a class that provides database connection access properties
 * 
 * @author  Chanvi Kotak, Tek Nepal, Qiaoran Li
 * @version 1.0
 * @since 2015-12-16 
 */

public interface Authenticate {
	static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	static final String DATABASE_URL = "jdbc:mysql://teknepal.com:3306/ISTE330PAPER";
	static final String user = "";
	static final String pass = "Test123";

	public abstract boolean authenticateUser(String user, String password);

}
