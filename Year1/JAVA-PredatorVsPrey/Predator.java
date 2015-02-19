import java.awt.Color;
import java.awt.Rectangle;

/**
 * creates a predator object that moves according to which button is clicked
 * 
 * @author Daniel Tran
 */
public class Predator extends Creature {
	private int direction;

	public Predator() {
		x = 350;
		y = 250;
		width = 100;
		height = 100;
		color = Color.YELLOW;
		direction = 3;
		boundingBox = new Rectangle(x, y, width, height);
	}

	/*
	 * moves the predator 0 - north 1 - east 2 - south 3 - west
	 */
	@Override
	public void move() {
		switch (direction) {
		case 0:
			y = y - 5 > 0 ? y - 5 : 0;
			break;
		case 1:
			x = x + 5 < 450 ? x + 5 : 450;
			break;
		case 2:
			y = y + 5 < 425 ? y + 5 : 425;
			break;
		case 3:
			x = x - 5 > 0 ? x - 5 : 0;
			break;
		}
	}

	/*
	 * @param the direction to turn in an integer value
	 */
	public void setDirection(int newDirection) {
		if (newDirection > 3)
			newDirection = 0;
		else if (newDirection < 0)
			newDirection = 3;
		direction = newDirection;
	}

	public int getDirection() {
		return direction;
	}

	/*
	 * check collision with the preys
	 */
	@Override
	public boolean collide(MoveableShape other) {
		Creature predator = (Creature) other;
		return this.getBounds().contains(predator.getBounds());
	}

}