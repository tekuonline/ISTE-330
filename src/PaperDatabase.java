
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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PaperDatabase implements Authenticate {
	private static String role = "public";
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet rs;
	private static ResultSetMetaData rsmd;
	private static PreparedStatement pstmt;
	private static String sql;
	protected static ArrayList<String> smallList = new ArrayList<String>();
	protected static ArrayList<ArrayList<String>> bigList = new ArrayList<ArrayList<String>>();
	// database constants

	protected PaperDatabaseData paperdata = new PaperDatabaseData(false);

	/**
	 * Connect to the database
	 * 
	 * @return
	 */
	public Connection connect() {
		if (paperdata.getConnection() == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				paperdata.setConnection(DriverManager.getConnection(DATABASE_URL, user, pass));
				// System.out.println("Connected to database!");

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
	 * Close connection to the database
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
	 * getData
	 * @param sql is a sql statement assigned by the user
	 * @param list is a arraylist containing string values
	 */
	public ArrayList getData(String sql, ArrayList list) {
		try {
			rs = prepare(sql, list).executeQuery(); // call prepare method
			rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					smallList.add(rs.getString(i));
				}
				ArrayList tempSmallList = new ArrayList<String>(smallList);
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
	 * setData method
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

	/*
	 * fetch method to fetch the data from database. 
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
	 * prepares the statement
	 * 
	 * @param sql
	 * @return
	 */
	private PreparedStatement prepare(String sql, ArrayList<String> value) {
		try {
			pstmt = paperdata.getConnection().prepareStatement(sql);
			for (int i = 0; i < value.size(); i++) {
				try {
					int d = Integer.parseInt(value.get(i).toString());
					pstmt.setInt(i + 1, d);
				} catch (Exception test) {
					String test1 = value.get(i).toString();
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
	 * Encodes the String using MD5 for password.
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
			if (ex.getMessage().contains("Duplicate entry")) {
				return "Duplicate";
			}
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

		ArrayList<String> pass = new ArrayList<String>();
		pass.add(password);
		try (PreparedStatement pstmt = prepare(select, pass)) {
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

	/*
		*//**
			 * Gets the user role based on username.
			 * 
			 * @param username
			 * @return
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
	 * Delete the paper using paper id
	 * 
	 * @param paperId
	 * @return
	*/
	public boolean deletePapers(int paperId) {
		String delete = "DELETE FROM papers WHERE id = ?";
		ArrayList<String> value = new ArrayList<String>();
		value.add("" + paperId);
		boolean success = setData(delete, value);
		return success;
	}
	
	/**
	 * 
	 * @param insertPaperid
	 * @param title
	 * @param abst
	 * @param citation
	 * @return
	 */
	public String insertPaper(String title, String abst, String citation) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(title);
		list.add(abst);
		list.add(citation);
	
		sql = "INSERT INTO papers VALUES (92,'test','test','test');";
	
		try {

			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "you are not authorized to add papers";
	}

	/**
	 * get all the papers for the logged in user.
	 * 
	 * @param username
	 * @return
	 */
	
	public String getRole() {
		return role;
	}

	public Connection getConnection() {
		return getConnection();
	}
	

} // end class

/**
# ************************************************************
# Host: teknepal.com (MySQL 5.5.44-MariaDB)
# Database: ISTE330PAPER
# Generation Time: 2015-12-13 04:43:21 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT *//*;
!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS ;
!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION ;
!40101 SET NAMES utf8 ;
!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 ;
!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' ;
!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 ;


# Dump of table authorship
# ------------------------------------------------------------
CREATE Database ISTE330PAPER;
USE ISTE330PAPER;

DROP TABLE IF EXISTS `authorship`;

CREATE TABLE `authorship` (
  `personId` int(11) NOT NULL,
  `paperId` int(11) NOT NULL,
  PRIMARY KEY (`personId`,`paperId`),
  KEY `fk_a_f` (`personId`),
  KEY `fk_a_p` (`paperId`),
  CONSTRAINT `fk_a_f` FOREIGN KEY (`personId`) REFERENCES `person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_a_p` FOREIGN KEY (`paperId`) REFERENCES `papers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `authorship` WRITE;
!40000 ALTER TABLE `authorship` DISABLE KEYS ;

INSERT INTO `authorship` (`personId`, `paperId`)
VALUES
	(2,2),
	(12,5),
	(26,7),
	(41,3);

!40000 ALTER TABLE `authorship` ENABLE KEYS ;
UNLOCK TABLES;


# Dump of table paper_keywords
# ------------------------------------------------------------

DROP TABLE IF EXISTS `paper_keywords`;

CREATE TABLE `paper_keywords` (
  `id` int(11) NOT NULL,
  `keyword` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`keyword`),
  KEY `pk_p` (`id`),
  CONSTRAINT `pk_p` FOREIGN KEY (`id`) REFERENCES `papers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `paper_keywords` WRITE;
!40000 ALTER TABLE `paper_keywords` DISABLE KEYS ;

INSERT INTO `paper_keywords` (`id`, `keyword`)
VALUES
	(2,'C#'),
	(2,'IIS'),
	(2,'Java'),
	(2,'performance'),
	(2,'PHP'),
	(2,'SOAP'),
	(2,'Tomcat'),
	(2,'web services'),
	(2,'XML'),
	(3,'course assignment'),
	(3,'department management'),
	(3,'faculty'),
	(3,'tools'),
	(3,'Web 2.0'),
	(5,'abduction'),
	(5,'curriculum'),
	(5,'education'),
	(5,'FITness'),
	(5,'informatics'),
	(5,'IT fluency'),
	(7,'javatest'),
	(10,'Tek');

!40000 ALTER TABLE `paper_keywords` ENABLE KEYS ;
UNLOCK TABLES;


# Dump of table papers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `papers`;

CREATE TABLE `papers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `abstract` text,
  `citation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `papers` WRITE;
!40000 ALTER TABLE `papers` DISABLE KEYS ;

INSERT INTO `papers` (`id`, `title`, `abstract`, `citation`)
VALUES
	(2,'Think Inside the Box! Optimizing Web Service Performance, Today','While Web Services Technology holds great promise for universal integration, several obstacles stand in the way of its acceptance. Work is being done to address these obstacles to allow adoption of web services technology in the future, but where do we stand today? In particular, what can be done today to combat the often cited problem of slow response times for web services? While XML hardware acceleration and SOAP compression schemes can improve the overall response, the authors have found that appropriate selection of client software, server software, and data structures can have a substantial impact. It is possible to have a profound impact on performance using tools that are routinely and dependably available to us now.','Zilora, Stephen, and Sai Sanjay Ketha. \"Think Inside the Box! Optimizing Web Services Performance Today.\" IEEE Communications Magazine, 46.3 (2008): 112-117.'),
	(3,'Work in Progress - Bringing Sanity to the Course Assignment Process','The floor of the NY Stock Exchange, with its noise and chaos, is an apt depiction of the typical course selection meeting that many universities experience. Professors attempt to shout over their colleagues or broker deals with one another in small cabals in an attempt to garner the sections they hope to teach. When first choices fall by the wayside, quick recalculations of schedules are necessary in order to determine the next best scenario and sections to request. As inexperienced junior faculty members, the authors found this experience daunting. In response, they wrote a simple web application that allowed faculty to make their selections, and broker deals, in a calm manner over an extended time period. The application was originally written for one sub-group of about 20 faculty within the department, but its popularity quickly spread to the rest of the department and then on to other departments within the college. The course assignment and reservation system (CARS) has grown steadily over the past several years in number of users, functionality, and scope. Today, faculty can plan their teaching load, work with colleagues to find mutually beneficial schedules, and easily retrieve historical information in preparation for annual reviews, promotion, or tenure appointments. Department administrators can manage course information, prepare information for certification agencies, assign faculty to courses, and monitor faculty loads. Staff and students also benefit from interfaces permitting access to appropriate information to assist them in their planning activities. Utilizing Web 2.0 technologies, the application is enjoyable to use and gives all of the disparate users a satisfying experience.','Zilora, S.J, and D.S Bogaard. \"Work in Progress - Bringing Sanity to the Course Assignment Process.\" Frontiers in Education Conference, 2008. FIE 2008. 38th Annual, (2008)'),
	(5,'JAVA: The Ultimate Beginner\'s Guide!','I am dipping my feet into several programming languages trying to figure out which one suits my needs the best. I already have some basic knowledge in Ruby, Python and now I want to try Java.','Amazon Digital Services, Inc. add'),
	(7,'Murach\'s SQL Server 2012 for Developers','Every application developer who uses SQL Server 2012 should own this book. To start, it presents the essential SQL statements for retrieving and updating the data in a database. You have to master these to work effectively with database data in your applications.','Mike Murach & Associates; 1 edition (August 20, 2012)'),
	(10,'Lonely Planet Nepal (Travel Guide)','Lonely Planet Nepal is your passport to the most relevant, up-to-date advice on what to see and skip, and what hidden discoveries await you. Soak in the hustle-and-bustle of Kathmandu\'s Durbar Square, trek to the base of the world\'s highest mountain, or raft the rapids of the Bhote Kosi; all with your trusted travel companion. Get to the heart of Nepal and begin your journey now!','Lonely Planet; 10 edition (December 15, 2015)'),
	(15,'admin','this is a book','Nepal');

!40000 ALTER TABLE `papers` ENABLE KEYS ;
UNLOCK TABLES;


# Dump of table person
# ------------------------------------------------------------

DROP TABLE IF EXISTS `person`;

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `person` WRITE;
!40000 ALTER TABLE `person` DISABLE KEYS ;

INSERT INTO `person` (`id`, `fName`, `lName`, `username`, `email`, `role`, `pass_salt`, `pass_md5`)
VALUES
	(1,'Tim','Nepal','test@rit.edu','test@rit.edu','Student',X'3538306533316461306237623234316337363365343566663631353762626538',X'3333323038613262663130383262366335626535356566366231326362616562'),
	(2,'Tek','Nepal','tn2089@rit.edu','Test123','Faculty',X'6161383338633037643934663335383433643836643439373035306434313036',X'3263383038666661363436366630663762313830376435656230666634636163'),
	(12,'Tek','Nepal','tn','Test123','admin',X'6566366664366163386365303131353239613239626539303765303233336637',X'3062313161303466376136623761623438373461643862303262333861333534'),
	(26,'t','t','t','t','faculty',X'6666613835313037363236396432623030653535616232303362663863616134',X'6337363937326531663535393664353966393766346239353833343265626161'),
	(41,'Ayesha','Nepal','anepal','anepal@teknepal.com','faculty',X'336462623439613334316262306666373266656439663137333235626434636337',X'3334396666663736306331643236643632363230363335313330343632663835'),
	(42,'Sarah','Jensen','sj','sj@gmail.com','student',X'323664313865616633303735626462643262363738626139353466643561656333',X'6431663636633635303837376564323832343036346334646266336639613863'),
	(43,'Tom','Brown','thu','thu@gmail.com','faculty',X'323163346139653531613635396562376636306430653465656365323435323937',X'3134326134633466353634313131346335396434333762376634323232653835'),
	(44,'Gom','Nepal','gm','gm','student',X'336236623836633534646337643736616532613866383964373530373631633235',X'3466363736646231333662666537623338353363313638363939306535646561');

!40000 ALTER TABLE `person` ENABLE KEYS ;
UNLOCK TABLES;

!40111 SET SQL_NOTES=@OLD_SQL_NOTES ;
!40101 SET SQL_MODE=@OLD_SQL_MODE ;
!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS ;
!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT ;
!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS ;
!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION ;
*/