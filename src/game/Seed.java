package game;

import java.awt.Color;

import util.Vector;

import render.ColorSprite2D;
import render.Drawable;
import util.Positioned;

public class Seed implements Drawable, Positioned, Carriable{
	
	private static final Vector dimension = new Vector(1, 1);
	private static final ColorSprite2D seedSprite = new ColorSprite2D(new Vector(), dimension, 1,new Color(255,255,0));
	
	private Vector position;
	
	public Seed(Vector position){
		this.position = position;
	}
	
	public void draw(){
		seedSprite.setPosition(position.minus(dimension.scale(.5)));
		seedSprite.draw();
	}
	
	public Vector getPosition(){
		return position;
	}
	
	public int antsRequired(){ //how many are needed to carry it.
		return 1;
	}
}
