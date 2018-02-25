import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;


public abstract class InventoryMaintenance implements InventorySystem
{
	
	private Properties props;
	private String dataFile;
	private String itemCat;

	
//constructor	
	public InventoryMaintenance(Properties props, String dataFile, String itemCat)
	{
		this.props = props;
		this.dataFile = dataFile;
		this.itemCat = itemCat;
	}
	
	//File file = new File (dataFile);
	
//setters
	public void setTable (Properties props)
	{
		this.props = props;
	}
	
	public void setdataFile (String dataFile)
	{
		this.dataFile = dataFile;
	}
	
	public void setItemCat (String itemCat)
	{
		this.itemCat = itemCat;
	}

	
//getters
	public Properties getTable()
	{
		return props;
	}
	
	public String getDataFile ()
	{
		return dataFile;
	}
	
	public String getItemCat()
	{
		return itemCat;
	}
	
	

//InventorySystem methods with overrides

	@Override
	public void getProps()
	{
		loadProperties();
		listProperties();
	}
	
	@Override
	public void addProps (String newInfo)
	{
		loadProperties();
		String stringToAdd = "/" + newInfo + "/";
		Object addValue = getTable().getProperty(stringToAdd);
		if (addValue != null)
		{
			System.out.println("The item you want to add is already in system. Please see below for current list:");
			System.out.println(addValue);
		}	
		else
		{
			getTable().setProperty(getFieldOne(stringToAdd),stringToAdd);
			saveProperties();
			//listProperties();
		}
	}
	
	/*@Override
	public void searchProps(String searchInfo)
	{
		loadProperties();
		Object value = getTable().getProperty(searchInfo);
		
		if (value !=null)
		{
			System.out.println(value);
			
		}
		else
		{
			
			System.out.println("Sorry, no match is found in our system. Please select add new item if needed");
		
		}
	}*/
	
	@Override
	public void deleteProps(String deleteInfo)
	{
		searchProps(deleteInfo);
		getTable().remove(deleteInfo);
		saveProperties ();
		listProperties ();
	}
	

@Override	
	public void editProps(int fieldNum, String title, String newInfo)
	{
		loadProperties();
	
		String org = getTable().getProperty(title);
		StringBuilder str = new StringBuilder (org);
		System.out.print(fieldNum);
		if (fieldNum == 1)
		{
			
			str.delete(1, (getFieldOne(org).length()+1)); 
			str.insert(1, newInfo);
			String newStr = str.toString();
			
			getTable().remove(title);
			getTable().setProperty(getFieldOne(newStr),newStr);
			saveProperties();
			loadProperties();
			
			
		}
		else if (fieldNum == 2)
		{ 
			
			str.delete(getFieldOne(org).length()+2, getFieldOne(org).length()+getFieldTwo(org).length()+2);
			str.insert(getFieldOne(org).length()+2, newInfo);
			String newStr = str.toString();
			getTable().remove(title);
			getTable().setProperty(getFieldOne(newStr),newStr);
			saveProperties();
			loadProperties();
		}
		
		else if (fieldNum == 3)
		{	
			
			str.delete(getFieldOne(org).length()+getFieldTwo(org).length()+3, getFieldOne(org).length()+getFieldTwo(org).length()+getFieldThree(org).length()+3);
			str.insert(getFieldOne(org).length()+getFieldTwo(org).length()+3, newInfo);
			String newStr = str.toString();
			getTable().remove(title);
			getTable().setProperty(getFieldOne(newStr),newStr);
			saveProperties();
			loadProperties();
		}
		else if (fieldNum == 4)
		{	
			
			str.delete(getFieldOne(org).length()+getFieldTwo(org).length()+ getFieldThree(org).length()+4, getFieldOne(org).length()+getFieldTwo(org).length()+getFieldThree(org).length()+getFieldFour(org).length()+4);
			str.insert(getFieldOne(org).length()+getFieldTwo(org).length()+ getFieldThree(org).length()+4, newInfo);
			String newStr = str.toString();
			getTable().remove(title);
			getTable().setProperty(getFieldOne(newStr),newStr);
			saveProperties();
			loadProperties();
		}
		else if (fieldNum == 5)
		{	
			
			str.delete(getFieldOne(org).length()+getFieldTwo(org).length()+ getFieldThree(org).length()+ getFieldFour(org).length()+5, getFieldOne(org).length()+getFieldTwo(org).length()+getFieldThree(org).length()+getFieldFour(org).length()+getFieldFive(org).length()+5);
			str.insert(getFieldOne(org).length()+getFieldTwo(org).length()+ getFieldThree(org).length()+getFieldFour(org).length()+5, newInfo);
			String newStr = str.toString();
			getTable().remove(title);
			getTable().setProperty(getFieldOne(newStr),newStr);
			saveProperties();
			loadProperties();
		}
		else 
		{
			System.out.println("Your input is not recognized. Please try again.");
		}
		listProperties();
	}
	


//Helper methods -- save/load/list
	public void saveProperties()
	{
	
		try
		{
			FileOutputStream output = new FileOutputStream(getDataFile());
	    	
			getTable().store( output, getItemCat()); 
	        output.close();
	        System.out.println("Change has been saved.");
	        listProperties();
		}
		catch (IOException ioException)
		{
			ioException.printStackTrace();
		}
	}

	public void loadProperties()
	{
		try
		{
			FileInputStream Input = new FileInputStream(getDataFile());
			getTable().load(Input);
			Input.close();	
		}
		catch (IOException ioException)
		{
			ioException.printStackTrace();
		}
	}
	
	public void listProperties ()
	{
		System.out.println("Please see below for current listing...");
		
		Set<Object> keys = getTable().keySet();
		
		for (Object key: keys)
		
			System.out.printf("%s\t%s%n", key, getTable().getProperty((String)key));
		
			System.out.println();
		
	}

//Helper methods -- get individual field info
	public String getFieldOne(String userInput)
	{
		StringBuilder str = new StringBuilder(userInput);
		int fieldOne = str.indexOf("/",1);
		String str1 = str.substring(1,fieldOne);
		return str1;
	}
	
	public String getFieldTwo(String userInput)
	{
		StringBuilder str = new StringBuilder(userInput);
		int fieldOne = str.indexOf("/",1);
		int fieldTwo = userInput.indexOf("/", fieldOne+1);
		String str2 = str.substring(fieldOne+1,fieldTwo);
		return str2;
	}
	
	public String getFieldThree(String userInput)
	{
		StringBuilder str = new StringBuilder(userInput);
		int fieldOne = str.indexOf("/",1);
		int fieldTwo = userInput.indexOf("/", fieldOne+1);
		int fieldThree = userInput.indexOf("/",fieldTwo+1);
		String str3 = str.substring(fieldTwo+1,fieldThree);
		return str3;
	}
	
	public String getFieldFour(String userInput)
	{
		StringBuilder str = new StringBuilder(userInput);
		int fieldOne = str.indexOf("/",1);
		int fieldTwo = userInput.indexOf("/", fieldOne+1);
		int fieldThree = userInput.indexOf("/",fieldTwo+1);
		int fieldFour = userInput.indexOf("/",fieldThree+1);
		String str4 = str.substring(fieldThree+1,fieldFour);
		return str4;
	}
	
	public String getFieldFive(String userInput)
	{
		StringBuilder str = new StringBuilder(userInput);
		int fieldOne = str.indexOf("/",1);
		int fieldTwo = userInput.indexOf("/", fieldOne+1);
		int fieldThree = userInput.indexOf("/",fieldTwo+1);
		int fieldFour = userInput.indexOf("/",fieldThree+1);
		int fieldFive = userInput.indexOf("/", fieldFour+1);
		String str5 = str.substring(fieldFour+1,fieldFive);
		return str5;
	}
	
	
}

