package main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import util.Vector;

public class Controller implements Runnable {
	private boolean gameRunning = true;
	private Model model;

	private Vector cameraPosition;
	private double cameraDistance;

	public Controller() {
		cameraPosition = new Vector(0, 0);
		cameraDistance = 100;
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
		}

		/*
		 * Clean up
		 */
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
}
