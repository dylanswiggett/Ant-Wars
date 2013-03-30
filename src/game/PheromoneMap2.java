package game;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import render.ColorSprite2D;
import render.Drawable;
import util.Vector;

public class PheromoneMap2 implements Drawable, Timed {
	private static final double PHEROMONE_LONGEVITY = 5;
	private static final double MINIMUM_PHEROMONE_INTENSITY = 5;
	private static final int SEARCH_RADIUS = 2;

	private Vector position;
	private double width, height;
	private int rows, columns;
	private double rowScale, colScale;

	private PheromoneList[][] pheromones;

	private double timeCounter;

	private ColorSprite2D sprite;

	public PheromoneMap2(Vector position, double width, double height, int rows, int columns) {
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.columns = columns;
		this.position = position;

		rowScale = width / rows;
		colScale = height / columns;

		pheromones = new PheromoneList[rows][columns];

		sprite = new ColorSprite2D(new Vector(0, 0), new Vector(1, 1), .5, Color.WHITE);
	}

	private int[] vectorToCoords(Vector p) {
		int row = (int) ((p.x - position.x) / rowScale);
		int col = (int) ((p.y - position.y) / colScale);

		if (row < 0)
			row = 0;
		else if (row >= rows)
			row = rows - 1;
		if (col < 0)
			col = 0;
		else if (col >= columns)
			col = columns - 1;

		return new int[] { row, col };
	}

	public Vector getDirection(Vector p, Vector prevDirection) {
		int[] coords = vectorToCoords(p);

		int row = coords[0];
		int col = coords[1];

		int bestX = 0;
		int bestY = 0;
		double bestIntensity = -1;

		for (int dX = -SEARCH_RADIUS; dX <= SEARCH_RADIUS; dX++)
			for (int dY = -SEARCH_RADIUS; dY <= SEARCH_RADIUS; dY++) {
				int x = dX + row;
				int y = dY + col;
				if (x < 0 || x >= rows || y < 0 || y >= columns)
					continue;
				double superIntensity = getSuperIntensity(x, y);
				if (superIntensity > bestIntensity) {
					bestIntensity = superIntensity;
					bestX = dX;
					bestY = dY;
				}
			}

		if (bestIntensity == 0)
			return new Vector();
		else {
			Vector best = new Vector((bestX + .5) * rowScale, (bestY + .5) * colScale).norm();
			if (best.mag2() < .5)
				return new Vector();
			else
				return best;
		}
	}

	// TODO: Replace this with a shader.
	public void draw() {
		GL11.glPushMatrix();
		GL11.glTranslated(position.x, position.y, 0);

		for (int x = 0; x < rows; x++)
			for (int y = 0; y < columns; y++) {
				double intensity = getIntensityAndClean(x, y);
				if (intensity == 0)
					continue;
				sprite.setColor(new Color((int) intensity, 0, 0));
				sprite.setPosition(new Vector(x * rowScale, y * colScale));
				sprite.setDimension(new Vector(rowScale, colScale));
				sprite.draw();
			}

		GL11.glPopMatrix();
	}

	public void addPheromone(Pheromone type, double x, double y, double intensity) {
		int[] coords = vectorToCoords(new Vector(x, y));

		int row = coords[0];
		int col = coords[1];

		PheromoneList l = pheromones[row][col];
		while (l != null) {
			if (l.pheromone == type) {
				l.intensity = intensity;
				l.refreshTime();
				return;
			} else
				l = l.next;
		}

		pheromones[row][col] = new PheromoneList(type, intensity, pheromones[row][col]);

	}

	private class PheromoneList {
		private Pheromone pheromone;

		// Intensity is the intensity of this pheromone node.
		// SuperIntensity is the intensity of this and all child nodes, with age
		// factored in.
		private double intensity, superIntensity;
		private double creationTime;
		PheromoneList next;

		public PheromoneList(Pheromone type, double intensity, double creationTime,
				PheromoneList next) {
			this.pheromone = type;
			this.intensity = intensity;
			this.creationTime = creationTime;
		}

		public PheromoneList(Pheromone type, double intensity, PheromoneList next) {
			this(type, intensity, timeCounter, next);
		}

		private void refreshTime() {
			creationTime = timeCounter;
		}
	}

	private double getIntensity(PheromoneList l) {
		return l.intensity / ((timeCounter - l.creationTime) / PHEROMONE_LONGEVITY + 1);
	}

	/**
	 * Removes any pheromone marker that has an intensity less than
	 * MINIMUM_PHEROMONE_INTENSITY.
	 */
	private PheromoneList cleanUpList(PheromoneList l) {
		if (l == null)
			return null;
		else {
			double intensity = getIntensity(l);

			if (intensity < MINIMUM_PHEROMONE_INTENSITY) {
				return cleanUpList(l.next);
			} else {
				l.next = cleanUpList(l.next);
				return l;
			}
		}
	}

	/**
	 * Returns the pheromone intensity of the given list of pheromones. If the
	 * list contains any pheromones of too low an intensity, they will be
	 * removed.
	 * 
	 * @param l
	 * @param x
	 * @param y
	 * @return
	 */
	private double getIntensityAndClean(PheromoneList l, int x, int y) {
		if (l == null)
			return 0;
		double intensity = getIntensity(l);

		if (intensity < MINIMUM_PHEROMONE_INTENSITY)
			pheromones[x][y] = cleanUpList(pheromones[x][y]);

		l.superIntensity = intensity + getIntensityAndClean(l.next, x, y);

		return l.superIntensity;
	}

	/**
	 * Returns the pheromone intensity at the given coordinate. If the list
	 * contains any pheromones of too low an intensity, they will be removed.
	 */
	public double getIntensityAndClean(int x, int y) {
		return getIntensityAndClean(pheromones[x][y], x, y);
	}

	public double getSuperIntensity(int x, int y) {
		if (pheromones[x][y] == null)
			return 0;
		else
			return pheromones[x][y].superIntensity;
	}

	public void step(double timeStep) {
		timeCounter += timeStep;
	}
}
