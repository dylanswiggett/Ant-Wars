package main;

public class Model implements Runnable{
	private boolean gameRunning = true;
	
	public Model() {
		
	}
	
	public void run() {
		while (gameRunning){
			
		}
		
		/*
		 * Clean up
		 */
	}
	
	public void endGame(){
		gameRunning = false;
	}
}
