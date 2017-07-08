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
import modelo.Planoavaliacao;
import modelo.PlanoavaliacaoPK;

/**
 *
 * @author Paulino Francisco
 */
public class PlanoavaliacaoJpaController implements Serializable {

    public PlanoavaliacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Planoavaliacao planoavaliacao) throws PreexistingEntityException, Exception {
        if (planoavaliacao.getPlanoavaliacaoPK() == null) {
            planoavaliacao.setPlanoavaliacaoPK(new PlanoavaliacaoPK());
        }
        planoavaliacao.getPlanoavaliacaoPK().setIddisc(planoavaliacao.getDisciplina().getIdDisc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina disciplina = planoavaliacao.getDisciplina();
            if (disciplina != null) {
                disciplina = em.getReference(disciplina.getClass(), disciplina.getIdDisc());
                planoavaliacao.setDisciplina(disciplina);
            }
            em.persist(planoavaliacao);
            if (disciplina != null) {
                disciplina.getPlanoavaliacaoList().add(planoavaliacao);
                disciplina = em.merge(disciplina);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanoavaliacao(planoavaliacao.getPlanoavaliacaoPK()) != null) {
                throw new PreexistingEntityException("Planoavaliacao " + planoavaliacao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Planoavaliacao planoavaliacao) throws NonexistentEntityException, Exception {
        planoavaliacao.getPlanoavaliacaoPK().setIddisc(planoavaliacao.getDisciplina().getIdDisc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Planoavaliacao persistentPlanoavaliacao = em.find(Planoavaliacao.class, planoavaliacao.getPlanoavaliacaoPK());
            Disciplina disciplinaOld = persistentPlanoavaliacao.getDisciplina();
            Disciplina disciplinaNew = planoavaliacao.getDisciplina();
            if (disciplinaNew != null) {
                disciplinaNew = em.getReference(disciplinaNew.getClass(), disciplinaNew.getIdDisc());
                planoavaliacao.setDisciplina(disciplinaNew);
            }
            planoavaliacao = em.merge(planoavaliacao);
            if (disciplinaOld != null && !disciplinaOld.equals(disciplinaNew)) {
                disciplinaOld.getPlanoavaliacaoList().remove(planoavaliacao);
                disciplinaOld = em.merge(disciplinaOld);
            }
            if (disciplinaNew != null && !disciplinaNew.equals(disciplinaOld)) {
                disciplinaNew.getPlanoavaliacaoList().add(planoavaliacao);
                disciplinaNew = em.merge(disciplinaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PlanoavaliacaoPK id = planoavaliacao.getPlanoavaliacaoPK();
                if (findPlanoavaliacao(id) == null) {
                    throw new NonexistentEntityException("The planoavaliacao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PlanoavaliacaoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Planoavaliacao planoavaliacao;
            try {
                planoavaliacao = em.getReference(Planoavaliacao.class, id);
                planoavaliacao.getPlanoavaliacaoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planoavaliacao with id " + id + " no longer exists.", enfe);
            }
            Disciplina disciplina = planoavaliacao.getDisciplina();
            if (disciplina != null) {
                disciplina.getPlanoavaliacaoList().remove(planoavaliacao);
                disciplina = em.merge(disciplina);
            }
            em.remove(planoavaliacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Planoavaliacao> findPlanoavaliacaoEntities() {
        return findPlanoavaliacaoEntities(true, -1, -1);
    }

    public List<Planoavaliacao> findPlanoavaliacaoEntities(int maxResults, int firstResult) {
        return findPlanoavaliacaoEntities(false, maxResults, firstResult);
    }

    private List<Planoavaliacao> findPlanoavaliacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Planoavaliacao.class));
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

    public Planoavaliacao findPlanoavaliacao(PlanoavaliacaoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Planoavaliacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanoavaliacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Planoavaliacao> rt = cq.from(Planoavaliacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
