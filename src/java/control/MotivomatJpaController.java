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
import modelo.Matricula;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Motivomat;
import modelo.MotivomatPK;

/**
 *
 * @author Paulino Francisco
 */
public class MotivomatJpaController implements Serializable {

    public MotivomatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Motivomat motivomat) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (motivomat.getMotivomatPK() == null) {
            motivomat.setMotivomatPK(new MotivomatPK());
        }
        motivomat.getMotivomatPK().setIdEstudante(motivomat.getMatricula().getMatriculaPK().getIdEstudante());
        motivomat.getMotivomatPK().setAno(motivomat.getMatricula().getMatriculaPK().getAno());
        List<String> illegalOrphanMessages = null;
        Matricula matriculaOrphanCheck = motivomat.getMatricula();
        if (matriculaOrphanCheck != null) {
            Motivomat oldMotivomatOfMatricula = matriculaOrphanCheck.getMotivomat();
            if (oldMotivomatOfMatricula != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Matricula " + matriculaOrphanCheck + " already has an item of type Motivomat whose matricula column cannot be null. Please make another selection for the matricula field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matricula matricula = motivomat.getMatricula();
            if (matricula != null) {
                matricula = em.getReference(matricula.getClass(), matricula.getMatriculaPK());
                motivomat.setMatricula(matricula);
            }
            em.persist(motivomat);
            if (matricula != null) {
                matricula.setMotivomat(motivomat);
                matricula = em.merge(matricula);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMotivomat(motivomat.getMotivomatPK()) != null) {
                throw new PreexistingEntityException("Motivomat " + motivomat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Motivomat motivomat) throws IllegalOrphanException, NonexistentEntityException, Exception {
        motivomat.getMotivomatPK().setIdEstudante(motivomat.getMatricula().getMatriculaPK().getIdEstudante());
        motivomat.getMotivomatPK().setAno(motivomat.getMatricula().getMatriculaPK().getAno());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Motivomat persistentMotivomat = em.find(Motivomat.class, motivomat.getMotivomatPK());
            Matricula matriculaOld = persistentMotivomat.getMatricula();
            Matricula matriculaNew = motivomat.getMatricula();
            List<String> illegalOrphanMessages = null;
            if (matriculaNew != null && !matriculaNew.equals(matriculaOld)) {
                Motivomat oldMotivomatOfMatricula = matriculaNew.getMotivomat();
                if (oldMotivomatOfMatricula != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Matricula " + matriculaNew + " already has an item of type Motivomat whose matricula column cannot be null. Please make another selection for the matricula field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (matriculaNew != null) {
                matriculaNew = em.getReference(matriculaNew.getClass(), matriculaNew.getMatriculaPK());
                motivomat.setMatricula(matriculaNew);
            }
            motivomat = em.merge(motivomat);
            if (matriculaOld != null && !matriculaOld.equals(matriculaNew)) {
                matriculaOld.setMotivomat(null);
                matriculaOld = em.merge(matriculaOld);
            }
            if (matriculaNew != null && !matriculaNew.equals(matriculaOld)) {
                matriculaNew.setMotivomat(motivomat);
                matriculaNew = em.merge(matriculaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MotivomatPK id = motivomat.getMotivomatPK();
                if (findMotivomat(id) == null) {
                    throw new NonexistentEntityException("The motivomat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MotivomatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Motivomat motivomat;
            try {
                motivomat = em.getReference(Motivomat.class, id);
                motivomat.getMotivomatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The motivomat with id " + id + " no longer exists.", enfe);
            }
            Matricula matricula = motivomat.getMatricula();
            if (matricula != null) {
                matricula.setMotivomat(null);
                matricula = em.merge(matricula);
            }
            em.remove(motivomat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Motivomat> findMotivomatEntities() {
        return findMotivomatEntities(true, -1, -1);
    }

    public List<Motivomat> findMotivomatEntities(int maxResults, int firstResult) {
        return findMotivomatEntities(false, maxResults, firstResult);
    }

    private List<Motivomat> findMotivomatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Motivomat.class));
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

    public Motivomat findMotivomat(MotivomatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Motivomat.class, id);
        } finally {
            em.close();
        }
    }

    public int getMotivomatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Motivomat> rt = cq.from(Motivomat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
