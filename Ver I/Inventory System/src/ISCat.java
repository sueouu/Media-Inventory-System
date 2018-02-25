
public enum ISCat {
	BOOK ("book", "title, author, price, year, rating"),
	CD ("CD", "title, artist, price, year, rating"),
	DVD("DVD","title, director, price, year, rating");
	
	private String catName;
	private String catFields;

	ISCat(String catName, String catFields)
	{
		this.catName = catName;
		this.catFields = catFields;
	}

	public String getCatName()
	{
		return catName;
	}
	
	public String getCatFields()
	{
		return catFields;
	}
}

