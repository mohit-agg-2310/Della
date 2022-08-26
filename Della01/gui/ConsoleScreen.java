package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import control.Controller;
import model.ActionItem;
import model.ActionItemManager;

/**
 * <p>
 * Title: ConsoleScreen
 * </p>
 *
 * <p>
 * Description:  A manually generated action item screen for Della
 * </p>
 *
 * <p>
 * Copyright: Copyright © 2022
 * </p>
 *
 * @author Lynn Robert Carter, Mohit
 * Many thanks to Harry Sameshima for his original work.
 * @version 1.00
 * @version 1.01
 */
public class ConsoleScreen extends JPanel {
	//---------------------------------------------------------------------------------------------------------------------
	// Console Screen constants

	//---------------------------------------------------------------------------------------------------------------------
	// Console Screen attributes

	//---------------------------------------------------------------------------------------------------------------------
	// Console Screen GUI elements
	JLabel consoleLabel = new JLabel();

	private Controller theController = null;
	private ActionItemManager aiM = null;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static final String[] inclusionStrings = {"All Action Items", "Open Action Items","Closed Action Items"};
	public static final String[] sortingDirection = {"Small to Large", "Large to Small"};
	public static final String[] firstSort = {"None", "Due Date","Creation Date","Assigned Member","Assigned Team"};
	public static final String[] secondSort = {"None", "Due Date","Creation Date","Assigned Member","Assigned Team"};
	public long[] duration;//= new long[aiM.actionItems.size()];
	DefaultListModel listModel = new DefaultListModel();
	public ArrayList<String> items = new ArrayList<String>();
	

	
	JLabel actionItemsLabel = new JLabel();
	JScrollPane actionItemScrollPane;// = new JScrollPane();
	JList list;
	JTextArea actionItemTextArea = new JTextArea();
	ActionListener actionItemSelectorActionListner = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { 
			//fillActionItemComboBox();
			getTheValue(); }
	};
	
	JLabel inclusionLabel = new JLabel();
	JComboBox inclusionComboBox = new JComboBox(inclusionStrings);
	ActionListener inclusionAL = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { updateComboBox(); }
	};
	
	JLabel sortingLabel = new JLabel();
	JComboBox sortingComboBox = new JComboBox(sortingDirection);
	ActionListener sortDirection = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { sortingDirection(); }
	};
	
	JLabel firstSortingLabel = new JLabel();
	JComboBox firstSortingComboBox = new JComboBox(firstSort);
	ActionListener firstfactor = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { firstFactor(); }
	};
	
	JLabel secondSortingLabel = new JLabel();
	JComboBox secondSortingComboBox = new JComboBox(secondSort);
	ActionListener secondfactor = new ActionListener() {
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

	
	JLabel datesLabel = new JLabel();
	JLabel creationLabel = new JLabel();
	JLabel creationValueLabel = new JLabel();
	JLabel dueDateLabel = new JLabel();
	JLabel dueValueLabel = new JLabel();
	
	JLabel actionItemLabel2 = new JLabel();
	JLabel statusLabel = new JLabel();
	JLabel statusValueLabel = new JLabel();
	
	JLabel memberLabel = new JLabel();
	JLabel memberValueLabel = new JLabel();
	
	JLabel teamLabel = new JLabel();
	JLabel teamValueLabel = new JLabel();
		
	JLabel copyrightLabel = new JLabel();
	//---------------------------------------------------------------------------------------------------------------------

	/**
	 * The ConsoleScreen class constructor.
	 * 
	 */
	public ConsoleScreen() {
		// Use a modified singleton pattern to access the application's state
				theController = Controller.getInstance();
				aiM = theController.getActionItemManager();
				duration= new long[aiM.actionItems.size()];
		// Set up all of the Graphical User Interface elements and position them on the screen
		guiInit();
		// Initialize the screen with the current action item
				loadScreen();
	
	}

	/**
	 * Initialize each graphic element, position it on the screen, and add it to the loayout.
	 * 
	 */
	private void guiInit() {
		this.setLayout(null);
		consoleLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 14));
		consoleLabel.setBorder(BorderFactory.createEtchedBorder());
		consoleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		consoleLabel.setText("Console");
		consoleLabel.setBounds(new Rectangle(0, 0, 657, 20));

		copyrightLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 10));
		//copyrightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		copyrightLabel.setText("Copyright © 2022 Mohit");
		copyrightLabel.setBounds(new Rectangle(400, 428, 645, 15));
		
		actionItemsLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		actionItemsLabel.setText("Action Items:");
		actionItemsLabel.setBounds(new Rectangle(6, 25, 123, 15));

		//list = new JList(aiM.actionItemStrings.toArray()); //data has type Object[]
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(-1);
		actionItemScrollPane= new JScrollPane(list);
		actionItemScrollPane.setBounds(new Rectangle(7, 41, 430, 100));
		//actionItemScrollPane.getViewport().add(actionItemTextArea);
		//actionItemTextArea.setText("");
		//putItems();
		list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent lse) {
               // updateComboBox();
               // String name = list.getSelectedValue().toString();
                int f = list.getSelectedIndex();
                if(f==-1)return;
        		for(ActionItem at : aiM.actionItems) {
        		if(at.getActionItemName().equals(items.get(f))) {
        			nameTextField.setText(at.getActionItemName().toString());
        			descriptionTextArea.setText(at.getDescription().toString());
        			resolutionTextArea.setText(at.getResolution().toString());
        			creationValueLabel.setText(dateFormat.format(at.getCreatedDate()));
        			dueValueLabel.setText(dateFormat.format(at.getDueDate()));
        			statusValueLabel.setText(at.getStatus().toString());
        			memberValueLabel.setText(at.getMembers());
        			teamValueLabel.setText(at.getTeams());
        			aiM.setCurrentActionItem(at);
        		}
        		
        		}
            }
        });
		//actionItemScrollPane.getViewport().addMouseListener(getTheValue());
		
		sortingLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		sortingLabel.setText("Sorting Direction:");
		sortingLabel.setBounds(new Rectangle(450, 25, 123, 15));
		
		sortingComboBox.setBounds(new Rectangle(451, 40, 150, 25));
		sortingComboBox.addActionListener(sortDirection);
		
		firstSortingLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		firstSortingLabel.setText("First Sorting Factor:");
		firstSortingLabel.setBounds(new Rectangle(450, 70, 123, 15));
		
		firstSortingComboBox.setBounds(new Rectangle(451, 85, 150, 25));
		firstSortingComboBox.addActionListener(sortDirection);
		
		secondSortingLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		secondSortingLabel.setText("Second Sorting Factor:");
		secondSortingLabel.setBounds(new Rectangle(450, 115, 150, 15));
		
		secondSortingComboBox.setBounds(new Rectangle(451, 130, 150, 25));
		secondSortingComboBox.addActionListener(sortDirection);
		
		inclusionLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		inclusionLabel.setText("Inclusion Factor:");
		inclusionLabel.setBounds(new Rectangle(450, 160, 150, 15));
		
		inclusionComboBox.setBounds(new Rectangle(451, 175, 150, 25));
		inclusionComboBox.addActionListener(sortDirection);
		
		selectedLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		selectedLabel.setText("Selected Action Item:");
		selectedLabel.setBounds(new Rectangle(6, 145, 123, 15));
		nameLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		nameLabel.setText("Name:");
		nameLabel.setBounds(new Rectangle(7, 165, 42, 15));
		nameTextField.setText("");
		nameTextField.setBounds(new Rectangle(46, 165, 390, 22));
		//nameTextField.getDocument().addDocumentListener(aiChangeListener);

		descriptionLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		descriptionLabel.setText("Description:");
		descriptionLabel.setBounds(new Rectangle(6, 190, 69, 15));
		descriptionScrollPane.setBounds(new Rectangle(7, 210, 430, 75));
		descriptionScrollPane.getViewport().add(descriptionTextArea);
		descriptionTextArea.setText("");
		//descriptionTextArea.getDocument().addDocumentListener(aiChangeListener);

		resolutionLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		resolutionLabel.setText("Resolution:");
		resolutionLabel.setBounds(new Rectangle(6, 295, 73, 15));
		resolutionScrollPane.setBounds(new Rectangle(7, 315, 430, 75));
		resolutionScrollPane.getViewport().add(resolutionTextArea);
		resolutionTextArea.setToolTipText("");
		resolutionTextArea.setText("");
		//resolutionTextArea.getDocument().addDocumentListener(aiChangeListener);

		datesLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		datesLabel.setText("Dates");
		datesLabel.setBounds(new Rectangle(450, 206, 34, 16));

		creationLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		creationLabel.setText("Creation:");
		creationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		creationLabel.setBounds(new Rectangle(469, 225, 51, 16));
		creationValueLabel.setText("");
		creationValueLabel.setBounds(new Rectangle(528, 225, 85, 16));

		dueDateLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		dueDateLabel.setText("Due:");
		dueDateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dueDateLabel.setBounds(new Rectangle(469, 243, 51, 16));
		dueValueLabel.setText("");
		dueValueLabel.setBounds(new Rectangle(528, 243, 85, 16));
		
		actionItemLabel2.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		actionItemLabel2.setText("Action Item");
		actionItemLabel2.setBounds(new Rectangle(450, 260, 67, 15));

		statusLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		statusLabel.setText("Status:");
		statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		statusLabel.setBounds(new Rectangle(469, 275, 51, 16));
		statusValueLabel.setText("");
		statusValueLabel.setBounds(new Rectangle(528, 275, 85, 16));
		
		memberLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		memberLabel.setText("Assign to Member:");
		//memberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		memberLabel.setBounds(new Rectangle(450, 292, 150, 16));
		memberValueLabel.setText("");
		memberValueLabel.setBounds(new Rectangle(451, 308, 150, 16));
		
		teamLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		teamLabel.setText("Assign to Team:");
		//teamLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		teamLabel.setBounds(new Rectangle(450, 325, 150, 16));
		teamValueLabel.setText("");
		teamValueLabel.setBounds(new Rectangle(451, 341, 150, 16));
		
		//----------------------------------------------------------------------------
		// Add the objects to the layout
		
		
		this.add(actionItemsLabel);
		this.add(actionItemScrollPane);
		//this.add(selectActionItemLabel);
		
		this.add(sortingLabel);
		this.add(sortingComboBox);
		
		this.add(inclusionLabel);
		this.add(inclusionComboBox);
		
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
		this.add(dueValueLabel);
		//this.add(formatLabel);
		this.add(actionItemLabel2);
		this.add(statusLabel);
		this.add(statusValueLabel);
		
		this.add(memberLabel);
		this.add(memberValueLabel);
		
		this.add(teamLabel);
		this.add(teamValueLabel);

		this.add(consoleLabel);
		this.add(copyrightLabel);
		
		// Done updating the GUI
				
	}
	
	public void loadScreen() {
		duration= new long[aiM.actionItems.size()];
		updateComboBox();
		//System.out.println("LoadScreen Console");
	}
	
	private void getTheValue() {
		//String selected =list.getSelectedValue().toString();
		
		int i=list.getSelectedIndex();
		System.out.println("Getting "+list.getSelectedValue());
		int j=0;
		for(ActionItem at : aiM.actionItems) {
		if(i==j) {
			nameTextField.setText(at.getActionItemName().toString());
			descriptionTextArea.setText(at.getDescription().toString());
			resolutionTextArea.setText(at.getResolution().toString());
			creationValueLabel.setText(dateFormat.format(at.getCreatedDate()));
			dueValueLabel.setText(dateFormat.format(at.getDueDate()));
			statusValueLabel.setText(at.getStatus().toString());
			memberValueLabel.setText(at.getMembers());
			teamValueLabel.setText(at.getTeams());
			aiM.setCurrentActionItem(at);
		}
		j++;
		}
		
	}
	/*public void putItems() {
		System.out.println("Console is selected");
		result = "";    
		for(int i = 0; i < aiM.actionItems.size(); i++)
		{
		    result += aiM.actionItemStrings.get(i).toString()+"\n";
		}
		actionItemTextArea.setText(result);
		
	}*/
	
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
		listModel.clear();
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
				listModel.addElement(names[k]);
				items.add(act.getActionItemName());
			}
			if(inclusionComboBox.getSelectedIndex()==1) {
				if(act.getStatus().equals("Open")) {
				System.out.println("Getting all open status");
				System.out.println(names[k]);
				listModel.addElement(names[k]);
				items.add(act.getActionItemName());}
			}
			if(inclusionComboBox.getSelectedIndex()==2) {
				if(act.getStatus().equals("Closed")) {
				System.out.println("Getting all closed status");
				System.out.println(names[k]);
				listModel.addElement(names[k]);
				items.add(act.getActionItemName());}
			}
		k++;
		}
		
	}
}