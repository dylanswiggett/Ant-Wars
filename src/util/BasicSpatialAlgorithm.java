package util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Basic, inefficient spatial algorithm for testing accuracy of other spatial algorithms.
 * 
 * @author dylan
 *
 * @param <E>
 */
public class BasicSpatialAlgorithm<E extends Positioned> implements SpatialAlgorithm<E>{
	private ArrayList<E> list;
	
	public BasicSpatialAlgorithm(){
		list = new ArrayList<>();
	}

	@Override
	public void add(E obj) {
		list.add(obj);
	}

	@Override
	public boolean remove(E obj) {
		return list.remove(obj);
	}

	@Override
	public E nearestTo(Vector p) {
		E nearest = null;
		double dist2 = Double.MAX_VALUE;
		
		for (E item : list){
			double newDist2 = p.minus(item.getPosition()).mag2();
			if (newDist2 < dist2){
				nearest = item;
				dist2 = newDist2;
			}
		}
		
		return nearest;
	}

	@Override
	public List<E> inCircle(Vector p, double r) {
		double dist2 = r * r;
		
		ArrayList<E> toReturn = new ArrayList<>();
		for (E item : list)
			if (p.minus(item.getPosition()).mag2() <= dist2)
				toReturn.add(item);
		return toReturn;
	}

	@Override
	public List<E> inRectangle(Vector corner1, Vector corner2) {
		ArrayList<E> toReturn = new ArrayList<>();
		
		for (E item : list){
			Vector p = item.getPosition();
			
			if (p.x >= corner1.x && p.x <= corner2.x &&
				p.y >= corner1.y && p.y <= corner2.y)
				list.add(item);
		}
		
		return toReturn;
	}

}
