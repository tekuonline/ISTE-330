
/*
 * @author Tek,Qioaran,Chanvi
 */


public class PaperKeywords
{
     //Equipment    table.
        private String id;
        private String keyword;
      
   
        
        
        private PaperDatabase myPaperDB = new PaperDatabase();
        
        // default constructor.
        public PaperKeywords()
        {
        }
        
        public PaperKeywords(String id, PaperDatabase pDatabase)
        {
                this.id = id;
                this.myPaperDB = pDatabase;
        }
        
       
    public PaperKeywords(String id, String keyword, PaperDatabase pDatabase)
        {
                
                this.id = id;
                this.keyword = keyword;
                this.myPaperDB = pDatabase;

        }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public PaperDatabase getMyPaperDB() {
		return myPaperDB;
	}

	public void setMyPaperDB(PaperDatabase myPaperDB) {
		this.myPaperDB = myPaperDB;
	}

	
}
		