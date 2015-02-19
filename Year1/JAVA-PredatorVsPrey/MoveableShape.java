import java.awt.Graphics2D;

/**
 * Interface for all shapes that are considered moveable
 * 
 * @author Daniel Tran 500554290
 * 
 */
public interface MoveableShape {
	void move();

	boolean collide(MoveableShape other);

	void draw(Graphics2D g2);
	
	int getX();
	
	int getY();

	void moveAway(int direction, int x, int y);
}