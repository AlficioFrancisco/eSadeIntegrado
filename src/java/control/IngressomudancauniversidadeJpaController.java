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
import modelo.Estudante;
import modelo.Pais;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Ingressomudancauniversidade;

/**
 *
 * @author Paulino Francisco
 */
public class IngressomudancauniversidadeJpaController implements Serializable {

    public IngressomudancauniversidadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingressomudancauniversidade ingressomudancauniversidade) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Estudante estudanteOrphanCheck = ingressomudancauniversidade.getEstudante();
        if (estudanteOrphanCheck != null) {
            Ingressomudancauniversidade oldIngressomudancauniversidadeOfEstudante = estudanteOrphanCheck.getIngressomudancauniversidade();
            if (oldIngressomudancauniversidadeOfEstudante != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Estudante " + estudanteOrphanCheck + " already has an item of type Ingressomudancauniversidade whose estudante column cannot be null. Please make another selection for the estudante field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudante estudante = ingressomudancauniversidade.getEstudante();
            if (estudante != null) {
                estudante = em.getReference(estudante.getClass(), estudante.getIdEstudante());
                ingressomudancauniversidade.setEstudante(estudante);
            }
            Pais paisUniversidade = ingressomudancauniversidade.getPaisUniversidade();
            if (paisUniversidade != null) {
                paisUniversidade = em.getReference(paisUniversidade.getClass(), paisUniversidade.getIdPais());
                ingressomudancauniversidade.setPaisUniversidade(paisUniversidade);
            }
            em.persist(ingressomudancauniversidade);
            if (estudante != null) {
                estudante.setIngressomudancauniversidade(ingressomudancauniversidade);
                estudante = em.merge(estudante);
            }
            if (paisUniversidade != null) {
                paisUniversidade.getIngressomudancauniversidadeList().add(ingressomudancauniversidade);
                paisUniversidade = em.merge(paisUniversidade);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIngressomudancauniversidade(ingressomudancauniversidade.getIdEstudante()) != null) {
                throw new PreexistingEntityException("Ingressomudancauniversidade " + ingressomudancauniversidade + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingressomudancauniversidade ingressomudancauniversidade) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingressomudancauniversidade persistentIngressomudancauniversidade = em.find(Ingressomudancauniversidade.class, ingressomudancauniversidade.getIdEstudante());
            Estudante estudanteOld = persistentIngressomudancauniversidade.getEstudante();
            Estudante estudanteNew = ingressomudancauniversidade.getEstudante();
            Pais paisUniversidadeOld = persistentIngressomudancauniversidade.getPaisUniversidade();
            Pais paisUniversidadeNew = ingressomudancauniversidade.getPaisUniversidade();
            List<String> illegalOrphanMessages = null;
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                Ingressomudancauniversidade oldIngressomudancauniversidadeOfEstudante = estudanteNew.getIngressomudancauniversidade();
                if (oldIngressomudancauniversidadeOfEstudante != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Estudante " + estudanteNew + " already has an item of type Ingressomudancauniversidade whose estudante column cannot be null. Please make another selection for the estudante field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estudanteNew != null) {
                estudanteNew = em.getReference(estudanteNew.getClass(), estudanteNew.getIdEstudante());
                ingressomudancauniversidade.setEstudante(estudanteNew);
            }
            if (paisUniversidadeNew != null) {
                paisUniversidadeNew = em.getReference(paisUniversidadeNew.getClass(), paisUniversidadeNew.getIdPais());
                ingressomudancauniversidade.setPaisUniversidade(paisUniversidadeNew);
            }
            ingressomudancauniversidade = em.merge(ingressomudancauniversidade);
            if (estudanteOld != null && !estudanteOld.equals(estudanteNew)) {
                estudanteOld.setIngressomudancauniversidade(null);
                estudanteOld = em.merge(estudanteOld);
            }
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                estudanteNew.setIngressomudancauniversidade(ingressomudancauniversidade);
                estudanteNew = em.merge(estudanteNew);
            }
            if (paisUniversidadeOld != null && !paisUniversidadeOld.equals(paisUniversidadeNew)) {
                paisUniversidadeOld.getIngressomudancauniversidadeList().remove(ingressomudancauniversidade);
                paisUniversidadeOld = em.merge(paisUniversidadeOld);
            }
            if (paisUniversidadeNew != null && !paisUniversidadeNew.equals(paisUniversidadeOld)) {
                paisUniversidadeNew.getIngressomudancauniversidadeList().add(ingressomudancauniversidade);
                paisUniversidadeNew = em.merge(paisUniversidadeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ingressomudancauniversidade.getIdEstudante();
                if (findIngressomudancauniversidade(id) == null) {
                    throw new NonexistentEntityException("The ingressomudancauniversidade with id " + id + " no longer exists.");
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
            Ingressomudancauniversidade ingressomudancauniversidade;
            try {
                ingressomudancauniversidade = em.getReference(Ingressomudancauniversidade.class, id);
                ingressomudancauniversidade.getIdEstudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingressomudancauniversidade with id " + id + " no longer exists.", enfe);
            }
            Estudante estudante = ingressomudancauniversidade.getEstudante();
            if (estudante != null) {
                estudante.setIngressomudancauniversidade(null);
                estudante = em.merge(estudante);
            }
            Pais paisUniversidade = ingressomudancauniversidade.getPaisUniversidade();
            if (paisUniversidade != null) {
                paisUniversidade.getIngressomudancauniversidadeList().remove(ingressomudancauniversidade);
                paisUniversidade = em.merge(paisUniversidade);
            }
            em.remove(ingressomudancauniversidade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingressomudancauniversidade> findIngressomudancauniversidadeEntities() {
        return findIngressomudancauniversidadeEntities(true, -1, -1);
    }

    public List<Ingressomudancauniversidade> findIngressomudancauniversidadeEntities(int maxResults, int firstResult) {
        return findIngressomudancauniversidadeEntities(false, maxResults, firstResult);
    }

    private List<Ingressomudancauniversidade> findIngressomudancauniversidadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingressomudancauniversidade.class));
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

    public Ingressomudancauniversidade findIngressomudancauniversidade(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingressomudancauniversidade.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngressomudancauniversidadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingressomudancauniversidade> rt = cq.from(Ingressomudancauniversidade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
