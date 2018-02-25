
public enum ISCat {
	BOOK ("book", "title", "author", "price", "year", "rating"),
	CD ("CD", "title", "artist", "price", "year", "rating"),
	DVD("DVD","title", "director", "price", "year", "rating");
	
	private final String catName;
	private final String fieldOne; 
	private final String fieldTwo;
	private final String fieldThree;
	private final String fieldFour; 
	private final String fieldFive;
	//private String catFields;

	ISCat(String catName, String fieldOne, String fieldTwo, String fieldThree, String fieldFour, String fieldFive)
	{
		this.catName = catName;
		this.fieldOne = fieldOne;
		this.fieldTwo = fieldTwo;
		this.fieldThree = fieldThree;
		this.fieldFour = fieldFour;
		this.fieldFive = fieldFive;
	}

	public String getCatName()
	{
		return catName;
	}
	
	public String[] getCatFields()
	{
		String []catFields = {fieldOne, fieldTwo, fieldThree, fieldFour, fieldFive};
		return catFields;
	}
}

