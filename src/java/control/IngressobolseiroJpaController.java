/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Bolsa;
import modelo.Estudante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Ingressobolseiro;

/**
 *
 * @author Paulino Francisco
 */
public class IngressobolseiroJpaController implements Serializable {

    public IngressobolseiroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingressobolseiro ingressobolseiro) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Estudante estudanteOrphanCheck = ingressobolseiro.getEstudante();
        if (estudanteOrphanCheck != null) {
            Ingressobolseiro oldIngressobolseiroOfEstudante = estudanteOrphanCheck.getIngressobolseiro();
            if (oldIngressobolseiroOfEstudante != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Estudante " + estudanteOrphanCheck + " already has an item of type Ingressobolseiro whose estudante column cannot be null. Please make another selection for the estudante field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bolsa bolsa = ingressobolseiro.getBolsa();
            if (bolsa != null) {
                bolsa = em.getReference(bolsa.getClass(), bolsa.getIdBolsa());
                ingressobolseiro.setBolsa(bolsa);
            }
            Estudante estudante = ingressobolseiro.getEstudante();
            if (estudante != null) {
                estudante = em.getReference(estudante.getClass(), estudante.getIdEstudante());
                ingressobolseiro.setEstudante(estudante);
            }
            em.persist(ingressobolseiro);
            if (bolsa != null) {
                bolsa.getIngressobolseiroList().add(ingressobolseiro);
                bolsa = em.merge(bolsa);
            }
            if (estudante != null) {
                estudante.setIngressobolseiro(ingressobolseiro);
                estudante = em.merge(estudante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIngressobolseiro(ingressobolseiro.getIdEstudante()) != null) {
                throw new PreexistingEntityException("Ingressobolseiro " + ingressobolseiro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingressobolseiro ingressobolseiro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingressobolseiro persistentIngressobolseiro = em.find(Ingressobolseiro.class, ingressobolseiro.getIdEstudante());
            Bolsa bolsaOld = persistentIngressobolseiro.getBolsa();
            Bolsa bolsaNew = ingressobolseiro.getBolsa();
            Estudante estudanteOld = persistentIngressobolseiro.getEstudante();
            Estudante estudanteNew = ingressobolseiro.getEstudante();
            List<String> illegalOrphanMessages = null;
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                Ingressobolseiro oldIngressobolseiroOfEstudante = estudanteNew.getIngressobolseiro();
                if (oldIngressobolseiroOfEstudante != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Estudante " + estudanteNew + " already has an item of type Ingressobolseiro whose estudante column cannot be null. Please make another selection for the estudante field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (bolsaNew != null) {
                bolsaNew = em.getReference(bolsaNew.getClass(), bolsaNew.getIdBolsa());
                ingressobolseiro.setBolsa(bolsaNew);
            }
            if (estudanteNew != null) {
                estudanteNew = em.getReference(estudanteNew.getClass(), estudanteNew.getIdEstudante());
                ingressobolseiro.setEstudante(estudanteNew);
            }
            ingressobolseiro = em.merge(ingressobolseiro);
            if (bolsaOld != null && !bolsaOld.equals(bolsaNew)) {
                bolsaOld.getIngressobolseiroList().remove(ingressobolseiro);
                bolsaOld = em.merge(bolsaOld);
            }
            if (bolsaNew != null && !bolsaNew.equals(bolsaOld)) {
                bolsaNew.getIngressobolseiroList().add(ingressobolseiro);
                bolsaNew = em.merge(bolsaNew);
            }
            if (estudanteOld != null && !estudanteOld.equals(estudanteNew)) {
                estudanteOld.setIngressobolseiro(null);
                estudanteOld = em.merge(estudanteOld);
            }
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                estudanteNew.setIngressobolseiro(ingressobolseiro);
                estudanteNew = em.merge(estudanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ingressobolseiro.getIdEstudante();
                if (findIngressobolseiro(id) == null) {
                    throw new NonexistentEntityException("The ingressobolseiro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingressobolseiro ingressobolseiro;
            try {
                ingressobolseiro = em.getReference(Ingressobolseiro.class, id);
                ingressobolseiro.getIdEstudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingressobolseiro with id " + id + " no longer exists.", enfe);
            }
            Bolsa bolsa = ingressobolseiro.getBolsa();
            if (bolsa != null) {
                bolsa.getIngressobolseiroList().remove(ingressobolseiro);
                bolsa = em.merge(bolsa);
            }
            Estudante estudante = ingressobolseiro.getEstudante();
            if (estudante != null) {
                estudante.setIngressobolseiro(null);
                estudante = em.merge(estudante);
            }
            em.remove(ingressobolseiro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingressobolseiro> findIngressobolseiroEntities() {
        return findIngressobolseiroEntities(true, -1, -1);
    }

    public List<Ingressobolseiro> findIngressobolseiroEntities(int maxResults, int firstResult) {
        return findIngressobolseiroEntities(false, maxResults, firstResult);
    }

    private List<Ingressobolseiro> findIngressobolseiroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingressobolseiro.class));
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

    public Ingressobolseiro findIngressobolseiro(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingressobolseiro.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngressobolseiroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingressobolseiro> rt = cq.from(Ingressobolseiro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
