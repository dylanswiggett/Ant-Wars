package game;

class PheromoneMap {
  enum Pheromones {TRAIL, FOOD, ALARM, AGGRESSION};
	
	
	public PheromoneMap(){
	}
	
	class PheromoneNode{
	  Pheromones type;
		PheromoneNode parent;
		
	}
}