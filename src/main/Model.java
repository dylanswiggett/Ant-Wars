package main;

import game.Ant;
import game.Timed;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import render.ColorSprite2D;
import render.Drawable;
import render.GridSprite2D;
import util.MersenneTwister;
import util.Vector;

public class Model implements Runnable{
	private static final double TIME_STEP = 0.01;
	
	public static final Random random = new MersenneTwister();
	
	private boolean gameRunning = true;
	
	protected ArrayList<Drawable> drawableObjects;
	protected ArrayList<Timed>    timedObjects;
	
	private ArrayList<Ant> ants;
	
	public Model() {
		drawableObjects = new ArrayList<>();
		timedObjects = new ArrayList<>();
		ants = new ArrayList<>();
		
		// White background plane, and a test polygon
		ColorSprite2D demoSprite = new ColorSprite2D(new Vector(-100, -100),
				new Vector(1000, 1000), 0, Color.WHITE);
		drawableObjects.add(demoSprite);
		
		GridSprite2D grid = new GridSprite2D(new Vector(-100, -100), new Vector(200, 200), 20, 20, .5);
		drawableObjects.add(grid);
		
		// Test ants
		for (int i = 0; i < 100; i++){
			Ant ant = new Ant(new Vector());
			ants.add(ant);
			drawableObjects.add(ant);
			timedObjects.add(ant);
		}
	}
	
	public void run() {
		while (gameRunning){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for (Timed timed : timedObjects){
				timed.step(TIME_STEP);
			}
		}
		
		/*
		 * Clean up
		 */
	}
	
	public void endGame(){
		gameRunning = false;
	}
}
