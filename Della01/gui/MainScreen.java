package gui;


import java.awt.*;

import javax.swing.*;

import model.ActionItemManager;

import control.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>
 * Title: MainScreen
 * </p>
 *
 * <p>
 * Description:  A manually generated screen for Della.  The navagation buttons are defined here and the 
 * 		other subordinate screens fill in the subscreen details (e.g. ConsoleScreen and ActionItemScreen)
 * </p>
 *
 * <p>
 * Copyright: Copyright © 2005, 2006, 2007, 2022
 * </p>
 *
 * @author Harry Sameshima, Lynn Robert Carter, Mohit
 * @version 1.00
 * @version 1.01
 */
public class MainScreen extends JFrame {
	//---------------------------------------------------------------------------------------------------------------------
	// Main (common) Screen constants

	//---------------------------------------------------------------------------------------------------------------------
	// Main (common) Screen attributes

	JPanel contentPane;
	JPanel buttonPanel = new JPanel();
	BorderLayout theBorderLayout = new BorderLayout();
	FlowLayout theFlowLayout = new FlowLayout();

	JButton consoleButton = new JButton();
	ActionListener consoleButtonActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { showConsole(ae); }
	};
	JButton actionItemsButton = new JButton();
	ActionListener actionItemsButtonActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { showActionItems(ae); }
	};
	JButton membersButton = new JButton();
	ActionListener membersButtonActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { showMembers(ae); }
	};
	JButton teamsButton = new JButton();
	ActionListener teamsButtonActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { showTeams(ae); }
	};
	JButton quitButton = new JButton();
	ActionListener quitButtonActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { doQuit(ae); }
	};

	private ConsoleScreen consoleScreen = new ConsoleScreen();
	private ActionItemScreen actionItemScreen = null;
	private MemberScreen memberScreen = null;
	private TeamScreen teamScreen = null;
	JPanel mainPanel = consoleScreen;				// Default the system to start at the console screen

	private Controller theController = null;

	public MainScreen() {
		// Force a read of the controller from the persistent store
		theController = Controller.getInstance();

		// Now finish our initialization
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		guiInit();
	}

	/**
	 * The GUI initialization.
	 *
	 */
	private void guiInit() {
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(theBorderLayout);

		// The code really only needs 600 by 475, but the Windows look an feel steals
		// some of these pixels, so we make it a little larger so both the PC and the
		// Macintosh versions look reasonable.
		setSize(new Dimension(657,530));
		setTitle("Della 00");
		// set up the screen changing buttons
		buttonPanel.setLayout(theFlowLayout);

		// The spaces before and after the labels is used to make the buttons larger
		consoleButton.setText("   Console   ");
		consoleButton.addActionListener(consoleButtonActionListner);
		actionItemsButton.setText("   Action Items   ");
		actionItemsButton.addActionListener(actionItemsButtonActionListner);
		membersButton.setText("   Members   ");
		membersButton.addActionListener(membersButtonActionListner);
		teamsButton.setText("   Teams   ");
		teamsButton.addActionListener(teamsButtonActionListner);
		quitButton.setText("   Quit   ");
		quitButton.addActionListener(quitButtonActionListner);

		buttonPanel.add(consoleButton, null);
		buttonPanel.add(actionItemsButton, null);
		buttonPanel.add(membersButton, null);
		buttonPanel.add(teamsButton, null);
		buttonPanel.add(quitButton, null);

		contentPane.add(buttonPanel, java.awt.BorderLayout.NORTH);
		contentPane.add(mainPanel, java.awt.BorderLayout.CENTER);
	}

	/**
	 * This shared routine checks to see if edits are pending and warns the user if editing work might be lost.
	 *
	 */
	private boolean okayToChangeScreens(){
		ActionItemManager aiM = theController.getActionItemManager();
		if (aiM.getEditChangesPending()) {
			int x = JOptionPane.showConfirmDialog(this,
					" \n" +
					"A Screen Change or a Quit has been requested and there     \n" +
					"          are pending edits to this Action Item!\n\n" +
					"Do you want to discard these edits?\n\n" +
					"Click \"Yes\" to discard these edits.\n\n" +
					"Click \"No\" to return to Action Items Screen.\n ",
					"Screen Change or Quit Requested with Pending Edits!",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.YES_NO_OPTION);
			if (x==0) {
				aiM.setEditChangesPending(false);
				return true;
			}
			else return false;
		}
		else
			return true;
	}

	/**
	 * Code to change to the Console screen
	 * @param e ActionEvent
	 */
	private void showConsole(ActionEvent e) {
		if (okayToChangeScreens()){
			contentPane.remove(mainPanel);
			if (consoleScreen == null) {
				consoleScreen = new ConsoleScreen();
			}
			mainPanel = consoleScreen;
			consoleScreen.loadScreen();
			contentPane.add(mainPanel, java.awt.BorderLayout.CENTER);
			this.repaint();
			this.validate();
		}
	}

	/**
	 * Code to change over to the Action Items Screen
	 * @param e ActionEvent
	 */
	private void showActionItems(ActionEvent e) {
		if (okayToChangeScreens()) {
		contentPane.remove(mainPanel);
		if (actionItemScreen == null) {
			actionItemScreen = new ActionItemScreen();
		}
		actionItemScreen.loadScreen();
		mainPanel = actionItemScreen;
		contentPane.add(mainPanel, java.awt.BorderLayout.CENTER);
		this.repaint();
		this.validate();
	}
	}
	/**
	 * Code to change over to the Members Screen
	 * @param e ActionEvent
	 */
	private void showMembers(ActionEvent e) {
		if (okayToChangeScreens()){
			contentPane.remove(mainPanel);
			if (memberScreen == null) {
				memberScreen = new MemberScreen();
			}
			mainPanel = memberScreen;
			contentPane.add(mainPanel, java.awt.BorderLayout.CENTER);
			this.repaint();
			this.validate();
		}
	}

	/**
	 * Code to change over to the Teams Screen
	 * @param e ActionEvent
	 */
	private void showTeams(ActionEvent e) {
		if (okayToChangeScreens()){
			contentPane.remove(mainPanel);
			if (teamScreen == null) {
				teamScreen = new TeamScreen();
			}
			mainPanel = teamScreen;
			contentPane.add(mainPanel, java.awt.BorderLayout.CENTER);;
			this.repaint();
			this.validate();
		}
	}

	/**
	 * Code to handle the "Quit" button.  If Della's state has changed, ask if we want to save it.
	 * @param e ActionEvent
	 */
	private void doQuit(ActionEvent e) {
		if (okayToChangeScreens()){
			if (theController.getDirtyFlag()) {
				int x = JOptionPane.showConfirmDialog(this,
						" \n" +
						"A Quit has been requested and there are updated     \n" +
						"          Action Items that have not been saved!\n\n" +
						"Do you want to save these Action Items?\n\n" + 
						"Click \"Yes\" to save the changed Action Items.\n\n" + 
						"Click \"No\" to ignore the changes.",
						"Quit requested with unsaved Action Items!\n",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.YES_NO_OPTION);
				if (x == 0) { 
					theController.save(); 
				}
			}
			//theController.save(); 
			System.exit(0);
		}
	}
}
