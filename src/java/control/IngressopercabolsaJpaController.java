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
import modelo.Estudante;
import modelo.Ingressopercabolsa;
import modelo.IngressopercabolsaPK;

/**
 *
 * @author Paulino Francisco
 */
public class IngressopercabolsaJpaController implements Serializable {

    public IngressopercabolsaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingressopercabolsa ingressopercabolsa) throws PreexistingEntityException, Exception {
        if (ingressopercabolsa.getIngressopercabolsaPK() == null) {
            ingressopercabolsa.setIngressopercabolsaPK(new IngressopercabolsaPK());
        }
        ingressopercabolsa.getIngressopercabolsaPK().setIdEstudante(ingressopercabolsa.getEstudante().getIdEstudante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudante estudante = ingressopercabolsa.getEstudante();
            if (estudante != null) {
                estudante = em.getReference(estudante.getClass(), estudante.getIdEstudante());
                ingressopercabolsa.setEstudante(estudante);
            }
            em.persist(ingressopercabolsa);
            if (estudante != null) {
                estudante.getIngressopercabolsaList().add(ingressopercabolsa);
                estudante = em.merge(estudante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIngressopercabolsa(ingressopercabolsa.getIngressopercabolsaPK()) != null) {
                throw new PreexistingEntityException("Ingressopercabolsa " + ingressopercabolsa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingressopercabolsa ingressopercabolsa) throws NonexistentEntityException, Exception {
        ingressopercabolsa.getIngressopercabolsaPK().setIdEstudante(ingressopercabolsa.getEstudante().getIdEstudante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingressopercabolsa persistentIngressopercabolsa = em.find(Ingressopercabolsa.class, ingressopercabolsa.getIngressopercabolsaPK());
            Estudante estudanteOld = persistentIngressopercabolsa.getEstudante();
            Estudante estudanteNew = ingressopercabolsa.getEstudante();
            if (estudanteNew != null) {
                estudanteNew = em.getReference(estudanteNew.getClass(), estudanteNew.getIdEstudante());
                ingressopercabolsa.setEstudante(estudanteNew);
            }
            ingressopercabolsa = em.merge(ingressopercabolsa);
            if (estudanteOld != null && !estudanteOld.equals(estudanteNew)) {
                estudanteOld.getIngressopercabolsaList().remove(ingressopercabolsa);
                estudanteOld = em.merge(estudanteOld);
            }
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                estudanteNew.getIngressopercabolsaList().add(ingressopercabolsa);
                estudanteNew = em.merge(estudanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IngressopercabolsaPK id = ingressopercabolsa.getIngressopercabolsaPK();
                if (findIngressopercabolsa(id) == null) {
                    throw new NonexistentEntityException("The ingressopercabolsa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IngressopercabolsaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingressopercabolsa ingressopercabolsa;
            try {
                ingressopercabolsa = em.getReference(Ingressopercabolsa.class, id);
                ingressopercabolsa.getIngressopercabolsaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingressopercabolsa with id " + id + " no longer exists.", enfe);
            }
            Estudante estudante = ingressopercabolsa.getEstudante();
            if (estudante != null) {
                estudante.getIngressopercabolsaList().remove(ingressopercabolsa);
                estudante = em.merge(estudante);
            }
            em.remove(ingressopercabolsa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingressopercabolsa> findIngressopercabolsaEntities() {
        return findIngressopercabolsaEntities(true, -1, -1);
    }

    public List<Ingressopercabolsa> findIngressopercabolsaEntities(int maxResults, int firstResult) {
        return findIngressopercabolsaEntities(false, maxResults, firstResult);
    }

    private List<Ingressopercabolsa> findIngressopercabolsaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingressopercabolsa.class));
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

    public Ingressopercabolsa findIngressopercabolsa(IngressopercabolsaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingressopercabolsa.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngressopercabolsaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingressopercabolsa> rt = cq.from(Ingressopercabolsa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
