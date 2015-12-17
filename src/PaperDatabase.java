/**
 * <h1>Paper Database</h1>
 * The Paper Database program implements an application that
 * provides a secure, simple and functional user interface to
 * browse, update, delete, insert new article instances to the
 * back end database. 
 * <p>
 * <b>Paper Database Class </b> is a class that implements the
 * Authenticate Class which provides methods to connect and 
 * close JDBC connection, as well as fetching and setting date
 * using prepared statements. Additionally it contains the method
 * to authenticate users and assign user roles based on the 
 * provided user credential. 
 * 
 * @author  Chanvi Kotak, Tek Nepal, Qiaoran Li
 * @version 1.0
 * @since   2015-12-16 
 */
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaperDatabase implements Authenticate {
	
/**
 * This method is used to connect to the back end database
 * using JDBC driver. 
 * @return Connection This returns connection to the database
 * @return nothing
 * @throws SQLException
 * @throws ClassNotFoundException
 */

	public Connection connect() {
		if (paperdata.getConnection() == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				paperdata.setConnection(DriverManager.getConnection(DATABASE_URL, user, pass));
			} catch (SQLException e) {
				System.out.println("Error  " + e.getMessage());
				e.printStackTrace();
			} catch (ClassNotFoundException ex) {
				System.out.println("Error  " + ex.getMessage());
			}
		}
		return paperdata.getConnection();
	}

	/**
	 * This method is used to close to the back end database
	 * using JDBC driver. 
	 * @return nothing
	 * @throws SQLException
	 */
	public void close() {
		if (paperdata.getConnection() != null) {
			try {
				paperdata.getConnection().close();
				paperdata.setConnection(null);
			} catch (SQLException e) {
				e.printStackTrace(); // delete this after all done
			}
		}
	}
	
/**
 * This method is used to retrieve data from the back end database
 * by calling and executing the prepared method and storying them 
 * in to a two dimensional ArrayList.
 * @param sql This is the prepared SQL statement used for data 
 * retrieval before binding with values
 * @param value This is the array of values that will be binded with
 * the prepared SQL statement.
 * @return ArrayList This returns ArrayList of data retrieved from 
 * the back end database.
 * @throws SQLException 
 */
	public ArrayList<ArrayList<String>> getData(String sql, ArrayList<String> list) {
		try {
			rs = prepare(sql, list).executeQuery(); // call prepare method
			rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					smallList.add(rs.getString(i));
				}
				ArrayList<String> tempSmallList = new ArrayList<String>(smallList);
				smallList.clear();
				bigList.add(tempSmallList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return bigList;
	}

/**
 * This method is used to modify or delete data from the back end
 * database by calling and executing the prepared method and return
 * a boolean value based on the status of execution.
 * @param sql This is the prepared SQL statement used for data 
 * retrieval before binding with values
 * @param value This is the array of values that will be binded with
 * the prepared SQL statement.
 * @return Boolean is a indicator that describes the success or failure
 * of the SQL execution. 
 * @throws SQLException 
 */

	public boolean setData(String sql, ArrayList<String> list) {
		try {
			prepare(sql, list).executeUpdate(); // call prepare method
			return true;
		} catch (SQLException s) {
			s.printStackTrace();
			return false;
		}
	}

/**
 * This method is used to generate a dynamic SQL statement that handles
 * different search searching parameters 
 * @param colum This is the Array of column names that was entered by
 * the user with the GUI controls (text boxes)
 * @param value This is the array of values that was entered by the user
 * and will be binded with the prepared SQL statement.
 * @return nothing
 */

	public void fetch(ArrayList<String> column, ArrayList<String> value) {
		switch (column.size()) {
		case 1:
			sql = " SELECT distinct papers.id,title,abstract,citation from papers   "
					+ " left join authorship on papers.id = authorship.paperId"
					+ " left join person on authorship.personId = person.id"
					+ " left join paper_keywords on papers.id = paper_keywords.id" + " WHERE " + column.get(0)
					+ " like ?;";
			break;
		case 2:
			sql = " SELECT distinct papers.id,title,abstract,citation from papers "
					+ " left join authorship on papers.id = authorship.paperId"
					+ " left join person on authorship.personId = person.id"
					+ " left join paper_keywords on papers.id = paper_keywords.id" + " WHERE " + column.get(0)
					+ " like ? AND " + column.get(1) + " like ?;";
			break;
		case 3:
			sql = " SELECT distinct papers.id,title,abstract,citation from papers  "
					+ " left join authorship on papers.id = authorship.paperId"
					+ " left join person on authorship.personId = person.id"
					+ " left join paper_keywords on papers.id = paper_keywords.id" + " WHERE " + column.get(0)
					+ " like ? AND " + column.get(1) + " like ?  AND " + column.get(2) + " like ? ;";
			break;
		}
		this.getData(sql, value);
	}

/**
 * This method is used to create and prepared statement and bind 
 * the SQL statement with the array of values provided by user
 * @param sql This is the prepared SQL statement used for data 
 * retrieval before binding with values
 * @param value This is the array of values that will be binded with
 * the prepared SQL statement.
 * @return PreparedStatement is a object of prepared statement
 * @throws SQLException 
 */

	private PreparedStatement prepare(String sql, ArrayList<String> value) {
		try {
			pstmt = paperdata.getConnection().prepareStatement(sql);
			for (int i = 0; i < value.size(); i++) {
				try {
					int d = Integer.parseInt(value.get(i).toString());
					pstmt.setInt(i + 1, d);
				} catch (Exception test) {
					pstmt.setString(i + 1, value.get(i).toString());
				}
			}
			return pstmt;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}// end prepare statement

/**
 * This method is used to encode the password using MD5 key
 * @param aString is the password entered by the user
 * @return String is a encrypted password
 * @throws NoSuchAlgorithmException
 * @throws UnsupportedEncodingException
 */

	private String md5(String aString) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md;
		String hex;
		StringBuffer hexString;
		byte[] bytesOfMessage;
		byte[] theDigest;

		hexString = new StringBuffer();
		bytesOfMessage = aString.getBytes("UTF-8");
		md = MessageDigest.getInstance("MD5");
		theDigest = md.digest(bytesOfMessage);

		for (int i = 0; i < theDigest.length; i++) {
			hex = Integer.toHexString(0xff & theDigest[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

/**
 * This method is used to generate a prepared statement to 
 * insert a new user instances into the back end database
 * @param fname is the first name of a user entered by an admin
 * @param lname is the last name of a user entered by an admin
 * @param username is the username of a user entered by an admin
 * @param email is the email of a user entered by an admin
 * @param role is the type of a user selected by an admin
 * @param password is password for a user generated by an admin
 * @return String a SQL statement for prepared statement
 * @throws NoSuchAlgorithmException
 * @throws UnsupportedEncodingException
 * @throws SQLException
 */
	public String createUser(String fname, String lname, String username, String email, String role, String password) {
		// paperdata.setConnection(connection);
		SecureRandom random;
		String insert;
		String salt;

		random = new SecureRandom();
		salt = new BigInteger(130, random).toString(16);

		insert = "INSERT INTO person " + "(fname, lname, username, email, role, pass_salt, pass_md5) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = paperdata.getConnection().prepareStatement(insert)) {
			pstmt.setString(1, fname);
			pstmt.setString(2, lname);
			pstmt.setString(3, username);
			pstmt.setString(4, email);
			pstmt.setString(5, role);
			pstmt.setString(6, salt);
			pstmt.setString(7, this.md5(salt + password));
			pstmt.executeUpdate();
			return "Created";
		} catch (NoSuchAlgorithmException | SQLException | UnsupportedEncodingException ex) {
			if (ex.getMessage().contains("Duplicate entry")) {
				return "Duplicate";
			}
			return "Could Not Create";
		}
	}

/**
 * This method is used to authenticate the user data in the 
 * back end database
 * @param user This user name that will be encrypted 
 * @param password  This is the password that will be encrypted 
 * @return Boolean is the indicator of whether the user is valid or not
 * @throws NoSuchAlgorithmException
 * @throws UnsupportedEncodingException
 * @throws SQLException
 */
	@Override
	public boolean authenticateUser(String user, String password) {
		String pass_md5;
		String pass_salt;
		String select;
		ResultSet res;

		select = "SELECT pass_salt, pass_md5 FROM person WHERE username = ?";
		res = null;

		ArrayList<String> pass = new ArrayList<String>();
		pass.add(password);
		try (PreparedStatement pstmt = prepare(select, pass)) {
			pstmt.setString(1, user);
			res = pstmt.executeQuery();
			res.next(); 					// username is unique
			pass_salt = res.getString(1);
			pass_md5 = res.getString(2);

			if (pass_md5.equals(this.md5(pass_salt + password))) {
				paperdata.setAuthenticated(true);
				return true;
			} else {
				paperdata.setAuthenticated(false);
				return false;
			}

		} catch (NoSuchAlgorithmException | SQLException | UnsupportedEncodingException ex) {
			return false;
		} finally {
			try {
				if (res instanceof ResultSet) {
					res.close();
				}
			} catch (SQLException ex) {
			}
		}
	}

/**
 *  This method gets the users role.
 *  @param username This is the username paramter to get the role
 *  @return String This returns role of user.
 */
	public String getRole(String username) {
		String getrole = "SELECT role FROM person WHERE username = ?";
		try (PreparedStatement pstmt = this.connect().prepareStatement(getrole)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				role = rs.getString("role");
			}
			return role;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
/**
 * This method is used to generate a prepared statement to delete
 * a paper instance within the back end database and calls the 
 * setData method
 * @param paperId is indicator for paper instances
 * @return Boolean is the indicator of whether the deletion was a 
 * success or failure
 */
	public boolean deletePapers(int paperId) {
		String delete = "DELETE FROM papers WHERE id = ?";
		ArrayList<String> value = new ArrayList<String>();
		value.add("" + paperId);
		boolean success = setData(delete, value);
		return success;
	}

/**
 * This method is an Accessor to retrieve the role attribute
 * @return String the textual description of the user type
 */
	public String getRole() {
		return role;
	}
	
	/**
	 * This method is an Accessor to retrieve the connection attribute
	 * @return Connection is the object of JDBC connection
	 */
	public Connection getConnection() {
		return getConnection();
	}
	
/**
 * instance variables
*/
	private static String role = "public";
	private static ResultSet rs;
	private static ResultSetMetaData rsmd;
	private static PreparedStatement pstmt;
	private static String sql;
	protected static ArrayList<String> smallList = new ArrayList<String>();
	protected static ArrayList<ArrayList<String>> bigList = new ArrayList<ArrayList<String>>();
	protected PaperDatabaseData paperdata = new PaperDatabaseData(false);

} // end class PaperDatabase
