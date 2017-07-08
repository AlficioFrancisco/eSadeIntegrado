/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Disciplinaanulada;
import modelo.Estudante;
import modelo.Funcionario;

/**
 *
 * @author Paulino Francisco
 */
public class DisciplinaanuladaJpaController implements Serializable {

    public DisciplinaanuladaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Disciplinaanulada disciplinaanulada) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudante idEstudante = disciplinaanulada.getIdEstudante();
            if (idEstudante != null) {
                idEstudante = em.getReference(idEstudante.getClass(), idEstudante.getIdEstudante());
                disciplinaanulada.setIdEstudante(idEstudante);
            }
            Funcionario funcionario = disciplinaanulada.getFuncionario();
            if (funcionario != null) {
                funcionario = em.getReference(funcionario.getClass(), funcionario.getIdFuncionario());
                disciplinaanulada.setFuncionario(funcionario);
            }
            em.persist(disciplinaanulada);
            if (idEstudante != null) {
                idEstudante.getDisciplinaanuladaList().add(disciplinaanulada);
                idEstudante = em.merge(idEstudante);
            }
            if (funcionario != null) {
                funcionario.getDisciplinaanuladaList().add(disciplinaanulada);
                funcionario = em.merge(funcionario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Disciplinaanulada disciplinaanulada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplinaanulada persistentDisciplinaanulada = em.find(Disciplinaanulada.class, disciplinaanulada.getIdanulacao());
            Estudante idEstudanteOld = persistentDisciplinaanulada.getIdEstudante();
            Estudante idEstudanteNew = disciplinaanulada.getIdEstudante();
            Funcionario funcionarioOld = persistentDisciplinaanulada.getFuncionario();
            Funcionario funcionarioNew = disciplinaanulada.getFuncionario();
            if (idEstudanteNew != null) {
                idEstudanteNew = em.getReference(idEstudanteNew.getClass(), idEstudanteNew.getIdEstudante());
                disciplinaanulada.setIdEstudante(idEstudanteNew);
            }
            if (funcionarioNew != null) {
                funcionarioNew = em.getReference(funcionarioNew.getClass(), funcionarioNew.getIdFuncionario());
                disciplinaanulada.setFuncionario(funcionarioNew);
            }
            disciplinaanulada = em.merge(disciplinaanulada);
            if (idEstudanteOld != null && !idEstudanteOld.equals(idEstudanteNew)) {
                idEstudanteOld.getDisciplinaanuladaList().remove(disciplinaanulada);
                idEstudanteOld = em.merge(idEstudanteOld);
            }
            if (idEstudanteNew != null && !idEstudanteNew.equals(idEstudanteOld)) {
                idEstudanteNew.getDisciplinaanuladaList().add(disciplinaanulada);
                idEstudanteNew = em.merge(idEstudanteNew);
            }
            if (funcionarioOld != null && !funcionarioOld.equals(funcionarioNew)) {
                funcionarioOld.getDisciplinaanuladaList().remove(disciplinaanulada);
                funcionarioOld = em.merge(funcionarioOld);
            }
            if (funcionarioNew != null && !funcionarioNew.equals(funcionarioOld)) {
                funcionarioNew.getDisciplinaanuladaList().add(disciplinaanulada);
                funcionarioNew = em.merge(funcionarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = disciplinaanulada.getIdanulacao();
                if (findDisciplinaanulada(id) == null) {
                    throw new NonexistentEntityException("The disciplinaanulada with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplinaanulada disciplinaanulada;
            try {
                disciplinaanulada = em.getReference(Disciplinaanulada.class, id);
                disciplinaanulada.getIdanulacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The disciplinaanulada with id " + id + " no longer exists.", enfe);
            }
            Estudante idEstudante = disciplinaanulada.getIdEstudante();
            if (idEstudante != null) {
                idEstudante.getDisciplinaanuladaList().remove(disciplinaanulada);
                idEstudante = em.merge(idEstudante);
            }
            Funcionario funcionario = disciplinaanulada.getFuncionario();
            if (funcionario != null) {
                funcionario.getDisciplinaanuladaList().remove(disciplinaanulada);
                funcionario = em.merge(funcionario);
            }
            em.remove(disciplinaanulada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Disciplinaanulada> findDisciplinaanuladaEntities() {
        return findDisciplinaanuladaEntities(true, -1, -1);
    }

    public List<Disciplinaanulada> findDisciplinaanuladaEntities(int maxResults, int firstResult) {
        return findDisciplinaanuladaEntities(false, maxResults, firstResult);
    }

    private List<Disciplinaanulada> findDisciplinaanuladaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Disciplinaanulada.class));
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

    public Disciplinaanulada findDisciplinaanulada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Disciplinaanulada.class, id);
        } finally {
            em.close();
        }
    }

    public int getDisciplinaanuladaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Disciplinaanulada> rt = cq.from(Disciplinaanulada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
