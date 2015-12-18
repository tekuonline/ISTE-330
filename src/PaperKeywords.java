/**
 * <h1>Paper Database</h1>
 * The Paper Database program implements an application that
 * provides a secure, simple and functional user interface to
 * browse, update, delete, insert new article instances to the
 * back end database. 
 * <p>
 * <b>Paper Keywords  Class </b> is a class that had the accessors and mutators for attributes
 *  used for searching keywords
 * 	data in paper database
 * 
 * @author  Chanvi Kotak, Tek Nepal, Qiaoran Li
 * @version 1.0
 * @since   2015-12-16 
 */

public class PaperKeywords
{
     //PaperKeywords    table.
        private String id;
        private String keyword;
      
   
        
        
        private PaperDatabase myPaperDB = new PaperDatabase();
        
     // default constructor for the PaperKeyword class.
        public PaperKeywords()
        {
        }
        
     // constructors with id , pdatabase attributes
        public PaperKeywords(String id, PaperDatabase pDatabase)
        {
                this.id = id;
                this.myPaperDB = pDatabase;
        }
        
        // constructors with id , pdatabase, keyword attributes   
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
		