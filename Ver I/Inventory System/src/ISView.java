import java.util.Scanner;

public class ISView {

	private ISModel model;
	private ISController controller;
	private int userCat;
	private int userAction;
	private int userEntryFieldNum;
	//private int selectedAction;
	private String fields;
	private String cat;
	private String userEntry;
	private String userEntry2;
	//private String info;
	
	
	public ISView(ISController controller) 
	{
		this.controller=controller;
	}


	public void itemSelection ()
	{
		Scanner scanner = new Scanner (System.in);
		
		
		while (userCat <=3){
			System.out.println("--------------- Main Menu ---------------");
			System.out.println("Please choose one of the following Categories\n" 
					+ "***Enter an interger only... Ex: For Books, enter 1 \n"
					+ "1 -- Books\n" + "2 -- CDs\n" +"3 -- DVDs\n" 
					+ "Any interger bigger than 3-- Exit Program\n");
			
			userCat = scanner.nextInt();
	
				if (userCat <=3)
				{
					controller.setMCat(userCat);
					
					System.out.println("Please choose one of the following actions\n"
							+"***Enter an integer only... Ex: For adding new items, enter 1\n"
							+"1 -- Add a New Item\n" + "2 -- Search an Item\n" + "3 -- Delete an Item\n"
							+"4 -- Edit an Item");
					userAction = scanner.nextInt();
					scanner.nextLine();
					
					if (userAction == 1)
					{
						System.out.println("Please provide the following " + cat + " info to add new item:\n"
								+ fields + "\n***Use '/' to seperate fields\n" + "Ex: title/Author/...\n");
						userEntry = scanner.nextLine();
						
						controller.setMOps(userEntry, userAction);
			
					}
					else if (userAction == 2)
					{
						System.out.println("Please provide the title of the " + cat + " to search" );
						userEntry = scanner.nextLine();
						
						controller.setUserInput(userEntry);
						controller.setMOps(userEntry, userAction);
					}
			
					else if (userAction == 3)
					{
						System.out.println("Please provide the title of the " + cat + " to delete");
						userEntry = scanner.nextLine();
						
						controller.setMOps(userEntry, userAction);
					}
			
					else if (userAction == 4)
					{
						System.out.println ("Please provide the title of the " + cat + " to edit" );
						userEntry = scanner.nextLine();
			
						System.out.println ("Please provide the field number (1 to 5) to edit... Ex: Enter '1' for field one");
						userEntryFieldNum = scanner.nextInt();
				
						scanner.nextLine();
						
						System.out.println ("Please provide new info for current field info replacement for field " + userEntryFieldNum);
						userEntry2 = scanner.nextLine();
						
						controller.setMOps(userEntry, userEntry2, userAction, userEntryFieldNum);
					}
					else
					{
						System.out.println ("Sorry, invalid Input");
					}
				}
				else
				{
						break;
				}
							
		}
		System.out.println("Exiting... goodbye");
		scanner.close();
	}
	
	public void modelInteraction(int selectedAction)
	{
		model.propsActions();
	}
	
	public void modelInteraction(String info)
	{
		model.getProps();
		cat = model.getCat();
		fields = model.getFields();		
	}
	
	//setISModel method
	
	public void setISModel(ISModel model)
	{
		this.model= model;
	}
	

}