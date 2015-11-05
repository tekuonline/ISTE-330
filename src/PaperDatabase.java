import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.sql.*;
import java.util.Set;


public class PaperDatabase{
    //database constants
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://teknepal.com:3306/PAPER";

    //connection object
    private Connection connection;
    //connect database
    
    public Connection connect(String user, String pass) {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL,user,pass);              
                } 
            catch ( SQLException e) {
            	System.out.println("Error  " + e.getMessage());  // delete this after all done 
                e.printStackTrace();// delete this after all done 
            	}
              catch (ClassNotFoundException ex) {
            	  System.out.println("Error  " + ex.getMessage());// delete this after all done 
              	}   	  
          	}
        return connection;
    }
    
    // close  database connection
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Closed the connection to the server"); //delete this after all done 
            } catch (SQLException e) {
                e.printStackTrace();// delete this after all done 
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
    
    
    public boolean createUser(String user, String password) {
        SecureRandom random;
        String insert;
        String salt;
 
        random = new SecureRandom();
        salt =  new BigInteger(130, random).toString(16);
 
        insert = "INSERT INTO users "
            + "(username, pass_salt, pass_md5) "
            + "VALUES (?, ?, ?)";
 
        try (PreparedStatement pstmt = this.connection.prepareStatement(insert)) {
            pstmt.setString(1, user);
            pstmt.setString(2, salt);
            pstmt.setString(3, this.md5(salt + password));
            pstmt.executeUpdate();
 
            return true;
        } catch(NoSuchAlgorithmException | SQLException | UnsupportedEncodingException ex) {
            return false;
        }
    }
    
    
    public boolean authenticateUser(String user, String password) {
        String pass_md5;
        String pass_salt;
        String select;
        ResultSet res;
 
        select = "SELECT pass_salt, pass_md5 FROM users WHERE username = ?";
        res = null;
 
        try(PreparedStatement pstmt = this.connection.prepareStatement(select)) {
            pstmt.setString(1, user);
            res = pstmt.executeQuery();
 
            res.next(); //username is unique
 
            pass_salt = res.getString(1);
            pass_md5 = res.getString(2);
 
            if (pass_md5.equals(this.md5(pass_salt + password))) {
                return true;
            } else {
                return false;
            }
 
        } catch(NoSuchAlgorithmException | SQLException | UnsupportedEncodingException ex) {
            return false;
        } finally {
            try {
                if (res instanceof ResultSet){
                    res.close();
                }
            } catch(SQLException ex) {
            }
        }
    }
    
    
  public boolean getPapers() {  
	  String  select = "SELECT * FROM papers WHERE id = ? "; 
       try(
        PreparedStatement s = connection.prepareStatement(select)){
    	s.setInt(1, 9);
        ResultSet rs = s.executeQuery();
        while (rs.next()) {
        	String userid = rs.getString("userid");
        	String username = rs.getString("username");	
        	System.out.println(userid +"  " +  username);
        }
            return true;
       }
       catch(Exception e){e.printStackTrace();}
       		return false;
       } 
  
  
  public boolean deletePapers(String username) {  
	  String  delete = "DELETE FROM users WHERE username = ?";
       try(
        PreparedStatement s = connection.prepareStatement(delete)){
    	s.setString(1, username);
       
        s.executeUpdate();
            return true;
       }
       catch(Exception e){e.printStackTrace();}
       		return false;
       } 
  
  
  public boolean searchPapersbyTitle(String paper) {  
	  String  search = "SELECT * FROM papers WHERE Title LIKE ?";
       try(
        PreparedStatement s = connection.prepareStatement(search)){
    	s.setString(1, '%'+paper+'%');
       
        ResultSet rs = s.executeQuery();
        while (rs.next()) {
        	String id = rs.getString("id");
        	String title = rs.getString("title");
        	String ab = rs.getString("abstract");
        	String citation = rs.getString("citation");
        	
        	System.out.println("Paper ID: " + id);
        	System.out.println("Title: "+ title);
        	System.out.println("Abstract: "+ ab);
        	System.out.println("Citation: "+ citation);
        }
            return true;
       }
       catch(Exception e){e.printStackTrace();}
       		return false;
       }
  
  public boolean searchPapersbyKeyWord(String paper) {  
	  String  search = "SELECT * FROM papers WHERE abstract LIKE ?";
       try(
        PreparedStatement s = connection.prepareStatement(search)){
    	s.setString(1, '%'+paper+'%');
       
        ResultSet rs = s.executeQuery();
        while (rs.next()) {
        	String id = rs.getString("id");
        	String title = rs.getString("title");
        	String ab = rs.getString("abstract");
        	String citation = rs.getString("citation");
        	
        	System.out.println("Paper ID: " + id);
        	System.out.println("Title: "+ title);
        	System.out.println("Abstract: "+ ab);
        	System.out.println("Citation: "+ citation);
        	
        }
            return true;
       }
       catch(Exception e){e.printStackTrace();}
       		return false;
      }
       
  
  public boolean updatePaper(int updatePaperid) {
	  
	  String  update = "UPDATE `papers` SET `title`='Tek nepal was a nice guy',`abstract`='its working',`citation`='Whats up?' WHERE id = ?;";
      try(
       PreparedStatement s = connection.prepareStatement(update)){
    	  s.setInt(1,updatePaperid);
    	  System.out.println(s);
        int rs = s.executeUpdate();
       	return true;
       
      }
      catch(Exception e){e.printStackTrace();}
      		return false;
  }
}//end class
    



/*********
CREATE TABLE users (
userid INT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(32) UNIQUE KEY NOT NULL,
pass_salt tinyblob NOT NULL,
pass_md5 tinyblob NOT NULL
);
*/
