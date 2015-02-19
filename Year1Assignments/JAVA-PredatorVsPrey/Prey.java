
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.*;

/**
 * Creates prey objects that move randomly in a small distance
 * 
 * @author Daniel Tran
 * 
 */
public class Prey extends Creature {
	Random rand = new Random();
	private int direction, count;
	private static final int DELTA = 2;
	private static final int DIRECTION_CHANGE = 35;
	/*
	 * create a new prey object
	 */
	public Prey() {
		x = rand.nextInt(450);
		y = rand.nextInt(450);
		direction = rand.nextInt(2);
		count = 0;
		width = 50;
		height = 50;
		color = Color.RED;
		boundingBox = new Rectangle(x, y, width, height);
	}

	/*
	 * move the prey objects on the frame
	 */
	@Override
	public void move() {
		//move the preys in simple patterns
		switch (direction) {
		case 0:
			if ((x - DELTA > 0) && (x - DELTA < 450) && count < DIRECTION_CHANGE) {
				x = x - DELTA;
			}
			if ((x + DELTA > 0) && (x + DELTA < 450) && count > DIRECTION_CHANGE) {
				x = x + DELTA;
			}
			if (x + DELTA == 450) {
				count = 0; 
			}
			if (x - DELTA == 0) {
				count = DIRECTION_CHANGE; 
			}
			count++;
			if (count == 100) {
				count = 0;
			}
			break;
		case 1:
			if ((y - DELTA > 0) && (y - DELTA < 450) && count < DIRECTION_CHANGE) {
				y = y - DELTA;
			}
			if ((y + DELTA > 0) && (y + DELTA < 450) && count > DIRECTION_CHANGE) {
				y = y + DELTA;
			}
			if (y + DELTA == 450) {
				count = 0; 
			}
			if (y - DELTA == 0) {
				count = DIRECTION_CHANGE; 
			}
			count++;
			if (count == DIRECTION_CHANGE * 2) {
				count = 0;
			}
			break;
		}
		
	}

	/*
	 * @param g2 the graphics component to draw the preys
	 */
	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(color);
		Ellipse2D.Double body = new Ellipse2D.Double(x, y, width / 2,
				height / 2);
		Ellipse2D.Double earL = new Ellipse2D.Double(x - 2, y - 2, width / 5,
				height / 5);
		Ellipse2D.Double earR = new Ellipse2D.Double(x + (2 * width / 5), y,
				width / 5, height / 5);
		g2.draw(body);
		g2.fill(body);
		g2.draw(earL);
		g2.fill(earL);
		g2.draw(earR);
		g2.fill(earR);
		move();
	}

}
