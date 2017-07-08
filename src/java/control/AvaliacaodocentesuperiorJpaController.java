/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Questaodocentesuperior;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Avaliacaodocentesuperior;

/**
 *
 * @author Paulino Francisco
 */
public class AvaliacaodocentesuperiorJpaController implements Serializable {

    public AvaliacaodocentesuperiorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Avaliacaodocentesuperior avaliacaodocentesuperior) {
        if (avaliacaodocentesuperior.getQuestaodocentesuperiorList() == null) {
            avaliacaodocentesuperior.setQuestaodocentesuperiorList(new ArrayList<Questaodocentesuperior>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Questaodocentesuperior> attachedQuestaodocentesuperiorList = new ArrayList<Questaodocentesuperior>();
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperiorToAttach : avaliacaodocentesuperior.getQuestaodocentesuperiorList()) {
                questaodocentesuperiorListQuestaodocentesuperiorToAttach = em.getReference(questaodocentesuperiorListQuestaodocentesuperiorToAttach.getClass(), questaodocentesuperiorListQuestaodocentesuperiorToAttach.getIdquestaodocentesuperior());
                attachedQuestaodocentesuperiorList.add(questaodocentesuperiorListQuestaodocentesuperiorToAttach);
            }
            avaliacaodocentesuperior.setQuestaodocentesuperiorList(attachedQuestaodocentesuperiorList);
            em.persist(avaliacaodocentesuperior);
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperior : avaliacaodocentesuperior.getQuestaodocentesuperiorList()) {
                questaodocentesuperiorListQuestaodocentesuperior.getAvaliacaodocentesuperiorList().add(avaliacaodocentesuperior);
                questaodocentesuperiorListQuestaodocentesuperior = em.merge(questaodocentesuperiorListQuestaodocentesuperior);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Avaliacaodocentesuperior avaliacaodocentesuperior) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avaliacaodocentesuperior persistentAvaliacaodocentesuperior = em.find(Avaliacaodocentesuperior.class, avaliacaodocentesuperior.getIdavaliacaodocentesuperior());
            List<Questaodocentesuperior> questaodocentesuperiorListOld = persistentAvaliacaodocentesuperior.getQuestaodocentesuperiorList();
            List<Questaodocentesuperior> questaodocentesuperiorListNew = avaliacaodocentesuperior.getQuestaodocentesuperiorList();
            List<Questaodocentesuperior> attachedQuestaodocentesuperiorListNew = new ArrayList<Questaodocentesuperior>();
            for (Questaodocentesuperior questaodocentesuperiorListNewQuestaodocentesuperiorToAttach : questaodocentesuperiorListNew) {
                questaodocentesuperiorListNewQuestaodocentesuperiorToAttach = em.getReference(questaodocentesuperiorListNewQuestaodocentesuperiorToAttach.getClass(), questaodocentesuperiorListNewQuestaodocentesuperiorToAttach.getIdquestaodocentesuperior());
                attachedQuestaodocentesuperiorListNew.add(questaodocentesuperiorListNewQuestaodocentesuperiorToAttach);
            }
            questaodocentesuperiorListNew = attachedQuestaodocentesuperiorListNew;
            avaliacaodocentesuperior.setQuestaodocentesuperiorList(questaodocentesuperiorListNew);
            avaliacaodocentesuperior = em.merge(avaliacaodocentesuperior);
            for (Questaodocentesuperior questaodocentesuperiorListOldQuestaodocentesuperior : questaodocentesuperiorListOld) {
                if (!questaodocentesuperiorListNew.contains(questaodocentesuperiorListOldQuestaodocentesuperior)) {
                    questaodocentesuperiorListOldQuestaodocentesuperior.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperior);
                    questaodocentesuperiorListOldQuestaodocentesuperior = em.merge(questaodocentesuperiorListOldQuestaodocentesuperior);
                }
            }
            for (Questaodocentesuperior questaodocentesuperiorListNewQuestaodocentesuperior : questaodocentesuperiorListNew) {
                if (!questaodocentesuperiorListOld.contains(questaodocentesuperiorListNewQuestaodocentesuperior)) {
                    questaodocentesuperiorListNewQuestaodocentesuperior.getAvaliacaodocentesuperiorList().add(avaliacaodocentesuperior);
                    questaodocentesuperiorListNewQuestaodocentesuperior = em.merge(questaodocentesuperiorListNewQuestaodocentesuperior);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = avaliacaodocentesuperior.getIdavaliacaodocentesuperior();
                if (findAvaliacaodocentesuperior(id) == null) {
                    throw new NonexistentEntityException("The avaliacaodocentesuperior with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avaliacaodocentesuperior avaliacaodocentesuperior;
            try {
                avaliacaodocentesuperior = em.getReference(Avaliacaodocentesuperior.class, id);
                avaliacaodocentesuperior.getIdavaliacaodocentesuperior();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avaliacaodocentesuperior with id " + id + " no longer exists.", enfe);
            }
            List<Questaodocentesuperior> questaodocentesuperiorList = avaliacaodocentesuperior.getQuestaodocentesuperiorList();
            for (Questaodocentesuperior questaodocentesuperiorListQuestaodocentesuperior : questaodocentesuperiorList) {
                questaodocentesuperiorListQuestaodocentesuperior.getAvaliacaodocentesuperiorList().remove(avaliacaodocentesuperior);
                questaodocentesuperiorListQuestaodocentesuperior = em.merge(questaodocentesuperiorListQuestaodocentesuperior);
            }
            em.remove(avaliacaodocentesuperior);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Avaliacaodocentesuperior> findAvaliacaodocentesuperiorEntities() {
        return findAvaliacaodocentesuperiorEntities(true, -1, -1);
    }

    public List<Avaliacaodocentesuperior> findAvaliacaodocentesuperiorEntities(int maxResults, int firstResult) {
        return findAvaliacaodocentesuperiorEntities(false, maxResults, firstResult);
    }

    private List<Avaliacaodocentesuperior> findAvaliacaodocentesuperiorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Avaliacaodocentesuperior.class));
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

    public Avaliacaodocentesuperior findAvaliacaodocentesuperior(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Avaliacaodocentesuperior.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvaliacaodocentesuperiorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Avaliacaodocentesuperior> rt = cq.from(Avaliacaodocentesuperior.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
