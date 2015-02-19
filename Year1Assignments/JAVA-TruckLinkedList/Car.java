import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * A car shape that can be positioned anywhere on the screen.
 */
public class Car extends Vehicle {

	// variable declarations
	protected Integer carNumber;
	protected Color color;
	protected boolean linked;
	protected Car nextCar;
	protected Car previousCar;
	protected boolean linkedToTruck;
	private final int CAR_NUMBER_X = 25;
	private final int CAR_NUMBER_Y = 20;
	private final int CAR_HOOK = 5;
	private final int CAR_WIDTH = 60;
	private final int CAR_HEIGHT = 20;

	/**
	 * Constructs a car with a given top left corner.
	 * 
	 * @param x
	 *            the x coordinate of the top left corner
	 * @param y
	 *            the y coordinate of the top left corner
	 * @param carNum
	 *            the number of the car
	 */
	public Car(int x, int y, Integer carNum) {
		super();
		xLeft = x;
		yTop = y;
		carNumber = carNum;
		linked = false;
		linkedToTruck = false;
		nextCar = null;
		previousCar = null;
		color = Color.BLACK;
		boundingBox = new Rectangle(xLeft, yTop, CAR_WIDTH, CAR_HEIGHT);
	}

	/**
	 * return true if mouse clicked in region of car otherwise return false and
	 * change color of car if it is true
	 * 
	 * @param x
	 *            the x position of the mouse when clicked
	 * 
	 * @param y
	 *            the y position of the mouse when clicked
	 */
	public boolean contains(int x, int y) {
		// check if mouse position when clicked is within the boundaries of the
		// car
		if ((x > boundingBox.getX())
				&& x < (boundingBox.getX() + boundingBox.getWidth())
				&& (y > boundingBox.getY())
				&& (y < (boundingBox.getY() + boundingBox.getHeight()))) {
			color = Color.RED;
			return true;
		}
		setColor(Color.BLACK);
		return false;
	}

	/**
	 * @param x
	 *            the x coordinate to move to
	 * @param y
	 *            the y coordinate to move to
	 * 
	 *            moves the car(s) to the location where dragged.
	 */
	public void move(int x, int y) {
		// create a point
		Point p = new Point(x, y);
		xLeft = x;
		yTop = y;
		color = Color.RED;

		// if there is a next car, move it as well
		if (nextCar != null) {
			nextCar.move(x + CAR_WIDTH + CAR_HOOK, y);
		}

		// move the bounding box
		boundingBox.setLocation(p);

	}

	/**
	 * @param car
	 *            to link to
	 * 
	 *            links this car to the back of the the car
	 */
	public void linkTo(Car car) {
		// if there is no next car link it to the car
		if (car.nextCar == null) {
			move(CAR_WIDTH + car.xLeft + CAR_HOOK, car.boundingBox.y);
			car.nextCar = this;
			previousCar = car;
			linked = true;

		}
		// otherwise recurse and link to next car
		else {
			linkTo(car.nextCar);
		}
	}

	/**
	 * @param truck
	 *            to link to
	 * 
	 *            links the car to the back of the truck
	 */
	public void linkTo(Truck truck) {
		// link to the truck
		move(truck.xLeft + truck.boundingBox.width, truck.yTop - 3);
		truck.first = this;
		linkedToTruck = true;
	}

	/**
	 * @return the bounding box
	 * @see Vehicle#getBoundingBox()
	 */
	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	/**
	 * @param changeTo
	 *            the color
	 * 
	 *            changes color of car to changeTo
	 */
	public void setColor(Color changeTo) {
		color = changeTo;
		if (nextCar != null) {
			nextCar.setColor(changeTo);
		}
	}

	/**
	 * Draws the car.
	 * 
	 * @param g2
	 *            the graphics context
	 */
	public void draw(Graphics2D g2) {
		Rectangle body = new Rectangle(xLeft, yTop + 10, 60, 10);
		Ellipse2D.Double frontTire = new Ellipse2D.Double(xLeft + 10,
				yTop + 20, 10, 10);
		Ellipse2D.Double rearTire = new Ellipse2D.Double(xLeft + 40, yTop + 20,
				10, 10);

		// The bottom of the front windshield
		Point2D.Double r1 = new Point2D.Double(xLeft + 10, yTop + 10);
		// The front of the roof
		Point2D.Double r2 = new Point2D.Double(xLeft + 20, yTop);
		// The rear of the roof
		Point2D.Double r3 = new Point2D.Double(xLeft + 40, yTop);
		// The bottom of the rear windshield
		Point2D.Double r4 = new Point2D.Double(xLeft + 50, yTop + 10);

		Line2D.Double frontWindshield = new Line2D.Double(r1, r2);
		Line2D.Double roofTop = new Line2D.Double(r2, r3);
		Line2D.Double rearWindshield = new Line2D.Double(r3, r4);

		g2.setColor(color);
		// number the cars
		g2.drawString(carNumber.toString(), xLeft + CAR_NUMBER_X, yTop
				+ CAR_NUMBER_Y);
		g2.setColor(color);
		// draw the hook
		g2.drawLine((int) (xLeft + body.getWidth()),
				(int) (yTop + body.getHeight() + CAR_HOOK),
				(int) (xLeft + body.getWidth() + CAR_HOOK),
				(int) (yTop + body.getHeight() + CAR_HOOK));
		// draw the car
		g2.draw(body);
		g2.draw(frontTire);
		g2.draw(rearTire);
		g2.draw(frontWindshield);
		g2.draw(roofTop);
		g2.draw(rearWindshield);

		if (nextCar != null)
			nextCar.draw(g2);
	}

}
