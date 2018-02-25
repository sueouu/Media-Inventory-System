import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;


public class ISModel extends AbstractTableModel {

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numberOfRows;
	
	
	
	private boolean connectedToDatabase = false;
	 
	 public ISModel( String url, String username, String password, String query) throws SQLException
	 {
		 connection = DriverManager.getConnection(url, username, password);
		 statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		 connectedToDatabase = true;
		 
		 setQuery(query); 
	 }
		 
	 public ISModel() {

	}

	public Class getColumnClass(int column) throws IllegalStateException
	 {
		 if (!connectedToDatabase) 
			 throw new IllegalStateException("Not Connected to Database");
			 
		 try 
		 {
			 String className = metaData.getColumnClassName(column + 1);
			 return Class.forName(className);
		 }
		 catch (Exception exception)
		 {
			 exception.printStackTrace();
		 }
			 
		 return Object.class;
			 
		 }
		 
	 public int getColumnCount() throws IllegalStateException
	 {
		 if (!connectedToDatabase) 
			 throw new IllegalStateException("Not Connected to Database");
			 
		 try 
		 {
			 return metaData.getColumnCount();  
		 }
		 catch (SQLException sqlException)
		 {
			 sqlException.printStackTrace();
		 }
			 
		 return 0;
	 }
		 
	 public String getColumnName(int column) throws IllegalStateException
	 {
		 if (!connectedToDatabase) 
			 throw new IllegalStateException("Not Connected to Database");
		 
		 try
		 {
			 return metaData.getColumnName(column + 1);
		 }
		 catch(SQLException sqlException)
		 {
			 sqlException.printStackTrace();
		 }
			 
		 return "";
		 
	 }
		 
	 public int getRowCount() throws IllegalStateException
	 {
		 if (!connectedToDatabase) 
			 throw new IllegalStateException("Not Connected to Database");
		 
		 return numberOfRows;
	 }
		 
	 public Object getValueAt(int row, int column) throws IllegalStateException
	 {
		 if (!connectedToDatabase ) 
			 throw new IllegalStateException( "Not Connected to Database");
		 
		 try
		 {
			 resultSet.absolute(row + 1);
			 return resultSet.getObject(column+1);
		 }
		 catch(SQLException sqlException)
		 {
			 sqlException.printStackTrace();
		 }
			 
		 return "";
	 }
	 
	 
	 public void setValueAt(Object value, int row, int column)
	 {
		 if(!connectedToDatabase)
		 throw new IllegalStateException ("not Connected to Database");
		 
		 try
		 {
			 resultSet.absolute(row);
			 resultSet.updateObject(column, value);

		 }
		 catch (SQLException sqlException)
		 {
			 sqlException.printStackTrace();
		 }
	 }
		 
	 public void setQuery(String query)throws SQLException, IllegalStateException
	 {
		 if (!connectedToDatabase) 
			 throw new IllegalStateException("Not Connected to Database"); 
		 
		 resultSet = statement.executeQuery(query);
		 metaData = resultSet.getMetaData();
		 resultSet.last();
		 numberOfRows = resultSet.getRow();
		 fireTableStructureChanged();
	 }
	 
	 
	 public void addProps(String s1, String s2, String s3, String s4, String s5) throws SQLException
	 {
		resultSet.moveToInsertRow();
		resultSet.updateString(2, s1);
		resultSet.updateString(3, s2);
		resultSet.updateString(4, s3);
		resultSet.updateString(5, s4);
		resultSet.updateString(6, s5);
			 
		resultSet.insertRow();
			
		metaData = resultSet.getMetaData();
		resultSet.last();
		numberOfRows = resultSet.getRow();
		fireTableStructureChanged();
		 
		
	 }
	 
	 public void deleteProps()throws SQLException
	 {
		 
		 resultSet.getRow();
		 resultSet.deleteRow();
			
		 metaData = resultSet.getMetaData();
		 resultSet.last();
		 numberOfRows = resultSet.getRow();
		 fireTableStructureChanged();
		 
	 }
	 
	
	 public void editProps(int row, int column, String newInfo) throws SQLException
	 {
		 
		 resultSet.updateString(column+1, newInfo);
		 resultSet.updateRow();
		 metaData = resultSet.getMetaData();
		 resultSet.last();
		 numberOfRows = resultSet.getRow();
		 fireTableStructureChanged();
	 }

	 public boolean isCellEditable (int row, int column)
	 {
		 if (column < 1)
		 {
			 return false;
		 }
		 else
		 {
			 return true;
		 }
	 }
	 
	 public void disconnectFromDatabase()
	 {
		 if (connectedToDatabase)
		 {
			 try
			 {
				 resultSet.close();                        
				 statement.close();                        
				 connection.close(); 
			 }
			 catch (SQLException sqlException)
			 {
				 sqlException.printStackTrace();
			 }
			 finally
			 {
				 connectedToDatabase = false;
			 }
		 }
		
	 }

}
