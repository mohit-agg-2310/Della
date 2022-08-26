package model;

import java.util.Date;

/**
 * <p>
 * Title: ActionItem
 * </p>
 *
 * <p>
 * Description: An entity to hold details about a particular action item
 * </p>
 *
 * <p>
 * Copyright: Copyright © 2005, 2006
 * </p>
 *
 * @author Harry Sameshima; Lynn Robert Carter
 * @version 1.00
 */
public class ActionItem {

	//---------------------------------------------------------------------------------------------------------------------
	// Attributes

	private String actionItemName;
	private String description;
	private String resolution;
	private String status;
	private Date dueDate;
	private Date createdDate;
	private String members;
	private String teams;
	//---------------------------------------------------------------------------------------------------------------------

	/**
	 * The ActionItem class constructors.
	 *
	 */
	public ActionItem() {
		actionItemName = description = resolution = status = "";
		members="-No Member Assigned-";
		teams="No Team Assigned-";
		dueDate = createdDate = null;
	}

	
	public ActionItem(String ai, String desc, String res, String stat, String me, String te) {

		actionItemName = ai;
		description = desc;
		resolution = res;
		status = stat;
		createdDate = new Date();
		members = me;
		teams =te;
	}

	// Just the usual getters and setters
	public String getActionItemName() { return actionItemName; }

	public String getDescription() { return description; }

	public String getResolution() { return resolution; }

	public String getStatus() { return status; }

	public Date getDueDate() { return dueDate; }

	public Date getCreatedDate() { return createdDate; }
	
	public String getMembers() {return members;}
	
	public String getTeams() {return teams;}

	public void setActionItemName(String x) { actionItemName = x; }

	public void setDescription(String x) { description = x; }

	public void setResolution(String x) { resolution = x; }

	public void setStatus(String x) { status = x; }

	public void setDueDate(Date x) { dueDate = x; }

	public void setCreatedDate(Date x) { createdDate = x; }
	
	public void setMembers(String x) { members = x; }
	
	public void setTeams(String x) { teams = x; }
}
