package model;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JList;

import control.Controller;
public class Member {
	
	//---------------------------------------------------------------------------------------------------------------------
		// Attributes

		private String memberName;
		private  ArrayList<String> availableTeams;
		private  ArrayList<String> currentTeams;
		private ActionItemManager aiM = null;
		private Controller theController = null;
		//---------------------------------------------------------------------------------------------------------------------

		/**
		 * The Member class constructors.
		 *
		 */
		public Member() {
			memberName =  "";
			availableTeams = new ArrayList<String>();
			currentTeams = new ArrayList<String>();
//			theController = Controller.getInstance();
			aiM = theController.getActionItemManager();
			
		}

		public Member(String member) {
			theController = Controller.getInstance();
			aiM = theController.getActionItemManager();
			
			memberName = member;
			availableTeams = new ArrayList<String>();//aiM.teams;
			currentTeams = new ArrayList<String>();
		}
		
		public Member(String member, ArrayList at, ArrayList ct) {

			memberName = member;
			availableTeams = at;
			currentTeams = ct;
		}

		// Just the usual getters and setters
		public String getMemberName() { return memberName; }

		public ArrayList getAvailableTeams() { return availableTeams; }

		public ArrayList getCurrentTeams() { return currentTeams; }

		public void setMemberName(String x) { memberName = x; }

		public void setAvailableTeams(ArrayList at) { 	availableTeams = at; }
		
		public void addAvailableTeams(String at) { 	availableTeams.add(at); }
		
		public void removeAvailableTeams(String at) { 	availableTeams.remove(at); }
		
		public void setCurrentTeams(ArrayList ct) { currentTeams = ct; }
		
		public void addCurrentTeams(String at) { 	currentTeams.add(at); }
		
		public void removeCurrentTeams(String at) { 	currentTeams.remove(at); }

		
}
