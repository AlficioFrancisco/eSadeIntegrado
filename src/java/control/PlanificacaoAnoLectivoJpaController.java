/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Faculdade;
import modelo.PlanificacaoAnoLectivo;

/**
 *
 * @author Paulino Francisco
 */
public class PlanificacaoAnoLectivoJpaController implements Serializable {

    public PlanificacaoAnoLectivoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanificacaoAnoLectivo planificacaoAnoLectivo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faculdade faculdade = planificacaoAnoLectivo.getFaculdade();
            if (faculdade != null) {
                faculdade = em.getReference(faculdade.getClass(), faculdade.getIdFaculdade());
                planificacaoAnoLectivo.setFaculdade(faculdade);
            }
            em.persist(planificacaoAnoLectivo);
            if (faculdade != null) {
                faculdade.getPlanificacaoAnoLectivoList().add(planificacaoAnoLectivo);
                faculdade = em.merge(faculdade);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanificacaoAnoLectivo planificacaoAnoLectivo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlanificacaoAnoLectivo persistentPlanificacaoAnoLectivo = em.find(PlanificacaoAnoLectivo.class, planificacaoAnoLectivo.getAno());
            Faculdade faculdadeOld = persistentPlanificacaoAnoLectivo.getFaculdade();
            Faculdade faculdadeNew = planificacaoAnoLectivo.getFaculdade();
            if (faculdadeNew != null) {
                faculdadeNew = em.getReference(faculdadeNew.getClass(), faculdadeNew.getIdFaculdade());
                planificacaoAnoLectivo.setFaculdade(faculdadeNew);
            }
            planificacaoAnoLectivo = em.merge(planificacaoAnoLectivo);
            if (faculdadeOld != null && !faculdadeOld.equals(faculdadeNew)) {
                faculdadeOld.getPlanificacaoAnoLectivoList().remove(planificacaoAnoLectivo);
                faculdadeOld = em.merge(faculdadeOld);
            }
            if (faculdadeNew != null && !faculdadeNew.equals(faculdadeOld)) {
                faculdadeNew.getPlanificacaoAnoLectivoList().add(planificacaoAnoLectivo);
                faculdadeNew = em.merge(faculdadeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planificacaoAnoLectivo.getAno();
                if (findPlanificacaoAnoLectivo(id) == null) {
                    throw new NonexistentEntityException("The planificacaoAnoLectivo with id " + id + " no longer exists.");
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
            PlanificacaoAnoLectivo planificacaoAnoLectivo;
            try {
                planificacaoAnoLectivo = em.getReference(PlanificacaoAnoLectivo.class, id);
                planificacaoAnoLectivo.getAno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planificacaoAnoLectivo with id " + id + " no longer exists.", enfe);
            }
            Faculdade faculdade = planificacaoAnoLectivo.getFaculdade();
            if (faculdade != null) {
                faculdade.getPlanificacaoAnoLectivoList().remove(planificacaoAnoLectivo);
                faculdade = em.merge(faculdade);
            }
            em.remove(planificacaoAnoLectivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanificacaoAnoLectivo> findPlanificacaoAnoLectivoEntities() {
        return findPlanificacaoAnoLectivoEntities(true, -1, -1);
    }

    public List<PlanificacaoAnoLectivo> findPlanificacaoAnoLectivoEntities(int maxResults, int firstResult) {
        return findPlanificacaoAnoLectivoEntities(false, maxResults, firstResult);
    }

    private List<PlanificacaoAnoLectivo> findPlanificacaoAnoLectivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanificacaoAnoLectivo.class));
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

    public PlanificacaoAnoLectivo findPlanificacaoAnoLectivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanificacaoAnoLectivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanificacaoAnoLectivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanificacaoAnoLectivo> rt = cq.from(PlanificacaoAnoLectivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
