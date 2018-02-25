import java.util.Properties;


public class Book extends InventoryMaintenance
{
	public Book(Properties props, String dataFile, String itemCat)
	{
		super (props,dataFile,itemCat);
		
	}

	@Override
	public void searchProps(String searchInfo)
	{
		loadProperties();
		Object value = getTable().getProperty(searchInfo);
			
		if (value !=null)
		{
			String str = value.toString();
			String str2 = " Author: "+ getFieldTwo(str);
			String str3 = " Price: " + getFieldThree(str);
			String str4 = " Published Year: " + getFieldFour(str);
			String str5 = " Rating: " + getFieldFive(str);
			
			System.out.println("Book Title: "+ searchInfo + str2 + str3 + str4 + str5);
				
		}
		
		else
		{	
			System.out.println("Sorry, no match is found in our system. Please select add new item if needed");
			
		}
	}
}
