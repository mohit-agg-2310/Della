package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import persistence.DataManager;

import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * <p>
 * Title: ActionItemManager
 * </p>
 *
 * <p>
 * Description: A class to organize and manage all known action items
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
public class ActionItemManager {

	//---------------------------------------------------------------------------------------------------------------------
	// Constants

	private transient SimpleDateFormat dateFormat = null;

	public static final int statusOpen = 0;			// The constants for Open and Close
	public static final int statusClosed = 1;
	public static final String[] statusStrings = {"Open", "Closed"};
	public List<ActionItem> actionItems = new ArrayList<ActionItem>();
	public List<Member> member = new ArrayList<Member>();
	public List<Team> team = new ArrayList<Team>();
	public ArrayList<String> actionItemStrings = new ArrayList<String>();
	public ArrayList<String> members = new ArrayList<String>();
	public ArrayList<String> teams = new ArrayList<String>();
	//public String actionItemStrings[] = null;
	
	private boolean editChangesPending;
	
	//---------------------------------------------------------------------------------------------------------------------
	// Attributes

	private ActionItem currentActionItem = null;	// Currently displayed action item
	private ActionItem emptyActionItem = null;		// The standard empty action item
	
	//---------------------------------------------------------------------------------------------------------------------

	/**
	 * The ActionItemManager class constructor.
	 * 
	 */
	public ActionItemManager() {
		currentActionItem = new ActionItem();
		emptyActionItem = new ActionItem();
		emptyActionItem.setCreatedDate(null);
	}
	
	/**
	 * Create an action item based on the parameters pass to the routine
	 * @param name String
	 * @param description String
	 * @param resolution String
	 * @param status String
	 * @param dueDateStr String
	 * @return ActionItem
	 */
	
	
	public ActionItem createActionItem(String name, String description,
			String resolution, String status,
			String dueDateStr, String member, String team) 
	throws Exception {
		
		
		ActionItem ai = new ActionItem(name, description, resolution, status, member, team);

		// Check if there are problems with the modifications.
		validateActionItem(ai, name, dueDateStr);

		
		/*int i=0;
		boolean f=true;
		for(ActionItem ait: actionItems) {
			if(ait.getActionItemName().equals(name)) {
				f=false;
				System.out.println("Item ALready Exists");
			}
			i++;
		}
		if(f) */
		{
		System.out.println("New item added");
		actionItems.add(ai);
		actionItemStrings.add(name);}
		
		
		// We passed the tests so it's ok to set the new current action item
		setCurrentActionItem(ai);
		return ai;
	}


	/**
	 * Update an existing action item based on the parameters pass to the routine
	 * @param name String
	 * @param description String
	 * @param resolution String
	 * @param status String
	 * @param dueDateStr String
	 * @return ActionItem
	 */
	
	public ActionItem updateActionItem(String name, String description,
			String resolution, String status,
			String dueDateStr, String member, String team) 
	throws Exception {
		
		ActionItem ai = new ActionItem(name, description, resolution, status, member, team);

		// Check if there are problems with the modifications.
		validateActionItem(ai, name, dueDateStr);
		int i=0;
		for(ActionItem ait: actionItems) {
			if(ait.getActionItemName().equals(name)) {
				actionItems.set(i,ai);
			}
			i++;
		}

		// We passed the tests so it's ok to set the new current action item
		setCurrentActionItem(ai);
		return ai;
	}
	
	public ActionItem deleteActionItem(String name, String description,
			String resolution, String status,
			String dueDateStr, String member, String team) 
	throws Exception {
		
		ActionItem ai = new ActionItem(name, description, resolution, status, member, team);
		//actionItems.remove(ai);
		actionItemStrings.remove(name);
		
		int i=0;
		for(ActionItem ait: actionItems) {
			if(ait.getActionItemName().equals(name)) {
				actionItems.remove(ait);
				System.out.println("Action Item is successfully deleted");
			}
			i++;
		}

		// We passed the tests so it's ok to set the new current action item
		setCurrentActionItem(null);
		return ai;
	}
	
		
	public void addMembers(String name) {
		members.add(name); 
		Collections.sort(members);
	}
	
	public void removeMembers(String name) {
		members.remove(name);
	}

	public void addTeams(String name) {
		teams.add(name);
		Collections.sort(teams);
		
	}
	
	public void removeTeams(String name) {
		teams.remove(name);
	}
	/**
	 * Check the parameters to see if the action item can be added to the list of action items.
	 * @param name String
	 * @param dueDateStr String
	 * @return boolean
	 * @throws an exception if there are any problems with the input.
	 */
	private void validateActionItem(ActionItem ai, String name, String dueDateStr) 
	throws Exception {
		if (name.trim().length() == 0) {
			throw new Exception("The Action Item Name must not be empty!   ");
		}

		Date dueDate = null;
		if (dueDateStr.length() != 0) {
			try {
				dueDate = dateFormat.parse(dueDateStr);
			}
			catch (ParseException ex) {
				throw new Exception("Please use the requested date format!   ");
			}
		}
		ai.setDueDate(dueDate);
	}

	// The usual getters and setters

	/**
	 * Get the current action item 
	 * @return	- The current action item
	 */
	public ActionItem getCurrentActionItem() {
		if (currentActionItem == null) 
			return emptyActionItem;
		return currentActionItem;
	}

	public void setCurrentActionItem(ActionItem x) { currentActionItem = x; }

	public void clearCurrentActionItem() { currentActionItem = emptyActionItem; }

	public void setDateFormatChecker() { dateFormat = new SimpleDateFormat("yyyy-MM-dd"); }

	public void setEditChangesPending(boolean flag){ editChangesPending = flag; }

	public boolean getEditChangesPending(){ return editChangesPending; }
}