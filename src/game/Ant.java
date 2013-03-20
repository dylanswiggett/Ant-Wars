package game;

import java.awt.Color;
import java.util.Random;

import render.ColorSprite2D;
import render.Drawable;
import util.Vector;

public class Ant implements Drawable, Timed{
	private static final Vector dimension = new Vector(1, 1);
	private static final ColorSprite2D antSprite = new ColorSprite2D(new Vector(), dimension, 1, Color.GRAY);
	private Vector position;
	private Vector direction;
	private int stepsWithDirection;
	
	public Ant(Vector initialPosition){
		position = initialPosition;
		direction = new Vector(0, 0);
		stepsWithDirection = 0;
	}
	
	public void step(double timeStep){
		Random random = new Random();
		
		if (random.nextInt(10000) < stepsWithDirection){
			stepsWithDirection = 0;
			direction = new Vector(random.nextDouble() - .5, random.nextDouble() - .5).scale(random.nextDouble() * 3);
		}
		stepsWithDirection++;
		
		Vector move = new Vector(direction);
		Vector randomVariation = new Vector(random.nextDouble() - .5, random.nextDouble() - .5);
		move.addInPlace(randomVariation);
		
		position.addInPlace(move.scale(timeStep));
	}
	
	public void draw(){
		antSprite.setPosition(position.minus(dimension.scale(.5)));
		antSprite.draw();
	}
}
