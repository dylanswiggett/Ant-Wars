package main;

import java.awt.Color;
import java.util.ArrayList;

import render.ColorSprite2D;
import render.Drawable;
import util.Vector;

public class Model implements Runnable{
	private boolean gameRunning = true;
	
	protected ArrayList<Drawable> drawableObjects;
	
	public Model() {
		drawableObjects = new ArrayList<>();
		
		ColorSprite2D demoSprite = new ColorSprite2D(new Vector(0, 0), new Vector(10, 10), 0, Color.WHITE);
		drawableObjects.add(demoSprite);
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
