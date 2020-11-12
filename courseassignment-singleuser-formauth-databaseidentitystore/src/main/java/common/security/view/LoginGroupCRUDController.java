package common.security.view;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import common.security.model.LoginGroup;
import common.security.service.LoginGroupRepository;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class LoginGroupCRUDController implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger(LoginGroupCRUDController.class.getName());
	
	@Inject
	private LoginGroupRepository loginGroupRepository;
	
	/** The current LoginGroup to create, edit, update or delete */
	@Produces
	@Named
	private LoginGroup loginGroupDetail = new LoginGroup();
	
	@Getter
	private List<LoginGroup> loginGroups;
	
	@Getter @Setter
	private boolean editMode = false;
	
	@Getter @Setter
	private Long editId;
		
	@PostConstruct
	public void init() {
		try {
			loginGroups = loginGroupRepository.list();
		} catch(RuntimeException e) {
			Messages.addGlobalWarn(e.getMessage());
			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Retrieve list of {0} was not successful.", LoginGroup.class.getSimpleName());
		}
	}
	
	public String create() {
		String outcome = null;
		
		try {
			loginGroupRepository.add(loginGroupDetail.getGroupname());
			loginGroupDetail = new LoginGroup();
			Messages.addFlashGlobalInfo("Create new {0} was successful", LoginGroup.class.getSimpleName());
			outcome = "list?faces-redirect=true";
		} catch(RuntimeException e) {
			Messages.addGlobalWarn(e.getMessage());
			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Create new {0} was not successful.", LoginGroup.class.getSimpleName());
		}
		
		return outcome;
	}

	public String update() {
		String outcome = null;
		
		try {
			loginGroupRepository.update(loginGroupDetail);
			loginGroupDetail = new LoginGroup();
			editMode = false;
			editId = null;
			Messages.addFlashGlobalInfo("Update existing {0} was successful", LoginGroup.class.getSimpleName());
			outcome = "list?faces-redirect=true";
		} catch(RuntimeException e) {
			Messages.addGlobalWarn(e.getMessage());

			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Update existing {0} was not successful.", LoginGroup.class.getSimpleName());
		}
		
		return outcome;
	}

	public String delete() {
		String outcome = null;
		
		try {
			loginGroupRepository.remove(loginGroupDetail.getId());
			loginGroups.remove(loginGroupDetail);
			loginGroupDetail = null;
			editMode = false;
			editId = null;
			Messages.addFlashGlobalInfo("Delete existing {0} was successful", LoginGroup.class.getSimpleName());
			outcome = "list?faces-redirect=true";
		} catch(RuntimeException e) {
			Messages.addGlobalWarn(e.getMessage());

			String systemMessage = String.format("Unauthorized access from IP %s", Faces.getRemoteAddr());
			logger.warning(systemMessage);
		} catch(Exception e) {
			Messages.addGlobalInfo("Delete existing {0} was not successful.", LoginGroup.class.getSimpleName());
		}
		
		return outcome;
	}

	public void edit() {
		if (!Faces.isPostback() && !Faces.isValidationFailed() ) {
			if (editId != null) {
				try {
					Optional<LoginGroup> optionalLoginGroup = loginGroupRepository.findById(editId);
					if (optionalLoginGroup.isPresent()) {
						loginGroupDetail = optionalLoginGroup.get();
						editMode = true;

					} else {
						Messages.addFlashGlobalWarn("{0} is not a valid id value", editId);
						Faces.navigate("listEvents?faces-redirect=true");
					}
				} catch (Exception e) {
					Messages.addGlobalInfo("Query by id was not successful");
				}	
			} else {
				Faces.navigate("list?faces-redirect=true");	
			}
		} 
	}
	
	public String cancel() {
		loginGroupDetail = null;
		editMode = false;
		return "list?faces-redirect=true";
	}
}