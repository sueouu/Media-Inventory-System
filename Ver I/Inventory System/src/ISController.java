
public class ISController {

	private ISModel model;
	
	public ISController(ISModel model) 
	{
		this.model=model;
	}


	public void setMCat (int c)
	{
		model.setCat(c);
	}
	
	public void setUserInput (String input)
	{
		if (model.getUserInput()!= input)
		{
			model.setUserInput(input);
		}
	}
	
	public void setMOps(int i)
	{
		model.setOps(i);
	}
	
	public void setMOps(String str, int i) 
	{
		model.setUserInput(str);
		model.setOps(i);
	}
	
	public void setMOps (String str, String str2, int o, int userEntryFieldNum)
	{
		model.setUserInput(str);
		model.setUserInputTwo(str2);
		model.setFieldNum(userEntryFieldNum);
		model.setOps(o);
	}
	
}
