package com.camunda.fox.showcase.jobannouncement.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("subTab")
// FIXME: quick solution to remember the state of the tab on a per user basis. We should review this and changed to a proper scope!
@SessionScoped
public class SubTabBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 624294621101599644L;

	/*
	 * Tracks the current active tab showing a the list of "Open work", "To describe", ...
	 */
	private Map<String, String> activeSubTabForUser = new HashMap<String, String>();
	private static String DEFAULT_TAB = "tab-own";
	
	@Inject
	private UserBean userBean;
	
	public String getActiveSubTabForUser() {
		String activeSubTab = activeSubTabForUser.get(userBean.getLoggedInUser());
		if (activeSubTab == null) {
			activeSubTab = DEFAULT_TAB;
		}
		
		log.finest("Getting active subtab '" + activeSubTab + "' for user '" + userBean.getLoggedInUser() + "'");
		activeSubTabForUser.put(userBean.getLoggedInUser(), activeSubTab);
		return activeSubTab;
	}

	public void setActiveSubTabForUser(String activeSubTab) {
		log.finest("Setting active subtab to '" + activeSubTab + "' for user '" + userBean.getLoggedInUser() + "'");
		activeSubTabForUser.put(userBean.getLoggedInUser(), activeSubTab);
	}
}