package common.security.view;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import common.security.model.LoginGroup;
import common.security.model.LoginUser;
import common.security.service.LoginUserRepository;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class LoginUserCRUDController implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger(LoginUserCRUDController.class.getName());
	
	@Inject
	private LoginUserRepository loginUserRepository;
	
	/** The current LoginUser to create, edit, update or delete */
	@Produces
	@Named
	private LoginUser loginUserDetail = new LoginUser();

	@Getter @Setter
//	@NotBlank(message="A user must be assigned to at least one group")
	@Size(min = 1, message = "A user must be assigned to at least one group")
	private String[] selectedGroups;

	
	@Getter
	private List<LoginUser> loginUsers;
	
	@Getter @Setter
	private boolean editMode = false;
	
	@Getter @Setter
	private Long editId;
		
	@PostConstruct
	public void init() {
		try {
			loginUsers = loginUserRepository.list();
		} catch(RuntimeException e) {
			Messages.addGlobalInfo(e.getMessage());
			Messages.addFlashGlobalInfo(e.getMessage());
			
			logger.warning("Access Exception: " + e.getMessage());
			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Retrieve list of {0} was not successful.", LoginUser.class.getSimpleName());
		}
	}
	
	public String create() {
				
		String outcome = null;
		
		try {
//			String[] groups = selectedGroups.split(",");
//			loginUserRepository.add(loginUserDetail, groups);
			loginUserRepository.add(loginUserDetail, selectedGroups);
			loginUserDetail = new LoginUser();
			Messages.addFlashGlobalInfo("Create new {0} was successful", LoginUser.class.getSimpleName());
			outcome = "list?faces-redirect=true";
		} catch(RuntimeException e) {
			Messages.addGlobalInfo(e.getMessage());
			logger.warning("Access Exception: " + e.getMessage());
			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Create new {0} was not successful.", LoginUser.class.getSimpleName());
		}
		
		return outcome;
	}

	public String update() {
		String outcome = null;
		
		try {
//			String[] groups = selectedGroups.split(",");
//			loginUserRepository.update(loginUserDetail, groups);

			loginUserRepository.update(loginUserDetail, selectedGroups);
			loginUserDetail = new LoginUser();
			editMode = false;
			editId = null;
			Messages.addFlashGlobalInfo("Update existing {0} was successful", LoginUser.class.getSimpleName());
			outcome = "list?faces-redirect=true";
		} catch(RuntimeException e) {
			Messages.addGlobalInfo(e.getMessage());
			logger.warning("Access Exception: " + e.getMessage());
			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Update existing {0} was not successful.", LoginUser.class.getSimpleName());
		}
		
		return outcome;
	}

	public String delete() {
		String outcome = null;
		
		try {
			loginUserRepository.remove(loginUserDetail.getId());
			loginUsers.remove(loginUserDetail);
			loginUserDetail = null;
			editMode = false;
			editId = null;
			Messages.addFlashGlobalInfo("Delete existing {0} was successful", LoginUser.class.getSimpleName());
			outcome = "list?faces-redirect=true";
		} catch(RuntimeException e) {
			Messages.addGlobalInfo(e.getMessage());
			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Delete existing {0} was not successful.", LoginUser.class.getSimpleName());
		}
		
		return outcome;
	}

	public void edit() {
		if (!Faces.isPostback() && !Faces.isValidationFailed() ) {
			if (editId != null) {
				try {
					Optional<LoginUser> optionalLoginUser = loginUserRepository.findById(editId);
					if (optionalLoginUser.isPresent()) {
						loginUserDetail = optionalLoginUser.get();
						editMode = true;

						selectedGroups = new String[loginUserDetail.getGroups().size()];
						int index = 0;
						for(LoginGroup group : loginUserDetail.getGroups()) {
							selectedGroups[index++] = group.getGroupname();
						}

					} else {
						Messages.addFlashGlobalWarn("{0} is not a valid id value", editId);
						Faces.navigate("/demo/jpa/course-assignments/index?faces-redirect=true");
					}

				} catch (Exception e) {
					Messages.addGlobalInfo("Query by id was not successful");
				}	
			} else {
				Faces.navigate("/demo/jpa/course-assignments/index?faces-redirect=true");
			}
		} 
	}
	
	public String cancel() {
		loginUserDetail = null;
		editMode = false;
		return "list?faces-redirect=true";
	}
	
	@Getter @Setter
	@NotBlank(message="Current Password field value is required")
	private String currentPassword;	
	
	public String changePassword() {
		String nextUrl = null;
		try {
			if (loginUserDetail.getPlainTextPassword().equals(loginUserDetail.getConfirmedPlainTextPassword())) {
				String currentUsername = Faces.getRemoteUser();
				loginUserRepository.changePassword(currentUsername, currentPassword, loginUserDetail.getPlainTextPassword());
				Messages.addFlashGlobalInfo("Change password was successful.");
				nextUrl = "/demo/jpa/course-assignments/index?faces-redirect=true";
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