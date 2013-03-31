package game;

import java.awt.Color;

import main.Model;
import render.ColorSprite2D;
import render.Drawable;
import util.Positioned;
import util.Vector;

public class Ant implements Drawable, Timed, Positioned{
	private enum AIState {FORAGING, CARRYING_FOOD, IDLE};
	
	private static final Vector dimension = new Vector(1, 1);
	private static final ColorSprite2D antSprite = new ColorSprite2D(new Vector(), dimension, 1, Color.GRAY);
	private AIState currentState;
	private Vector position;
	private Vector direction;
	private int stepsWithDirection;
	private PheromoneMap pheromoneMap;
	private PheromoneMap2 testMap;
	private StaticMap map;
	
	public void setPheromoneMap(PheromoneMap m){
		pheromoneMap = m;
	}
	
	public void setTestMap(PheromoneMap2 m){
		testMap = m;
	}
	
	public void setMap(StaticMap m){
		map = m;
	}
	
	public Ant(Vector initialPosition){
		position = initialPosition;
		direction = new Vector(0, 0);
		stepsWithDirection = 0;
		currentState = AIState.FORAGING;
	}
	
	public void step(double timeStep){
		switch(currentState){
			case FORAGING:
				//A more purposeful walk.
				if (Model.random.nextInt(10000) < stepsWithDirection){
					stepsWithDirection = 0;
					if( direction.mag2() == 0 ){
						direction = (new Vector(Model.random.nextDouble() - .5, Model.random.nextDouble() - .5).scale(Model.random.nextDouble() * 3))
									.norm()
									.scale(1+(Model.random.nextInt(5)-2)/3);
					} else {
						direction = direction.rot(Model.random.nextInt(3)-1);
					}
				}
				stepsWithDirection++;
				position.addInPlace(direction.scale(timeStep));
			break;
			case CARRYING_FOOD:
				
			break;
			case IDLE:
				//Maybe add flocking behavior?
				if (Model.random.nextInt(10000) < stepsWithDirection){
					stepsWithDirection = 0;
					direction = new Vector(Model.random.nextDouble() - .5, Model.random.nextDouble() - .5).scale(Model.random.nextDouble() * 3);
				}
				stepsWithDirection++;
				position.addInPlace(direction.scale(timeStep));
				
			break;
		}
		/*
		
		Vector move = new Vector(direction);
		Vector randomVariation = new Vector(Model.random.nextDouble() - .5, Model.random.nextDouble() - .5);
		move.addInPlace(randomVariation);
		
		Vector pheromoneAttraction = testMap.getDirection(position, new Vector()).scale(timeStep);//pheromoneMap.getBest(position, new Vector(0,0), 150, 10).scale(timeStep);

		position.addInPlace(pheromoneAttraction);
		position.addInPlace(move.scale(timeStep));*/
	}
	
	public void draw(){
		antSprite.setPosition(position.minus(dimension.scale(.5)));
		antSprite.draw();
	}
	
	public Vector getPosition() {
		return position;
	}
}
