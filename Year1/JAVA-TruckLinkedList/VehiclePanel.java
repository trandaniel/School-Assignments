import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class VehiclePanel extends JPanel {
	protected ArrayList<Car> cars;
	protected Truck truck;
	protected int currentCars;
	protected final int MAX_CARS = 5;
	protected Car selected;
	protected final int FRAME_WIDTH = 750;
	protected final int FRAME_HEIGHT = 750;

	/**
	 * 
	 * construct the panel
	 */
	public VehiclePanel() {
		currentCars = 0;
		cars = new ArrayList<Car>();

		/**
		 * inner class for a mouse adapter
		 * 
		 * @see MouseAdapter
		 */
		class MyListener extends MouseAdapter {

			/**
			 * @param e
			 *            the mouse event
			 * 
			 *            draws the vehicles and selects them if clicked on
			 * 
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				// paint all the vehicles
				repaint();
				if (getTruck() == null) {
					truck = new Truck(e.getX(), e.getY());
				} else {
					if (currentCars % MAX_CARS != 0 || currentCars == 0) {
						currentCars++;
						cars.add(new Car(e.getX(), e.getY(), currentCars));
					}
				}
				repaint();

				// repaint the cars if one is selected, paint it red
				for (Car car : cars) {
					car.color = Color.BLACK;
				}

				// if a car is selected paint it red
				for (Car car : cars) {
					if (car.contains(e.getX(), e.getY())) {
						selected = car;

						selected.color = Color.RED;
						repaint();
						break;
					}
				}
			}

			/**
			 * @param e
			 *            the mouse event
			 * 
			 *            when the mouse is released check if it intersects
			 *            another car if it does link it to the car
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				// if there is a selected car
				if (selected != null) {
					for (Car car : cars) {
						// if car overlaps another car, link to the car and exit
						// loop
						if (car.getBoundingBox().intersects(
								selected.getBoundingBox())
								&& (!car.getBoundingBox().equals(
										selected.getBoundingBox()))
								&& selected.linked == false) {
							selected.linkTo(car);
							repaint();
							break;
						}
						// if car overlaps with truck
						if (getTruck().getBoundingBox().intersects(
								selected.getBoundingBox())
								&& getTruck() != null && selected != null) {
							// if there is already a car, link it to the car
							if (getTruck().first != null) {
								selected.linkTo(getTruck().first);
								// otherwise link the car to the truck and exit
								// loop
							} else {
								selected.linkTo(getTruck());
								break;
							}
						}

					}
					if (selected.previousCar != null) {
						while (selected.previousCar != null) {
							selected = selected.previousCar;
						}
					}
					// if car is moved(dropped) outside the frame, return it to
					// position 0,0
					if (selected.getBoundingBox().x > FRAME_WIDTH
							|| selected.getBoundingBox().y > FRAME_HEIGHT)
						selected.move(0, 0);
				}
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				//
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				//
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//
			}

		}
		// add the listener to the panel
		MouseListener listener = new MyListener();
		this.addMouseListener(listener);

		/**
		 * inner class for mouse listener for the panel
		 * 
		 * @see MouseMotionListener
		 * 
		 */
		class MyMouseListener implements MouseMotionListener {
			/**
			 * @param e
			 *            the mouseevent
			 * 
			 *            check if there is a selected car, if there is drag it
			 */
			public void mouseDragged(MouseEvent e) {
				// if there is a car selected
				if (selected != null) {
					// while there is a previous car set selected to the
					// previous car
					while (selected.previousCar != null) {
						selected = selected.previousCar;
					}
					// if the selected car is not linked to a truck, move the
					// car
					if (selected.linkedToTruck == false) {
						selected.move(e.getX(), e.getY());
						repaint();
					}
				}
			}

			public void mouseMoved(MouseEvent event) {
				//
			}
		}
		// add motion listener to class
		MouseMotionListener listener1 = new MyMouseListener();
		addMouseMotionListener(listener1);

	}

	/**
	 * method to paint all the cars
	 * 
	 * @param g
	 *            the graphics component
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (truck != null) {
			truck.draw(g2);
		}
		if (!cars.isEmpty()) {
			for (Car car : cars) {
				car.draw(g2);
			}
		}

	}

	/**
	 * reset the simulator by clearing all the values
	 * 
	 */
	public void newSimulator() {
		cars.clear();
		currentCars = 0;
		selected = null;
		truck = null;
		removeAll();
		updateUI();
	}

	/**
	 * add the selected car if there is one to the front of the truck
	 */
	public void addFirst() {
		if (truck.first == null) {
			selected.linkTo(truck);
			repaint();
			return;
		}
		if (selected.linkedToTruck) {
			return;
		}
		Car truckLink = truck.first;
		Car first = selected;
		Car last = first;
		while (last.nextCar != null) {
			last = last.nextCar;
		}
		first.linkTo(truck);
		truckLink.linkTo(last);
		repaint();
	}

	/**
	 * 
	 * @return the truck
	 */
	public Truck getTruck() {
		// 
		return truck;
	}

	/**
	 * add the selected vehicle to the end of the truck if there is one
	 * 
	 */
	public void addLast() {
		if (selected.linkedToTruck == true) {
			return;
		}
		Car temp = truck.first;
		if (temp == null) {
			selected.linkTo(truck);
			repaint();
			return;
		}

		while (temp.nextCar != null) {
			if (temp.getBoundingBox().equals(temp.nextCar.getBoundingBox())) {
				return;
			}
			temp = temp.nextCar;
		}
		selected.linkTo(temp);
		repaint();

	}

	/**
	 * removes the first car linked to the truck
	 */
	public void removeFirst() {
		if (truck.first == null) {
			return;
		}
		if (truck.first.nextCar == null) {
			cars.remove(truck.first);
			truck.first = null;
			repaint();
			return;
		}
		truck.first = truck.first.nextCar;
		cars.remove(truck.first.previousCar);
		truck.first.move((int) (truck.xLeft + truck.boundingBox.getWidth()),
				truck.yTop - 3);
		repaint();

	}

	/**
	 * removes the last car linked to the truck
	 */
	public void removeLast() {
		if (truck.first == null) {
			return;
		}
		if (truck.first.nextCar == null) {
			cars.remove(truck.first);
			truck.first = null;
			repaint();
			return;
		}
		Car temp = truck.first;
		while (temp.nextCar != null) {
			temp = temp.nextCar;
		}
		temp.previousCar.nextCar = null;
		cars.remove(temp);
		repaint();
	}
}
