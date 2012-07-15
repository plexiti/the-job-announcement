package com.camunda.fox.showcase.jobannouncement.web;

import static com.camunda.fox.showcase.jobannouncement.process.ProcessConstants.*;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.identity.User;

import com.plexiti.helper.Servlets;


@Named("users")
@SessionScoped
public class UserBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1495492136976861302L;

	private String loggedInUser = "gonzo";
	private User loggedInUserDetails;
	private String locale;
	private TimeZone timeZone = TimeZone.getDefault();
	private String activeSubTab = "tab-own";
	private String browser = initBrowser();
	private boolean manager;
	private List<User> usersList;
	
	@PostConstruct
	public void init() {
		manager = identityService.createGroupQuery().groupMember(loggedInUser).groupId(ROLE_MANAGER).count() > 0;
		usersList = identityService.createUserQuery().list();
		loggedInUserDetails = identityService.createUserQuery().userId(loggedInUser).singleResult();
		Iterator<User> it = usersList.iterator();
		while (it.hasNext()) {
			if (it.next().getId().equals(loggedInUser)) {
				it.remove(); break;
			}
		}
	}

	public String getLoggedInUser() {
		return this.loggedInUser ;
	}
	
	public String getLoggedInUserName() {
		return this.loggedInUserDetails.getFirstName() + " " + loggedInUserDetails.getLastName();
	}

	public String getImage() {
	    return getImage(loggedInUser);
	}

	public String getImage(String userId) {
	    return UserImageServlet.PATH + userId;
	}
	
	public boolean isManager() {
		return manager;
	}
	
	public List<User> getUsersList() {
		return usersList;
	}

	public void doLogin(String loggedInUser) {
		this.loggedInUser = loggedInUser;
		init();
	}

	public TimeZone getTimeZone() {
		// TODO needs to be read from the browser
		return timeZone;
	}

	public String getLocale() {
		return locale = locale != null ? locale : getFacesContext().getViewRoot().getLocale().getLanguage();
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public FacesContext getFacesContext() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (ctx == null)
			throw new ContextNotActiveException("FacesContext is not active");
		return ctx;
	}
	
	public String getFullName() {
		return getFullName(loggedInUser);
	}
	
	public String getFullName(String userId) {
		User user = identityService.createUserQuery().userId(userId).singleResult();
		return user.getFirstName() + " " + user.getLastName();
	}

	public String getFirstName(String userId) {
		User user = identityService.createUserQuery().userId(userId).singleResult();
		return user.getFirstName();
	}
	
	public String getJobMessageKey(String userId) {
		return "job." + userId;
	}
	
	public String initBrowser() {
		return Servlets.browser((HttpServletRequest) getFacesContext().getExternalContext().getRequest());
	}

	public String getBrowser() {
		return browser;
	}

	public String getActiveSubTab() {
		return activeSubTab;
	}

	public void setActiveSubTab(String activeSubTab) {
		this.activeSubTab = activeSubTab;
	}

}
