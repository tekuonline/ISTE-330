
public class TestMain {
	private static boolean au;
	public static void main(String[] args) {
		
		
		PaperDatabase paperDb = new PaperDatabase();
		PrimeSearch window = new PrimeSearch();
		window.frmResearchPaperDatabase.setVisible(true);
		
		 if (paperDb.connect("teku","Test123") != null){
			 	System.out.println("connected to SQLServer ");
		 }
		
        if (paperDb.createUser("test", "password")) {
            System.out.println("User created");}
            else System.out.println("Can't Create Duplicate User");
        
        if (paperDb.createUser("tes", "Nepal")) {
            System.out.println("User created");}
            else System.out.println("Can't Create Duplicaate User");
        

        if (au = paperDb.authenticateUser("te", "Nepal")) {
            System.out.println("User authenticated");
            }
            else {System.out.println("Invalid Username and or Password");
            }
        
        System.out.println("Trying To get papers...........");
        paperDb.getPapers();
        
       if (paperDb.deletePapers(7)) {
         System.out.println("Paper deleted");}
        	else System.out.println("Can't Delete Paper");
        
        System.out.println("************Searching Paper By title*****************");
        if (paperDb.searchPapersbyTitle("th")) {
           }
        	else System.out.println("paper cant be found");
        
        
        System.out.println("**************Searching Paper By KeyWords************");
        if (paperDb.searchPapersbyKeyWord("Technology")) {
           }
        	else System.out.println("paper cant be found");
        
        
        System.out.println("**************Updating the paper************");
        if(au){
        	if (paperDb.updatePaper(5)) {
        		System.out.println("paper updated");
        	}
        }
        else{ System.out.println("You are not authorized to update the paper!");
        }
        
        System.out.println("**************Inserting the paper************");
        if (paperDb.insertPaper(7)) {
        	System.out.println("paper Added");
        }
        else System.out.println("paper cant be added.");
        paperDb.close();
    } 
    
}

