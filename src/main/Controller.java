package main;

public class Controller implements Runnable {
	private boolean gameRunning = true;
	private Model model;
	
	public Controller() {
		
	}
	
	public void setModel(Model model){
		this.model = model;
	}
	
	public void run() {
		while (gameRunning){
			
		}
		
		/*
		 * Clean up
		 */
	}
	
	public void endGame() {
		gameRunning = false;
	}
}
