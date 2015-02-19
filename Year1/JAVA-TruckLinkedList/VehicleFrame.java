import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.*;

public class VehicleFrame extends JFrame {
	private static final int FRAME_WIDTH = 750;
	private static final int FRAME_HEIGHT = 750;
	private JMenuItem newViewer, exitViewer, randomize;
	private JMenuItem up, down, left, right;
	private JMenuItem addFirst, addLast, removeFirst, removeLast;
	private JMenuBar menuBar;
	private JMenu file, edit, moveTruck, list;
	private JPanel panel;

	/**
	 * construct a frame
	 */
	public VehicleFrame() {
		setSize(FRAME_WIDTH, FRAME_HEIGHT);

		// create a menubar object
		menuBar = new JMenuBar();

		// create File menu
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		// create new and exit and add to File menu
		newViewer = new JMenuItem("New", KeyEvent.VK_N);
		exitViewer = new JMenuItem("Exit", KeyEvent.VK_X);

		// add items to File
		file.add(newViewer);
		file.add(exitViewer);

		// create new Edit menu
		edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);

		// create a submenu Move
		moveTruck = new JMenu("Move");
		moveTruck.setMnemonic(KeyEvent.VK_M);

		// add menu items to the submenu Move
		up = new JMenuItem("Up", KeyEvent.VK_UP);
		down = new JMenuItem("Down", KeyEvent.VK_DOWN);
		left = new JMenuItem("Left", KeyEvent.VK_LEFT);
		right = new JMenuItem("Right", KeyEvent.VK_RIGHT);

		// add direction to the submenu Move
		moveTruck.add(up);
		moveTruck.add(down);
		moveTruck.add(left);
		moveTruck.add(right);

		// create a randomize menu item
		randomize = new JMenuItem("Randomize", KeyEvent.VK_R);

		// add menu items to Edit
		edit.add(moveTruck);
		edit.add(randomize);

		// create a new menu List
		list = new JMenu("List");
		list.setMnemonic(KeyEvent.VK_S);

		// create new menuItems for List
		addFirst = new JMenuItem("Add First", KeyEvent.VK_D);
		addLast = new JMenuItem("Add Last", KeyEvent.VK_L);
		removeFirst = new JMenuItem("Remove First", KeyEvent.VK_A);
		removeLast = new JMenuItem("Remove Last", KeyEvent.VK_Z);

		// add menu items to menu List
		list.add(addFirst);
		list.add(addLast);
		list.add(removeFirst);
		list.add(removeLast);

		// add File Edit and List menu's to the menubar
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(list);

		// set action commands for listener
		newViewer.setActionCommand("new");
		exitViewer.setActionCommand("exit");
		up.setActionCommand("up");
		down.setActionCommand("down");
		left.setActionCommand("left");
		right.setActionCommand("right");
		randomize.setActionCommand("random");
		addFirst.setActionCommand("addFirst");
		addLast.setActionCommand("addLast");
		removeFirst.setActionCommand("removeFirst");
		removeLast.setActionCommand("removeLast");

		/**
		 * Listener for all of the menu items
		 * 
		 */
		class MenuListener implements ActionListener {
			Random random = new Random();

			/**
			 * when a menu item is clicked check the action that was fired
			 * 
			 * 
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

				// create new simulation
				if (e.getActionCommand().equals("new")) {
					((VehiclePanel) panel).newSimulator();
					return;
				}

				// exit simulation
				if (e.getActionCommand().equals("exit")) {
					System.exit(0);
				}

				// if there is no truck dont do anything
				if (((VehiclePanel) panel).getTruck() == null) {
					return;
				}

				// move truck up
				if (e.getActionCommand().equals("up")) {
					((VehiclePanel) panel).getTruck().move(
							((VehiclePanel) panel).getTruck().xLeft,
							((VehiclePanel) panel).getTruck().yTop - 25);
					((VehiclePanel) panel).repaint();
					return;
				}

				// move truck down
				if (e.getActionCommand().equals("down")) {
					((VehiclePanel) panel).getTruck().move(
							((VehiclePanel) panel).getTruck().xLeft,
							((VehiclePanel) panel).getTruck().yTop + 25);
					((VehiclePanel) panel).repaint();
					return;
				}

				// move truck left
				if (e.getActionCommand().equals("left")) {
					((VehiclePanel) panel).getTruck().move(
							((VehiclePanel) panel).getTruck().xLeft - 25,
							((VehiclePanel) panel).getTruck().yTop);
					((VehiclePanel) panel).repaint();
					return;
				}

				// move truck right
				if (e.getActionCommand().equals("right")) {
					((VehiclePanel) panel).getTruck().move(
							((VehiclePanel) panel).getTruck().xLeft + 25,
							((VehiclePanel) panel).getTruck().yTop);
					((VehiclePanel) panel).repaint();
					return;
				}

				// randomize truck location
				if (e.getActionCommand().equals("random")) {
					((VehiclePanel) panel).getTruck().move(random.nextInt(400),
							random.nextInt(650));
					((VehiclePanel) panel).repaint();
					return;
				}

				// add selected car if there is one to the front of the truck
				if (e.getActionCommand().equals("addFirst")) {
					((VehiclePanel) panel).addFirst();
					return;
				}

				// add selected element if there is one to the back of the truck
				if (e.getActionCommand().equals("addLast")) {
					((VehiclePanel) panel).addLast();
					return;

				}

				// remove the first car of the truck
				if (e.getActionCommand().equals("removeFirst")) {
					((VehiclePanel) panel).removeFirst();
					return;
				}

				// remove the last car of the truck
				if (e.getActionCommand().equals("removeLast")) {
					((VehiclePanel) panel).removeLast();
					return;
				}

			}

		}

		// add the panel
		panel = new VehiclePanel();
		setJMenuBar(menuBar);
		add(panel);

		// add listener to all of the menuitems
		ActionListener listener = new MenuListener();
		newViewer.addActionListener(listener);
		exitViewer.addActionListener(listener);
		up.addActionListener(listener);
		down.addActionListener(listener);
		left.addActionListener(listener);
		right.addActionListener(listener);
		randomize.addActionListener(listener);
		addFirst.addActionListener(listener);
		addLast.addActionListener(listener);
		removeFirst.addActionListener(listener);
		removeLast.addActionListener(listener);

	}
}
