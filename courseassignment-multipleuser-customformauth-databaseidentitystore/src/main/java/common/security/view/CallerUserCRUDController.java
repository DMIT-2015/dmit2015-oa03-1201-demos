package common.security.view;

import common.security.model.CallerUser;
import common.security.service.CallerUserRepository;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Named
@ViewScoped
public class CallerUserCRUDController implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger(CallerUserCRUDController.class.getName());
	
	@Inject
	private CallerUserRepository userRepository;
	
	/** The current CallerUser to create, edit, update or delete */
	@Produces
	@Named
	private CallerUser userDetail = new CallerUser();

	@Getter @Setter
//	@NotBlank(message="A user must be assigned to at least one group")
	@Size(min = 1, message = "A user must be assigned to at least one group")
	private String[] selectedGroups;
	
	@Getter
	private List<CallerUser> users;
	
	@Getter @Setter
	private boolean editMode = false;
	
	@Getter @Setter
	private String editId;
		
	@PostConstruct
	public void init() {
		try {
			users = userRepository.list();
		} catch(RuntimeException e) {
			Messages.addGlobalInfo(e.getMessage());
			Messages.addFlashGlobalInfo(e.getMessage());
			
			logger.warning("Access Exception: " + e.getMessage());
			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Retrieve list of {0} was not successful.", CallerUser.class.getSimpleName());
		}
	}
	
	public String create() {
				
		String outcome = null;
		
		try {
			userRepository.add(userDetail, userDetail.getPlainTextPassword(), selectedGroups);
			userDetail = new CallerUser();
			Messages.addFlashGlobalInfo("Create new {0} was successful", CallerUser.class.getSimpleName());
			outcome = "list?faces-redirect=true";
		} catch(RuntimeException e) {
			Messages.addGlobalInfo(e.getMessage());
			logger.warning("Access Exception: " + e.getMessage());
			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Create new {0} was not successful.", CallerUser.class.getSimpleName());
		}
		
		return outcome;
	}

	public String update() {
		String outcome = null;
		
		try {
			userRepository.update(userDetail, selectedGroups);
			userDetail = new CallerUser();
			editMode = false;
			editId = null;
			Messages.addFlashGlobalInfo("Update existing {0} was successful", CallerUser.class.getSimpleName());
			outcome = "list?faces-redirect=true";
		} catch(RuntimeException e) {
			Messages.addGlobalInfo(e.getMessage());
			logger.warning("Access Exception: " + e.getMessage());
			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Update existing {0} was not successful.", CallerUser.class.getSimpleName());
		}
		
		return outcome;
	}

	public String delete() {
		String outcome = null;
		
		try {
			userRepository.remove(userDetail.getUsername());
			users.remove(userDetail);
			userDetail = null;
			editMode = false;
			editId = null;
			Messages.addFlashGlobalInfo("Delete existing {0} was successful", CallerUser.class.getSimpleName());
			outcome = "list?faces-redirect=true";
		} catch(RuntimeException e) {
			Messages.addGlobalInfo(e.getMessage());
			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Delete existing {0} was not successful.", CallerUser.class.getSimpleName());
		}
		
		return outcome;
	}

	public void edit() {
		if (!Faces.isPostback() && !Faces.isValidationFailed() ) {
			if (editId != null) {
				try {
					Optional<CallerUser> optionalCallerUser = userRepository.findById(editId);
					if (optionalCallerUser.isPresent()) {
						userDetail = optionalCallerUser.get();
						editMode = true;

						selectedGroups = userDetail.getGroups().toArray(String[]::new);

					} else {
						Messages.addFlashGlobalWarn("{0} is not a valid id value", editId);
						Faces.navigate("/course-assignments/index?faces-redirect=true");
					}

				} catch (Exception e) {
					Messages.addGlobalInfo("Query by id was not successful");
				}	
			} else {
				Faces.navigate("/course-assignments/index?faces-redirect=true");
			}
		} 
	}
	
	public String cancel() {
		userDetail = null;
		editMode = false;
		return "list?faces-redirect=true";
	}
	
	@Getter @Setter
	@NotBlank(message="Current Password field value is required")
	private String currentPassword;	
	
	public String changePassword() {
		String nextUrl = null;
		try {
			if (userDetail.getPlainTextPassword().equals(userDetail.getConfirmedPlainTextPassword())) {
				String currentUsername = Faces.getRemoteUser();
				userRepository.changePassword(currentUsername, currentPassword, userDetail.getPlainTextPassword());
				Messages.addFlashGlobalInfo("Change password was successful.");
				nextUrl = "//course-assignments/index?faces-redirect=true";
			} else {
				Messages.addGlobalInfo("New password must match Confirmed new password.");
			}
		} catch(RuntimeException e) {
			Messages.addGlobalInfo(e.getMessage());
			logger.warning("Access Exception: " + e.getMessage());
			String message = String.format("Remote IP: %s\n Remote User: %s\n", Faces.getRemoteAddr(), Faces.getRemoteUser());			
			logger.warning(message);
		} catch(Exception e) {
			Messages.addGlobalInfo(e.getMessage());
		}
		return nextUrl;
	}
}