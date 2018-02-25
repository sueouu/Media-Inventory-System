import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ISViewII extends JFrame{


	//private static final long serialVersionUID = 1L;
	private ISController controller;
	private ISModel model;
	
	private JPanel welPanel;
	private JPanel catPanel;
	private JPanel opsPanel;
	private JPanel disPanel;
	
	
	private JLabel type;
	private JLabel ops;
	private JTextArea welcome;
	private JTextArea disResult;
	
	private final JList<String> catList;
	private final JList<String> opsList;


	private static final String [] cNames = {"BOOK", "CD", "DVD"};
	private static final String [] oNames = {"Add Item", "Search Item", "Delete Item", "Edit Item", "Display List"};
	private String [] fieldListArray;
	
	private String cat;
	private String msg;
	private String userEntry;
	private String userEntry2;
	private String userEntryField;
	
	private int userAction;
	
	public ISViewII (ISController controller)
	{
		super("Inventory System ");
		this.controller=controller;
		setLayout (new GridLayout(2,1,10,10));
		
		
		
		welPanel = new JPanel();
		welPanel.setLayout(new FlowLayout());
		welPanel.setBackground(new Color (116,176,245));
		TitledBorder welBorder = new TitledBorder("Start Menu");
		welBorder.setTitleJustification(TitledBorder.CENTER);
		welBorder.setTitlePosition(TitledBorder.TOP);
		
		welPanel.setBorder(welBorder);
		
		GridBagConstraints w = new GridBagConstraints();
		w.fill = GridBagConstraints.HORIZONTAL;
		
		add(welPanel,w);
		
		welcome = new JTextArea("Welcome!  Instructions below... " 
				+ "\n*Select Category from Step One to see Available Actions"
				+ "\n*Close window to exit Inventory System " + "\n*Enjoy!");
		
		welcome.setEditable(false);
		welcome.setOpaque(false);
		welcome.setFont(new Font("sansserif", Font.BOLD, 11));
		welPanel.add(welcome);
		welPanel.setVisible(true);
		
				
		//catpanel
		catPanel = new JPanel();
		catPanel.setLayout(new BorderLayout());
		catPanel.setBackground(new Color (181,230,124));
		TitledBorder catBorder = new TitledBorder("Step One");
		catBorder.setTitleJustification(TitledBorder.CENTER);
		catBorder.setTitlePosition(TitledBorder.TOP);
		
		catPanel.setBorder(catBorder);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		add(catPanel,c);
		
		
		type = new JLabel("  Choose Item Category  ");
		//type.setFocusable(false);
		//type.setOpaque(false);
		catPanel.add(type,BorderLayout.NORTH);
		
		catList = new JList<String>(cNames);
		catList.setVisibleRowCount(3);
		catList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		CatSelectionHandler catSelectionHanlder = new CatSelectionHandler ();
		catList.addListSelectionListener(catSelectionHanlder);
		
		catPanel.add( new JScrollPane( catList ) );
		catPanel.setVisible(true);
		

		//Ops Panel
		
		opsPanel = new JPanel();
		opsPanel.setLayout(new BorderLayout());
		opsPanel.setBackground(new Color(143,182,98));
		TitledBorder opsBorder = new TitledBorder("Step Two");
		opsBorder.setTitleJustification(TitledBorder.CENTER);
		opsBorder.setTitlePosition(TitledBorder.TOP);

		opsPanel.setBorder(opsBorder);
		
		opsPanel.setVisible(false);
		
		GridBagConstraints oo = new GridBagConstraints();
		oo.fill = GridBagConstraints.HORIZONTAL;
		add(opsPanel,oo);
		
		ops = new JLabel("   Choose Operation Type   ");
		//ops.setFocusable(false);
		ops.setOpaque(false);
		opsPanel.add(ops,BorderLayout.NORTH);
		
		opsList = new JList<String>(oNames);
		opsList.setVisibleRowCount(5);
		opsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		OpsSelectionHandler opsSelectionHanlder = new OpsSelectionHandler ();
		opsList.addListSelectionListener(opsSelectionHanlder);
		
		opsPanel.add( new JScrollPane( opsList ) );
		
		
	//display panel
		disPanel = new JPanel();
		disPanel.setLayout(new BorderLayout());
		/*TitledBorder disBorder = new TitledBorder("Current Inventory");
		disBorder.setTitleJustification(TitledBorder.CENTER);
		disBorder.setTitlePosition(TitledBorder.TOP);
		disPanel.setBackground(Color.ORANGE);*/
		GridBagConstraints d = new GridBagConstraints();
		d.fill = GridBagConstraints.HORIZONTAL;		
		
		add(disPanel, d);
		
		disPanel.setVisible(false);
		
		disResult = new JTextArea();
		disResult.setEditable(false);
		disResult.setOpaque(true);
		//disPanel.add(disResult);
		JScrollPane disScrollPane = new JScrollPane(disResult);
		disScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		disPanel.add(disScrollPane);
		
		
	}
	

	private class CatSelectionHandler implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent event) 
		{
			opsList.clearSelection();
			int s = catList.getSelectedIndex();
			
			if (s == 0)
			{
				controller.setMCat(1);
			}
			
			if (s == 1)
			{
				controller.setMCat(2);
			}
			
			if (s == 2)
			{
				controller.setMCat(3);
			}
			
			opsPanel.setVisible(true);	
		}
			
	}
	
	private class OpsSelectionHandler implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent event) 
		{
			disPanel.setVisible(true);
			int op = opsList.getSelectedIndex();
			
			String add = ("Please provide the following "+ cat + "info to add: \n" + getFields() + "\nUse '/' to seperate fields.  Ex: Title/Author/...");
			String search = ("Please provide the title of the " + cat + " to search");
			String delete = ("Please provide the title of the " + cat + " to delete");
			String edit1 = ("Please provide the title of the " + cat + " to edit");
			String edit2 = ("Please provide the field number (1 to 5) to edit..." + "\nEx: Enter '1' for field one");
			
			if (op == 0)
			{
				setOps(1,add);
				opsList.clearSelection();
				disPanel.setVisible(false);
			}
			
			else if (op ==1)
			{
				setOps(2, search);
				opsList.clearSelection();
				disPanel.setVisible(false);
			}
			
			else if (op == 2)
			{
				setOps (3, delete);	
				opsList.clearSelection();
			}
			
			else if (op == 3)
			{
				setOps(5);
				setOps (4, edit1, edit2);
				opsList.clearSelection();
			}
			
			else if (op == 4)
			{
				setOps(5);
				opsList.clearSelection();
			}
			
			
		}
	}
	
	private void setOps (int i)
	{
		userAction = i;
		controller.setMOps(userAction);
		disResult.setText(msg);
	}
	
	
	private void setOps (int i, String s)
	{
		userAction = i;
		userEntry = JOptionPane.showInputDialog(s);
		controller.setMOps(userEntry, i);
		JOptionPane.showMessageDialog(null, msg);
		
	}
	
	private void setOps (int i, String s, String s2)
	{
		userAction = i;
		userEntry = JOptionPane.showInputDialog(s);
		userEntryField = JOptionPane.showInputDialog(s2);
		int userEntryFieldNum = Integer.parseInt(userEntryField);
		userEntry2 = JOptionPane.showInputDialog("Please provide new info for replacement for field " + userEntryFieldNum);
		
		controller.setMOps(userEntry, userEntry2, i, userEntryFieldNum);
		
		disResult.setText(msg);
	}
	
	public String getFields()
	{
		String fields = " ";

		for (int i = 0; i < 5; i++)
		{
			fields = fields + fieldListArray[i] + ",";
		}
	
		return fields;
	}
	
	public void setFrameProps ()
	{
		getContentPane().setBackground(new Color (223, 243, 200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension sz = tk.getScreenSize();
		int sh = sz.height;
		int sw = sz.width;
		setSize (sw/2, sh/2);
		setLocation(sw/4,sw/4);
		setVisible(true);		
	}
	
	public void setISModel(ISModel model)
	{
		this.model= model;
	}
	
	public void modelInteraction(int selectedAction)
	{
		model.propsActions();
		msg = model.getMsg();
	}
	
	
	public void modelInteraction(String info)
	{
		model.getProps();
		cat = model.getCat();
		fieldListArray = model.getCatFieldArray();		
	}
	
}
