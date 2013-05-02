package com.camunda.fox.showcase.jobannouncement.web;

import static com.camunda.fox.showcase.jobannouncement.process.ProcessConstants.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.Picture;
import org.camunda.bpm.engine.identity.User;

import com.plexiti.helper.Servlets;


@Named("users")
@SessionScoped
public class UserBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1495492136976861302L;

	// Users
	private List<User> usersList;	
	
	// Current Sample User
	private String loggedInUser = "gonzo";
	private User loggedInUserDetails;
	private boolean manager;

	// Session info
	private String browser = Servlets.browser((HttpServletRequest) getFacesContext().getExternalContext().getRequest());
	private String locale;
	private TimeZone timeZone = TimeZone.getDefault(); // TODO needs to be read from the browser
	private String activeSubTab = "tab-own";

	@PostConstruct
	public void init() {
    /*
     * If the users kermit, gonzo and fozzie do not exsit, we create them.
     */
    createGroupIfNotExists(identityService, ROLE_MANAGER);
    createGroupIfNotExists(identityService, ROLE_STAFF);
    createUserIfNotExists(identityService, "kermit", "Kermit", "The Frog", "/resources/img/users/kermit.png", new String[] { ROLE_STAFF, ROLE_MANAGER } );
    createUserIfNotExists(identityService, "fozzie", "Fozzie", "The Bear", "/resources/img/users/fozzie.png", new String[] { ROLE_STAFF } );
    createUserIfNotExists(identityService, "gonzo", "Gonzo", "The Great", "/resources/img/users/gonzo.png", new String[] { ROLE_STAFF, ROLE_MANAGER } );

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

  private void createGroupIfNotExists(IdentityService identityService, String groupId) {
    /*
     * Check if needed group exists and create it otherwise
     */
    Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
    if (group == null) {
      log.info("Group '" + groupId + "' does not exist.");
      group = identityService.newGroup(groupId);
      identityService.saveGroup(group);
      log.info("New group '" + groupId + "' created.");
    }
  }

  private void createUserIfNotExists(IdentityService identityService,
                                     String userId,
                                     String firstName,
                                     String lastName,
                                     String imagePath,
                                     String[] groups) {
    /*
     * Check if needed user exists and create it otherwise
     */
    User user = identityService.createUserQuery().userId(userId).singleResult();
    if (user == null) {
      log.info("User '" + userId + "' does not exist.");
      user = identityService.newUser(userId);
      user.setFirstName(firstName);
      user.setLastName(lastName);
      identityService.saveUser(user);
      identityService.setUserPicture(userId, pictureForUser(imagePath));
      log.info("New user '" + userId + "' created.");

      /*
       * Add user to groups
       */
      for (String groupId: groups) {
        identityService.createMembership(userId, groupId);
      }
    }
  }

  private Picture pictureForUser(String picturePath) {
    Picture picture = null;
    try {
      InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(picturePath);
      log.info("Loading user picture from '" + picturePath + "'");
      byte[] imgData = IOUtils.toByteArray(is);
      picture = new Picture(imgData, "image/png");
    } catch (IOException e) {
      log.severe("Unable to load picture at URL '" + picturePath + "'");
    }

    return picture;
  }

  public void doLogin(String loggedInUser) {
		this.loggedInUser = loggedInUser;
		init();
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

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public String getLocale() {
		return locale = locale != null ? locale : getFacesContext().getViewRoot().getLocale().getLanguage();
	}

	public void setLocale(String locale) {
		this.locale = locale;
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
