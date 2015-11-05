
public class TestMain {

	public static void main(String[] args) {
		
		PaperDatabase paperDb = new PaperDatabase();
		PrimeSearch window = new PrimeSearch();
		window.frmResearchPaperDatabase.setVisible(true);
		
		 if (paperDb.connect("teku","Test123") != null){
			 	System.out.println("connected to SQLServer ");
		 }
		
        if (paperDb.createUser("test", "password")) {
            System.out.println("User created");}
            else System.out.println("Can't Create User");
        
        if (paperDb.createUser("tes", "Nepal")) {
            System.out.println("User created");}
            else System.out.println("Can't Create User");
        

        if (paperDb.authenticateUser("tes", "Nepal")) {
            System.out.println("User authenticated");}
            else System.out.println("Can't Authenticate User");
        
        System.out.println("Trying To get papers...........");
        paperDb.getPapers();
       // if (paperDb.deletePapers("Tek")) {
         //   System.out.println("user deleted");}
        	//else System.out.println("Can't Delete Paper");
        System.out.println("************Searching Paper By title*****************");
        if (paperDb.searchPapersbyTitle("th")) {
           }
        	else System.out.println("paper cant be found");
        
        
        System.out.println("**************Searching Paper By KeyWords************");
        if (paperDb.searchPapersbyKeyWord("th")) {
           }
        	else System.out.println("paper cant be found");
        
        
        System.out.println("**************Updating the paper************");
        if (paperDb.updatePaper(5)) {
        	System.out.println("paper updated");
        }
        
        System.out.println("**************Inserting the paper************");
        if (paperDb.insertPaper(7)) {
        	System.out.println("paper Added");
        }
        paperDb.close();
    } 
    
}

