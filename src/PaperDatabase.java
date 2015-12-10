
/**
 * @authors TekNepal, Qiaoran Li, Chanvi Kotak
 *
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
import java.sql.SQLException;
import java.util.ArrayList;

public class PaperDatabase implements Authenticate {
	private static String role = "public";
	Connection connection;
	// database constants

	protected PaperDatabaseData paperdata = new PaperDatabaseData(false);

	/**
	 * Connect to the database
	 * 
	 * @param user
	 * @param pass
	 * @return
	 */
	public Connection connect() {
		if (paperdata.getConnection() == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				paperdata.setConnection(DriverManager.getConnection(DATABASE_URL, user, pass));
			} catch (SQLException e) {
				System.out.println("Error  " + e.getMessage()); // delete this
																// after all
																// done
				e.printStackTrace(); // delete this after all done
			} catch (ClassNotFoundException ex) {
				System.out.println("Error  " + ex.getMessage()); // delete this
																	// after all
																	// done
			}
		}
		return paperdata.getConnection();
	}

	/**
	 * Close connection to the database
	 */
	public void close() {
		if (paperdata.getConnection() != null) {
			try {
				paperdata.getConnection().close();
				paperdata.setConnection(null);
				System.out.println("Closed the connection to the server"); // delete
																			// this
																			// after
																			// all
																			// done
			} catch (SQLException e) {
				e.printStackTrace(); // delete this after all done
			}
		}
	}

	/**
	 * Encodes the String using MD5 for password.
	 * 
	 * @param aString
	 * @return
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
	 * Creates a new user.
	 * 
	 * @param fname
	 * @param lname
	 * @param username
	 * @param email
	 * @param role
	 * @param password
	 * @return
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
			//System.out.println(ex.getMessage());
			if (ex.getMessage().contains("Duplicate entry")){
				return "Duplicate";
			}
			//ex.printStackTrace();
			return "Could Not Create";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Authe#authenticateUser(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean authenticateUser(String user, String password) {
		String pass_md5;
		String pass_salt;
		String select;
		ResultSet res;

		select = "SELECT pass_salt, pass_md5 FROM person WHERE username = ?";
		res = null;

		try (PreparedStatement pstmt = prepare(select)) {
			pstmt.setString(1, user);
			res = pstmt.executeQuery();
			res.next(); // username is unique
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
	 * Gets the user role based on username.
	 * 
	 * @param username
	 * @return
	 */

	public String getRole(String username) {
		String Getrole = "SELECT role FROM person WHERE username = ?";
		try (PreparedStatement pstmt = prepare(Getrole)) {
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
	 * prepares the statement
	 * 
	 * @param sql
	 * @return
	 */
	private PreparedStatement prepare(String sql) {
		try {
			PreparedStatement statement = paperdata.getConnection().prepareStatement(sql);
			return statement;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}// end prepare statement

	/**
	 * gets the paper using paper id.
	 * 
	 * @param paperId
	 * @return
	 */
	public boolean getPaper(int paperId) {
		String select = "SELECT * FROM papers WHERE id = ?";
		try {
			PreparedStatement pstmt = prepare(select);
			// PreparedStatement pstmt = connection.prepareStatement(select)) {
			pstmt.setInt(1, paperId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String title = rs.getString("title");
				String ab = rs.getString("abstract");
				String citation = rs.getString("citation");
				System.out.println("Paper ID: " + id);
				System.out.println("Title: " + title);
				System.out.println("Abstract: " + ab);
				System.out.println("Citation: " + citation);

			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Delete the paper using paper id
	 * 
	 * @param paperId
	 * @return
	 */
	public boolean deletePapers(int paperId) {
		String delete = "DELETE FROM papers WHERE id = ?";
		try {
			PreparedStatement pstmt = prepare(delete);
			pstmt.setInt(1, 7);

			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Finds the paper using the paperTitle
	 * 
	 * @param paperTitle
	 * @return
	 */
	public ArrayList<String> searchPapersbyTitle(String paperTitle) {
		ArrayList<String> paperByTitle = new ArrayList<String>();
		String search = "SELECT * FROM papers WHERE Title LIKE ?";
		try (PreparedStatement pstmt = prepare(search)) {
			pstmt.setString(1, '%' + paperTitle + '%');

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String title = rs.getString("title");
				String ab = rs.getString("abstract");
				String citation = rs.getString("citation");

				// System.out.println("Paper ID: " + id);
				// System.out.println("Title: " + title);
				// System.out.println("Abstract: " + ab);
				// System.out.println("Citation: " + citation);
				//paperByTitle.add(id);
				paperByTitle.add(title);
				paperByTitle.add(ab);
				paperByTitle.add(citation);
			}
			return paperByTitle;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Search paper using auther name.
	 * 
	 * @param authorName
	 * @return
	 */
	public ArrayList<String> searchPapersbyAuthor(String authorName) {
		ArrayList<String> paperByAuthor = new ArrayList<String>();
		String authorSearch = "SELECT papers.`id`, papers.`title`, papers.`abstract`, papers.`citation` From `papers`JOIN `authorship` ON authorship.personId=papers.id JOIN person ON person.`id`= authorship.personId WHERE person.fname = ?;";
		try (PreparedStatement pstmt = prepare(authorSearch)) {
			pstmt.setString(1, authorName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String title = rs.getString("title");
				String ab = rs.getString("abstract");
				String citation = rs.getString("citation");

//				System.out.println("Paper ID: " + id);
//				System.out.println("Title: " + title);
//				System.out.println("Abstract: " + ab);
//				System.out.println("Citation: " + citation);

				//paperByAuthor.add(id);
				paperByAuthor.add(title);
				paperByAuthor.add(ab);
				paperByAuthor.add(citation);
			}
			return paperByAuthor;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Search paper using keyword.
	 * 
	 * @param paperKeyword
	 * @return
	 */
	public ArrayList<String> searchPapersbyKeyWord(String paperKeyword) {
		ArrayList<String> paperByKeyWords = new ArrayList<String>();
		String search = "SELECT papers.`id`, papers.`title`, papers.`abstract`, papers.`citation` From `papers` INNER JOIN paper_keywords ON papers.id=paper_keywords.id WHERE paper_keywords.keyword LIKE ?;";

		try (PreparedStatement pstmt = prepare(search)) {
			pstmt.setString(1, '%' + paperKeyword + '%');

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String title = rs.getString("title");
				String ab = rs.getString("abstract");
				String citation = rs.getString("citation");

				System.out.println("Paper ID: " + id);
				System.out.println("Title: " + title);
				System.out.println("Abstract: " + ab);
				System.out.println("Citation: " + citation);
				//paperByKeyWords.add(id);
				paperByKeyWords.add(title);
				paperByKeyWords.add(ab);
				paperByKeyWords.add(citation);
			}
			return paperByKeyWords;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param paperKeyword
	 * @return
	 */
	public ArrayList<String> searchPapersAll(String paperAuthor, String paperTitle, String paperKeyword) {
		ArrayList<String> searchPapersAll = new ArrayList<String>();
		String search = "SELECT distinct paperId, title, `abstract`,`citation` "
				+ "from papers left join authorship on papers.id = authorship.paperId "
				+ "left join person on authorship.personId = person.id left join paper_keywords "
				+ "on papers.id = paper_keywords.id where fName = ? and title = ? and keyword like ?";

		try (PreparedStatement pstmt = prepare(search)) {
			pstmt.setString(1, paperAuthor);
			pstmt.setString(2, paperTitle);
			pstmt.setString(3, '%' + paperKeyword + '%');
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String paperId = rs.getString("paperId");
				String title = rs.getString("title");
				String ab = rs.getString("abstract");
				String citation = rs.getString("citation");

				System.out.println("Paper ID: " + paperId);
				System.out.println("Title: " + title);
				System.out.println("Abstract: " + ab);
				System.out.println("Citation: " + citation);
				//searchPapersAll.add(paperId);
				searchPapersAll.add(title);
				searchPapersAll.add(ab);
				searchPapersAll.add(citation);
			}
			return searchPapersAll;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * update paper using paperID
	 * 
	 * @param updatePaperid
	 * @return
	 */
	public String updatePaper(int updatePaperid, String title, String abst, String citation) {
		if (role.equalsIgnoreCase("faculty") && (paperdata.isAuthenticated() == true)) {
			String update = "UPDATE `papers` SET `title`=?,`abstract`=?,`citation`=? WHERE id = ?;";
			try (PreparedStatement pstmt = prepare(update)) {
				pstmt.setInt(4, updatePaperid);
				pstmt.setString(1, title);
				pstmt.setString(2, abst);
				pstmt.setString(3, citation);
				int rs = pstmt.executeUpdate();
				return "Paper " + updatePaperid + " Updated";
			} catch (SQLException e) {
				e.printStackTrace();
				return "Error Updating paper" + updatePaperid + "";
			}
		}
		return "You are not authorized to update the paper!";
	}

	/**
	 * 
	 * @param insertPaperid
	 * @param title
	 * @param abst
	 * @param citation
	 * @return
	 */
	public String insertPaper(int insertPaperid, String title, String abst, String citation) {
		//if (r.equalsIgnoreCase("faculty") && (paperdata.isAuthenticated() == true)) {
			String insertPaper = "INSERT INTO papers VALUES (?,?,?,?)";
			System.out.println("Paperid" + insertPaperid + "title "+title +"Abstract" + abst+ " Citation "+ citation);
			try (PreparedStatement pstmt = paperdata.getConnection().prepareStatement(insertPaper)) {
				pstmt.setInt(1, insertPaperid);
				pstmt.setString(2, title);
				pstmt.setString(3, abst);
				pstmt.setString(4, citation);
				pstmt.executeUpdate();
				return "Paper Inserted";
			} catch (SQLException e) {
				return "Cannot add Duplicate Paper!";
			}
		//return "you are not authorized to add papers";
	}

	/**
	 * insert keywords
	 * 
	 * @param insertPaperid
	 * @param Keyword
	 * @return
	 */
	public String insertKeywords(int insertPaperid, String Keyword) {
		if (role.equalsIgnoreCase("faculty") && (paperdata.isAuthenticated() == true)) {
			String insertPaper = "INSERT INTO paper_keywords VALUES (?,?)";

			try (PreparedStatement pstmt = prepare(insertPaper)) {
				pstmt.setInt(1, insertPaperid);
				pstmt.setString(2, Keyword);
				int rs = pstmt.executeUpdate();
				return "Keyword " + Keyword + " for paper id = " + insertPaperid + " Inserted";
			} catch (SQLException e) {
				// System.out.println(e.getMessage());
				return "Duplicate Keywords cant be added";
			}
		}
		return "you are not authorized to add Keywords";
	}

	/**
	 * get all the papers for the logged in user.
	 * 
	 * @param username
	 * @return
	 */
	public String getMyPapers(String username) {
		String search = "SELECT papers.`id`, papers.`title`, papers.`abstract`, papers.`citation` From "
				+ "`papers` JOIN `authorship` ON authorship.personId=papers.id  JOIN person ON person.`id`= authorship.personId  WHERE person.username = ?;";

		try (PreparedStatement pstmt = prepare(search)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String title = rs.getString("title");
				String ab = rs.getString("abstract");
				String citation = rs.getString("citation");

				System.out.println("Paper ID: " + id);
				System.out.println("Title: " + title);
				System.out.println("Abstract: " + ab);
				System.out.println("Citation: " + citation);
			}
			return "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Could not find your papers";
	}

	public String getRole() {
		return role;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

} // end class

/*********
 * CREATE TABLE `person` ( `id` int(11) NOT NULL AUTO_INCREMENT, `fName`
 * varchar(45) NOT NULL DEFAULT '', `lName` varchar(45) NOT NULL DEFAULT '',
 * `username` varchar(45) NOT NULL DEFAULT '', `email` varchar(45) NOT NULL
 * DEFAULT '', `role` varchar(15) DEFAULT '', `pass_salt` tinyblob NOT NULL,
 * `pass_md5` tinyblob NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY
 * `uc_usernameemail` (`username`,`email`) ) ENGINE=InnoDB AUTO_INCREMENT=95
 * DEFAULT CHARSET=latin1;
 */
