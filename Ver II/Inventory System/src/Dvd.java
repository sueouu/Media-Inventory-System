import java.util.Properties;


public class Dvd extends InventoryMaintenance{
	public Dvd(Properties props, String dataFile, String itemCat)
	{
		super (props,dataFile,itemCat);
	}
	
	@Override
	public String searchProps(String searchInfo)
	{
		String searchResult;
		loadProperties();
		Object value = getTable().getProperty(searchInfo);
			
		if (value !=null)
		{
			String str = value.toString();
			String str2 = " Director: "+ getFieldTwo(str);
			String str3 = " Price: " + getFieldThree(str);
			String str4 = " Released Year: " + getFieldFour(str);
			String str5 = " Rating: " + getFieldFive(str);
			
			searchResult = "DVD Title: "+ searchInfo + str2 + str3 + str4 + str5;
				
		}
		
		else
		{	
			searchResult = "Sorry, no match is found in our system. Please select add new item if needed";
			
		}
		
		return searchResult;
	}

}
