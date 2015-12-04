
/**
 * @authors TekNepal, Qiaoran Li, Chanvi Kotak
 *
 */

import java.sql.Connection;

public class PaperDatabaseData {
	private boolean authenticated;
	private Connection connection;


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

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}