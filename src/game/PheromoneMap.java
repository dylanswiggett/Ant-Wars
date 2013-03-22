package game;

import java.util.*;

class PheromoneMap {
	enum Pheromones {TRAIL, FOOD, ALARM, AGGRESSION};
	int width;
	int height;
	int gridSize;
	
	public PheromoneMap(int width, int height, int gridSize){
		this.width = width;
		this.height = height;
		this.gridSize = gridSize;
	}
	
	class GridSection { 
		List<PheromoneNode> pheromones;
		
		public GridSection(){
		  pheromones = new ArrayList<PheromoneNode>();	
		}
	}
	
	class PheromoneNode {
	  Pheromones type;
	  PheromoneNode parent;
	  
	}
	
	class PheromoneTrail {
	  	
	}
}