import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Rectangle;

/**
 * Write a description of class Creature here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Creature implements MoveableShape {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Color color;
	protected Rectangle boundingBox;

	public Rectangle getBounds() {
		return new Rectangle(this.x, this.y, this.width, this.height);
	}

	/*
	 * default draw for creatures
	 */
	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(color);
		Ellipse2D.Double body = new Ellipse2D.Double(x, y, width / 2,
				height / 2);
		g2.draw(body);
		g2.fill(body);
		move();
	}

	/*
	 * return x value of creatures
	 */
	public int getX() {
		return x;
	}

	/*
	 * return y value of creatures
	 */
	public int getY() {
		return y;
	}

	public void move() {

	}

	@Override
	/*
	 * all other creatures besides PreyB move in simple patterns
	 */
	public void moveAway(int direction, int x, int y) {

	}

	@Override
	public boolean collide(MoveableShape other) {
		// preys do nothing when colliding with each other
		return false;
	}

}
