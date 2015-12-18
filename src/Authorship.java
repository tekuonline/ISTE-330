/**
 * <h1>Paper Database Data</h1>
 * The Paper Database program implements an application that
 * provides a secure, simple and functional user interface to
 * browse, update, delete, insert new article instances to the
 * back end database. 
 * <p>
 * <b>Authorship Data Class </b> is a class that provides accessors and mutators 
 * for paper object.
 * 
 * @author  Chanvi Kotak, Tek Nepal, Qiaoran Li
 * @version 1.0
 * @since 2015-12-16 
 */

public class Authorship
{
     //Equipment    table.
        private String personId;
        private String paperId;
      
   
        
        
        private PaperDatabase myPaperDB = new PaperDatabase();
        
        // default constructor.
        public Authorship()
        {
        }
        
     // constructor with id , pDatabase parameters
        public Authorship(String id, PaperDatabase pDatabase)
        {
                this.personId = id;
                this.myPaperDB = pDatabase;
        }
        
     // constructor with personId , paperId, pdatabase parameters   
    public Authorship(String personId, String paperId, PaperDatabase pDatabase)
        {
                
                this.personId = personId;
                this.paperId = paperId;
                this.myPaperDB = pDatabase;

        }

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public PaperDatabase getMyPaperDB() {
		return myPaperDB;
	}

	public void setMyPaperDB(PaperDatabase myPaperDB) {
		this.myPaperDB = myPaperDB;
	}
}
		