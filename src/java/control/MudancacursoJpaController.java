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
import modelo.Curso;
import modelo.Estudante;
import modelo.Mudancacurso;

/**
 *
 * @author Paulino Francisco
 */
public class MudancacursoJpaController implements Serializable {

    public MudancacursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mudancacurso mudancacurso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso cursoDestino = mudancacurso.getCursoDestino();
            if (cursoDestino != null) {
                cursoDestino = em.getReference(cursoDestino.getClass(), cursoDestino.getIdCurso());
                mudancacurso.setCursoDestino(cursoDestino);
            }
            Curso cursoOrigem = mudancacurso.getCursoOrigem();
            if (cursoOrigem != null) {
                cursoOrigem = em.getReference(cursoOrigem.getClass(), cursoOrigem.getIdCurso());
                mudancacurso.setCursoOrigem(cursoOrigem);
            }
            Estudante idEstudante = mudancacurso.getIdEstudante();
            if (idEstudante != null) {
                idEstudante = em.getReference(idEstudante.getClass(), idEstudante.getIdEstudante());
                mudancacurso.setIdEstudante(idEstudante);
            }
            em.persist(mudancacurso);
            if (cursoDestino != null) {
                cursoDestino.getMudancacursoList().add(mudancacurso);
                cursoDestino = em.merge(cursoDestino);
            }
            if (cursoOrigem != null) {
                cursoOrigem.getMudancacursoList().add(mudancacurso);
                cursoOrigem = em.merge(cursoOrigem);
            }
            if (idEstudante != null) {
                idEstudante.getMudancacursoList().add(mudancacurso);
                idEstudante = em.merge(idEstudante);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mudancacurso mudancacurso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mudancacurso persistentMudancacurso = em.find(Mudancacurso.class, mudancacurso.getIdmudanca());
            Curso cursoDestinoOld = persistentMudancacurso.getCursoDestino();
            Curso cursoDestinoNew = mudancacurso.getCursoDestino();
            Curso cursoOrigemOld = persistentMudancacurso.getCursoOrigem();
            Curso cursoOrigemNew = mudancacurso.getCursoOrigem();
            Estudante idEstudanteOld = persistentMudancacurso.getIdEstudante();
            Estudante idEstudanteNew = mudancacurso.getIdEstudante();
            if (cursoDestinoNew != null) {
                cursoDestinoNew = em.getReference(cursoDestinoNew.getClass(), cursoDestinoNew.getIdCurso());
                mudancacurso.setCursoDestino(cursoDestinoNew);
            }
            if (cursoOrigemNew != null) {
                cursoOrigemNew = em.getReference(cursoOrigemNew.getClass(), cursoOrigemNew.getIdCurso());
                mudancacurso.setCursoOrigem(cursoOrigemNew);
            }
            if (idEstudanteNew != null) {
                idEstudanteNew = em.getReference(idEstudanteNew.getClass(), idEstudanteNew.getIdEstudante());
                mudancacurso.setIdEstudante(idEstudanteNew);
            }
            mudancacurso = em.merge(mudancacurso);
            if (cursoDestinoOld != null && !cursoDestinoOld.equals(cursoDestinoNew)) {
                cursoDestinoOld.getMudancacursoList().remove(mudancacurso);
                cursoDestinoOld = em.merge(cursoDestinoOld);
            }
            if (cursoDestinoNew != null && !cursoDestinoNew.equals(cursoDestinoOld)) {
                cursoDestinoNew.getMudancacursoList().add(mudancacurso);
                cursoDestinoNew = em.merge(cursoDestinoNew);
            }
            if (cursoOrigemOld != null && !cursoOrigemOld.equals(cursoOrigemNew)) {
                cursoOrigemOld.getMudancacursoList().remove(mudancacurso);
                cursoOrigemOld = em.merge(cursoOrigemOld);
            }
            if (cursoOrigemNew != null && !cursoOrigemNew.equals(cursoOrigemOld)) {
                cursoOrigemNew.getMudancacursoList().add(mudancacurso);
                cursoOrigemNew = em.merge(cursoOrigemNew);
            }
            if (idEstudanteOld != null && !idEstudanteOld.equals(idEstudanteNew)) {
                idEstudanteOld.getMudancacursoList().remove(mudancacurso);
                idEstudanteOld = em.merge(idEstudanteOld);
            }
            if (idEstudanteNew != null && !idEstudanteNew.equals(idEstudanteOld)) {
                idEstudanteNew.getMudancacursoList().add(mudancacurso);
                idEstudanteNew = em.merge(idEstudanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mudancacurso.getIdmudanca();
                if (findMudancacurso(id) == null) {
                    throw new NonexistentEntityException("The mudancacurso with id " + id + " no longer exists.");
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
            Mudancacurso mudancacurso;
            try {
                mudancacurso = em.getReference(Mudancacurso.class, id);
                mudancacurso.getIdmudanca();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mudancacurso with id " + id + " no longer exists.", enfe);
            }
            Curso cursoDestino = mudancacurso.getCursoDestino();
            if (cursoDestino != null) {
                cursoDestino.getMudancacursoList().remove(mudancacurso);
                cursoDestino = em.merge(cursoDestino);
            }
            Curso cursoOrigem = mudancacurso.getCursoOrigem();
            if (cursoOrigem != null) {
                cursoOrigem.getMudancacursoList().remove(mudancacurso);
                cursoOrigem = em.merge(cursoOrigem);
            }
            Estudante idEstudante = mudancacurso.getIdEstudante();
            if (idEstudante != null) {
                idEstudante.getMudancacursoList().remove(mudancacurso);
                idEstudante = em.merge(idEstudante);
            }
            em.remove(mudancacurso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mudancacurso> findMudancacursoEntities() {
        return findMudancacursoEntities(true, -1, -1);
    }

    public List<Mudancacurso> findMudancacursoEntities(int maxResults, int firstResult) {
        return findMudancacursoEntities(false, maxResults, firstResult);
    }

    private List<Mudancacurso> findMudancacursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mudancacurso.class));
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

    public Mudancacurso findMudancacurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mudancacurso.class, id);
        } finally {
            em.close();
        }
    }

    public int getMudancacursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mudancacurso> rt = cq.from(Mudancacurso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
