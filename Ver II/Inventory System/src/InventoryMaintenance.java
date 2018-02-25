import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.lang.StringBuilder;


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
	public String listProps()
	{
		return listProperties();
	}
	
	@Override
	public String addProps (String newInfo)
	{
		loadProperties();
		String msg;
		
		String stringToAdd = "/" + newInfo + "/";
		
		Object addValue = getTable().getProperty(newInfo);
		if (addValue != null)
		{
			msg = ("The item you want to add is already in system. /nSelelct Display List to see current inventory." );
			//listProperties();
			//System.out.println(addValue);
		}	
		else if (countLash(newInfo) < 6)
		{
			msg = ("Must provide info on all the fields. if a field is unkonwn. use TBD as a temp solution.");
		}
		else 
		{
		getTable().setProperty(getFieldOne(stringToAdd),stringToAdd);
		saveProperties();
		
		msg = (newInfo + " has been added\n");
		
		listProperties();
		}
		return msg;	
	
		
	}
	
	public int countLash(String newInfo)
	{
		int count = 0;
		int index = 0;
		String strToLookFor = "/";
		String stringToAdd = "/" + newInfo + "/";
		while ((index = stringToAdd.indexOf(strToLookFor,index)) != -1)
		{
			index ++;
			count ++;
		}
		
		return count;
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
	public String deleteProps(String deleteInfo)
	{
		//searchProps(deleteInfo);
		String msg;
		Object delValue = getTable().getProperty(deleteInfo);
		if (delValue == null)
		{
			msg = (deleteInfo + " is not in the current inventory.");
			saveProperties ();
	
		}	
		else
		{	
			getTable().remove(deleteInfo);
			saveProperties ();
			msg = (deleteInfo + " has been deleted..\n");

		}
	
		return msg;
	}
	

@Override	
	public String editProps(int fieldNum, String title, String newInfo)
	{
		loadProperties();
		String msg;
		String org = getTable().getProperty(title);
		StringBuilder str = new StringBuilder (org);
		//System.out.print(fieldNum);
		if (fieldNum == 1)
		{
			
			str.delete(1, (getFieldOne(org).length()+1)); 
			str.insert(1, newInfo);
			String newStr = str.toString();
			
			getTable().remove(title);
			getTable().setProperty(getFieldOne(newStr),newStr);
			saveProperties();
			msg = ("\nInventory updated!" + listProperties());
			
			
		}
		else if (fieldNum == 2)
		{ 
			
			str.delete(getFieldOne(org).length()+2, getFieldOne(org).length()+getFieldTwo(org).length()+2);
			str.insert(getFieldOne(org).length()+2, newInfo);
			String newStr = str.toString();
			getTable().remove(title);
			getTable().setProperty(getFieldOne(newStr),newStr);
			saveProperties();
			msg = ("\nInventory updated!" + listProperties());
		}
		
		else if (fieldNum == 3)
		{	
			
			str.delete(getFieldOne(org).length()+getFieldTwo(org).length()+3, getFieldOne(org).length()+getFieldTwo(org).length()+getFieldThree(org).length()+3);
			str.insert(getFieldOne(org).length()+getFieldTwo(org).length()+3, newInfo);
			String newStr = str.toString();
			getTable().remove(title);
			getTable().setProperty(getFieldOne(newStr),newStr);
			saveProperties();
			msg = ("\nInventory updated!" + listProperties());
		}
		else if (fieldNum == 4)
		{	
			
			str.delete(getFieldOne(org).length()+getFieldTwo(org).length()+ getFieldThree(org).length()+4, getFieldOne(org).length()+getFieldTwo(org).length()+getFieldThree(org).length()+getFieldFour(org).length()+4);
			str.insert(getFieldOne(org).length()+getFieldTwo(org).length()+ getFieldThree(org).length()+4, newInfo);
			String newStr = str.toString();
			getTable().remove(title);
			getTable().setProperty(getFieldOne(newStr),newStr);
			saveProperties();
			msg = ("\nInventory updated!" + listProperties());
		}
		else if (fieldNum == 5)
		{	
			
			str.delete(getFieldOne(org).length()+getFieldTwo(org).length()+ getFieldThree(org).length()+ getFieldFour(org).length()+5, getFieldOne(org).length()+getFieldTwo(org).length()+getFieldThree(org).length()+getFieldFour(org).length()+getFieldFive(org).length()+5);
			str.insert(getFieldOne(org).length()+getFieldTwo(org).length()+ getFieldThree(org).length()+getFieldFour(org).length()+5, newInfo);
			String newStr = str.toString();
			getTable().remove(title);
			getTable().setProperty(getFieldOne(newStr),newStr);
			saveProperties();
			msg = ("\nInventory updated!" + listProperties());
		}
		else 
		{
			msg =("Your input is not recognized. Please try again.");
		}
		
		return msg;
	}
	


//Helper methods -- save/load/list
	public void saveProperties()
	{
	
		try
		{
			FileOutputStream output = new FileOutputStream(getDataFile());
	    	
			getTable().store( output, getItemCat()); 
	        output.close();
	        //System.out.println("Change has been saved.");
	        //listProperties();
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
	
	public String listProperties ()
	{
		//System.out.println("Please see below for current listing...");
		
		Set<Object> keys = getTable().keySet();
		
		List <String> propsList = new ArrayList<String>();
		
		for (Object key: keys)
		{
			propsList.add("\n" + key + "\t" + props.getProperty((String)key));
			
			//System.out.printf("%s\t%s%n", key, getTable().getProperty((String)key));
		
			//System.out.println();
		}
		
		String strList = "\n===== Current Inventory =====" + listToDisplay(propsList) + "\n";
		return strList;
	}

	public String listToDisplay (List <String> list)
	{
		String r = "";
		ListIterator <String> it = list.listIterator(list.size());
		while (it.hasPrevious())
		{
			String str = (it.previous());
			r = r + str;
		}
		return r;
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

