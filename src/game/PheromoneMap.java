package game;

import java.awt.Color;
import java.util.*;

import main.Model;

import render.ColorSprite2D;
import render.Drawable;

import util.MersenneTwister;
import util.Vector;

public class PheromoneMap implements Drawable{
	public enum Pheromones {TRAIL, FOOD, ALARM, AGGRESSION};
	public enum Direction{NORTH, SOUTH, EAST, WEST};
	private List<PheromoneNode> pheromones;
	private ColorSprite2D sprite;
	private Vector spriteOffset;
	
	public PheromoneMap(){
		pheromones = new ArrayList<PheromoneNode>();
		int spriteSize = 1;
		sprite = new ColorSprite2D(new Vector(0,0), new Vector(spriteSize, spriteSize), 1, Color.RED);
		spriteOffset = new Vector(-spriteSize / 2, -spriteSize / 2);
	}
	
	/** Given a position, this chooses the next direction to visit.
	 * 
	 * @param point - gives the current position to decide from.
	 * @param preferredDirection - gives the preferred direction of travel.
	 * @param range - the range to search for pheromones.
	 * @param jitter - the amount of randomness in the decision. High values
	 *   will result in choosing non-optimal directions.
	 *   Zero will result in targeting the exact highest pheromone.
	 * @return the best direction to go. the zero vector if there are no pheromones in range.
	 */
	public Vector getBest(Vector point, Vector preferredDirection, int range, int jitter){
		//inefficient implementation for now.
		//TODO implement
		int range2 = range*range;
		Vector best = new Vector(0,0);
		for(PheromoneNode n : pheromones){
			Vector delta = n.position.minus(point);
			double distance2 = delta.mag2();
			if(distance2 < range2){
				//probably requires lots of fine tuning.
				int typeModifier = 1;
				if( n.type == Pheromones.ALARM ){
					typeModifier = -2;
				}
				double score;
				if( distance2 < 1 ) {
					score = 0;
				} else {
					score = ((1/distance2+0.1) + (1/(preferredDirection.minus(point.normOrZero()).mag2()+0.1) + typeModifier*n.intensity + Model.random.nextDouble()*jitter));
				}
				best.addInPlace(delta.normOrZero().scale(score));
			}
		}
		return best.normOrZero();
	}
	
	public void placePheromone(Vector position, Pheromones type, int intensity){
		pheromones.add(new PheromoneNode(position, type, intensity));
	}
	
	public void draw(){
		for( PheromoneNode n : pheromones ){
			sprite.setPosition(n.position.minus(spriteOffset));
			sprite.draw();
		}
	}
	
	private class PheromoneNode {
	  Pheromones type;
	  int intensity;
	  Vector position;
	  
	  PheromoneNode(Vector position, Pheromones type, int intensity){
		  this.position = position;
		  this.type = type;
		  this.intensity = intensity;
	  }
	}
}