package main;

import game.Ant;
import game.PheromoneMap;
import game.PheromoneMap2;
import game.StaticMap;
import game.Timed;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import render.ColorSprite2D;
import render.Drawable;
import render.GridSprite2D;
import render.RectTextureSprite2D;
import util.MersenneTwister;
import util.RTree;
import util.Vector;
import util.Positioned;
import util.SpatialAlgorithm;

public class Model implements Runnable{
	private static final double TIME_STEP = 0.01;
	
	public static final Random random = new MersenneTwister();
	
	private boolean gameRunning = true;
	
	protected List<Drawable> drawableObjects;
	protected List<Timed>    timedObjects;
	
	private List<Ant> ants;
	public PheromoneMap pheromoneMap;
	public PheromoneMap2 testMap;
	public StaticMap staticMap;
	
	public Model() {
		drawableObjects = Collections.synchronizedList(new ArrayList<Drawable>());
		timedObjects = Collections.synchronizedList(new ArrayList<Timed>());
		ants = Collections.synchronizedList(new ArrayList<Ant>());
		GridSprite2D grid = new GridSprite2D(new Vector(-200, -200), new Vector(400, 400), 20, 20, 1);
		drawableObjects.add(grid);
		pheromoneMap = new PheromoneMap();
		pheromoneMap.placePheromone(new Vector(20,20),PheromoneMap.Pheromones.TRAIL, 10);
		pheromoneMap.placePheromone(new Vector(25,25),PheromoneMap.Pheromones.TRAIL, 10);
		drawableObjects.add(pheromoneMap);
	
		staticMap = new StaticMap();
		staticMap.placePheromone(new Vector(20,20),StaticMap.Pheromones.TRAIL, 10);
		staticMap.placePheromone(new Vector(25,25),StaticMap.Pheromones.TRAIL, 10);
		drawableObjects.add(staticMap);
		
		testMap = new PheromoneMap2(new Vector(-200, -200), 400, 400, 100, 100);
		drawableObjects.add(testMap);
		timedObjects.add(testMap);
		
		// Test ants
		for (int i = 0; i < 100; i++){
			Ant ant = new Ant(new Vector());
			ant.setPheromoneMap(pheromoneMap);
			ant.setTestMap(testMap);
			ant.setMap(staticMap);
			ants.add(ant);
			drawableObjects.add(ant);
			timedObjects.add(ant);
		}
		RectTextureSprite2D texTest = new RectTextureSprite2D(new Vector(-100, -20), new Vector(50, 50), 3, "assets/textures/leaf1.png", "PNG");
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
