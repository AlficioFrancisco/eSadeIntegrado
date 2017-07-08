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
import modelo.Curso;
import modelo.Estudante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Ingressotransferencia;

/**
 *
 * @author Paulino Francisco
 */
public class IngressotransferenciaJpaController implements Serializable {

    public IngressotransferenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingressotransferencia ingressotransferencia) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Estudante estudanteOrphanCheck = ingressotransferencia.getEstudante();
        if (estudanteOrphanCheck != null) {
            Ingressotransferencia oldIngressotransferenciaOfEstudante = estudanteOrphanCheck.getIngressotransferencia();
            if (oldIngressotransferenciaOfEstudante != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Estudante " + estudanteOrphanCheck + " already has an item of type Ingressotransferencia whose estudante column cannot be null. Please make another selection for the estudante field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso curso = ingressotransferencia.getCurso();
            if (curso != null) {
                curso = em.getReference(curso.getClass(), curso.getIdCurso());
                ingressotransferencia.setCurso(curso);
            }
            Estudante estudante = ingressotransferencia.getEstudante();
            if (estudante != null) {
                estudante = em.getReference(estudante.getClass(), estudante.getIdEstudante());
                ingressotransferencia.setEstudante(estudante);
            }
            em.persist(ingressotransferencia);
            if (curso != null) {
                curso.getIngressotransferenciaList().add(ingressotransferencia);
                curso = em.merge(curso);
            }
            if (estudante != null) {
                estudante.setIngressotransferencia(ingressotransferencia);
                estudante = em.merge(estudante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIngressotransferencia(ingressotransferencia.getIdEstudante()) != null) {
                throw new PreexistingEntityException("Ingressotransferencia " + ingressotransferencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingressotransferencia ingressotransferencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingressotransferencia persistentIngressotransferencia = em.find(Ingressotransferencia.class, ingressotransferencia.getIdEstudante());
            Curso cursoOld = persistentIngressotransferencia.getCurso();
            Curso cursoNew = ingressotransferencia.getCurso();
            Estudante estudanteOld = persistentIngressotransferencia.getEstudante();
            Estudante estudanteNew = ingressotransferencia.getEstudante();
            List<String> illegalOrphanMessages = null;
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                Ingressotransferencia oldIngressotransferenciaOfEstudante = estudanteNew.getIngressotransferencia();
                if (oldIngressotransferenciaOfEstudante != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Estudante " + estudanteNew + " already has an item of type Ingressotransferencia whose estudante column cannot be null. Please make another selection for the estudante field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cursoNew != null) {
                cursoNew = em.getReference(cursoNew.getClass(), cursoNew.getIdCurso());
                ingressotransferencia.setCurso(cursoNew);
            }
            if (estudanteNew != null) {
                estudanteNew = em.getReference(estudanteNew.getClass(), estudanteNew.getIdEstudante());
                ingressotransferencia.setEstudante(estudanteNew);
            }
            ingressotransferencia = em.merge(ingressotransferencia);
            if (cursoOld != null && !cursoOld.equals(cursoNew)) {
                cursoOld.getIngressotransferenciaList().remove(ingressotransferencia);
                cursoOld = em.merge(cursoOld);
            }
            if (cursoNew != null && !cursoNew.equals(cursoOld)) {
                cursoNew.getIngressotransferenciaList().add(ingressotransferencia);
                cursoNew = em.merge(cursoNew);
            }
            if (estudanteOld != null && !estudanteOld.equals(estudanteNew)) {
                estudanteOld.setIngressotransferencia(null);
                estudanteOld = em.merge(estudanteOld);
            }
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                estudanteNew.setIngressotransferencia(ingressotransferencia);
                estudanteNew = em.merge(estudanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ingressotransferencia.getIdEstudante();
                if (findIngressotransferencia(id) == null) {
                    throw new NonexistentEntityException("The ingressotransferencia with id " + id + " no longer exists.");
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
            Ingressotransferencia ingressotransferencia;
            try {
                ingressotransferencia = em.getReference(Ingressotransferencia.class, id);
                ingressotransferencia.getIdEstudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingressotransferencia with id " + id + " no longer exists.", enfe);
            }
            Curso curso = ingressotransferencia.getCurso();
            if (curso != null) {
                curso.getIngressotransferenciaList().remove(ingressotransferencia);
                curso = em.merge(curso);
            }
            Estudante estudante = ingressotransferencia.getEstudante();
            if (estudante != null) {
                estudante.setIngressotransferencia(null);
                estudante = em.merge(estudante);
            }
            em.remove(ingressotransferencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingressotransferencia> findIngressotransferenciaEntities() {
        return findIngressotransferenciaEntities(true, -1, -1);
    }

    public List<Ingressotransferencia> findIngressotransferenciaEntities(int maxResults, int firstResult) {
        return findIngressotransferenciaEntities(false, maxResults, firstResult);
    }

    private List<Ingressotransferencia> findIngressotransferenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingressotransferencia.class));
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

    public Ingressotransferencia findIngressotransferencia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingressotransferencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngressotransferenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingressotransferencia> rt = cq.from(Ingressotransferencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
