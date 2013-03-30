package util;

import java.util.HashMap;
import java.util.List;

import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.rtree.RTree;

public class RTreeWrapper<E extends Positioned> implements SpatialAlgorithm<E> {
	private RTree tree;
	private HashMap<Rectangle, E> rectToObj;
	private HashMap<E, Rectangle> objToRect;
	private HashMap<Rectangle, Integer> ids;
	
	private int id;
	
	public RTreeWrapper(){
		tree = new RTree();
		rectToObj = new HashMap<>();
		objToRect = new HashMap<>();
		ids = new HashMap<>();
	}
	
	@Override
	public void add(E obj) {
		Rectangle rect = convertToRect(obj.getPosition());
		rectToObj.put(rect, obj);
		objToRect.put(obj, rect);
		tree.add(rect, ++id);
		ids.put(rect, id);
	}

	@Override
	public boolean remove(E obj) {
		Rectangle rect = objToRect.remove(obj);
		rectToObj.remove(rect);
		
		return tree.delete(rect, ids.get(rect));
	}

	@Override
	public E nearestTo(Vector p) {
		Rectangle rect = convertToRect(p);
		
		//return tree.nearest(rect, )
		
		return null;
	}

	@Override
	public List<E> inCircle(Vector p, double r) {
		return null;
	}

	@Override
	public List<E> inRectangle(Vector corner1, Vector corner2) {
		Rectangle rect = new Rectangle();
		rect.set((float) corner1.x, (float) corner1.y, (float) corner2.x, (float) corner2.y);
		return null;
	}
	
	private Rectangle convertToRect(Vector v){
		Rectangle rect = new Rectangle();
		rect.set((float) v.x, (float) v.y, (float) v.x, (float) v.y);
		return rect;
	}

}
