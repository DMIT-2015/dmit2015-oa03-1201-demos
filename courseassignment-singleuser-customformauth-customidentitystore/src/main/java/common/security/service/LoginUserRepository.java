package common.security.service;

import common.security.model.LoginGroup;
import common.security.model.LoginUser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional      // Every method in class requires a new transaction
@Interceptors({LoginUserSecurityInterceptor.class})
public class LoginUserRepository {

    @PersistenceContext(unitName = "h2database-jpa-pu")     // The unitUnit is only needed when there are multiple persistence units defined in persistence.xml
    private EntityManager em;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private LoginGroupRepository loginGroupRepository;

    public boolean add(LoginUser newLoginUser, String[] groupNames) {

        if (findByUsername(newLoginUser.getUsername()).isPresent()) {
            throw new RuntimeException("Username " + newLoginUser.getUsername() + " is already in use by another user.");
        }

        String hashedPassword = passwordHash.generate(newLoginUser.getPlainTextPassword().toCharArray());
        newLoginUser.setPassword(hashedPassword);

        if (groupNames != null && groupNames.length > 0 ) {
            for(String singleGroupName : groupNames ) {
                Optional<LoginGroup> optionalLoginGroup = loginGroupRepository.findByGroupName(singleGroupName);
                if (optionalLoginGroup.isPresent()) {
                    LoginGroup userGroup = optionalLoginGroup.get();
                    newLoginUser.getGroups().add(userGroup);
                }
            }
        }

        em.persist(newLoginUser);
        return true;
    }

    public boolean update(LoginUser updatedLoginUser, String[] groupNames) {
        boolean success = false;

        if (groupNames != null && groupNames.length > 0 ) {
            updatedLoginUser.getGroups().clear();
            for(String singleGroupName : groupNames ) {
                Optional<LoginGroup> optionalLoginGroup = loginGroupRepository.findByGroupName(singleGroupName);
                if (optionalLoginGroup.isPresent()) {
                    LoginGroup userGroup = optionalLoginGroup.get();
                    updatedLoginUser.getGroups().add(userGroup);
                }
            }
        }

        Optional<LoginUser> optionalLoginUser = findById(updatedLoginUser.getId());
        if (optionalLoginUser.isPresent()) {
            LoginUser existingLoginUser = optionalLoginUser.get();
            existingLoginUser.setUsername(updatedLoginUser.getUsername());
            existingLoginUser.setPlainTextPassword(updatedLoginUser.getPlainTextPassword());
            existingLoginUser.setConfirmedPlainTextPassword(updatedLoginUser.getConfirmedPlainTextPassword());
            existingLoginUser.setGroups(updatedLoginUser.getGroups());
            em.merge(existingLoginUser);
            em.flush();
            success = true;
        }

        return success;
    }

    public boolean remove(Long id) {
        boolean success = false;

        Optional<LoginUser> optionalLoginUser = findById(id);
        if (optionalLoginUser.isPresent()) {
            LoginUser existingLoginUser = optionalLoginUser.get();
            em.remove(existingLoginUser);
            em.flush();
            success = true;
        }

        return success;
    }

    public Optional<LoginUser> findById(Long id) {
        Optional<LoginUser> optionalLoginUser = Optional.empty();
        try {
            LoginUser querySingleResult = em.find(LoginUser.class, id);
            if (querySingleResult != null) {
                optionalLoginUser = Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalLoginUser;
    }

    public Optional<LoginUser> findByUsername(String username) {
        Optional<LoginUser> optionalLoginUser = Optional.empty();
        try {
            optionalLoginUser = Optional.of(em
                    .createQuery("SELECT u FROM LoginUser u WHERE u.username = :usernameValue ", LoginUser.class)
                    .setParameter("usernameValue", username)
                    .getSingleResult());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return optionalLoginUser;
    }

    public List<LoginUser> list() {
        return em.createQuery(
                "SELECT u FROM LoginUser u ORDER BY u.username"
                , LoginUser.class)
                .getResultList();
    }

    public void changePassword(String username, String currentPlainTextPassword, String newPlainTextPassword) throws Exception {
        char[] currentPassword = currentPlainTextPassword.toCharArray();

        Optional<LoginUser> optionalLoginUser = findByUsername(username);
        if (optionalLoginUser.isPresent()) {
            LoginUser existingLoginUser = optionalLoginUser.get();

            // verify currentPlainTextPassword is valid
            if (passwordHash.verify(currentPassword, existingLoginUser.getPassword())) {
                String newHashedPassword = passwordHash.generate(newPlainTextPassword.toCharArray());
                existingLoginUser.setPassword(newHashedPassword);
                em.merge(existingLoginUser);
            } else {
                throw new Exception("Current password is incorrect.");
            }

        }
    }
    
}
