
import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class GameViewer {

	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 500;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Predator vs Prey");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new GamePanel();
		frame.add(panel, BorderLayout.CENTER);

		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
	}
}
