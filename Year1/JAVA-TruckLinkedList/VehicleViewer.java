import javax.swing.JFrame;

/**
 * Viewer to add the panel and frame
 */
public class VehicleViewer {
	public static void main(String[] args) {
		JFrame frame = new VehicleFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setTitle("Vehicle");
		frame.setVisible(true);
	}
}
