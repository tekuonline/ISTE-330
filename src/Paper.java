/**
 * <h1>Paper Database Data</h1>
 * The Paper Database program implements an application that
 * provides a secure, simple and functional user interface to
 * browse, update, delete, insert new article instances to the
 * back end database. 
 * <p>
 * <b>Paper  Class </b> is a class that provides 
 * accessors and mutators to the paper object
 * 
 * @author  Chanvi Kotak, Tek Nepal, Qiaoran Li
 * @version 1.0
 * @since 2015-12-16 
 */

public class Paper
{
	//attributes declared
        private String id;
        private String title;
        private String abstrac;
        private String citation;
   
        
        
        private PaperDatabase myPaperDB = new PaperDatabase();
        
        // default constructor.
        public Paper()
        {
        }
     // constructor wih id , pDatabase parameters
        public Paper(String id, PaperDatabase pDatabase)
        {
                this.id = id;
                this.myPaperDB = pDatabase;
        }
        
        /**
         * 
         * @param id
         * @param title
         * @param abstrac
         * @param citation
         * @param pDatabase
         * constructor with id, title, abstrac,citation,pDatabase attributes
         */
         
         
    public Paper(String id, String title, String abstrac, String citation, PaperDatabase pDatabase)
        {
                
                this.id = id;
                this.title = title;
                this.abstrac = abstrac;
                this.citation = citation;
                this.myPaperDB = pDatabase;

        }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbstrac() {
		return abstrac;
	}

	public void setAbstrac(String abstrac) {
		this.abstrac = abstrac;
	}

	public String getCitation() {
		return citation;
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

	public PaperDatabase getMyPaperDB() {
		return myPaperDB;
	}

	public void setMyPaperDB(PaperDatabase myPaperDB) {
		this.myPaperDB = myPaperDB;
	}
}
		