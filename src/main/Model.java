package main;

import game.Ant;
import game.Timed;
import game.PheromoneMap;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import render.ColorSprite2D;
import render.Drawable;
import render.GridSprite2D;
import render.RectTextureSprite2D;
import util.MersenneTwister;
import util.Vector;

public class Model implements Runnable{
	private static final double TIME_STEP = 0.01;
	
	public static final Random random = new MersenneTwister();
	
	private boolean gameRunning = true;
	
	protected ArrayList<Drawable> drawableObjects;
	protected ArrayList<Timed>    timedObjects;
	
	private ArrayList<Ant> ants;
	private PheromoneMap pheromoneMap;
	
	public Model() {
		drawableObjects = new ArrayList<>();
		timedObjects = new ArrayList<>();
		ants = new ArrayList<>();
		
		// White background plane, and a test polygon
		ColorSprite2D demoSprite = new ColorSprite2D(new Vector(-200, -200),
				new Vector(400, 400), 0, Color.WHITE);
		drawableObjects.add(demoSprite);
		
		GridSprite2D grid = new GridSprite2D(new Vector(-200, -200), new Vector(400, 400), 40, 40, .5);
		drawableObjects.add(grid);

		PheromoneMap pheromoneMap = new PheromoneMap();
		pheromoneMap.placePheromone(new Vector(20,20),PheromoneMap.Pheromones.TRAIL, 10);
		drawableObjects.add(pheromoneMap);
		
		// Test ants
		for (int i = 0; i < 100; i++){
			Ant ant = new Ant(new Vector());
			ant.setPheromoneMap(pheromoneMap);
			ants.add(ant);
			drawableObjects.add(ant);
			timedObjects.add(ant);
		}

		
		
		RectTextureSprite2D texTest = new RectTextureSprite2D(new Vector(-20, -20), new Vector(50, 50), 3, "assets/textures/leaf1.png", "PNG");
		drawableObjects.add(texTest);
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
