
/*
 * @author Tek,Qioaran, Chanvi
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
        
        public Authorship(String id, PaperDatabase pDatabase)
        {
                this.personId = id;
                this.myPaperDB = pDatabase;
        }
        
       
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
		