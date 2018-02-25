
public class ISMain {

	public static void main(String[] args) 
	{
		
		ISModel model = new ISModel();
		ISController controller = new ISController(model);
		ISView view = new ISView(controller);
	
		model.setISView (view);
		view.setISModel(model);
		view.itemSelection();
		
	}
}
