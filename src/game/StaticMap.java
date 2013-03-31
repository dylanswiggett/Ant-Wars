package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Model;
import render.ColorSprite2D;
import render.Drawable;
import util.Positioned;
import util.RTree;
import util.SpatialAlgorithm;
import util.Vector;

public class StaticMap implements Drawable{
	public enum Pheromones {TRAIL, FOOD, ALARM, AGGRESSION};
	public enum Direction{NORTH, SOUTH, EAST, WEST};
	private List<Positioned> items;
	private ColorSprite2D sprite;
	private Vector spriteOffset;
	private SpatialAlgorithm<Positioned> map;
	
	public StaticMap(){
		items = new ArrayList<Positioned>();
		double spriteSize = .5;
		sprite = new ColorSprite2D(new Vector(0,0), new Vector(spriteSize, spriteSize), 1, Color.RED);
		spriteOffset = new Vector(-spriteSize / 2, -spriteSize / 2);
		
		map = new RTree<>();
	}
	
	public void placePheromone(Vector position, Pheromones type, int intensity){
		items.add(new PheromoneNode(position, type, intensity));
	}
	
	public void placeItem(Positioned item){
		map.add(item);
	}
	
	public void draw(){
		for( Positioned n : items ){
		}
	}
	
	private class PheromoneNode implements Positioned{
	  Pheromones type;
	  int intensity;
	  Vector position;
	  
	  PheromoneNode(Vector position, Pheromones type, int intensity){
		  this.position = position;
		  this.type = type;
		  this.intensity = intensity;
	  }
	  
	  public Vector getPosition() {
		  return position;
	  }
	}
}