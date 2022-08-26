package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import control.*;

import model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
/**
 * <p>
 * Title: ActionItemScreen
 * </p>
 * 
 * <p>
 * Description: A manually generated Action Item Screen for Della
 * </p>
 * 
 * <p>
 * Copyright: Copyright © 2022
 * </p>
 * 
 * @author Lynn Robert Carter, Mohit
 * @version 1.00
 * @version 1.01
 * Many thanks to Harry Sameshima for his original work.
 */
public class ActionItemScreen extends JPanel {
	//---------------------------------------------------------------------------------------------------------------------
	// Action Item Screen constants

	public static final int noItemSelected = -1;

	//---------------------------------------------------------------------------------------------------------------------
	// Action Item Screen attributes

	private Boolean updatingGUI = false;
	private Controller theController = null;
	private ActionItemManager aiM = null;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static final String[] inclusionStrings = {"All Action Items", "Open Action Items","Closed Action Items"};
	public static final String[] sortingDirection = {"Small to Large", "Large to Small"};
	public static final String[] firstSort = {"None", "Due Date","Creation Date","Assigned Member","Assigned Team"};
	public static final String[] secondSort = {"None", "Due Date","Creation Date","Assigned Member","Assigned Team"};
	//public DefaultListModel listModel = new DefaultListModel();
	public long[] duration;//= new long[aiM.actionItems.size()];
	public ArrayList<String> items = new ArrayList<String>();

	//---------------------------------------------------------------------------------------------------------------------
	// Action Item Screen GUI elements
	JLabel actionItemLabel = new JLabel();
	
	JLabel actionItemsLabel = new JLabel();
	JComboBox actionItemComboBox = new JComboBox();
	ActionListener actionItemSelectorActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { 
			getTheValues(); 
			}
	};
	JLabel selectActionItemLabel = new JLabel();
	
	JLabel inclusionLabel = new JLabel();
	JComboBox inclusionComboBox = new JComboBox(inclusionStrings);
	ActionListener inclusionActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { updateComboBox(); 
		}
	};

	JLabel sortingLabel = new JLabel();
	JComboBox sortingComboBox = new JComboBox(sortingDirection);
	ActionListener sortDirection = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { sortingDirection(); }
	};
	
	JLabel firstSortingLabel = new JLabel();
	JComboBox firstSortingComboBox = new JComboBox(firstSort);
	ActionListener firstfactorAL = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { firstFactor(); }
	};
	
	JLabel secondSortingLabel = new JLabel();
	JComboBox secondSortingComboBox = new JComboBox(secondSort);
	ActionListener secondfactorAL = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { secondFactor(); }
	};
	
	JLabel selectedLabel = new JLabel();
	JLabel nameLabel = new JLabel();
	JTextField nameTextField = new JTextField();
	JLabel descriptionLabel = new JLabel();
	JScrollPane descriptionScrollPane = new JScrollPane();
	JTextArea descriptionTextArea = new JTextArea();
	JLabel resolutionLabel = new JLabel();
	JScrollPane resolutionScrollPane = new JScrollPane();
	JTextArea resolutionTextArea = new JTextArea();

	// Unsaved updated fields
	DocumentListener aiChangeListener = new DocumentListener() {
		public void changedUpdate(DocumentEvent de){ checkForUnsavedUpdates(); }
		public void insertUpdate(DocumentEvent de){ checkForUnsavedUpdates(); }
		public void removeUpdate(DocumentEvent de){ checkForUnsavedUpdates(); }
	};
	JLabel unsavedChangesLabel = new JLabel();

	JLabel datesLabel = new JLabel();
	JLabel creationLabel = new JLabel();
	JLabel creationValueLabel = new JLabel();
	JLabel dueDateLabel = new JLabel();
	JTextField dueDateTextField = new JTextField();
	JLabel formatLabel = new JLabel();
	JLabel actionItemLabel2 = new JLabel();
	JLabel statusLabel = new JLabel();
	JComboBox statusComboBox = new JComboBox(ActionItemManager.statusStrings);
	ActionListener statusSelectorActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { checkForUnsavedUpdates(); }
	};
	
	JLabel memberLabel = new JLabel();
	JComboBox memberComboBox = new JComboBox();
	ActionListener memberActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { 
			/*String x= memberComboBox.getSelectedItem().toString();
			for(Member m : aiM.member) {
				if(m.getMemberName().equals(x)) {
					teamComboBox.setSelectedItem(m.getCurrentTeams());
				}
			}*/
		}
	};

	JLabel teamLabel = new JLabel();
	JComboBox teamComboBox = new JComboBox();
	ActionListener teamActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { 
			/*String x= teamComboBox.getSelectedItem().toString();
			for(Team m : aiM.team) {
				if(m.getTeamName().equals(x)) {
					teamComboBox.setSelectedItem(m.getCurrentMembers());
				}
			}*/			
		}
	};
	// Action Buttons
	JButton updateButton = new JButton();
	ActionListener updateButtonActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { updateActionItem(ae); }
	};
	JButton clearButton = new JButton();
	ActionListener clearButtonActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { clearActionItemForm(ae); }
	};
	JButton createButton = new JButton();
	ActionListener createButtonActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { createActionItem(ae); }
	};
	JButton deleteButton = new JButton();
	ActionListener deleteButtonActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			confirmDelete(); 
		}
	};
	//---------------------------------------------------------------------------------------------------------------------


	/**
	 * The ActionItemScreen class constructor.
	 * 
	 */
	public ActionItemScreen() {
		// Use a modified singleton pattern to access the application's state
		theController = Controller.getInstance();
		aiM = theController.getActionItemManager();
		duration= new long[aiM.actionItems.size()];
		// Set up all of the Graphical User Interface elements and place them on the screen
		guiInit();
		
		// Initialize the screen with the current action item
		loadScreen();
	}

	protected void confirmDelete() {
		int x = JOptionPane.showConfirmDialog(this,
				" \n" +
				"Do you really wish to delete the sected item? \n" +
				
				"Click \"Yes\" to delete the Action Items.\n\n" + 
				"Click \"No\" to ignore the deletion process.",
				"Delete requested for the current Action Item!\n",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.YES_NO_OPTION);
		if (x==0)
		deleteActionItem();
		
	}

	/**
	 * Initialize each graphic element, position it on the screen, and add it to the loayout.
	 * 
	 */
	private void guiInit() {
		// Updating the GUI
		updatingGUI = true;
		
		// Set all of the graphical elements in this screen by adding them to the layout
		this.setLayout(null);

		actionItemLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 14));
		actionItemLabel.setBorder(BorderFactory.createEtchedBorder());
		actionItemLabel.setHorizontalAlignment(SwingConstants.CENTER);
		actionItemLabel.setText("Action Items");
		actionItemLabel.setBounds(new Rectangle(0, 0, 657, 20));
		
		actionItemsLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		actionItemsLabel.setText("Action Items:");
		actionItemsLabel.setBounds(new Rectangle(6, 25, 123, 15));

		actionItemComboBox.setBounds(new Rectangle(7, 41, 630, 25));
		actionItemComboBox.addActionListener(actionItemSelectorActionListner);
		
		selectActionItemLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		selectActionItemLabel.setText("Select an Action Item from the pull-down list above to examine and update it.");
		selectActionItemLabel.setBounds(new Rectangle(6, 65, 600, 15));

		inclusionLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		inclusionLabel.setText("Inclusion Factor:");
		inclusionLabel.setBounds(new Rectangle(45, 85, 123, 15));
		
		inclusionComboBox.setBounds(new Rectangle(46, 100, 140, 25));
		inclusionComboBox.addActionListener(inclusionActionListener);
		
		sortingLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		sortingLabel.setText("Sorting Direction:");
		sortingLabel.setBounds(new Rectangle(200, 85, 123, 15));
		
		sortingComboBox.setBounds(new Rectangle(201, 100, 130, 25));
		sortingComboBox.addActionListener(sortDirection);
		
		firstSortingLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		firstSortingLabel.setText("First Sorting Factor:");
		firstSortingLabel.setBounds(new Rectangle(350, 85, 123, 15));
		
		firstSortingComboBox.setBounds(new Rectangle(351, 100, 130, 25));
		firstSortingComboBox.addActionListener(firstfactorAL);
		
		secondSortingLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		secondSortingLabel.setText("Second Sorting Factor:");
		secondSortingLabel.setBounds(new Rectangle(500, 85, 123, 15));
		
		secondSortingComboBox.setBounds(new Rectangle(501, 100, 130, 25));
		secondSortingComboBox.addActionListener(secondfactorAL);
		
		selectedLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		selectedLabel.setText("Selected Action Item:");
		selectedLabel.setBounds(new Rectangle(6, 145, 123, 15));
		nameLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		nameLabel.setText("Name:");
		nameLabel.setBounds(new Rectangle(7, 165, 42, 15));
		nameTextField.setText("");
		nameTextField.setBounds(new Rectangle(46, 165, 390, 22));
		nameTextField.getDocument().addDocumentListener(aiChangeListener);

		descriptionLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		descriptionLabel.setText("Description:");
		descriptionLabel.setBounds(new Rectangle(6, 190, 69, 15));
		descriptionScrollPane.setBounds(new Rectangle(7, 210, 430, 75));
		descriptionScrollPane.getViewport().add(descriptionTextArea);
		descriptionTextArea.setText("");
		descriptionTextArea.getDocument().addDocumentListener(aiChangeListener);

		resolutionLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		resolutionLabel.setText("Resolution:");
		resolutionLabel.setBounds(new Rectangle(6, 295, 73, 15));
		resolutionScrollPane.setBounds(new Rectangle(7, 315, 430, 75));
		resolutionScrollPane.getViewport().add(resolutionTextArea);
		resolutionTextArea.setToolTipText("");
		resolutionTextArea.setText("");
		resolutionTextArea.getDocument().addDocumentListener(aiChangeListener);

		datesLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		datesLabel.setText("Dates");
		datesLabel.setBounds(new Rectangle(450, 175, 34, 16));

		creationLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		creationLabel.setText("Creation:");
		creationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		creationLabel.setBounds(new Rectangle(469, 195, 51, 16));
		creationValueLabel.setText("");
		creationValueLabel.setBounds(new Rectangle(528, 195, 85, 16));

		dueDateLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		dueDateLabel.setText("Due:");
		dueDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dueDateLabel.setBounds(new Rectangle(469, 217, 51, 16));
		dueDateTextField.setBounds(new Rectangle(524, 215, 90, 20));
		dueDateTextField.getDocument().addDocumentListener(aiChangeListener);
		formatLabel.setFont(new java.awt.Font("Dialog", Font.PLAIN, 10));
		formatLabel.setText("Use yyyy-mm-dd format");
		formatLabel.setBounds(new Rectangle(495, 238, 125, 11));

		actionItemLabel2.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		actionItemLabel2.setText("Action Item");
		actionItemLabel2.setBounds(new Rectangle(450, 260, 67, 15));

		statusLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		statusLabel.setText("Status:");
		statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statusLabel.setBounds(new Rectangle(469, 277, 51, 16));
		statusComboBox.setBounds(new Rectangle(524, 275, 90, 25));
		statusComboBox.addActionListener(statusSelectorActionListner);
		
		memberLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		memberLabel.setText("Assigned to Member:");
		memberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		memberLabel.setBounds(new Rectangle(450, 310, 120, 16));
		memberComboBox.setBounds(new Rectangle(451, 327, 150, 25));
		memberComboBox.addActionListener(memberActionListner);
		
		teamLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		teamLabel.setText("Assigned to Team:");
		teamLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		teamLabel.setBounds(new Rectangle(450, 352, 120, 16));
		teamComboBox.setBounds(new Rectangle(451, 368, 150, 25));
		teamComboBox.addActionListener(teamActionListner);

		updateButton.setFont(new Font("Dialog", Font.BOLD, 11));
		updateButton.setBounds(new Rectangle(3, 395, 170, 30));
		updateButton.setText("Update This Action Item");
		updateButton.addActionListener(updateButtonActionListner);

		clearButton.setFont(new Font("Dialog", Font.BOLD, 11));
		clearButton.setBounds(new Rectangle(173, 395, 126, 30));
		clearButton.setText("Clear This Form");
		clearButton.addActionListener(clearButtonActionListner);
		
		createButton.setFont(new Font("Dialog", Font.BOLD, 11));
		createButton.setBounds(new Rectangle(300, 395, 170, 30));
		createButton.setText("Create a New Action Item");
		createButton.addActionListener(createButtonActionListner);
		
		deleteButton.setFont(new Font("Dialog", Font.BOLD, 11));
		deleteButton.setBounds(new Rectangle(471, 395, 170, 30));
		deleteButton.setText("Delete This Action Item");
		deleteButton.addActionListener(deleteButtonActionListner);

		unsavedChangesLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		unsavedChangesLabel.setBounds(new Rectangle(250, 430, 200, 15));
		unsavedChangesLabel.setText("");
		unsavedChangesLabel.setForeground(Color.red);		
		
		for(String names:aiM.actionItemStrings) {
			System.out.println(names);
			actionItemComboBox.addItem(names);	
			
		}
		
		
		//----------------------------------------------------------------------------
		// Add the objects to the layout
		this.add(actionItemLabel);
		
		this.add(actionItemsLabel);
		this.add(actionItemComboBox);
		this.add(selectActionItemLabel);
		
		this.add(inclusionLabel);
		this.add(inclusionComboBox);
		
		this.add(sortingLabel);
		this.add(sortingComboBox);
		
		this.add(firstSortingLabel);
		this.add(firstSortingComboBox);
		
		this.add(secondSortingLabel);
		this.add(secondSortingComboBox);

		this.add(selectedLabel);
		this.add(nameLabel);
		this.add(nameTextField);
		this.add(descriptionLabel);
		this.add(descriptionScrollPane);
		this.add(resolutionLabel);
		this.add(resolutionScrollPane);

		this.add(datesLabel);
		this.add(creationLabel);
		this.add(creationValueLabel);
		this.add(dueDateLabel);
		this.add(dueDateTextField);
		this.add(formatLabel);
		this.add(actionItemLabel2);
		this.add(statusLabel);
		this.add(statusComboBox);
		this.add(memberLabel);
		this.add(memberComboBox);
		this.add(teamLabel);
		this.add(teamComboBox);

		this.add(updateButton);
		this.add(clearButton);
		this.add(createButton);
		this.add(deleteButton);
		
		this.add(unsavedChangesLabel);

		// Done updating the GUI
		updatingGUI = false;
	}
	


	/**
	 * Clear the current action item and the attribute related combo boxes
	 * 
	 */
	private void clearAI() {
		updatingGUI = true;
		aiM.clearCurrentActionItem();
		nameTextField.setText("");
		descriptionTextArea.setText("");
		resolutionTextArea.setText("");
		creationValueLabel.setText("");
		dueDateTextField.setText("");

		// Select the Open status
		statusComboBox.setSelectedIndex(ActionItemManager.statusOpen);
		updatingGUI = false;
	}

	/**
	 * Process a "Clear This Form" button click request
	 * Clear out the current action item and inform the user if this results in unsaved changes
	 * 
	 * @param e ActionEvent
	 */
	private void clearActionItemForm(ActionEvent e) {
		// Reset the current Action Item Fields
		clearAI();
		
		theController.setDirtyFlag(true);
		checkForUnsavedUpdates();
	}

	/**
	 * Update the current action item in memory
	 * 
	 * @param e ActionEvent
	 */
	private void updateActionItem(ActionEvent e) {
		//if(aiM.actionItemStrings.isEmpty()) 
		if(actionItemComboBox.getSelectedIndex()==-1|| aiM.actionItemStrings.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "No action item has been selected for updating",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			return;}
		// Tell the ActionItemManager to save the update
		try {
			aiM.updateActionItem(nameTextField.getText(),
					descriptionTextArea.getText(),
					resolutionTextArea.getText(),
					statusComboBox.getSelectedItem().toString(),
					dueDateTextField.getText(),
					memberComboBox.getSelectedItem().toString(),
					teamComboBox.getSelectedItem().toString()
					);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// This code is just for Della00 to simulate the effect of the "create" button being pressed
		// remove this for Della01
		creationValueLabel.setText(dateFormat.format(new Date()));
		
		theController.setDirtyFlag(true);
		checkForUnsavedUpdates();
		theController.save();
	}
	
	private void deleteActionItem() {
		//if(aiM.actionItemStrings.isEmpty()) 
		if(actionItemComboBox.getSelectedIndex()==-1||aiM.actionItemStrings.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "No action item has been selected for deletion",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			return;}
		// Tell the ActionItemManager to save the update
		try {
			aiM.deleteActionItem(nameTextField.getText(),
					descriptionTextArea.getText(),
					resolutionTextArea.getText(),
					statusComboBox.getSelectedItem().toString(),
					dueDateTextField.getText(),
					memberComboBox.getSelectedItem().toString(),
					teamComboBox.getSelectedItem().toString()
					);			
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println(ex.getMessage());
			
		}
		nameTextField.setText("");
		descriptionTextArea.setText("");
		resolutionTextArea.setText("");
		statusComboBox.setSelectedIndex(0);
		dueDateTextField.setText("");
		memberComboBox.setSelectedIndex(0);
		teamComboBox.setSelectedIndex(0);
		
		actionItemComboBox.removeAllItems();//Item(nameTextField.getText());
		for(String names:aiM.actionItemStrings) {
			System.out.println(names);
			actionItemComboBox.addItem(names);	
			}
		theController.save();
		return;
	}
	
	/**
	 * Update the current action item in memory
	 * 
	 * @param e ActionEvent
	 */

	private void createActionItem(ActionEvent e) {
		// Tell the ActionItemManager to save the update
		String member, team;
		if(memberComboBox.getSelectedItem()==null){member="-No member is Selected-";}
		else
			member=memberComboBox.getSelectedItem().toString();
		
		if(teamComboBox.getSelectedItem()==null){team="-No team is Selected-";}
		else
			team=teamComboBox.getSelectedItem().toString();
		try {
			aiM.createActionItem(nameTextField.getText(),
					descriptionTextArea.getText(),
					resolutionTextArea.getText(),
					statusComboBox.getSelectedItem().toString(),
					dueDateTextField.getText(),
					member,
					team
					);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		creationValueLabel.setText(dateFormat.format(new Date()));

		actionItemComboBox.addItem(nameTextField.getText());
		System.out.println("Added an item "+nameTextField.getText());
		theController.setDirtyFlag(true);
		//theController.setActionItemManager(aiM);
		//theController.save();
		checkForUnsavedUpdates();
		theController.save();
	}

	/**
	 * Fill the screen with the values of the current action item, if we have one, and display it.
	 */
	public void loadScreen() {
		
		updatingGUI = true;
		// Fetch the current action item.  If there isn't one, leave now
		ActionItem ai = aiM.getCurrentActionItem();
		if (ai == null) {
			clearAI();
			updatingGUI = true;
			statusComboBox.setSelectedIndex(ActionItemManager.statusOpen);
			creationValueLabel.setText("");
			dueDateTextField.setText("");
		}
		else {
			// Define the text fields
			updatingGUI = true;
			nameTextField.setText(ai.getActionItemName());
			descriptionTextArea.setText(ai.getDescription());
			descriptionTextArea.setCaretPosition(0);
			resolutionTextArea.setText(ai.getResolution());
			resolutionTextArea.setCaretPosition(0);
		}
		// Define the status combobox value
		for (int i = 0; i < ActionItemManager.statusStrings.length; ++i)
			if (ai.getStatus().compareTo(ActionItemManager.statusStrings[i]) == 0) {
				statusComboBox.setSelectedIndex(i);
				break;
			}
		if(memberComboBox.getItemCount()<aiM.members.size()) {
			memberComboBox.removeAllItems();
		for(String names:aiM.members) {
			System.out.println(names);
			memberComboBox.addItem(names);	}
		}
		
		if(teamComboBox.getItemCount()<aiM.teams.size()) {
			teamComboBox.removeAllItems();
		for(String names:aiM.teams) {
			System.out.println(names);
			teamComboBox.addItem(names);	}
		}
		
		if(items.isEmpty()) {
			for(String names:aiM.actionItemStrings) {
				items.add(names);
			}
		}
		// Define the creation and due dates
		if (ai.getCreatedDate() != null)
			creationValueLabel.setText(dateFormat.format(ai.getCreatedDate()));
		else
			creationValueLabel.setText("");
		if (ai.getDueDate() != null)
			dueDateTextField.setText(dateFormat.format(ai.getDueDate()));
		else
			dueDateTextField.setText("");
		
		updatingGUI = false;
	}
	
	/**
	 * Any number of events has occurred that could change the display.  See if the current edit values still
	 * match the current action item.  If so, then no warning is needed.  If not, then given a warning (red
	 * text) that informs the user that there are edits to the action item that have not been saved.
	 * 
	 */
	private void checkForUnsavedUpdates(){
		if (updatingGUI) return;
		if (nameTextField.getText().equals(aiM.getCurrentActionItem().getActionItemName()) &&
				descriptionTextArea.getText().equals(aiM.getCurrentActionItem().getDescription()) &&
				resolutionTextArea.getText().equals(aiM.getCurrentActionItem().getResolution()) && 
				dueDateTextField.getText().equals(aiM.getCurrentActionItem().getDueDate()!=null?dateFormat.format(aiM.getCurrentActionItem().getDueDate()):"") &&
				(	(statusComboBox.getSelectedIndex() == 0 && aiM.getCurrentActionItem().getStatus().equals("")) ||
						(statusComboBox.getSelectedIndex() == 0 && aiM.getCurrentActionItem().getStatus().equals("Open")) ||
						(statusComboBox.getSelectedIndex() == 1 && aiM.getCurrentActionItem().getStatus().equals("Closed"))
				)
		){
			unsavedChangesLabel.setText("");
			aiM.setEditChangesPending(false);
		}
		else {
			unsavedChangesLabel.setText("There are unsaved changes!");
			aiM.setEditChangesPending(true);
		}	
	}
	
	public void getTheValues() {
		int selected = actionItemComboBox.getSelectedIndex();
		if(items.isEmpty()) {
			for(String names:aiM.actionItemStrings) {
				items.add(names);
			}
		}
		if (selected==-1)return;
		
		System.out.println("Getting "+items.get(selected));
		
		for(ActionItem at : aiM.actionItems) {
		if(items.get(selected).equals(at.getActionItemName())) {
			nameTextField.setText(at.getActionItemName().toString());
			descriptionTextArea.setText(at.getDescription().toString());
			resolutionTextArea.setText(at.getResolution().toString());
			creationValueLabel.setText(dateFormat.format(at.getCreatedDate()));
//			dueDateTextField.setText(dateFormat.format(at.getDueDate()));
			statusComboBox.setSelectedIndex((at.getStatus().equals("Open"))?0:1);
			memberComboBox.setSelectedItem(at.getMembers());
			teamComboBox.setSelectedItem(at.getTeams());
			aiM.setCurrentActionItem(at);
		}
		
		}
		checkForUnsavedUpdates();
	}
	
	
	
	public void sortingDirection() {
		int sCB = sortingComboBox.getSelectedIndex();
		int fsCB = firstSortingComboBox.getSelectedIndex();
		int ssCB = secondSortingComboBox.getSelectedIndex();
		
		int i=0;
		//getting the duration of all the ActionItems
		for(ActionItem m : aiM.actionItems) {
			duration[i]=(m.getDueDate().getTime()- m.getCreatedDate().getTime());
			System.out.println("Duration of "+m.getActionItemName().toString()+" is "+duration[i]);
			i++;
		}
		switch(sCB) {
		case 0:
			for(int m = 0; m< aiM.actionItems.size();m++) {
				for(int n = m+1; n< aiM.actionItems.size();n++) {
					if(duration[m]>duration[n]) {
						Collections.swap(aiM.actionItems,m,n);
						Collections.swap(aiM.actionItemStrings,m,n);
						long temp = duration[m];
						duration[m]=duration[n];
						duration[n]=temp;
						System.out.print("Selected Sorting direction : Small to large");
					}
				}
			}
			break;
		case 1:
			for(int m = 0; m< aiM.actionItems.size();m++) {
				for(int n = m+1; n< aiM.actionItems.size();n++) {
					if(duration[m]<duration[n]) {
						Collections.swap(aiM.actionItems,m,n);
						Collections.swap(aiM.actionItemStrings,m,n);
						long temp = duration[m];
						duration[m]=duration[n];
						duration[n]=temp;
						System.out.print("Selected Sorting direction : Large to small");
					}
				}
			}
			break;
		}
		updateComboBox();
		/*actionItemComboBox.removeAllItems();
		for(String names:aiM.actionItemStrings) {
			System.out.println(names);
			actionItemComboBox.addItem(names);	
			}*/
	}
	
	public void firstFactor() {
		int fsCB = firstSortingComboBox.getSelectedIndex();
		int x,y;
		if(fsCB==0) {
			secondSortingComboBox.setSelectedIndex(0);
		secondSortingComboBox.disable();
		}
		//enable second sorting factor if first sorting factor is not empty
		if(fsCB!=0)
			secondSortingComboBox.enable();
		switch(fsCB) {
		case 0:
			break;
		case 1:{
			System.out.print("First sorting factor :  Due Date ; Second Sorting Factor: none ; Sorting direction : Small to large");
			for(x=0;x<aiM.actionItems.size();x++) {
				for(y=x+1;y<aiM.actionItems.size();y++) {
					if(aiM.actionItems.get(x).getDueDate().after(aiM.actionItems.get(y).getDueDate())) {
						Collections.swap(aiM.actionItems,x,y);
						Collections.swap(aiM.actionItemStrings,x,y);
						long temp = duration[x];
						duration[x]=duration[y];
						duration[y]=temp;
					}
				}
			}
			break;}
		case 2:{
			for(x=0;x<aiM.actionItems.size();x++) {
				for(y=x+1;y<aiM.actionItems.size();y++) {
					if(aiM.actionItems.get(x).getCreatedDate().after(aiM.actionItems.get(y).getCreatedDate())) {
						Collections.swap(aiM.actionItems,x,y);
						Collections.swap(aiM.actionItemStrings,x,y);
						long temp = duration[x];
						duration[x]=duration[y];
						duration[y]=temp;
					}
				}
			}
			System.out.print("First sorting factor :  Creation Date ; Second Sorting Factor: none ; Sorting direction : Small to large");
			break;}
		case 3:{
			for(x=0;x<aiM.actionItems.size();x++) {
				for(y=x+1;y<aiM.actionItems.size();y++) {
					if(aiM.actionItems.get(x).getMembers().charAt(0)>aiM.actionItems.get(y).getMembers().charAt(0)) {
						Collections.swap(aiM.actionItems,x,y);
						Collections.swap(aiM.actionItemStrings,x,y);
						long temp = duration[x];
						duration[x]=duration[y];
						duration[y]=temp;
					}
				}
			}
			break;}
		case 4:{
			for(x=0;x<aiM.actionItems.size();x++) {
				for(y=x+1;y<aiM.actionItems.size();y++) {
					if(aiM.actionItems.get(x).getTeams().charAt(0)>aiM.actionItems.get(y).getTeams().charAt(0)) {
						Collections.swap(aiM.actionItems,x,y);
						Collections.swap(aiM.actionItemStrings,x,y);
						long temp = duration[x];
						duration[x]=duration[y];
						duration[y]=temp;
					}
				}
			}
			break;	}
		}
		updateComboBox();
		
	}
	public void secondFactor() {
		int fsCB = firstSortingComboBox.getSelectedIndex();
		int ssCB = secondSortingComboBox.getSelectedIndex();
		int x,y;
		if(fsCB==0) {
			secondSortingComboBox.setSelectedIndex(0);
		secondSortingComboBox.disable();
		}
		//enable second sorting factor if first sorting factor is not empty
		if(fsCB!=0)
			secondSortingComboBox.enable();
		switch(ssCB) {
		case 0:
			break;
		case 1:{
			System.out.print("First sorting factor :  Due Date ; Second Sorting Factor: none ; Sorting direction : Small to large");
			for(x=0;x<aiM.actionItems.size();x++) {
				for(y=1;y<aiM.actionItems.size();y++) {
					if(aiM.actionItems.get(x).getDueDate().after(aiM.actionItems.get(y).getDueDate())) {
						Collections.swap(aiM.actionItems,x,y);
						Collections.swap(aiM.actionItemStrings,x,y);
						long temp = duration[x];
						duration[x]=duration[y];
						duration[y]=temp;
					}
				}
			}
			break;}
		case 2:{
			for(x=0;x<aiM.actionItems.size();x++) {
				for(y=1;y<aiM.actionItems.size();y++) {
					if(aiM.actionItems.get(x).getCreatedDate().after(aiM.actionItems.get(y).getCreatedDate())) {
						Collections.swap(aiM.actionItems,x,y);
						Collections.swap(aiM.actionItemStrings,x,y);
						long temp = duration[x];
						duration[x]=duration[y];
						duration[y]=temp;
					}
				}
			}
			System.out.print("First sorting factor :  Creation Date ; Second Sorting Factor: none ; Sorting direction : Small to large");
			break;}
		case 3:
			for(x=0;x<aiM.actionItems.size();x++) {
				for(y=x+1;y<aiM.actionItems.size();y++) {
					if(aiM.actionItems.get(x).getMembers().charAt(0)>aiM.actionItems.get(y).getMembers().charAt(0)) {
						Collections.swap(aiM.actionItems,x,y);
						Collections.swap(aiM.actionItemStrings,x,y);
						long temp = duration[x];
						duration[x]=duration[y];
						duration[y]=temp;
					}
				}
			}
			break;
		case 4:
			for(x=0;x<aiM.actionItems.size();x++) {
				for(y=x+1;y<aiM.actionItems.size();y++) {
					if(aiM.actionItems.get(x).getTeams().charAt(0)>aiM.actionItems.get(y).getTeams().charAt(0)) {
						Collections.swap(aiM.actionItems,x,y);
						Collections.swap(aiM.actionItemStrings,x,y);
						long temp = duration[x];
						duration[x]=duration[y];
						duration[y]=temp;
					}
				}
			}
			break;	
		}
		updateComboBox();
	}
	
	public void updateComboBox() {
		int sCB = sortingComboBox.getSelectedIndex();
		int fsCB = firstSortingComboBox.getSelectedIndex();
		int ssCB = secondSortingComboBox.getSelectedIndex();
		String names[]=new String[aiM.actionItemStrings.size()];
		int k=0;
		actionItemComboBox.removeAllItems();
		items.removeAll(items);
		for(ActionItem act : aiM.actionItems) {
			if(fsCB==0)
				names[k]=act.getActionItemName();
			if(fsCB==1) {
				if(ssCB==0) {
					names[k]=dateFormat.format(act.getDueDate())+" : "+act.getActionItemName();
				}
				if(ssCB==1) {
					names[k]=dateFormat.format(act.getDueDate())+" : "+dateFormat.format(act.getDueDate())+" : "+act.getActionItemName();
				}
				if(ssCB==2) {
					names[k]=dateFormat.format(act.getDueDate())+" : "+dateFormat.format(act.getCreatedDate())+" : "+act.getActionItemName();
				}
				if(ssCB==3) {
					names[k]=dateFormat.format(act.getDueDate())+" : "+act.getMembers()+" : "+act.getActionItemName();
				}
				if(ssCB==4) {
					names[k]=dateFormat.format(act.getDueDate())+" : "+act.getTeams()+" : "+act.getActionItemName();
				}
			}	
			if(fsCB==2) {
				if(ssCB==0) {
					names[k]=dateFormat.format(act.getCreatedDate())+" : "+act.getActionItemName();
				}
				if(ssCB==1) {
					names[k]=dateFormat.format(act.getCreatedDate())+" : "+dateFormat.format(act.getDueDate())+" : "+act.getActionItemName();
				}
				if(ssCB==2) {
					names[k]=dateFormat.format(act.getCreatedDate())+" : "+dateFormat.format(act.getCreatedDate())+" : "+act.getActionItemName();
				}
				if(ssCB==3) {
					names[k]=dateFormat.format(act.getCreatedDate())+" : "+act.getMembers()+" : "+act.getActionItemName();
				}
				if(ssCB==4) {
					names[k]=dateFormat.format(act.getCreatedDate())+" : "+act.getTeams()+" : "+act.getActionItemName();
				}
			}
				
			if(fsCB==3) {
				if(ssCB==0) {
					names[k]=act.getMembers()+" : "+act.getActionItemName();
				}
				if(ssCB==1) {
					names[k]=act.getMembers()+" : "+dateFormat.format(act.getDueDate())+" : "+act.getActionItemName();
				}
				if(ssCB==2) {
					names[k]=act.getMembers()+" : "+dateFormat.format(act.getCreatedDate())+" : "+act.getActionItemName();
				}
				if(ssCB==3) {
					names[k]=act.getMembers()+" : "+act.getMembers()+" : "+act.getActionItemName();
				}
				if(ssCB==4) {
					names[k]=act.getMembers()+" : "+act.getTeams()+" : "+act.getActionItemName();
				}
			}
				
			if(fsCB==4){
				if(ssCB==0) {
					names[k]=act.getTeams()+" : "+act.getActionItemName();
				}
				if(ssCB==1) {
					names[k]=act.getTeams()+" : "+dateFormat.format(act.getDueDate())+" : "+act.getActionItemName();
				}
				if(ssCB==2) {
					names[k]=act.getTeams()+" : "+dateFormat.format(act.getCreatedDate())+" : "+act.getActionItemName();
				}
				if(ssCB==3) {
					names[k]=act.getTeams()+" : "+act.getMembers()+" : "+act.getActionItemName();
				}
				if(ssCB==4) {
					names[k]=act.getTeams()+" : "+act.getTeams()+" : "+act.getActionItemName();
				}
			}
			
			if(inclusionComboBox.getSelectedIndex()==0) {
				System.out.println(names[k]);
				actionItemComboBox.addItem(names[k]);
				items.add(act.getActionItemName());
			}
			if(inclusionComboBox.getSelectedIndex()==1) {
				if(act.getStatus().equals("Open")) {
				System.out.println("Getting all open status");
				System.out.println(names[k]);
				actionItemComboBox.addItem(names[k]);
				items.add(act.getActionItemName());}
			}
			if(inclusionComboBox.getSelectedIndex()==2) {
				if(act.getStatus().equals("Closed")) {
				System.out.println("Getting all closed status");
				System.out.println(names[k]);
				actionItemComboBox.addItem(names[k]);
				items.add(act.getActionItemName());}
			}
		k++;
		}
	}
	
}