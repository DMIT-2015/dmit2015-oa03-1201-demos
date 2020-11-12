package common.security.service;

import common.security.model.LoginGroup;

import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional      // Every method in class requires a new transaction
@Interceptors({LoginGroupSecurityInterceptor.class})
public class LoginGroupRepository {

    @PersistenceContext(unitName = "h2database-jpa-pu")     // The unitUnit is only needed when there are multiple persistence units defined in persistence.xml
    private EntityManager em;

    public boolean add(String groupname) {
        if (findByGroupName(groupname).isPresent()) {
            throw new RuntimeException("The group name " + groupname + " already exists");
        }

        LoginGroup newLoginGroup = new LoginGroup();
        newLoginGroup.setGroupname(groupname);
        em.persist(newLoginGroup);
        return true;
    }

    public boolean update(LoginGroup updatedLoginGroup) {
        boolean success = false;

        Optional<LoginGroup> optionalLoginGroup = findById(updatedLoginGroup.getId());
        if (optionalLoginGroup.isPresent()) {
            LoginGroup existingLoginGroup = optionalLoginGroup.get();
            existingLoginGroup.setGroupname(updatedLoginGroup.getGroupname());
            em.merge(existingLoginGroup);
            em.flush();
            success = true;
        }

        return success;
    }

    public boolean remove(Long id) {
        boolean success = false;

        Optional<LoginGroup> optionalLoginGroup = findById(id);
        if (optionalLoginGroup.isPresent()) {
            LoginGroup existingLoginGroup = optionalLoginGroup.get();
            em.remove(existingLoginGroup);
            em.flush();
            success = true;
        }

        return success;
    }

    public Optional<LoginGroup> findById(Long id) {
        Optional<LoginGroup> optionalLoginGroup = Optional.empty();
        try {
            LoginGroup querySingleResult = em.find(LoginGroup.class, id);
            if (querySingleResult != null) {
                optionalLoginGroup = Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalLoginGroup;
    }

    public Optional<LoginGroup> findByGroupName(String groupname) {
        Optional<LoginGroup> optionalLoginGroup = Optional.empty();
        try {
            optionalLoginGroup = Optional.of(em
                    .createQuery("SELECT g FROM LoginGroup g WHERE g.groupname = :groupnameValue ", LoginGroup.class)
                    .setParameter("groupnameValue", groupname)
                    .getSingleResult());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return optionalLoginGroup;
    }

    public List<LoginGroup> list() {
        return em.createQuery(
                "SELECT g FROM LoginGroup g ORDER BY g.groupname"
                , LoginGroup.class)
                .getResultList();
    }
    
}
