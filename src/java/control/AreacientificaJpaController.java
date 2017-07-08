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
import modelo.Disciplina;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Areacientifica;

/**
 *
 * @author Paulino Francisco
 */
public class AreacientificaJpaController implements Serializable {

    public AreacientificaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Areacientifica areacientifica) {
        if (areacientifica.getDisciplinaList() == null) {
            areacientifica.setDisciplinaList(new ArrayList<Disciplina>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Disciplina> attachedDisciplinaList = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListDisciplinaToAttach : areacientifica.getDisciplinaList()) {
                disciplinaListDisciplinaToAttach = em.getReference(disciplinaListDisciplinaToAttach.getClass(), disciplinaListDisciplinaToAttach.getIdDisc());
                attachedDisciplinaList.add(disciplinaListDisciplinaToAttach);
            }
            areacientifica.setDisciplinaList(attachedDisciplinaList);
            em.persist(areacientifica);
            for (Disciplina disciplinaListDisciplina : areacientifica.getDisciplinaList()) {
                Areacientifica oldAreaCientificaOfDisciplinaListDisciplina = disciplinaListDisciplina.getAreaCientifica();
                disciplinaListDisciplina.setAreaCientifica(areacientifica);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
                if (oldAreaCientificaOfDisciplinaListDisciplina != null) {
                    oldAreaCientificaOfDisciplinaListDisciplina.getDisciplinaList().remove(disciplinaListDisciplina);
                    oldAreaCientificaOfDisciplinaListDisciplina = em.merge(oldAreaCientificaOfDisciplinaListDisciplina);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Areacientifica areacientifica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Areacientifica persistentAreacientifica = em.find(Areacientifica.class, areacientifica.getIdarea());
            List<Disciplina> disciplinaListOld = persistentAreacientifica.getDisciplinaList();
            List<Disciplina> disciplinaListNew = areacientifica.getDisciplinaList();
            List<Disciplina> attachedDisciplinaListNew = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListNewDisciplinaToAttach : disciplinaListNew) {
                disciplinaListNewDisciplinaToAttach = em.getReference(disciplinaListNewDisciplinaToAttach.getClass(), disciplinaListNewDisciplinaToAttach.getIdDisc());
                attachedDisciplinaListNew.add(disciplinaListNewDisciplinaToAttach);
            }
            disciplinaListNew = attachedDisciplinaListNew;
            areacientifica.setDisciplinaList(disciplinaListNew);
            areacientifica = em.merge(areacientifica);
            for (Disciplina disciplinaListOldDisciplina : disciplinaListOld) {
                if (!disciplinaListNew.contains(disciplinaListOldDisciplina)) {
                    disciplinaListOldDisciplina.setAreaCientifica(null);
                    disciplinaListOldDisciplina = em.merge(disciplinaListOldDisciplina);
                }
            }
            for (Disciplina disciplinaListNewDisciplina : disciplinaListNew) {
                if (!disciplinaListOld.contains(disciplinaListNewDisciplina)) {
                    Areacientifica oldAreaCientificaOfDisciplinaListNewDisciplina = disciplinaListNewDisciplina.getAreaCientifica();
                    disciplinaListNewDisciplina.setAreaCientifica(areacientifica);
                    disciplinaListNewDisciplina = em.merge(disciplinaListNewDisciplina);
                    if (oldAreaCientificaOfDisciplinaListNewDisciplina != null && !oldAreaCientificaOfDisciplinaListNewDisciplina.equals(areacientifica)) {
                        oldAreaCientificaOfDisciplinaListNewDisciplina.getDisciplinaList().remove(disciplinaListNewDisciplina);
                        oldAreaCientificaOfDisciplinaListNewDisciplina = em.merge(oldAreaCientificaOfDisciplinaListNewDisciplina);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = areacientifica.getIdarea();
                if (findAreacientifica(id) == null) {
                    throw new NonexistentEntityException("The areacientifica with id " + id + " no longer exists.");
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
            Areacientifica areacientifica;
            try {
                areacientifica = em.getReference(Areacientifica.class, id);
                areacientifica.getIdarea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The areacientifica with id " + id + " no longer exists.", enfe);
            }
            List<Disciplina> disciplinaList = areacientifica.getDisciplinaList();
            for (Disciplina disciplinaListDisciplina : disciplinaList) {
                disciplinaListDisciplina.setAreaCientifica(null);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
            }
            em.remove(areacientifica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Areacientifica> findAreacientificaEntities() {
        return findAreacientificaEntities(true, -1, -1);
    }

    public List<Areacientifica> findAreacientificaEntities(int maxResults, int firstResult) {
        return findAreacientificaEntities(false, maxResults, firstResult);
    }

    private List<Areacientifica> findAreacientificaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Areacientifica.class));
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

    public Areacientifica findAreacientifica(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Areacientifica.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreacientificaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Areacientifica> rt = cq.from(Areacientifica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
