package game;

import java.awt.Color;

import main.Model;
import render.ColorSprite2D;
import render.Drawable;
import util.Positioned;
import util.Vector;

public class Ant implements Drawable, Timed, Positioned{
	private static final Vector dimension = new Vector(1, 1);
	private static final ColorSprite2D antSprite = new ColorSprite2D(new Vector(), dimension, 1, Color.GRAY);
	private Vector position;
	private Vector direction;
	private int stepsWithDirection;
	private PheromoneMap pheromoneMap;
	
	public void setPheromoneMap(PheromoneMap m){
		pheromoneMap = m;
	}
	
	public Ant(Vector initialPosition){
		position = initialPosition;
		direction = new Vector(0, 0);
		stepsWithDirection = 0;
	}
	
	public void step(double timeStep){
		
		/*
		 * Test code -- This is a pure (slightly smoothed) drunken walk.
		 * This may be factored in to the final behavior.
		 */
		
		if (Model.random.nextInt(10000) < stepsWithDirection){
			stepsWithDirection = 0;
			direction = new Vector(Model.random.nextDouble() - .5, Model.random.nextDouble() - .5).scale(Model.random.nextDouble() * 3);
		}
		stepsWithDirection++;
		
		Vector move = new Vector(direction);
		Vector randomVariation = new Vector(Model.random.nextDouble() - .5, Model.random.nextDouble() - .5);
		move.addInPlace(randomVariation);
		
		Vector pheromoneAttraction = pheromoneMap.getBest(position, new Vector(0,0), 150, 10).scale(timeStep);

		position.addInPlace(pheromoneAttraction);
		position.addInPlace(move.scale(timeStep));
	}
	
	public void draw(){
		antSprite.setPosition(position.minus(dimension.scale(.5)));
		antSprite.draw();
	}
	
	public Vector getPosition() {
		return position;
	}
}
