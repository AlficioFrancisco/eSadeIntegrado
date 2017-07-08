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
import modelo.Caracter;

/**
 *
 * @author Paulino Francisco
 */
public class CaracterJpaController implements Serializable {

    public CaracterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caracter caracter) {
        if (caracter.getDisciplinaList() == null) {
            caracter.setDisciplinaList(new ArrayList<Disciplina>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Disciplina> attachedDisciplinaList = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListDisciplinaToAttach : caracter.getDisciplinaList()) {
                disciplinaListDisciplinaToAttach = em.getReference(disciplinaListDisciplinaToAttach.getClass(), disciplinaListDisciplinaToAttach.getIdDisc());
                attachedDisciplinaList.add(disciplinaListDisciplinaToAttach);
            }
            caracter.setDisciplinaList(attachedDisciplinaList);
            em.persist(caracter);
            for (Disciplina disciplinaListDisciplina : caracter.getDisciplinaList()) {
                Caracter oldCaracterOfDisciplinaListDisciplina = disciplinaListDisciplina.getCaracter();
                disciplinaListDisciplina.setCaracter(caracter);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
                if (oldCaracterOfDisciplinaListDisciplina != null) {
                    oldCaracterOfDisciplinaListDisciplina.getDisciplinaList().remove(disciplinaListDisciplina);
                    oldCaracterOfDisciplinaListDisciplina = em.merge(oldCaracterOfDisciplinaListDisciplina);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Caracter caracter) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caracter persistentCaracter = em.find(Caracter.class, caracter.getIdCaracter());
            List<Disciplina> disciplinaListOld = persistentCaracter.getDisciplinaList();
            List<Disciplina> disciplinaListNew = caracter.getDisciplinaList();
            List<Disciplina> attachedDisciplinaListNew = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListNewDisciplinaToAttach : disciplinaListNew) {
                disciplinaListNewDisciplinaToAttach = em.getReference(disciplinaListNewDisciplinaToAttach.getClass(), disciplinaListNewDisciplinaToAttach.getIdDisc());
                attachedDisciplinaListNew.add(disciplinaListNewDisciplinaToAttach);
            }
            disciplinaListNew = attachedDisciplinaListNew;
            caracter.setDisciplinaList(disciplinaListNew);
            caracter = em.merge(caracter);
            for (Disciplina disciplinaListOldDisciplina : disciplinaListOld) {
                if (!disciplinaListNew.contains(disciplinaListOldDisciplina)) {
                    disciplinaListOldDisciplina.setCaracter(null);
                    disciplinaListOldDisciplina = em.merge(disciplinaListOldDisciplina);
                }
            }
            for (Disciplina disciplinaListNewDisciplina : disciplinaListNew) {
                if (!disciplinaListOld.contains(disciplinaListNewDisciplina)) {
                    Caracter oldCaracterOfDisciplinaListNewDisciplina = disciplinaListNewDisciplina.getCaracter();
                    disciplinaListNewDisciplina.setCaracter(caracter);
                    disciplinaListNewDisciplina = em.merge(disciplinaListNewDisciplina);
                    if (oldCaracterOfDisciplinaListNewDisciplina != null && !oldCaracterOfDisciplinaListNewDisciplina.equals(caracter)) {
                        oldCaracterOfDisciplinaListNewDisciplina.getDisciplinaList().remove(disciplinaListNewDisciplina);
                        oldCaracterOfDisciplinaListNewDisciplina = em.merge(oldCaracterOfDisciplinaListNewDisciplina);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = caracter.getIdCaracter();
                if (findCaracter(id) == null) {
                    throw new NonexistentEntityException("The caracter with id " + id + " no longer exists.");
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
            Caracter caracter;
            try {
                caracter = em.getReference(Caracter.class, id);
                caracter.getIdCaracter();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caracter with id " + id + " no longer exists.", enfe);
            }
            List<Disciplina> disciplinaList = caracter.getDisciplinaList();
            for (Disciplina disciplinaListDisciplina : disciplinaList) {
                disciplinaListDisciplina.setCaracter(null);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
            }
            em.remove(caracter);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Caracter> findCaracterEntities() {
        return findCaracterEntities(true, -1, -1);
    }

    public List<Caracter> findCaracterEntities(int maxResults, int firstResult) {
        return findCaracterEntities(false, maxResults, firstResult);
    }

    private List<Caracter> findCaracterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caracter.class));
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

    public Caracter findCaracter(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caracter.class, id);
        } finally {
            em.close();
        }
    }

    public int getCaracterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caracter> rt = cq.from(Caracter.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
