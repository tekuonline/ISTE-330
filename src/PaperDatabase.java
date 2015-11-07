import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.sql.*;

public class PaperDatabase{
    //database constants
	
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://teknepal.com:3306/PAPER";
    private static  String role = "public";
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
    
    
    public boolean createUser(String fname,String lname,String username,String email, String role, String password) {
        SecureRandom random;
        String insert;
        String salt;
 
        random = new SecureRandom();
        salt =  new BigInteger(130, random).toString(16);
 
        insert = "INSERT INTO persontest "
            + "(fname, lname, username, email, role, pass_salt, pass_md5) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
 
        try (PreparedStatement pstmt = connection.prepareStatement(insert)) {
            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setString(3, username);
            pstmt.setString(4, email);
            pstmt.setString(5, role);
            pstmt.setString(6, salt);
            pstmt.setString(7, this.md5(salt + password));
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
 
        select = "SELECT pass_salt, pass_md5 FROM persontest WHERE username = ?";
        res = null;
 
        try(PreparedStatement pstmt = connection.prepareStatement(select)) {
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
    
    
  public boolean getPaper(int paperId) {  
	  String  select = "SELECT * FROM papers WHERE id = ?"; 
       try(
        PreparedStatement pstmt = connection.prepareStatement(select)){
    	pstmt.setInt(1, paperId);
        ResultSet rs = pstmt.executeQuery();
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
  
  
  public boolean deletePapers(int paperId) {  
	  String  delete = "DELETE FROM papers WHERE id = ?";
       try(
        PreparedStatement pstmt = connection.prepareStatement(delete)){
    	pstmt.setInt(1, 7);
       
        pstmt.executeUpdate();
            return true;
       }
       catch(Exception e){e.printStackTrace();}
       		return false;
       } 
  
 protected boolean getRole(String username) {  
	  String  delete = "SELECT role FROM persontest WHERE username = ?";
       try(
        PreparedStatement pstmt = connection.prepareStatement(delete)){
    	pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
        role = rs.getString("role");
        
        System.out.println("The role of this user is " +role);
        }
            return true;
       }
       catch(Exception e){e.printStackTrace();}
       		return false;
       		
       } 
  
  
  public boolean searchPapersbyTitle(String paperTitle) {  
	  String  search = "SELECT * FROM papers WHERE Title LIKE ?";
       try(
        PreparedStatement pstmt = connection.prepareStatement(search)){
    	pstmt.setString(1, '%'+paperTitle+'%');
       
        ResultSet rs = pstmt.executeQuery();
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
  
  public boolean searchPapersbyKeyWord(String paperKeyword) {  
	  String  search = "SELECT * FROM papers WHERE abstract LIKE ?";
       try(
        PreparedStatement pstmt = connection.prepareStatement(search)){
    	pstmt.setString(1, '%'+paperKeyword+'%');
       
        ResultSet rs = pstmt.executeQuery();
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
	  System.out.println(role);
		  if(role.equals("faculty")){
		  String  update = "UPDATE `papers` SET `title`='Tek Nepal was a nice guy',`abstract`='its working',`citation`='Whats up?' WHERE id = ?;";
	      try(
	    	  PreparedStatement pstmt = connection.prepareStatement(update)){
	    	  pstmt.setInt(1,updatePaperid);
	    	  int rs = pstmt.executeUpdate();
	    	  return true;
	    	  } 
	      	catch (Exception e) {
	    	  e.printStackTrace();
	    	  return false;
	      	} 	 
		  }  
		  return false;
  }
  	public String insertPaper(int insertPaperid) {
    if(role.equals("faculty")){
	  String insertPaper = "INSERT INTO papers VALUES (?,'This is added paper','Paper added', 'inserted citation')";
	  try(
			  	PreparedStatement pstmt = connection.prepareStatement(insertPaper)){
		  		pstmt.setInt(1,insertPaperid);
		        int rs = pstmt.executeUpdate();
		       	return "updated";  
		      }
		      catch(Exception e){
		    	System.out.println(e.getMessage());
		    	return "error updating";
		      }
    		}
		return "not authorized";
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
