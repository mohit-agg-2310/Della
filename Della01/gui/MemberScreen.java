package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import control.Controller;
import model.ActionItem;
import model.ActionItemManager;
import model.Member;
/**
 * <p>
 * Title: MemberScreen
 * </p>
 *
 * <p>
 * Description: The Della Member Screen code
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
public class MemberScreen extends JPanel {
	//---------------------------------------------------------------------------------------------------------------------
	// Member Screen constants


	//---------------------------------------------------------------------------------------------------------------------
	// Member Screen attributes

	private Controller theController = null;
	private ActionItemManager aiM = null;
	
	private DefaultListModel listModel = new DefaultListModel();
	private DefaultListModel listModel1 = new DefaultListModel();
	private DefaultListModel listModel2 = new DefaultListModel();
	
	boolean remove = false;

	//---------------------------------------------------------------------------------------------------------------------
	// Member Screen GUI elements
	JLabel memberLabel = new JLabel();

	JLabel NameLabel = new JLabel();
	JTextField NameTextField = new JTextField();
	
	JLabel addNameLabel = new JLabel();
	JLabel addNameLabel1 = new JLabel();
	JLabel addNameLabel2 = new JLabel();
	JLabel addNameLabel3 = new JLabel();
	
	JLabel removeNameLabel = new JLabel();
	JLabel removeNameLabel1 = new JLabel();
	JLabel removeNameLabel2 = new JLabel();
	
	JLabel clickLabel = new JLabel();
	JLabel clickLabel1 = new JLabel();
	
	JLabel addAffiliationLabel = new JLabel();
	JLabel addAffiliationLabel1 = new JLabel();
	JLabel addAffiliationLabel2 = new JLabel();
	JLabel addAffiliationLabel3 = new JLabel();	
	
	JLabel removeAffiliationLabel = new JLabel();
	JLabel removeAffiliationLabel1 = new JLabel();
	JLabel removeAffiliationLabel2 = new JLabel();
	JLabel removeAffiliationLabel3 = new JLabel();
		 
	JButton addToList = new JButton();
	ActionListener addButton = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { addMember(ae); }
	};
	JButton removeFromList = new JButton();
	ActionListener removeButton = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { removeMember(ae); }
	};
	
	JList list;
	JLabel individualList = new JLabel();
	JScrollPane indiScrollPane;// = new JScrollPane();
	
	JList list1;
	JLabel availableLable = new JLabel();
	JLabel availableLable1 = new JLabel();
	JScrollPane availableScrollPane;
	
	JList list2;
	JLabel currentLable = new JLabel();
	JLabel currentLable1 = new JLabel();
	JScrollPane currentScrollPane;
	
	JButton addAffiliation = new JButton();
	ActionListener addAff = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { addAffiliation(ae); }
	};
	JButton removeAffiliation = new JButton();
	ActionListener removeAff = new ActionListener() {
		public void actionPerformed(ActionEvent ae) { removeAffiliation(ae);}
	};
	
	//---------------------------------------------------------------------------------------------------------------------

	/**
	 * The MemberScreen class constructor.
	 * 
	 */
	public MemberScreen() {
		
		// Use a modified singleton pattern to access the application's state
		theController = Controller.getInstance();
		aiM = theController.getActionItemManager();
		System.out.println("Member is selected");
		// Set up all of the Graphical User Interface elements and place them on the screen
		guiInit();
	}

	/**
	 * Initialize each graphic element, position it on the screen, and add it to the layout.
	 * 
	 */
	private void guiInit() {
		this.setLayout(null);
		memberLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 14));
		memberLabel.setBorder(BorderFactory.createEtchedBorder());
		memberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		memberLabel.setText("Members");
		memberLabel.setBounds(new Rectangle(0, 0, 657, 20));
		
		NameLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		NameLabel.setText("Name of someone new (Last, First Middle)");
		NameLabel.setBounds(new Rectangle(6, 25, 250, 15));
		NameTextField.setText("");
		NameTextField.setBounds(new Rectangle(6, 40, 200, 22));
		//NameTextField.getDocument().addDocumentListener(aiChangeListener);
		
		addNameLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		addNameLabel.setText("To add a name to the list:");
		addNameLabel.setBounds(new Rectangle(6, 75, 150, 15));
		addNameLabel1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		addNameLabel1.setText("1.Click on the box above.");
		addNameLabel1.setBounds(new Rectangle(6, 90, 150, 15));
		addNameLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		addNameLabel2.setText("2.Type the name.");
		addNameLabel2.setBounds(new Rectangle(6, 105, 150, 15));
		addNameLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		addNameLabel3.setText("3.Click the \"Add to List\" button.");
		addNameLabel3.setBounds(new Rectangle(6, 120, 170, 15));
		
		removeNameLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		removeNameLabel.setText("To remove a name from the list:");
		removeNameLabel.setBounds(new Rectangle(6, 145, 200, 15));
		removeNameLabel1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		removeNameLabel1.setText("1.Click on the name to remove.");
		removeNameLabel1.setBounds(new Rectangle(6, 160, 200, 15));
		removeNameLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		removeNameLabel2.setText("2.Click on \"Remove from List\" button.");
		removeNameLabel2.setBounds(new Rectangle(6, 175, 200, 15));
		
		addAffiliationLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		addAffiliationLabel.setText("To add a team affiliation for an individual:");
		addAffiliationLabel.setBounds(new Rectangle(6, 200, 250, 15));
		addAffiliationLabel1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		addAffiliationLabel1.setText("1.Click on the name of the individual above right.");
		addAffiliationLabel1.setBounds(new Rectangle(6, 215, 250, 15));
		addAffiliationLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		addAffiliationLabel2.setText("2.Click on a team name in the list below.");
		addAffiliationLabel2.setBounds(new Rectangle(6, 230, 200, 15));
		addAffiliationLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		addAffiliationLabel3.setText("3.Click on \"Add affiliation\" button.");
		addAffiliationLabel3.setBounds(new Rectangle(6, 245, 200, 15));
		
		removeAffiliationLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		removeAffiliationLabel.setText("To remove a team affiliation for an individual:");
		removeAffiliationLabel.setBounds(new Rectangle(370, 200, 250, 15));
		removeAffiliationLabel1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		removeAffiliationLabel1.setText("1.Click on the name of the individual above.");
		removeAffiliationLabel1.setBounds(new Rectangle(370, 215, 250, 15));
		removeAffiliationLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		removeAffiliationLabel2.setText("2.Click on a team name in the list below.");
		removeAffiliationLabel2.setBounds(new Rectangle(370, 230, 200, 15));
		removeAffiliationLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		removeAffiliationLabel3.setText("3.Click on \"Remove affiliation\" button.");
		removeAffiliationLabel3.setBounds(new Rectangle(370, 245, 200, 15));
		
		addToList.setFont(new Font("Dialog", Font.BOLD, 11));
		addToList.setBounds(new Rectangle(250, 40, 170, 30));
		addToList.setText("Add to list \u2014>");
		addToList.addActionListener(addButton);
		
		removeFromList.setFont(new Font("Dialog", Font.BOLD, 11));
		removeFromList.setBounds(new Rectangle(250, 100, 170, 30));
		removeFromList.setText("<\u2014 Remove from List");
		removeFromList.addActionListener(removeButton);
		
		clickLabel.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		clickLabel.setText("Click on an individual's name");
		clickLabel.setBounds(new Rectangle(260, 160, 200, 15));
		clickLabel1.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		clickLabel1.setText("to see team affiliations.");
		clickLabel1.setBounds(new Rectangle(260, 175, 200, 15));
		
		individualList.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		individualList.setText("Individuals known by Della");
		individualList.setBounds(new Rectangle(450, 25, 250, 15));
		
		list = new JList(listModel); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//list.setLayoutOrientation(JList.VERTICAL_WRAP);
		//list.setVisibleRowCount(-1);
		indiScrollPane = new JScrollPane(list);
		indiScrollPane.setBounds(new Rectangle(450,40, 180, 150));
		//list.setBounds(new Rectangle(450,40, 180, 150));
		addItems();
		list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent lse) {
            	 if(list.getSelectedIndex()==-1)return;
            	if(!remove) {
            	availableLable1.setText(list.getSelectedValue().toString());
            	currentLable1.setText(list.getSelectedValue().toString());
            	 listModel1.removeAllElements();
            	 listModel2.removeAllElements();
            	
            	for ( int i = 0; i < aiM.member.size();i++){
            		if(aiM.member.get(i).getMemberName().equals(list.getSelectedValue().toString())) {
            			/*if(aiM.member.get(i).getAvailableTeams().size()!=0) {
            			for(int x=0;x<aiM.member.get(i).getAvailableTeams().size();x++) {
            			listModel1.addElement(aiM.member.get(i).getAvailableTeams().get(x));}
            			}*/
            			//if(aiM.member.get(i).getAvailableTeams().size()==0) {
            				if(aiM.teams.size()!=0) {
            					for(String te : aiM.teams) {
            						if(!(aiM.member.get(i).getCurrentTeams().contains(te))) {
            							//if(!(aiM.member.get(i).getAvailableTeams().contains(te))) 
            							{
            							listModel1.addElement(te);
            							if(!(aiM.member.get(i).getAvailableTeams().contains(te)))
            								aiM.member.get(i).addAvailableTeams(te);}
            						}
            					}
            				}
            			//}
            			if(!(aiM.member.get(i).getCurrentTeams().isEmpty())) {
            			for(int y=0;y<aiM.member.get(i).getCurrentTeams().size();y++) {
            			listModel2.addElement(aiM.member.get(i).getCurrentTeams().get(y));}}
            		}
      			} 
            }}
        });
		//Collections.sort(list);
		availableLable.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		availableLable.setText("Available teams for");
		availableLable.setBounds(new Rectangle(7, 270, 200, 15));
		availableLable1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		availableLable1.setText("");
		availableLable1.setBounds(new Rectangle(7, 285, 200, 15));
		
		list1 = new JList(listModel1); //data has type Object[]
		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//list1.setLayoutOrientation(JList.VERTICAL_WRAP);
		//list1.setVisibleRowCount(-1);
		availableScrollPane = new JScrollPane(list1);
		availableScrollPane.setBounds(new Rectangle(7,300, 180, 150));
		//list1.setBounds(new Rectangle(7,300, 180, 150));

		currentLable.setFont(new java.awt.Font("Dialog", Font.BOLD, 11));
		currentLable.setText("Current teams for");
		currentLable.setBounds(new Rectangle(450, 270, 200, 15));
		currentLable1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 11));
		currentLable1.setText("");
		currentLable1.setBounds(new Rectangle(450, 285, 200, 15));
		
		list2 = new JList(listModel2); //data has type Object[]
		list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//list2.setLayoutOrientation(JList.VERTICAL_WRAP);
		//list2.setVisibleRowCount(-1);
		currentScrollPane = new JScrollPane(list2);
		currentScrollPane.setBounds(new Rectangle(450,300, 180, 150));
		//list2.setBounds(new Rectangle(450,300, 180, 150));
		
		addAffiliation.setFont(new Font("Dialog", Font.BOLD, 11));
		addAffiliation.setBounds(new Rectangle(250, 330, 170, 30));
		addAffiliation.setText("Add affiliation \u2014>");
		addAffiliation.addActionListener(addAff);
		
		removeAffiliation.setFont(new Font("Dialog", Font.BOLD, 11));
		removeAffiliation.setBounds(new Rectangle(250, 380, 170, 30));
		removeAffiliation.setText("<\u2014 Remove affiliation");
		removeAffiliation.addActionListener(removeAff);
		
		
		//----------------------------------------------------------------------------
		// Add the objects to the layout
		
		
		this.add(memberLabel);
		this.add(NameLabel);
		this.add(NameTextField);
		this.add(addNameLabel);
		this.add(addNameLabel1);
		this.add(addNameLabel2);
		this.add(addNameLabel3);
		
		this.add(removeNameLabel);
		this.add(removeNameLabel1);
		this.add(removeNameLabel2);
		
		this.add(addAffiliationLabel);
		this.add(addAffiliationLabel1);
		this.add(addAffiliationLabel2);
		this.add(addAffiliationLabel3);
		
		this.add(clickLabel);
		this.add(clickLabel1);
		
		this.add(removeAffiliationLabel);
		this.add(removeAffiliationLabel1);
		this.add(removeAffiliationLabel2);
		this.add(removeAffiliationLabel3);
		
		this.add(addToList);
		this.add(removeFromList);
		
		this.add(individualList);
		this.add(indiScrollPane);
		//this.add(frame);
		
		this.add(availableLable);
		this.add(availableLable1);
		this.add(availableScrollPane);
		
		this.add(currentLable);
		this.add(currentLable1);
		this.add(currentScrollPane);
		
		this.add(addAffiliation);
		this.add(removeAffiliation);
		//this.add(list);
		//this.add(list1);
		//this.add(list2);
		
	}
	
	private void addMember(ActionEvent e) {
		if(NameTextField.getText().equals(null)||NameTextField.getText().equals(" "))return;
		if(aiM.members.contains(NameTextField.getText())) {
			JOptionPane.showMessageDialog(this, NameTextField.getText()+" already exists!!! ",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			NameTextField.setText("");
			return;
		}
		listModel.addElement(NameTextField.getText());
		//Collections.sort(listModel);
		aiM.addMembers(NameTextField.getText());
		Member m = new Member(NameTextField.getText()) ;
		aiM.member.add(m);
		NameTextField.setText("");
		addItems();
		theController.save();
			return;
		}
	
	private void removeMember(ActionEvent e) {
		 if(list.getSelectedIndex()==-1){
				JOptionPane.showMessageDialog(this, "No member has been selected for removing!!! ",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		remove = true;
		availableLable1.setText("");
    	currentLable1.setText("");
    	String rm = list.getSelectedValue().toString();
		listModel.removeElement(list.getSelectedValue());
		aiM.removeMembers(rm);
		for(int i=0;i<aiM.member.size();i++) {
			if(aiM.member.get(i).getMemberName().equals(rm)) {
				aiM.member.remove(i);
			}
		}
		remove = false;
		theController.save();
			return;
		}
	
	public void addItems() {
		listModel.clear();
		Collections.sort(aiM.members);
		for ( int i = 0; i < aiM.members.size(); i++ ){
			  listModel.addElement( aiM.members.get(i));
			}
		
	}
	
	private void addAffiliation(ActionEvent e) {
		 if(list1.getSelectedIndex()==-1){
				JOptionPane.showMessageDialog(this, "No team has been selected for adding!!! ",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		 if(listModel1.getSize()==1){
				JOptionPane.showMessageDialog(this, "A member may not be on more than ten teams",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		String x =list1.getSelectedValue().toString();
		String y = availableLable1.getText();
		for(int i=0;i<aiM.team.size();i++) {
			if(aiM.team.get(i).getTeamName().equals(x)) {
				if(aiM.team.get(i).getCurrentMembers().size()==10) {
					JOptionPane.showMessageDialog(this, "No more than ten members can belong to a team!",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
		System.out.println(list1.getSelectedValue().getClass());
		for(int i=0;i<aiM.member.size();i++) {
			if(aiM.member.get(i).getMemberName().equals(y)) {
				aiM.member.get(i).removeAvailableTeams(x);
				aiM.member.get(i).addCurrentTeams(x);
				
			}
		}
		for(int i=0;i<aiM.team.size();i++) {
			if(aiM.team.get(i).getTeamName().equals(x)) {
				aiM.team.get(i).addCurrentMembers(y); 
				aiM.team.get(i).removeAvailableMembers(y);
			}
		}
		listModel2.addElement(list1.getSelectedValue());
		listModel1.removeElement(list1.getSelectedValue());
		theController.save();
		return;
		}
	
	private void removeAffiliation(ActionEvent e) {
		 if(list2.getSelectedIndex()==-1){
				JOptionPane.showMessageDialog(this, "No team has been selected for removing!!! ",
						"Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		String x =list2.getSelectedValue().toString();
		String y = availableLable1.getText(); 
		for(int i=0;i<aiM.member.size();i++) {
			if(aiM.member.get(i).getMemberName().equals(y)) {
				aiM.member.get(i).addAvailableTeams(x);
				aiM.member.get(i).removeCurrentTeams(x);
			}
		}
		for(int i=0;i<aiM.team.size();i++) {
			if(aiM.team.get(i).getTeamName().equals(x)) {
				aiM.team.get(i).removeCurrentMembers(y); 
				aiM.team.get(i).addAvailableMembers(y);
			}
		}
		listModel1.addElement(list2.getSelectedValue());
		listModel2.removeElement(list2.getSelectedValue());
		theController.save();
		return;
		}

	
	
}
