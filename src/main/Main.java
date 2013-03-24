package main;

public class Main {
	
	// Pre-set values for now.
	private static final int WIDTH = 750;
	private static final int HEIGHT = 500;
	
	public static void main(String[] args){
		View view = new View(WIDTH, HEIGHT);
		Controller controller = new Controller();
		Model model = new Model();
		
		view.setModel(model);
		view.setController(controller);
		controller.setModel(model);
		
		new Thread(model).start();
		new Thread(controller).start();
		new Thread(view).start();
	}
}
