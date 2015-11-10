import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;


public class PaperDatabase implements Authenticate{
	//database constants

	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://teknepal.com:3306/ISTE330PAPER";
	private static String role = "public";
	protected PaperDatabaseData paperdata = new PaperDatabaseData(false);

	public Connection connect(String user, String pass) {
		if (paperdata.getConnection() == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				paperdata.setConnection(DriverManager.getConnection(DATABASE_URL, user, pass));
			} catch (SQLException e) {
				System.out.println("Error  " + e.getMessage()); // delete this after all done 
				e.printStackTrace(); // delete this after all done 
			} catch (ClassNotFoundException ex) {
				System.out.println("Error  " + ex.getMessage()); // delete this after all done 
			}
		}
		return paperdata.getConnection();
	}

	// close  database connection
	public void close() {
		if (paperdata.getConnection() != null) {
			try {
				paperdata.getConnection().close();
				paperdata.setConnection(null);
				System.out.println("Closed the connection to the server"); //delete this after all done 
			} catch (SQLException e) {
				e.printStackTrace(); // delete this after all done 
			}
		}
	}

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


	public boolean createUser(String fname, String lname, String username, String email, String role, String password) {
		SecureRandom random;
		String insert;
		String salt;

		random = new SecureRandom();
		salt = new BigInteger(130, random).toString(16);

		insert = "INSERT INTO person " + "(fname, lname, username, email, role, pass_salt, pass_md5) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = paperdata.getConnection().prepareStatement(insert)) {
			pstmt.setString(1, fname);
			pstmt.setString(2, lname);
			pstmt.setString(3, username);
			pstmt.setString(4, email);
			pstmt.setString(5, role);
			pstmt.setString(6, salt);
			pstmt.setString(7, this.md5(salt + password));
			pstmt.executeUpdate();

			return true;
		} catch (NoSuchAlgorithmException | SQLException | UnsupportedEncodingException ex) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see Authenticate#authenticateUser(java.lang.String, java.lang.String)
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
			res.next(); //username is unique
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
			} catch (SQLException ex) {}
		}
	}

	public String getRole(String username) {
		String Getrole = "SELECT role FROM person WHERE username = ?";
		try (
		PreparedStatement pstmt = prepare(Getrole)) {
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

	private PreparedStatement prepare(String sql)
		{
			try
			{
				PreparedStatement statement = paperdata.getConnection().prepareStatement(sql);
				return statement;
			}
			catch(SQLException e)
			{
				System.err.println(e.getMessage());
				return null;
			}
		}// end prepare statement 
	    

	public boolean getPaper(int paperId) {
		String select = "SELECT * FROM papers WHERE id = ?";
		try {
				PreparedStatement pstmt = prepare(select);
		//PreparedStatement pstmt = connection.prepareStatement(select)) {
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

	public boolean searchPapersbyTitle(String paperTitle) {
		String search = "SELECT * FROM papers WHERE Title LIKE ?";
		try (
		PreparedStatement pstmt = prepare(search)) {
			pstmt.setString(1, '%' + paperTitle + '%');

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
	
	public boolean searchPapersbyAuthor(String authorName) {
		String authorSearch = "SELECT papers.`id`, papers.`title`, papers.`abstract`, papers.`citation` From `papers`JOIN `authorship` ON authorship.personId=papers.id JOIN person ON person.`id`= authorship.personId WHERE person.fname = ?;";
		try (
		PreparedStatement pstmt = prepare(authorSearch)) {
		pstmt.setString(1, authorName);
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

	public boolean searchPapersbyKeyWord(String paperKeyword) {
		String search = "SELECT papers.`id`, papers.`title`, papers.`abstract`, papers.`citation` From `papers` INNER JOIN paper_keywords ON papers.id=paper_keywords.id WHERE paper_keywords.keyword LIKE ?;";
		
		try (
		PreparedStatement pstmt = prepare(search)) {
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
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	public String updatePaper(int updatePaperid) {
		if (role.equalsIgnoreCase("faculty") && (paperdata.isAuthenticated() == true)) {
			String update = "UPDATE `papers` SET `title`='Tek Nepal was a nice guy',`abstract`='its working',`citation`='Whats up?' WHERE id = ?;";
			try (
			PreparedStatement pstmt = prepare(update)) {
				pstmt.setInt(1, updatePaperid);
				int rs = pstmt.executeUpdate();
				return "Paper "+updatePaperid+" Updated";
			} catch (SQLException e) {
				e.printStackTrace();
				return "Error Updating paper"+updatePaperid+"";
			}
		}
		return "You are not authorized to update the paper!";
	}
	
	public String insertPaper(int insertPaperid) {
		if (role.equalsIgnoreCase("faculty") && (paperdata.isAuthenticated() == true)) {
			String insertPaper = "INSERT INTO papers VALUES (?,'This is added paper','Paper added', 'inserted citation')";
			
			try (
			PreparedStatement pstmt = prepare(insertPaper)) {
				pstmt.setInt(1, insertPaperid);
				int rs = pstmt.executeUpdate();
				return "Paper "+insertPaperid+" Inserted";
			} catch (SQLException e) {
				return "Cannot add Duplicate Paper!";
			}
		}
		return "you are not authorized to add papers";
	}
	public String insertKeywords(int insertPaperid, String Keyword) {
		if (role.equalsIgnoreCase("faculty") && (paperdata.isAuthenticated() == true)) {
			String insertPaper = "INSERT INTO paper_keywords VALUES (?,?)";
			
			try (
			PreparedStatement pstmt = prepare(insertPaper)) {
				pstmt.setInt(1, insertPaperid);
				pstmt.setString(2, Keyword);
				int rs = pstmt.executeUpdate();
				return "Keyword "+Keyword+" for paper id = "+insertPaperid+" Inserted";
			} catch (SQLException e) {
				//System.out.println(e.getMessage());
				return "Duplicate Keywords cant be added";
			}
		}
		return "you are not authorized to add Keywords";
	}
	public String getMyPapers(String username) {
		String search = "SELECT papers.`id`, papers.`title`, papers.`abstract`, papers.`citation` From `papers` JOIN `authorship` ON authorship.personId=papers.id  JOIN person ON person.`id`= authorship.personId  WHERE person.username = ?;";
		
		try (
		PreparedStatement pstmt = prepare(search)) {
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

} //end class


/*********
CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fName` varchar(45) NOT NULL DEFAULT '',
  `lName` varchar(45) NOT NULL DEFAULT '',
  `username` varchar(45) NOT NULL DEFAULT '',
  `email` varchar(45) NOT NULL DEFAULT '',
  `role` varchar(15) DEFAULT '',
  `pass_salt` tinyblob NOT NULL,
  `pass_md5` tinyblob NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_usernameemail` (`username`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=latin1;
*/