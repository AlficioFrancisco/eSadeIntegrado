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
import modelo.Motivomat;
import modelo.Curso;
import modelo.Estudante;
import modelo.Funcionario;
import modelo.Matriculaanulada;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Matricula;
import modelo.MatriculaPK;

/**
 *
 * @author Paulino Francisco
 */
public class MatriculaJpaController implements Serializable {

    public MatriculaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Matricula matricula) throws PreexistingEntityException, Exception {
        if (matricula.getMatriculaPK() == null) {
            matricula.setMatriculaPK(new MatriculaPK());
        }
        matricula.getMatriculaPK().setIdEstudante(matricula.getEstudante().getIdEstudante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Motivomat motivomat = matricula.getMotivomat();
            if (motivomat != null) {
                motivomat = em.getReference(motivomat.getClass(), motivomat.getMotivomatPK());
                matricula.setMotivomat(motivomat);
            }
            Curso curso = matricula.getCurso();
            if (curso != null) {
                curso = em.getReference(curso.getClass(), curso.getIdCurso());
                matricula.setCurso(curso);
            }
            Estudante estudante = matricula.getEstudante();
            if (estudante != null) {
                estudante = em.getReference(estudante.getClass(), estudante.getIdEstudante());
                matricula.setEstudante(estudante);
            }
            Funcionario funcionario = matricula.getFuncionario();
            if (funcionario != null) {
                funcionario = em.getReference(funcionario.getClass(), funcionario.getIdFuncionario());
                matricula.setFuncionario(funcionario);
            }
            Matriculaanulada matriculaanulada = matricula.getMatriculaanulada();
            if (matriculaanulada != null) {
                matriculaanulada = em.getReference(matriculaanulada.getClass(), matriculaanulada.getMatriculaanuladaPK());
                matricula.setMatriculaanulada(matriculaanulada);
            }
            em.persist(matricula);
            if (motivomat != null) {
                Matricula oldMatriculaOfMotivomat = motivomat.getMatricula();
                if (oldMatriculaOfMotivomat != null) {
                    oldMatriculaOfMotivomat.setMotivomat(null);
                    oldMatriculaOfMotivomat = em.merge(oldMatriculaOfMotivomat);
                }
                motivomat.setMatricula(matricula);
                motivomat = em.merge(motivomat);
            }
            if (curso != null) {
                curso.getMatriculaList().add(matricula);
                curso = em.merge(curso);
            }
            if (estudante != null) {
                estudante.getMatriculaList().add(matricula);
                estudante = em.merge(estudante);
            }
            if (funcionario != null) {
                funcionario.getMatriculaList().add(matricula);
                funcionario = em.merge(funcionario);
            }
            if (matriculaanulada != null) {
                Matricula oldMatriculaOfMatriculaanulada = matriculaanulada.getMatricula();
                if (oldMatriculaOfMatriculaanulada != null) {
                    oldMatriculaOfMatriculaanulada.setMatriculaanulada(null);
                    oldMatriculaOfMatriculaanulada = em.merge(oldMatriculaOfMatriculaanulada);
                }
                matriculaanulada.setMatricula(matricula);
                matriculaanulada = em.merge(matriculaanulada);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMatricula(matricula.getMatriculaPK()) != null) {
                throw new PreexistingEntityException("Matricula " + matricula + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matricula matricula) throws IllegalOrphanException, NonexistentEntityException, Exception {
        matricula.getMatriculaPK().setIdEstudante(matricula.getEstudante().getIdEstudante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matricula persistentMatricula = em.find(Matricula.class, matricula.getMatriculaPK());
            Motivomat motivomatOld = persistentMatricula.getMotivomat();
            Motivomat motivomatNew = matricula.getMotivomat();
            Curso cursoOld = persistentMatricula.getCurso();
            Curso cursoNew = matricula.getCurso();
            Estudante estudanteOld = persistentMatricula.getEstudante();
            Estudante estudanteNew = matricula.getEstudante();
            Funcionario funcionarioOld = persistentMatricula.getFuncionario();
            Funcionario funcionarioNew = matricula.getFuncionario();
            Matriculaanulada matriculaanuladaOld = persistentMatricula.getMatriculaanulada();
            Matriculaanulada matriculaanuladaNew = matricula.getMatriculaanulada();
            List<String> illegalOrphanMessages = null;
            if (motivomatOld != null && !motivomatOld.equals(motivomatNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Motivomat " + motivomatOld + " since its matricula field is not nullable.");
            }
            if (matriculaanuladaOld != null && !matriculaanuladaOld.equals(matriculaanuladaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Matriculaanulada " + matriculaanuladaOld + " since its matricula field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (motivomatNew != null) {
                motivomatNew = em.getReference(motivomatNew.getClass(), motivomatNew.getMotivomatPK());
                matricula.setMotivomat(motivomatNew);
            }
            if (cursoNew != null) {
                cursoNew = em.getReference(cursoNew.getClass(), cursoNew.getIdCurso());
                matricula.setCurso(cursoNew);
            }
            if (estudanteNew != null) {
                estudanteNew = em.getReference(estudanteNew.getClass(), estudanteNew.getIdEstudante());
                matricula.setEstudante(estudanteNew);
            }
            if (funcionarioNew != null) {
                funcionarioNew = em.getReference(funcionarioNew.getClass(), funcionarioNew.getIdFuncionario());
                matricula.setFuncionario(funcionarioNew);
            }
            if (matriculaanuladaNew != null) {
                matriculaanuladaNew = em.getReference(matriculaanuladaNew.getClass(), matriculaanuladaNew.getMatriculaanuladaPK());
                matricula.setMatriculaanulada(matriculaanuladaNew);
            }
            matricula = em.merge(matricula);
            if (motivomatNew != null && !motivomatNew.equals(motivomatOld)) {
                Matricula oldMatriculaOfMotivomat = motivomatNew.getMatricula();
                if (oldMatriculaOfMotivomat != null) {
                    oldMatriculaOfMotivomat.setMotivomat(null);
                    oldMatriculaOfMotivomat = em.merge(oldMatriculaOfMotivomat);
                }
                motivomatNew.setMatricula(matricula);
                motivomatNew = em.merge(motivomatNew);
            }
            if (cursoOld != null && !cursoOld.equals(cursoNew)) {
                cursoOld.getMatriculaList().remove(matricula);
                cursoOld = em.merge(cursoOld);
            }
            if (cursoNew != null && !cursoNew.equals(cursoOld)) {
                cursoNew.getMatriculaList().add(matricula);
                cursoNew = em.merge(cursoNew);
            }
            if (estudanteOld != null && !estudanteOld.equals(estudanteNew)) {
                estudanteOld.getMatriculaList().remove(matricula);
                estudanteOld = em.merge(estudanteOld);
            }
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                estudanteNew.getMatriculaList().add(matricula);
                estudanteNew = em.merge(estudanteNew);
            }
            if (funcionarioOld != null && !funcionarioOld.equals(funcionarioNew)) {
                funcionarioOld.getMatriculaList().remove(matricula);
                funcionarioOld = em.merge(funcionarioOld);
            }
            if (funcionarioNew != null && !funcionarioNew.equals(funcionarioOld)) {
                funcionarioNew.getMatriculaList().add(matricula);
                funcionarioNew = em.merge(funcionarioNew);
            }
            if (matriculaanuladaNew != null && !matriculaanuladaNew.equals(matriculaanuladaOld)) {
                Matricula oldMatriculaOfMatriculaanulada = matriculaanuladaNew.getMatricula();
                if (oldMatriculaOfMatriculaanulada != null) {
                    oldMatriculaOfMatriculaanulada.setMatriculaanulada(null);
                    oldMatriculaOfMatriculaanulada = em.merge(oldMatriculaOfMatriculaanulada);
                }
                matriculaanuladaNew.setMatricula(matricula);
                matriculaanuladaNew = em.merge(matriculaanuladaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MatriculaPK id = matricula.getMatriculaPK();
                if (findMatricula(id) == null) {
                    throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MatriculaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matricula matricula;
            try {
                matricula = em.getReference(Matricula.class, id);
                matricula.getMatriculaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Motivomat motivomatOrphanCheck = matricula.getMotivomat();
            if (motivomatOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Matricula (" + matricula + ") cannot be destroyed since the Motivomat " + motivomatOrphanCheck + " in its motivomat field has a non-nullable matricula field.");
            }
            Matriculaanulada matriculaanuladaOrphanCheck = matricula.getMatriculaanulada();
            if (matriculaanuladaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Matricula (" + matricula + ") cannot be destroyed since the Matriculaanulada " + matriculaanuladaOrphanCheck + " in its matriculaanulada field has a non-nullable matricula field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Curso curso = matricula.getCurso();
            if (curso != null) {
                curso.getMatriculaList().remove(matricula);
                curso = em.merge(curso);
            }
            Estudante estudante = matricula.getEstudante();
            if (estudante != null) {
                estudante.getMatriculaList().remove(matricula);
                estudante = em.merge(estudante);
            }
            Funcionario funcionario = matricula.getFuncionario();
            if (funcionario != null) {
                funcionario.getMatriculaList().remove(matricula);
                funcionario = em.merge(funcionario);
            }
            em.remove(matricula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Matricula> findMatriculaEntities() {
        return findMatriculaEntities(true, -1, -1);
    }

    public List<Matricula> findMatriculaEntities(int maxResults, int firstResult) {
        return findMatriculaEntities(false, maxResults, firstResult);
    }

    private List<Matricula> findMatriculaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matricula.class));
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

    public Matricula findMatricula(MatriculaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matricula.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatriculaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matricula> rt = cq.from(Matricula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
