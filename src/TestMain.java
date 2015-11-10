public class TestMain {
	private static String username = "test@rit.edu";
	private static String password = "Test123";

	public static void main(String[] args) {

		PaperDatabase paperDb = new PaperDatabase();
		// PrimeSearch window = new PrimeSearch();
		// window.frmResearchPaperDatabase.setVisible(true);

		if (paperDb.connect("teku", "Test123") != null) {
			System.out.println("connected to SQLServer ");
		}
		System.out.println("**************Creating User************");
		if (paperDb.createUser("Tek", "Nepal", "tn2089@rit.edu", "Test123",
				"faculty", "Test123")) {
			System.out.println("User created");
		} else
			System.out.println("Can't Create Duplicate User");
		

		System.out.println("**************Authenticating User************");
		if (paperDb.authenticateUser(username, password)) {
			System.out.println("User authenticated");
		} else {
			System.out.println("Invalid Username and or Password");
		}

		System.out.println("**************Role of this user************");
		String role = paperDb.getRole(username);
		System.out.println(role);
		System.out.println("**************************");

		System.out.println("Trying To get papers...........");
		paperDb.getPaper(1); // pass paper id to get

		// if (paperDb.deletePapers(7)) {
		// System.out.println("Paper deleted");}
		// else System.out.println("Can't Delete Paper");

		System.out
				.println("************Searching Paper By title*****************");
		if (paperDb.searchPapersbyTitle("th")) {
		} else
			System.out.println("paper cant be found");

		System.out.println("**************Searching Paper By KeyWords************");
		if (paperDb.searchPapersbyKeyWord("tek")) {
		} else
			System.out.println("paper cant be found");
		
		System.out.println("**************Searching Paper By Author************");
		if (paperDb.searchPapersbyAuthor("Tek")) {
		} else
			System.out.println("paper cant be found");

		System.out.println("**************Updating the paper************");
		String updateStatus = paperDb.updatePaper(5);
		System.out.println(updateStatus);

		System.out.println("**************Inserting the paper************");
		String insertStatus = paperDb.insertPaper(7);
		System.out.println(insertStatus);
		
		System.out.println("**************Inserting Keywords************");
		String insertKeywordStatus = paperDb.insertKeywords(7, "javatest");
		System.out.println(insertKeywordStatus);
		
		
		System.out.println("**************All My Papers************");
		String myPapers = paperDb.getMyPapers(username);
			System.out.println(myPapers);

		paperDb.close();
	}

}
