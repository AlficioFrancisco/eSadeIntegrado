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
import modelo.Funcionario;
import modelo.Matricula;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Matriculaanulada;
import modelo.MatriculaanuladaPK;

/**
 *
 * @author Paulino Francisco
 */
public class MatriculaanuladaJpaController implements Serializable {

    public MatriculaanuladaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Matriculaanulada matriculaanulada) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (matriculaanulada.getMatriculaanuladaPK() == null) {
            matriculaanulada.setMatriculaanuladaPK(new MatriculaanuladaPK());
        }
        matriculaanulada.getMatriculaanuladaPK().setIdEstudante(matriculaanulada.getMatricula().getMatriculaPK().getIdEstudante());
        matriculaanulada.getMatriculaanuladaPK().setAno(matriculaanulada.getMatricula().getMatriculaPK().getAno());
        List<String> illegalOrphanMessages = null;
        Matricula matriculaOrphanCheck = matriculaanulada.getMatricula();
        if (matriculaOrphanCheck != null) {
            Matriculaanulada oldMatriculaanuladaOfMatricula = matriculaOrphanCheck.getMatriculaanulada();
            if (oldMatriculaanuladaOfMatricula != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Matricula " + matriculaOrphanCheck + " already has an item of type Matriculaanulada whose matricula column cannot be null. Please make another selection for the matricula field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario funcionario = matriculaanulada.getFuncionario();
            if (funcionario != null) {
                funcionario = em.getReference(funcionario.getClass(), funcionario.getIdFuncionario());
                matriculaanulada.setFuncionario(funcionario);
            }
            Matricula matricula = matriculaanulada.getMatricula();
            if (matricula != null) {
                matricula = em.getReference(matricula.getClass(), matricula.getMatriculaPK());
                matriculaanulada.setMatricula(matricula);
            }
            em.persist(matriculaanulada);
            if (funcionario != null) {
                funcionario.getMatriculaanuladaList().add(matriculaanulada);
                funcionario = em.merge(funcionario);
            }
            if (matricula != null) {
                matricula.setMatriculaanulada(matriculaanulada);
                matricula = em.merge(matricula);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMatriculaanulada(matriculaanulada.getMatriculaanuladaPK()) != null) {
                throw new PreexistingEntityException("Matriculaanulada " + matriculaanulada + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matriculaanulada matriculaanulada) throws IllegalOrphanException, NonexistentEntityException, Exception {
        matriculaanulada.getMatriculaanuladaPK().setIdEstudante(matriculaanulada.getMatricula().getMatriculaPK().getIdEstudante());
        matriculaanulada.getMatriculaanuladaPK().setAno(matriculaanulada.getMatricula().getMatriculaPK().getAno());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matriculaanulada persistentMatriculaanulada = em.find(Matriculaanulada.class, matriculaanulada.getMatriculaanuladaPK());
            Funcionario funcionarioOld = persistentMatriculaanulada.getFuncionario();
            Funcionario funcionarioNew = matriculaanulada.getFuncionario();
            Matricula matriculaOld = persistentMatriculaanulada.getMatricula();
            Matricula matriculaNew = matriculaanulada.getMatricula();
            List<String> illegalOrphanMessages = null;
            if (matriculaNew != null && !matriculaNew.equals(matriculaOld)) {
                Matriculaanulada oldMatriculaanuladaOfMatricula = matriculaNew.getMatriculaanulada();
                if (oldMatriculaanuladaOfMatricula != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Matricula " + matriculaNew + " already has an item of type Matriculaanulada whose matricula column cannot be null. Please make another selection for the matricula field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (funcionarioNew != null) {
                funcionarioNew = em.getReference(funcionarioNew.getClass(), funcionarioNew.getIdFuncionario());
                matriculaanulada.setFuncionario(funcionarioNew);
            }
            if (matriculaNew != null) {
                matriculaNew = em.getReference(matriculaNew.getClass(), matriculaNew.getMatriculaPK());
                matriculaanulada.setMatricula(matriculaNew);
            }
            matriculaanulada = em.merge(matriculaanulada);
            if (funcionarioOld != null && !funcionarioOld.equals(funcionarioNew)) {
                funcionarioOld.getMatriculaanuladaList().remove(matriculaanulada);
                funcionarioOld = em.merge(funcionarioOld);
            }
            if (funcionarioNew != null && !funcionarioNew.equals(funcionarioOld)) {
                funcionarioNew.getMatriculaanuladaList().add(matriculaanulada);
                funcionarioNew = em.merge(funcionarioNew);
            }
            if (matriculaOld != null && !matriculaOld.equals(matriculaNew)) {
                matriculaOld.setMatriculaanulada(null);
                matriculaOld = em.merge(matriculaOld);
            }
            if (matriculaNew != null && !matriculaNew.equals(matriculaOld)) {
                matriculaNew.setMatriculaanulada(matriculaanulada);
                matriculaNew = em.merge(matriculaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MatriculaanuladaPK id = matriculaanulada.getMatriculaanuladaPK();
                if (findMatriculaanulada(id) == null) {
                    throw new NonexistentEntityException("The matriculaanulada with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MatriculaanuladaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matriculaanulada matriculaanulada;
            try {
                matriculaanulada = em.getReference(Matriculaanulada.class, id);
                matriculaanulada.getMatriculaanuladaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matriculaanulada with id " + id + " no longer exists.", enfe);
            }
            Funcionario funcionario = matriculaanulada.getFuncionario();
            if (funcionario != null) {
                funcionario.getMatriculaanuladaList().remove(matriculaanulada);
                funcionario = em.merge(funcionario);
            }
            Matricula matricula = matriculaanulada.getMatricula();
            if (matricula != null) {
                matricula.setMatriculaanulada(null);
                matricula = em.merge(matricula);
            }
            em.remove(matriculaanulada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Matriculaanulada> findMatriculaanuladaEntities() {
        return findMatriculaanuladaEntities(true, -1, -1);
    }

    public List<Matriculaanulada> findMatriculaanuladaEntities(int maxResults, int firstResult) {
        return findMatriculaanuladaEntities(false, maxResults, firstResult);
    }

    private List<Matriculaanulada> findMatriculaanuladaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matriculaanulada.class));
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

    public Matriculaanulada findMatriculaanulada(MatriculaanuladaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matriculaanulada.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatriculaanuladaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matriculaanulada> rt = cq.from(Matriculaanulada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
