package common.security.identitystore;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import common.security.model.LoginUser;
import common.security.service.LoginUserRepository;

@ApplicationScoped
public class ApplicationIdentityStore implements IdentityStore {

	@Inject
	private LoginUserRepository loginUserRepository;
	
	@Inject
	private Pbkdf2PasswordHash passwordHash;
	
	@Override
	public CredentialValidationResult validate(Credential credential) {
		CredentialValidationResult validationResult = CredentialValidationResult.INVALID_RESULT;

		UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
			
		String username = login.getCaller();

		Optional<LoginUser> optionalLoginUser = loginUserRepository.findByUsername(username);
		if (optionalLoginUser.isPresent()) {
			LoginUser existingLoginUser = optionalLoginUser.get();
			if (passwordHash.verify(login.getPasswordAsString().toCharArray(), existingLoginUser.getPassword())) {
				validationResult = new CredentialValidationResult(username, existingLoginUser.getGroups().stream().map(item -> item.getGroupname()).collect(Collectors.toSet()));
			}
		}

		return validationResult;
	}	
	
}