/**
 * <h1>Paper Database Data</h1>
 * The Paper Database program implements an application that
 * provides a secure, simple and functional user interface to
 * browse, update, delete, insert new article instances to the
 * back end database. 
 * <p>
 * <b>Person Class </b> is a class that provides accessors and mutators 
 * for attributes in person class object declared
 * 
 * @author  Chanvi Kotak, Tek Nepal, Qiaoran Li
 * @version 1.0
 * @since 2015-12-16 
 */

public class Person
{
	//Attributes declared
        private String id;
        private String fname;
        private String lname;
        private String username;
        private String email;
        private String role;
        private String pass_salt;
        private String pass_md5;
        
        
        private PaperDatabase myPaperDB = new PaperDatabase();
        
        // default constructor.
        public Person()
        {
        }
     // constructor with, pDatabase parameters
        public Person(String id, PaperDatabase pDatabase)
        {
                this.id = id;
                this.myPaperDB = pDatabase;
        }
        
       /**
        * constructor
        * @param id
        * @param fname
        * @param lname
        * @param username
        * @param email
        * @param role
        * @param pass_salt
        * @param pass_md5
        * @param pDatabase
        */
        public Person(String id, String fname, String lname, String username, String email, String role, String pass_salt, String pass_md5, PaperDatabase pDatabase)
        {
                
                this.id = id;
                this.fname = fname;
                this.lname = lname;
                this.username = username;
                this.email = email;
                this.role = role;
                this.pass_md5 = pass_md5;
                this.pass_salt = pass_salt;
                this.myPaperDB = pDatabase;

        }

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getFname() {
			return fname;
		}

		public void setFname(String fname) {
			this.fname = fname;
		}

		public String getLname() {
			return lname;
		}

		public void setLname(String lname) {
			this.lname = lname;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getPass_salt() {
			return pass_salt;
		}

		public void setPass_salt(String pass_salt) {
			this.pass_salt = pass_salt;
		}

		public String getPass_md5() {
			return pass_md5;
		}

		public void setPass_md5(String pass_md5) {
			this.pass_md5 = pass_md5;
		}

		public PaperDatabase getMyPaperDB() {
			return myPaperDB;
		}

		public void setMyPaperDB(PaperDatabase myPaperDB) {
			this.myPaperDB = myPaperDB;
		}
}
