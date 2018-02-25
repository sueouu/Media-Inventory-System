
public class ISMain {

	public static void main(String[] args) 
	{
		
		ISModel model = new ISModel();
		ISController controller = new ISController(model);
		ISViewII view = new ISViewII(controller);
	
		model.setISView (view);
		view.setISModel(model);
		view.setFrameProps();
		
	}
}
