/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Planomatriculas;

/**
 *
 * @author Paulino Francisco
 */
public class PlanomatriculasJpaController implements Serializable {

    public PlanomatriculasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Planomatriculas planomatriculas) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(planomatriculas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanomatriculas(planomatriculas.getAnoLec()) != null) {
                throw new PreexistingEntityException("Planomatriculas " + planomatriculas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Planomatriculas planomatriculas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            planomatriculas = em.merge(planomatriculas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planomatriculas.getAnoLec();
                if (findPlanomatriculas(id) == null) {
                    throw new NonexistentEntityException("The planomatriculas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Planomatriculas planomatriculas;
            try {
                planomatriculas = em.getReference(Planomatriculas.class, id);
                planomatriculas.getAnoLec();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planomatriculas with id " + id + " no longer exists.", enfe);
            }
            em.remove(planomatriculas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Planomatriculas> findPlanomatriculasEntities() {
        return findPlanomatriculasEntities(true, -1, -1);
    }

    public List<Planomatriculas> findPlanomatriculasEntities(int maxResults, int firstResult) {
        return findPlanomatriculasEntities(false, maxResults, firstResult);
    }

    private List<Planomatriculas> findPlanomatriculasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Planomatriculas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Planomatriculas findPlanomatriculas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Planomatriculas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanomatriculasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Planomatriculas> rt = cq.from(Planomatriculas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
