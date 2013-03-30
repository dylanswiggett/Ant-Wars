package main;

import game.Pheromone;
import game.PheromoneMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import util.Vector;
import util.Vector3D;

public class Controller implements Runnable {
	private boolean gameRunning = true;
	private Model model;

	private Vector cameraPosition;
	private double cameraDistance;
	
	private float FOV;

	public Controller() {
		cameraPosition = new Vector(0, 0);
		cameraDistance = 100;
		FOV = 45.0f;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	

	public void run() {
		while (!Keyboard.isCreated());
		
		while (gameRunning) {
			/*
			 * Camera Controls
			 */
			cameraDistance += Mouse.getDWheel() * cameraDistance / 1000;
			if (cameraDistance < 20)
				cameraDistance = 20;
			if (cameraDistance > 50000)
				cameraDistance = 50000;
			
			double cameraSpeed = cameraDistance / 10000000.0;
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				cameraPosition.addInPlace(new Vector(-cameraSpeed, 0));
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				cameraPosition.addInPlace(new Vector(cameraSpeed, 0));
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				cameraPosition.addInPlace(new Vector(0, -cameraSpeed));
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				cameraPosition.addInPlace(new Vector(0, cameraSpeed));
			
			if (Mouse.isButtonDown(0)){
				synchronized (model.testMap) {
					Vector mousePosition = pickPointOnScreen(new Vector(Mouse.getX(), Mouse.getY()));
					model.testMap.addPheromone(Pheromone.TRAIL, mousePosition.x, mousePosition.y, 100);
				}
			}
		}

		/*
		 * Clean up
		 */
	}
	
	private Vector pickPointOnScreen(Vector screenPixel) {
		Vector3D cameraForwards = new Vector3D(0, 0, -1);
		Vector3D cameraRight = new Vector3D(1, 0, 0);
		Vector3D cameraUp = new Vector3D(0, 1, 0);

		double screenX = screenPixel.x - Display.getWidth() / 2;
		double screenY = screenPixel.y - Display.getHeight() / 2;

		Vector3D screenVector = cameraForwards
				.scale((float) (Display.getHeight() / (2 * Math.tan(FOV / 360 * Math.PI))))
				.add(cameraRight.scale((float) screenX))
				.add(cameraUp.scale((float) screenY));

		double distScale = cameraDistance / screenVector.getZ();
		screenVector = screenVector.scale(-(float) distScale);
		return cameraPosition.plus(new Vector(screenVector.getX(),
				screenVector.getY()));
	}

	public void endGame() {
		gameRunning = false;
	}

	public Vector getCameraPosition() {
		return cameraPosition;
	}

	public double getCameraDistance() {
		return cameraDistance;
	}
	
	public float getFOV() {
		return FOV;
	}
}
