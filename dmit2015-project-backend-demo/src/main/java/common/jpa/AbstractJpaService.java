package common.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractJpaService {

    @PersistenceContext(unitName = "h2database-jpa-pu")
    private EntityManager em;

    protected <T> void create(T entity) {
        em.persist(entity);
    }

    protected <T> T save(T entity) {
        T merge = em.merge(entity);
        return merge;
    }

    protected <T> T read(Object id, Class<T> entityClass) {
        return find(entityClass, id);
    }

    protected <T> T find(Class<T> entityClass, Object id) {
        return em.find(entityClass, id);
    }

    protected <T> Optional<T> findOptional(Class<T> entityClass, Object id) {
        Optional<T> optionalResult = Optional.empty();
        try {
            T singleResult = em.find(entityClass, id);
            if (singleResult != null) {
                optionalResult = Optional.of(singleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalResult;
    }

    protected <T> T findFresh(Class<T> entityClass, Object id) {
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.cache.retrieveMode", "BYPASS");
        return em.find(entityClass, id, hints);
    }

    protected <T> List<T> findAll(Class<T> entityClass) {
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        Root<T> queryRoot = criteriaQuery.from(entityClass);
        criteriaQuery.select(queryRoot);
        return em.createQuery(criteriaQuery).getResultList();
    }

    protected <T> List<T> findRange(Class<T> entityClass, int from, int to) {
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        Root<T> queryRoot = criteriaQuery.from(entityClass);
        criteriaQuery.select(queryRoot);
        Query query = em.createQuery(criteriaQuery);
        query.setMaxResults(to - from + 1);
        query.setFirstResult(from);
        return query.getResultList();
    }

    protected <T> List<T> findRange(Class<T> entityClass, int[] range) {
        return findRange(entityClass, range[0], range[1]);
    }

    protected <T> int count(Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> queryRoot = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.count(queryRoot));
        return ((Long) em.createQuery(criteriaQuery).getSingleResult()).intValue();
    }

    protected boolean isAttached(Object entity) {
        return em.contains(entity);
    }

    protected void clearCache() {
        em.flush();
        em.getEntityManagerFactory().getCache().evictAll();
    }

    protected void delete(Object entity) {
        if (isAttached(entity)) {
            em.remove(entity);
        } else {
            em.remove( em.merge(entity) );
        }
    }

    protected <T> void deleteById(Class<T> entityClass, Object id) {
        Optional<T> optionalEntity = findOptional(entityClass, id);
        if (optionalEntity.isPresent()) {
            T entity = optionalEntity.get();
            delete(entity);
        }
    }


    protected <T> int deleteAll(Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaDelete<T> delete = criteriaBuilder.createCriteriaDelete(entityClass);
        Root<T> root = delete.from(entityClass);
        return em.createQuery(delete).executeUpdate();
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
