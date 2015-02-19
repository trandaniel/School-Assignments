import java.awt.Rectangle;

public abstract class Vehicle {
	protected Rectangle boundingBox;
	protected int xLeft, yTop;

	/**
	 * initlize stored variables
	 */
	public Vehicle() {
		boundingBox = null;
		xLeft = 0;
		yTop = 0;
	}

	/**
	 * 
	 * @return the bounding box
	 */
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
}
