import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class ISView extends JFrame{


	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/inventorysystem";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Sjou12344321*";
	
	private static final String BOOK_QUERY = "SELECT * FROM books";
	private static final String CD_QUERY = "SELECT * FROM cds";
	private static final String DVD_QUERY = "SELECT * FROM dvds";
	
	private ISModel tableModel;
	private ISModel iSmodel;
	
	
	private static final String [] cNames = {"BOOK", "CD", "DVD"};
	private static final String [] qNames = {BOOK_QUERY, CD_QUERY, DVD_QUERY};
	
	
	
	public ISView (ISModel iSmodel) throws ClassNotFoundException
	{
		super("Inventory System");
		this.iSmodel = iSmodel;
		
		try
		{
			tableModel = new ISModel(DATABASE_URL, USERNAME, PASSWORD, BOOK_QUERY);
			
			JPanel comPanel = new JPanel();
			comPanel.setLayout(new BorderLayout());
			
			
			JPanel welPanel = new JPanel();
			welPanel.setBackground(new Color (116,176,245));
			TitledBorder welBorder = new TitledBorder("Read-Me");
			welBorder.setTitleJustification(TitledBorder.CENTER);
			welBorder.setTitlePosition(TitledBorder.TOP);
			welPanel.setBorder(welBorder);
			
			JTextArea welcome = new JTextArea ("____Welcome!  Instructions below____"
					+"\n 1. Select Category from Step One to see current inventory for corresponding category"
					+"\n 2. Right Click on Table values to add, delete, or edit an entry.  When editing, first select a specific cell before right-clicking table.  Item_ID column is not editable"
					+"\n 3. Click on column names to sort table values"
					+"\n 4. Use Filter function to search an entry"
					+"\n 5. Close window to exit Inventory System ");
			
			welcome.setEditable(false);
			welcome.setOpaque(false);
			welcome.setFont(new Font("sansserif", Font.BOLD, 11));
			welPanel.add(welcome);
			
			comPanel.add(welPanel, BorderLayout.NORTH);
			
			//catpanel
			JPanel catPanel = new JPanel();
			//catPanel.setLayout(new GridLayout(2,1));
			catPanel.setLayout(new BorderLayout());
			catPanel.setBackground(new Color (181,230,124));
			TitledBorder catBorder = new TitledBorder("Step One");
			catBorder.setTitleJustification(TitledBorder.CENTER);
			catBorder.setTitlePosition(TitledBorder.TOP);
			
			catPanel.setBorder(catBorder);
			
			JLabel type = new JLabel("  Choose Item Category  ");
			//type.setFocusable(false);
			//type.setOpaque(false);
			catPanel.add(type,BorderLayout.NORTH);
			
			JList catList = new JList(cNames);
			catList.setVisibleRowCount(3);
			catList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			catPanel.add( new JScrollPane(catList));
			
			comPanel.add(catPanel, BorderLayout.SOUTH);
			add(comPanel, BorderLayout.NORTH);
			
			
			catList.addListSelectionListener(
					new ListSelectionListener()
					{
						@Override
						public void valueChanged(ListSelectionEvent event)
						{
							try
							{
								
								tableModel.setQuery(qNames[catList.getSelectedIndex()]);
							}
							catch (SQLException sqlException)
							{
								JOptionPane.showMessageDialog(null, 
										sqlException.getMessage(), "Database error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
					);
			
			
			JPanel opsPanel = new JPanel();
			opsPanel.setLayout(new BorderLayout());
			opsPanel.setBackground(new Color(143,182,98));
			TitledBorder opsBorder = new TitledBorder("Additional Action");
			opsBorder.setTitleJustification(TitledBorder.CENTER);
			opsBorder.setTitlePosition(TitledBorder.TOP);

			opsPanel.setBorder(opsBorder);

			JLabel ops = new JLabel("   Enter full value or partial value to filter (case insensitive)  ");
			//ops.setFocusable(false);
			ops.setOpaque(false);
			opsPanel.add(ops,BorderLayout.NORTH);
			
			JTable resultTable = new JTable(tableModel)
			{
				@Override
				public Component prepareRenderer(TableCellRenderer renderer, int row, int column) 
				{
					Component comp = super.prepareRenderer(renderer, row, column);
					comp.setBackground(row % 2 == 0 ? Color.lightGray: Color.white);
					return comp;
				}
			};
			
			JPopupMenu popMenu = new JPopupMenu();
			JMenuItem Add = new JMenuItem("Add Entry");
			JMenuItem Delete = new JMenuItem("Delete Entry");
			JMenuItem Edit = new JMenuItem("Edit Cell");
			
			popMenu.add(Add);
			popMenu.add(Delete);
			popMenu.add(Edit);
			
			resultTable.setComponentPopupMenu(popMenu);
			resultTable.addMouseListener(new TableMouseListener(resultTable));
			
			JLabel filterLabel = new JLabel("Filter/Search:");
			final JTextField filterText = new JTextField();
			JButton filterButton = new JButton("Apply Filter");
			Box boxSouth = Box.createHorizontalBox();
			
			boxSouth.add(filterLabel);
			boxSouth.add(filterText);
			boxSouth.add(filterButton);
			
			opsPanel.add(boxSouth, BorderLayout.SOUTH);
			
			add(new JScrollPane(resultTable), BorderLayout.CENTER);
			
			add(opsPanel, BorderLayout.SOUTH);
			
			
			final TableRowSorter<TableModel>sorter =
					new TableRowSorter<TableModel>(tableModel);
			resultTable.setRowSorter(sorter);
			
			filterButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						String text = filterText.getText();
		                  if (text.length() == 0)
		                  {
		                	  sorter.setRowFilter(null);
		                  }
		                  else
		                  {
		                	  try
		                      {
		                		  sorter.setRowFilter( 
		                				  RowFilter.regexFilter("(?i)" + text));
		                      }
		                	  catch(PatternSyntaxException pse)
		                	  {
		                		  JOptionPane.showMessageDialog( null,
		                                  "Bad regex pattern", "Bad regex pattern",
		                                  JOptionPane.ERROR_MESSAGE);
		                	  }
		                	  
		                		
		                  }
		                	
					}
				}
						
			);
			
			
			Add.addActionListener(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event)
					{
						JTextField field1 = new JTextField();
						JTextField field2 = new JTextField();
						JTextField field3 = new JTextField();
						JTextField field5 = new JTextField();
						JTextField field4 = new JTextField();
						String value1 = "";
						String value2 = "";
						String value3 = "";
						String value4 = "";
						String value5 = "";
							
						Object [] message = 
						{
								tableModel.getColumnName(1) + ": ", field1,
								tableModel.getColumnName(2)+ ": ", field2,
								tableModel.getColumnName(3)+ "[INT only]: ", field3,
								tableModel.getColumnName(4)+ "[INT only]: ", field4,
								tableModel.getColumnName(5)+ "[INT only]: ", field5,
								
						};
						int option = JOptionPane.showConfirmDialog(null, message, "Please enter input below", JOptionPane.OK_CANCEL_OPTION); 
						if (option == JOptionPane.OK_OPTION)
						{
						    value1 = field1.getText();
						    value2 = field2.getText();
						    value3 = field3.getText();
						    value4 = field4.getText();
						    value5 = field5.getText();
						}
							
						try
						{
							tableModel.addProps(value1, value2, value3, value4, value5);
						}
						catch (SQLException sqlException1)
						{
							JOptionPane.showMessageDialog(null, 
									sqlException1.getMessage(), "Database error or invalid input!",
									JOptionPane.ERROR_MESSAGE);
								
							try
							{
								tableModel.setQuery(BOOK_QUERY);
							}
							catch (SQLException sqlException2)
							{
								JOptionPane.showMessageDialog(null, 
										sqlException2.getMessage(), "Database error or invalid input!",
										JOptionPane.ERROR_MESSAGE);
								tableModel.disconnectFromDatabase();
								System.exit(1);
							}
						}
					}
						
				}
					
			);
			
			Delete.addActionListener(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event)
					{
						try
						{
							if(resultTable.getSelectedRow() !=-1)
							{
								tableModel.deleteProps();
							}
							else
							{
								JOptionPane.showMessageDialog(null,  "A row needs to be seleted in order for entry removal.");
								return;
							}
						}
						catch(SQLException sqlException1)
						{
							JOptionPane.showMessageDialog(null, 
									sqlException1.getMessage(), "Database error",
									JOptionPane.ERROR_MESSAGE);
							
							try
							{
								tableModel.setQuery(BOOK_QUERY);
							}
							catch (SQLException sqlException2)
							{
								JOptionPane.showMessageDialog(null, 
										sqlException2.getMessage(), "Database error or invalid input!",
										JOptionPane.ERROR_MESSAGE);
								tableModel.disconnectFromDatabase();
								System.exit(1);
							}
						}
					}
				}
				
			);
			
			
			Edit.addActionListener(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent event)
						{
							int column = resultTable.convertColumnIndexToModel(resultTable.getSelectedColumn());
							int row = resultTable.convertRowIndexToModel(resultTable.getSelectedRow());
							
							if(tableModel.isCellEditable(row, column) == false)
							{
								JOptionPane.showMessageDialog(null, "Please select a cell other than 'ItemID' to edit entry");
								return;
							}
							try
							{
								String newInfo = JOptionPane.showInputDialog("Please enter new info for your selection");
								
								tableModel.editProps(row, column, newInfo);
							}
							catch(SQLException sqlException1)
							{
								JOptionPane.showMessageDialog(null, 
										sqlException1.getMessage(), "Database error",
										JOptionPane.ERROR_MESSAGE);
								
								try
								{
									tableModel.setQuery(BOOK_QUERY);
								}
								catch (SQLException sqlException2)
								{
									JOptionPane.showMessageDialog(null, 
											sqlException2.getMessage(), "Database error or invalid input!",
											JOptionPane.ERROR_MESSAGE);
									tableModel.disconnectFromDatabase();
									System.exit(1);
								}
							}
							
						}
						
					}
						
				);
		}
		catch (SQLException sqlException)
		{
			JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
			tableModel.disconnectFromDatabase();
			System.exit(1);
		}
		
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		addWindowListener(
			new WindowAdapter()
			{
				public void windowClosed(WindowEvent event)
				{
					tableModel.disconnectFromDatabase();
					System.exit(0);
				}
			}
		);
		
	}
	
	public void setFrameProps ()
	{
		getContentPane().setBackground(new Color (223, 243, 200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension sz = tk.getScreenSize();
		int sh = sz.height;
		int sw = sz.width;
		setSize (950, 450);
		setLocation(sw/4,sh/4);
		setVisible(true);		
	}
}

class TableMouseListener extends MouseAdapter
{
	private JTable table;
	
	public TableMouseListener(JTable table) {
        this.table = table;
    }
     
    @Override
    public void mousePressed(MouseEvent event) {
 
        Point point = event.getPoint();
        int currentRow = table.rowAtPoint(point);
        table.setRowSelectionInterval(currentRow, currentRow);
    }
}


