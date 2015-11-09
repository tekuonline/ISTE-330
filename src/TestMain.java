
public class TestMain {
	private static String  username = "tn2089@rit.edu";
	private static String  password = "Test123";

	
	public static void main(String[] args) {
		
		
		PaperDatabase paperDb = new PaperDatabase();
		//PrimeSearch window = new PrimeSearch();
		//window.frmResearchPaperDatabase.setVisible(true);
		
		 if (paperDb.connect("teku","Test123") != null){
			 	System.out.println("connected to SQLServer ");
		 }
		
        if (paperDb.createUser("Tim", "Nepal", "test@rit.edu", "test@rit.edu", "Student", "Test123")) {
            System.out.println("User created");}
            else System.out.println("Can't Create Duplicate User");

        if (paperDb.authenticateUser(username, password)) {
            System.out.println("User authenticated");
            }
            else {System.out.println("Invalid Username and or Password");
            }
        
        
        System.out.println("**************Role of this user************");
        String role = paperDb.getRole(username) ;
        System.out.println(role);
        System.out.println("**************************");
        
        System.out.println("Trying To get papers...........");
        paperDb.getPaper(1);  //pass paper id to get 
        
     // if (paperDb.deletePapers(7)) {
      //  System.out.println("Paper deleted");}
      //  else System.out.println("Can't Delete Paper");
        
        System.out.println("************Searching Paper By title*****************");
        if (paperDb.searchPapersbyTitle("th")) {
           }
        	else System.out.println("paper cant be found");
        
        
        System.out.println("**************Searching Paper By KeyWords************");
        if (paperDb.searchPapersbyKeyWord("Technology")) {
           }
        	else System.out.println("paper cant be found");
        
        
        System.out.println("**************Updating the paper************");
        	String updateStatus = paperDb.updatePaper(5); 
        	System.out.println(updateStatus);
        	
        
        System.out.println("**************Inserting the paper************");
        String insertStatus = paperDb.insertPaper(7) ;
        System.out.println(insertStatus);
     
        
        
        paperDb.close();
    } 
    
}

