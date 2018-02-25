import java.util.Properties;

public class ISModel 
{
	private ISViewII view;
	//private int itemCatAsNumber;
	private int ISCatNum;
	private int fieldNum;
	private String uInput;
	private String uInput2;
	private String msg;
	
	private ISCat selectedCat;
	
	private enum ISOperations {ADD, SEARCH, DELETE, EDIT, DISPLAY};
	private ISOperations selectedOps; 
	
	private Properties bookProps = new Properties();
	private Properties cdProps = new Properties();
	private Properties dvdProps = new Properties();
	
	private Book book = new Book (bookProps, "Book-IS.dat", "Books");
	private Cd cd = new Cd(cdProps, "CD-IS.dat", "CDs");
	private Dvd dvd = new Dvd (dvdProps, "DVD-IS.dat", "DVDs");
	
	private InventoryMaintenance [] IMObjects = new InventoryMaintenance [] {book, cd, dvd};
	
	public ISModel(){};
	
	public void setCat(int c)
	{
		setISCatNum (c-1);
		
		if (c==1)
		{
			selectedCat = ISCat.BOOK;	
		}
		
		else if (c == 2)
		{
			selectedCat = ISCat.CD;
		}
		
		else if (c == 3)
		{
			selectedCat = ISCat.DVD;
		}
		
		view.modelInteraction(" "+ selectedCat);
		
	}

	public String getCat()
	{
		return selectedCat.getCatName().toString();
				
	}

	/*public void setItemCatAsNumber (int i)
	{
		itemCatAsNumber = i;
	}
	
	public int getItemCatAsNumber ()
	{
		return itemCatAsNumber;
	}*/
	public String[] getCatFieldArray()

	{
		return selectedCat.getCatFields();
	}
	
	public void setOps(int o)
	{
		if (o == 1)	
		{
			selectedOps = ISOperations.ADD;
			
		}
		else if (o == 2)
		{
			selectedOps = ISOperations.SEARCH;
		}
		else if (o == 3)
		{
				selectedOps = ISOperations.DELETE;  
			
		}
		else if(o == 4)
		{
			selectedOps = ISOperations.EDIT;
		}
		else if(o == 5)
		{
			selectedOps = ISOperations.DISPLAY;
		}
		
		view.modelInteraction(o);	
	}		
		
	

	public void setISCatNum(int s)
	{
		ISCatNum = s;
	}
	
	public int getISCatNum()
	{
		return ISCatNum;
	}
	
	public void setFieldNum(int f)
	{
		fieldNum = f;
	}
	
	public void setUserInput(String input)
	{
		uInput = input;
	}
	
	public void setUserInputTwo (String input2)
	{
		uInput2 = input2;
		
	}

	public int getFieldNum()
	{
		return fieldNum;
	}
	
	public String getUserInput()
	{
		return uInput;
	}

	public String getUserInputTwo ()
	{
		return uInput2;
	}

	public void setMsg(String m)
	{
		msg = m;
	}
	
	public String getMsg()
	{
		return msg;
	}
	//props methods
	
	public void propsActions ()
	{
		if (selectedOps == ISOperations.ADD)
		{	
			this.addProps(getUserInput());
		}
		
		else if (selectedOps == ISOperations.SEARCH)
		{
			this.searchProps(getUserInput());
		}
		
		else if (selectedOps == ISOperations.DELETE)
		{
			this.deleteProps(getUserInput());
		}
		
		else if (selectedOps == ISOperations.EDIT)
			
		{
			this.editProps(getFieldNum(), getUserInput(),getUserInputTwo());
		}
		
		else if (selectedOps == ISOperations.DISPLAY)
		{
			this.listProps();
		}
			
			
	}
	
	public void listProps()
	{
		String r = IMObjects[getISCatNum()].listProps();
		setMsg(r);
	}
	public void getProps()
	{
		IMObjects[getISCatNum()].getProps();
	}
	
	public void addProps(String newInfo)
	{
		String r = IMObjects[getISCatNum()].addProps(newInfo);
		setMsg(r);
	}
	
	public void searchProps(String searchInfo)
	{
		IMObjects[getISCatNum()].searchProps(searchInfo);
		String r = IMObjects[getISCatNum()].searchProps(searchInfo);
		setMsg(r);
	}
	
	public void deleteProps(String deleteInfo)
	{
		String r = IMObjects[getISCatNum()].deleteProps(deleteInfo);
		setMsg(r);
	}
	
	public void editProps(int fieldNum, String title, String newInfo)
	{
		String r = IMObjects[getISCatNum()].editProps(fieldNum, title, newInfo);
		setMsg (r);
	}

	//setView for ISMain
	public void setISView (ISViewII view)
	{
		this.view = view;
	}
}

