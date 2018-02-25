
public interface InventorySystem 
{
	void getProps();
	String listProps();
	String addProps(String newInfo);
	String searchProps(String searchInfo);
	String deleteProps(String deleteInfo);
	String editProps(int fieldNum, String title, String newInfo);


}
