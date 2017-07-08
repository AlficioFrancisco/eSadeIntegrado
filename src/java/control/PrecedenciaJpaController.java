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
import modelo.Precedencia;
import modelo.PrecedenciaPK;

/**
 *
 * @author Paulino Francisco
 */
public class PrecedenciaJpaController implements Serializable {

    public PrecedenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Precedencia precedencia) throws PreexistingEntityException, Exception {
        if (precedencia.getPrecedenciaPK() == null) {
            precedencia.setPrecedenciaPK(new PrecedenciaPK());
        }
        precedencia.getPrecedenciaPK().setPrecedencia(precedencia.getDisciplina().getIdDisc());
        precedencia.getPrecedenciaPK().setIdDisc(precedencia.getDisciplina1().getIdDisc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina disciplina = precedencia.getDisciplina();
            if (disciplina != null) {
                disciplina = em.getReference(disciplina.getClass(), disciplina.getIdDisc());
                precedencia.setDisciplina(disciplina);
            }
            Disciplina disciplina1 = precedencia.getDisciplina1();
            if (disciplina1 != null) {
                disciplina1 = em.getReference(disciplina1.getClass(), disciplina1.getIdDisc());
                precedencia.setDisciplina1(disciplina1);
            }
            em.persist(precedencia);
            if (disciplina != null) {
                disciplina.getPrecedenciaList().add(precedencia);
                disciplina = em.merge(disciplina);
            }
            if (disciplina1 != null) {
                disciplina1.getPrecedenciaList().add(precedencia);
                disciplina1 = em.merge(disciplina1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrecedencia(precedencia.getPrecedenciaPK()) != null) {
                throw new PreexistingEntityException("Precedencia " + precedencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Precedencia precedencia) throws NonexistentEntityException, Exception {
        precedencia.getPrecedenciaPK().setPrecedencia(precedencia.getDisciplina().getIdDisc());
        precedencia.getPrecedenciaPK().setIdDisc(precedencia.getDisciplina1().getIdDisc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Precedencia persistentPrecedencia = em.find(Precedencia.class, precedencia.getPrecedenciaPK());
            Disciplina disciplinaOld = persistentPrecedencia.getDisciplina();
            Disciplina disciplinaNew = precedencia.getDisciplina();
            Disciplina disciplina1Old = persistentPrecedencia.getDisciplina1();
            Disciplina disciplina1New = precedencia.getDisciplina1();
            if (disciplinaNew != null) {
                disciplinaNew = em.getReference(disciplinaNew.getClass(), disciplinaNew.getIdDisc());
                precedencia.setDisciplina(disciplinaNew);
            }
            if (disciplina1New != null) {
                disciplina1New = em.getReference(disciplina1New.getClass(), disciplina1New.getIdDisc());
                precedencia.setDisciplina1(disciplina1New);
            }
            precedencia = em.merge(precedencia);
            if (disciplinaOld != null && !disciplinaOld.equals(disciplinaNew)) {
                disciplinaOld.getPrecedenciaList().remove(precedencia);
                disciplinaOld = em.merge(disciplinaOld);
            }
            if (disciplinaNew != null && !disciplinaNew.equals(disciplinaOld)) {
                disciplinaNew.getPrecedenciaList().add(precedencia);
                disciplinaNew = em.merge(disciplinaNew);
            }
            if (disciplina1Old != null && !disciplina1Old.equals(disciplina1New)) {
                disciplina1Old.getPrecedenciaList().remove(precedencia);
                disciplina1Old = em.merge(disciplina1Old);
            }
            if (disciplina1New != null && !disciplina1New.equals(disciplina1Old)) {
                disciplina1New.getPrecedenciaList().add(precedencia);
                disciplina1New = em.merge(disciplina1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PrecedenciaPK id = precedencia.getPrecedenciaPK();
                if (findPrecedencia(id) == null) {
                    throw new NonexistentEntityException("The precedencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PrecedenciaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Precedencia precedencia;
            try {
                precedencia = em.getReference(Precedencia.class, id);
                precedencia.getPrecedenciaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The precedencia with id " + id + " no longer exists.", enfe);
            }
            Disciplina disciplina = precedencia.getDisciplina();
            if (disciplina != null) {
                disciplina.getPrecedenciaList().remove(precedencia);
                disciplina = em.merge(disciplina);
            }
            Disciplina disciplina1 = precedencia.getDisciplina1();
            if (disciplina1 != null) {
                disciplina1.getPrecedenciaList().remove(precedencia);
                disciplina1 = em.merge(disciplina1);
            }
            em.remove(precedencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Precedencia> findPrecedenciaEntities() {
        return findPrecedenciaEntities(true, -1, -1);
    }

    public List<Precedencia> findPrecedenciaEntities(int maxResults, int firstResult) {
        return findPrecedenciaEntities(false, maxResults, firstResult);
    }

    private List<Precedencia> findPrecedenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Precedencia.class));
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

    public Precedencia findPrecedencia(PrecedenciaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Precedencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrecedenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Precedencia> rt = cq.from(Precedencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
