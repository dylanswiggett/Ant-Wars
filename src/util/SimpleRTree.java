package util;

import game.Ant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import render.Drawable;

public class SimpleRTree<E extends Positioned> implements SpatialAlgorithm<E>, Drawable {
	private static final int DEFAULT_MAX_SATURATION = 4;
	private static final int DEFAULT_SUBDIVS = 2;

	private int maxSaturation, subdivs;

	private RTreeNode<E> root;

	/**
	 * Creates a new empty RTree.
	 * 
	 * @param maxSaturation
	 *            Maximum number of objects to put in a rectangle before
	 *            subdividing.
	 * @param subdivs
	 *            Number of rectangles to split a node into when it becomes
	 *            saturated.
	 */
	public SimpleRTree(int maxSaturation, int subdivs) {
		if (subdivs > maxSaturation)
			subdivs = maxSaturation;

		this.maxSaturation = maxSaturation;
		this.subdivs = subdivs;
	}

	/**
	 * Creates a new empty RTree with reasonable default parameters.
	 */
	public SimpleRTree() {
		this(DEFAULT_MAX_SATURATION, DEFAULT_SUBDIVS);
	}

	@Override
	public void draw() {

	}

	@Override
	public void add(E obj) {
		if (root != null)
			root.add(obj);
		else
			root = new RTreeNode<>(obj);
	}

	@Override
	public boolean remove(E obj) {
		if (root == null)
			return root.remove(obj);
		else
			return false;
	}

	// TODO Implement
	@Override
	public E nearestTo(Vector p) {
		return null;
	}

	@Override
	public List<E> inCircle(Vector p, double r) {
		List<E> list = inRectangle(p.minus(new Vector(r, r)), p.plus(new Vector(r, r)));
		
		Iterator<E> i = list.iterator();
		
		double dist2 = r * r;
		
		while (i.hasNext()){
			E item = i.next();
			if (item.getPosition().minus(p).mag2() > dist2)
				i.remove();
		}
		
		return list;
	}

	@Override
	public List<E> inRectangle(Vector corner1, Vector corner2) {
		if (root == null)
			return new LinkedList<E>();
		else
			return root.searchRectangle(corner1, corner2);
	}

	class RTreeNode<E extends Positioned> {
		private List<E> objects;
		private List<RTreeNode<E>> subNodes;
		private double volume;

		/**
		 * Defines the lower left corner of the bounding rectangle.
		 */
		private Vector lowerCorner;

		/**
		 * Defines the upper right corner of the bounding rectangle.
		 */
		private Vector upperCorner;

		public RTreeNode(E initialObj) {
			objects = new ArrayList<>();
			objects.add(initialObj);

			lowerCorner = new Vector(initialObj.getPosition());
			upperCorner = new Vector(lowerCorner);

			volume = 0;
		}
		
		private List<E> searchRectangle(Vector c1, Vector c2){
			LinkedList<E> list = new LinkedList<>();
			if (contains(c1, c2)){
				if (objects != null){
					for (E obj : objects){
						Vector pos = obj.getPosition();
						if(pos.x >= c1.x && pos.x <= c2.x &&
						   pos.y >= c1.y && pos.y <= c2.y)
							list.add(obj);
					}
				} else
					for (RTreeNode<E> n : subNodes)
						list.addAll(n.searchRectangle(c1, c2));
			}
			return list;
		}

		private void add(E obj) {
			if (objects != null) {
				objects.add(obj);
				expandToContain(obj.getPosition());

				if (objects.size() >= maxSaturation)
					subdivide();
			} else {
				RTreeNode<E> minNode = null; // Node that would require the
												// least expansion.
				double minExpansionAmount = Double.MAX_VALUE;

				// Find the best node to add the object to.
				// (Currently based purely on volume needed to expand,
				// may want to base on surface area increase instead).
				for (RTreeNode<E> node : subNodes) {
					double expansionAmount = node.neededExpansion(obj.getPosition());
					if (expansionAmount < minExpansionAmount) {
						minExpansionAmount = expansionAmount;
						minNode = node;

						// TODO: If multiple nodes could already contain the
						// object, select the one that contains the least
						// objects.
					}
				}
				
				minNode.add(obj);
				expandToContain(minNode);
			}
		}

		/**
		 * Adds subNodes to an RTreeNode, and divides up the node's objects
		 * between them. Assumes that the node is not already subdivided, and
		 * has enough children to fill the set number of subNodes.
		 * 
		 * @return
		 */
		private void subdivide() {
			subNodes = new ArrayList<>();

			// TODO Start subnodes with most distant objects, rather than first
			// added.
			for (int i = 0; i < maxSaturation; i++)
				subNodes.add(new RTreeNode<>(objects.remove(0)));

			while (!objects.isEmpty())
				add(objects.remove(0));

			objects = null;
		}

		private boolean contains(Vector p) {
			return (p.x >= lowerCorner.x && p.x <= upperCorner.x && p.y >= lowerCorner.y && p.y <= upperCorner.y);
		}
		
		private boolean contains(Vector c1, Vector c2) {
			return !(lowerCorner.x > c2.x || upperCorner.x < c1.x ||
					 lowerCorner.y > c2.y || upperCorner.y < c1.y);
		}

		private void calculateVolume() {
			Vector dim = upperCorner.minus(lowerCorner); 
			volume = dim.x * dim.y;
		}

		private void expandToContain(RTreeNode<E> node) {
			if (node.upperCorner.x > upperCorner.x)
				upperCorner.x = node.upperCorner.x;
			if (node.lowerCorner.x < lowerCorner.x)
				lowerCorner.x = node.lowerCorner.x;
			if (node.upperCorner.y > upperCorner.y)
				upperCorner.y = node.upperCorner.y;
			if (node.lowerCorner.y < lowerCorner.y)
				lowerCorner.y = node.lowerCorner.y;
			calculateVolume();
		}

		private void expandToContain(Vector p) {
			if (p.x > upperCorner.x)
				upperCorner.x = p.x;
			else if (p.x < lowerCorner.x)
				lowerCorner.x = p.x;
			if (p.y > upperCorner.y)
				upperCorner.y = p.y;
			else if (p.y < lowerCorner.y)
				lowerCorner.y = p.y;
			calculateVolume();
		}

		/**
		 * Volume the rectangle would need to expand to contain the point.
		 * 
		 * @param p
		 *            Point to check against.
		 * @return
		 */
		private double neededExpansion(Vector p) {
			if (contains(p))
				return 0;
			else {
				double Xlength = Math.max(p.x - lowerCorner.x, upperCorner.x - p.x);
				double Ylength = Math.max(p.y - lowerCorner.y, upperCorner.y - p.y);
				return (Xlength * Ylength) - volume;
			}
		}

		/**
		 * Remove the first instance of the given object from the tree. <br/>
		 * <br/>
		 * 
		 * Note: This does NOT optimize the tree or remove empty nodes. <br/>
		 * TODO: Make removal of nodes better optimized.
		 * 
		 * @param obj
		 *            Object to remove.
		 * @return
		 */
		private boolean remove(E obj) {
			if (!contains(obj.getPosition()))
				return false;
			else if (objects != null && objects.remove(obj) == true)
				return true;
			else {
				for (RTreeNode<E> node : subNodes)
					if (node.remove(obj))
						return true;
				return false;
			}
		}
	}

	public static void main(String[] args) {
		Random r = new Random();

		SpatialAlgorithm<Positioned> defaultAlgo = new BasicSpatialAlgorithm<>();

		SpatialAlgorithm<Positioned> tree = new SimpleRTree<>();

		for (int i = 0; i < 1000000; i++){
			Ant ant = new Ant(new Vector(r.nextInt(1000), r.nextInt(1000)));
			defaultAlgo.add(ant);
			tree.add(ant);
		}

		/*
		 * TESTS GO HERE
		 */
		
		long startTime = System.currentTimeMillis();
		List<Positioned> basicList = defaultAlgo.inCircle(new Vector(), 10);
		long endTime = System.currentTimeMillis();
		System.out.println("Basic algorithm found " + basicList.size() + " in " + ((double)endTime - startTime) / 1000 + " seconds.");
		
		startTime = System.currentTimeMillis();
		List<Positioned> testList  = tree.inCircle(new Vector(), 10);
		endTime = System.currentTimeMillis();
		System.out.println("RTree found " + testList.size() + " in " + ((double)endTime - startTime) / 1000 + " seconds.");
		
		System.out.println("TESTED");

	}

}
