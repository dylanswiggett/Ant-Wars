package util;

import java.util.List;

public interface SpatialAlgorithm<E extends Positioned> {
	public void add(E obj);
	public boolean remove(E obj);
	public E nearestTo(Vector p);
	public List<E> inCircle(Vector p, double r);
	public List<E> inRectangle(Vector corner1, Vector corner2);
}
