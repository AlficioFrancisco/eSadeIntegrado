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
import modelo.Disciplina;
import modelo.Docente;
import modelo.Lecciona;
import modelo.LeccionaPK;

/**
 *
 * @author Paulino Francisco
 */
public class LeccionaJpaController implements Serializable {

    public LeccionaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lecciona lecciona) throws PreexistingEntityException, Exception {
        if (lecciona.getLeccionaPK() == null) {
            lecciona.setLeccionaPK(new LeccionaPK());
        }
        lecciona.getLeccionaPK().setIddisc(lecciona.getDisciplina().getIdDisc());
        lecciona.getLeccionaPK().setIddocente(lecciona.getDocente().getIddocente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina disciplina = lecciona.getDisciplina();
            if (disciplina != null) {
                disciplina = em.getReference(disciplina.getClass(), disciplina.getIdDisc());
                lecciona.setDisciplina(disciplina);
            }
            Docente docente = lecciona.getDocente();
            if (docente != null) {
                docente = em.getReference(docente.getClass(), docente.getIddocente());
                lecciona.setDocente(docente);
            }
            em.persist(lecciona);
            if (disciplina != null) {
                disciplina.getLeccionaList().add(lecciona);
                disciplina = em.merge(disciplina);
            }
            if (docente != null) {
                docente.getLeccionaList().add(lecciona);
                docente = em.merge(docente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLecciona(lecciona.getLeccionaPK()) != null) {
                throw new PreexistingEntityException("Lecciona " + lecciona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lecciona lecciona) throws NonexistentEntityException, Exception {
        lecciona.getLeccionaPK().setIddisc(lecciona.getDisciplina().getIdDisc());
        lecciona.getLeccionaPK().setIddocente(lecciona.getDocente().getIddocente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lecciona persistentLecciona = em.find(Lecciona.class, lecciona.getLeccionaPK());
            Disciplina disciplinaOld = persistentLecciona.getDisciplina();
            Disciplina disciplinaNew = lecciona.getDisciplina();
            Docente docenteOld = persistentLecciona.getDocente();
            Docente docenteNew = lecciona.getDocente();
            if (disciplinaNew != null) {
                disciplinaNew = em.getReference(disciplinaNew.getClass(), disciplinaNew.getIdDisc());
                lecciona.setDisciplina(disciplinaNew);
            }
            if (docenteNew != null) {
                docenteNew = em.getReference(docenteNew.getClass(), docenteNew.getIddocente());
                lecciona.setDocente(docenteNew);
            }
            lecciona = em.merge(lecciona);
            if (disciplinaOld != null && !disciplinaOld.equals(disciplinaNew)) {
                disciplinaOld.getLeccionaList().remove(lecciona);
                disciplinaOld = em.merge(disciplinaOld);
            }
            if (disciplinaNew != null && !disciplinaNew.equals(disciplinaOld)) {
                disciplinaNew.getLeccionaList().add(lecciona);
                disciplinaNew = em.merge(disciplinaNew);
            }
            if (docenteOld != null && !docenteOld.equals(docenteNew)) {
                docenteOld.getLeccionaList().remove(lecciona);
                docenteOld = em.merge(docenteOld);
            }
            if (docenteNew != null && !docenteNew.equals(docenteOld)) {
                docenteNew.getLeccionaList().add(lecciona);
                docenteNew = em.merge(docenteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LeccionaPK id = lecciona.getLeccionaPK();
                if (findLecciona(id) == null) {
                    throw new NonexistentEntityException("The lecciona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LeccionaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lecciona lecciona;
            try {
                lecciona = em.getReference(Lecciona.class, id);
                lecciona.getLeccionaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lecciona with id " + id + " no longer exists.", enfe);
            }
            Disciplina disciplina = lecciona.getDisciplina();
            if (disciplina != null) {
                disciplina.getLeccionaList().remove(lecciona);
                disciplina = em.merge(disciplina);
            }
            Docente docente = lecciona.getDocente();
            if (docente != null) {
                docente.getLeccionaList().remove(lecciona);
                docente = em.merge(docente);
            }
            em.remove(lecciona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lecciona> findLeccionaEntities() {
        return findLeccionaEntities(true, -1, -1);
    }

    public List<Lecciona> findLeccionaEntities(int maxResults, int firstResult) {
        return findLeccionaEntities(false, maxResults, firstResult);
    }

    private List<Lecciona> findLeccionaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lecciona.class));
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

    public Lecciona findLecciona(LeccionaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lecciona.class, id);
        } finally {
            em.close();
        }
    }

    public int getLeccionaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lecciona> rt = cq.from(Lecciona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
