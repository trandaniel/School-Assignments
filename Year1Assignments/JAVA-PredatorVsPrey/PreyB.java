import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class PreyB extends Creature {
	Random rand = new Random();
	private final int DELTA = 4;

	public PreyB() {
		x = rand.nextInt(450);
		y = rand.nextInt(450);
		width = 50;
		height = 50;
		color = Color.GREEN;
		boundingBox = new Rectangle(x, y, width, height);
	}

	/*
	 * method to move preys of type B away from the predator as it approaches
	 * the prey
	 * 
	 * @param direction the direction the predator moves
	 * 
	 * @param playerX the x coordinate the player is at
	 * 
	 * @param playerY the y coordinate the player is at
	 */
	public void moveAway(int direction, int playerX, int playerY) {
		// move away from predator as it approaches
		switch (direction) {
		case 0:
			if (playerX - x < 0) {
				x += DELTA;
				y -= DELTA;
			}
			if (playerX - x > 0) {
				x -= DELTA;
				y -= DELTA;
			}
			if (x > 450) {
				x = 450;
			}
			if (y < 0) {
				y = 0;
			}
			if (x < 0) {
				x = 0;
			}
			if (y > 450) {
				y = 450;
			}
			break;
		case 1:
			if (playerY - y < 0) {
				x += DELTA;
				y += DELTA;
			}
			if (playerY - y > 0) {
				x += DELTA;
				y -= DELTA;
			}
			if (x > 450) {
				x = 450;
			}
			if (y > 450) {
				y = 450;
			}
			if (x < 0) {
				x = 0;
			}
			if (y < 0) {
				y = 0;
			}
			break;
		case 2:
			if (playerX - x > 0) {
				x -= DELTA;
				y += DELTA;
			}
			if (playerX - x < 0) {
				x += DELTA;
				y += DELTA;
			}
			if (x > 450) {
				x = 450;
			}
			if (y > 450) {
				y = 450;
			}
			if (x < 0) {
				x = 0;
			}
			if (y < 0) {
				y = 0;
			}
			break;
		case 3:
			if (playerY - y < 0) {
				x -= DELTA;
				y += DELTA;
			}
			if (playerY - y > 0) {
				x -= DELTA;
				y -= DELTA;
			}
			if (x < 0) {
				x = 0;
			}
			if (y < 0) {
				y = 0;
			}
			if (x > 450) {
				x = 450;
			}
			if (y > 450) {
				y = 450;
			}
			break;
		}

	}

	/*
	 * @param g2 the graphics component to draw the preys
	 */
	@Override
	public void draw(Graphics2D g2) {
		// draw the preys
		g2.setColor(color);
		Ellipse2D.Double body = new Ellipse2D.Double(x, y, width / 2,
				height / 2);
		Ellipse2D.Double earL = new Ellipse2D.Double(x - 2,
				(y + height / 2) - 10, width / 5, height / 5);
		Ellipse2D.Double earR = new Ellipse2D.Double(x + (2 * width / 5),
				(y + height / 2) - 10, width / 5, height / 5);
		g2.draw(body);
		g2.fill(body);
		g2.draw(earL);
		g2.fill(earL);
		g2.draw(earR);
		g2.fill(earR);
		move();
	}

}
