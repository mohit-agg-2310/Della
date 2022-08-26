package model;

import java.util.ArrayList;

import control.Controller;

public class Team {

	//---------------------------------------------------------------------------------------------------------------------
			// Attributes

			private String teamName;
			private  ArrayList<String> availableMembers;
			private  ArrayList<String> currentMembers;
			private ActionItemManager aiM = null;
			private Controller theController = null;
			//---------------------------------------------------------------------------------------------------------------------

			/**
			 * The Member class constructors.
			 *
			 */
			public Team() {
				teamName =  "";
				availableMembers = new ArrayList<String>();
				currentMembers = new ArrayList<String>();
				theController = Controller.getInstance();
				aiM = theController.getActionItemManager();
				
			}

			public Team(String t) {
				theController = Controller.getInstance();
				aiM = theController.getActionItemManager();
				
				teamName = t;
				availableMembers =new ArrayList<String>();// aiM.members;
				currentMembers = new ArrayList<String>();
			}
			
			public Team(String t, ArrayList at, ArrayList ct) {

				teamName = t;
				availableMembers = at;
				currentMembers = ct;
			}

			// Just the usual getters and setters
			public String getTeamName() { return teamName; }

			public ArrayList getAvailableMembers() { return availableMembers; }

			public ArrayList getCurrentMembers() { return currentMembers; }

			public void setTeamName(String x) { teamName = x; }

			public void setAvailableMembers(ArrayList at) { 	availableMembers = at; }
			
			public void addAvailableMembers(String at) { 	availableMembers.add(at); }

			public void removeAvailableMembers(String at) { 	availableMembers.remove(at); }

			public void setCurrentMembers(ArrayList ct) { currentMembers = ct; }

			public void addCurrentMembers(String ct) { currentMembers.add(ct); }
			
			public void removeCurrentMembers(String ct) { currentMembers.remove(ct); }
			
	
}