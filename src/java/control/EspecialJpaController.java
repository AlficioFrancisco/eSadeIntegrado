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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Especial;

/**
 *
 * @author Paulino Francisco
 */
public class EspecialJpaController implements Serializable {

    public EspecialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Especial especial) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Estudante estudanteOrphanCheck = especial.getEstudante();
        if (estudanteOrphanCheck != null) {
            Especial oldEspecialOfEstudante = estudanteOrphanCheck.getEspecial();
            if (oldEspecialOfEstudante != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Estudante " + estudanteOrphanCheck + " already has an item of type Especial whose estudante column cannot be null. Please make another selection for the estudante field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudante estudante = especial.getEstudante();
            if (estudante != null) {
                estudante = em.getReference(estudante.getClass(), estudante.getIdEstudante());
                especial.setEstudante(estudante);
            }
            em.persist(especial);
            if (estudante != null) {
                estudante.setEspecial(especial);
                estudante = em.merge(estudante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEspecial(especial.getIdEstudante()) != null) {
                throw new PreexistingEntityException("Especial " + especial + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Especial especial) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especial persistentEspecial = em.find(Especial.class, especial.getIdEstudante());
            Estudante estudanteOld = persistentEspecial.getEstudante();
            Estudante estudanteNew = especial.getEstudante();
            List<String> illegalOrphanMessages = null;
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                Especial oldEspecialOfEstudante = estudanteNew.getEspecial();
                if (oldEspecialOfEstudante != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Estudante " + estudanteNew + " already has an item of type Especial whose estudante column cannot be null. Please make another selection for the estudante field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estudanteNew != null) {
                estudanteNew = em.getReference(estudanteNew.getClass(), estudanteNew.getIdEstudante());
                especial.setEstudante(estudanteNew);
            }
            especial = em.merge(especial);
            if (estudanteOld != null && !estudanteOld.equals(estudanteNew)) {
                estudanteOld.setEspecial(null);
                estudanteOld = em.merge(estudanteOld);
            }
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                estudanteNew.setEspecial(especial);
                estudanteNew = em.merge(estudanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = especial.getIdEstudante();
                if (findEspecial(id) == null) {
                    throw new NonexistentEntityException("The especial with id " + id + " no longer exists.");
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
            Especial especial;
            try {
                especial = em.getReference(Especial.class, id);
                especial.getIdEstudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The especial with id " + id + " no longer exists.", enfe);
            }
            Estudante estudante = especial.getEstudante();
            if (estudante != null) {
                estudante.setEspecial(null);
                estudante = em.merge(estudante);
            }
            em.remove(especial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Especial> findEspecialEntities() {
        return findEspecialEntities(true, -1, -1);
    }

    public List<Especial> findEspecialEntities(int maxResults, int firstResult) {
        return findEspecialEntities(false, maxResults, firstResult);
    }

    private List<Especial> findEspecialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Especial.class));
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

    public Especial findEspecial(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Especial.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspecialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Especial> rt = cq.from(Especial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
