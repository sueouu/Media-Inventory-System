
public class Main {
	public static void main(String[] args) throws ClassNotFoundException {
		ISModel model = new ISModel();
		ISView view = new ISView(model);
		
		view.setFrameProps();
		
	}

}
