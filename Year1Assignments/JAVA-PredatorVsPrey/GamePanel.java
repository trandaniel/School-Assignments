
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Predator predator;
	private final static int NUM_OF_PREY = 25;
	private final static int NUM_OF_PREYB = 25;
	private ArrayList<MoveableShape> preys;
	private ArrayList<MoveableShape> preysB;
	private static final int DELAY = 25;
	private static final int SECOND = 1000;
	private Timer t;
	private int score;
	//set delay for frame to builds
	private static int timeInSeconds, timeInMinutes, timeSoFar;
	private String timePrint;

	// create the panel for the game
	public GamePanel() {
		score = 0;
		preys = new ArrayList<MoveableShape>();
		preysB = new ArrayList<MoveableShape>();
		for (int i = 0; i < NUM_OF_PREY; i++) {
			preys.add(new Prey());
		}
		for (int i = 0; i < NUM_OF_PREYB; i++) {
			preysB.add(new PreyB());
		}
		predator = new Predator();
		

		/*
		 * inner class that listens to a timer and repaints after a certian delay
		 */
		class TimerListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				timeSoFar += DELAY;
				repaint();
			}
		}
		
		
		ActionListener timerListener = new TimerListener();
		t = new Timer(DELAY, timerListener);

		/*
		 * inner class that listens to mouse input to change direction and start the game
		 */
		class MyListener extends MouseAdapter {
			public void mousePressed(MouseEvent event) {
				if (event.getButton() == MouseEvent.BUTTON1) {
					predator.setDirection(predator.getDirection() - 1);
				}
				if (event.getButton() == MouseEvent.BUTTON2) {
					t.start();
					timeInSeconds = 0;
					timeSoFar = 0;
				}
				if (event.getButton() == MouseEvent.BUTTON3) {
					predator.setDirection(predator.getDirection() + 1);
				}
			}
		}
		MouseListener listener = new MyListener();
		this.addMouseListener(listener);

	}

	/*
	 * paint component to draw the frame
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (MoveableShape prey : preys) {
			prey.draw(g2);
			if (predator.collide(prey)) {
				preys.remove(prey);
				score++;
				break;
			}
		}
		
		for (MoveableShape preyB : preysB) {
			preyB.moveAway(predator.getDirection(), predator.getX(),
						predator.getY());
			preyB.draw(g2);
			if (predator.collide(preyB)) {
				preysB.remove(preyB);
				score++;
				break;
			}
		}
		predator.draw(g2);
		g2.setColor(Color.BLACK);
		g2.drawString("Score: " + Integer.toString(score), 150, 10);
		//print time in minutes:seconds format
		if (timeSoFar % SECOND == 0) {
			timeSoFar = 0;
			timeInSeconds++;
		}
		if (timeInSeconds == 60) {
			timeInSeconds = 0;
			timeInMinutes++;
		}
		timePrint = "Time: " + timeInMinutes + ":" + timeInSeconds;
		g2.drawString(timePrint, 0, 10);
		//stop game when all preys are caught
		if (score == NUM_OF_PREY + NUM_OF_PREYB) {
			t.stop();
		}
	}
}