package render;

import util.Vector;

public abstract class Sprite2D implements Drawable {
	protected Vector position, dimension;
	protected int verticalOffset; // Amount to offset from z = 0 when drawing.
									// Use to avoid clipping when drawing
									// overlapping objects.

	public Sprite2D(Vector position, Vector dimension, int verticalOffset) {
		this.position = position;
		this.dimension = dimension;
		this.verticalOffset = verticalOffset;
	}

	public abstract void draw();
}
