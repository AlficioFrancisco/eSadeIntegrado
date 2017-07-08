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
import modelo.NotapautaOld;
import modelo.NotapautaOldPK;

/**
 *
 * @author Paulino Francisco
 */
public class NotapautaOldJpaController implements Serializable {

    public NotapautaOldJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NotapautaOld notapautaOld) throws PreexistingEntityException, Exception {
        if (notapautaOld.getNotapautaOldPK() == null) {
            notapautaOld.setNotapautaOldPK(new NotapautaOldPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(notapautaOld);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNotapautaOld(notapautaOld.getNotapautaOldPK()) != null) {
                throw new PreexistingEntityException("NotapautaOld " + notapautaOld + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NotapautaOld notapautaOld) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            notapautaOld = em.merge(notapautaOld);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NotapautaOldPK id = notapautaOld.getNotapautaOldPK();
                if (findNotapautaOld(id) == null) {
                    throw new NonexistentEntityException("The notapautaOld with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NotapautaOldPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NotapautaOld notapautaOld;
            try {
                notapautaOld = em.getReference(NotapautaOld.class, id);
                notapautaOld.getNotapautaOldPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notapautaOld with id " + id + " no longer exists.", enfe);
            }
            em.remove(notapautaOld);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NotapautaOld> findNotapautaOldEntities() {
        return findNotapautaOldEntities(true, -1, -1);
    }

    public List<NotapautaOld> findNotapautaOldEntities(int maxResults, int firstResult) {
        return findNotapautaOldEntities(false, maxResults, firstResult);
    }

    private List<NotapautaOld> findNotapautaOldEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NotapautaOld.class));
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

    public NotapautaOld findNotapautaOld(NotapautaOldPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NotapautaOld.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotapautaOldCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NotapautaOld> rt = cq.from(NotapautaOld.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
