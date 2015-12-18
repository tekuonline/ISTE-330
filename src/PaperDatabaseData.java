/**
 * <h1>Paper Database Data</h1>
 * The Paper Database program implements an application that
 * provides a secure, simple and functional user interface to
 * browse, update, delete, insert new article instances to the
 * back end database. 
 * <p>
 * <b>Paper Database  Data Class </b> is a class that provides accessors and mutators 
 * for authentication and connection.
 * 
 * @author  Chanvi Kotak, Tek Nepal, Qiaoran Li
 * @version 1.0
 * @since 2015-12-16 
 */

import java.sql.Connection;

public class PaperDatabaseData {
	private boolean authenticated;
	private Connection connection;

/**
 * This method is an Accessor to retrieve the authenticated attribute
 * @return authenticated  object for authenticate.
 */

	public PaperDatabaseData(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public boolean isAuthenticated() {
		System.out.println("Authenticated");
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
/**
 * This method is an Accessor to retrieve the connection attribute
 * @return Connection is the object of JDBC connection
 */

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}