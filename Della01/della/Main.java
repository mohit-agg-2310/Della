package della;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;

import gui.*;

/**
 * <p>
 * Title: Della
 * </p>
 *
 * <p>
 * Description: An issue and action logging and tracking manager
 * </p>
 *
 * <p>
 * Copyright: Copyright © 2005, 2006
 * </p>
 *
 * @author Harry Sameshima; Modified by Lynn Robert Carter
 * @version 1.00
 */
public class Main {

	/**
	 * Construct and show the application.
	 */
	public Main() {
		MainScreen frame = new MainScreen();

		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation( (screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		frame.setVisible(true);
	}

	/**
	 * Application entry point.
	 *
	 * @param args String[]
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {public void run() {new Main();}});
	}
}