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
import modelo.Estudante;
import modelo.Provincia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Ingressoexameadmissao;

/**
 *
 * @author Paulino Francisco
 */
public class IngressoexameadmissaoJpaController implements Serializable {

    public IngressoexameadmissaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingressoexameadmissao ingressoexameadmissao) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Estudante estudanteOrphanCheck = ingressoexameadmissao.getEstudante();
        if (estudanteOrphanCheck != null) {
            Ingressoexameadmissao oldIngressoexameadmissaoOfEstudante = estudanteOrphanCheck.getIngressoexameadmissao();
            if (oldIngressoexameadmissaoOfEstudante != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Estudante " + estudanteOrphanCheck + " already has an item of type Ingressoexameadmissao whose estudante column cannot be null. Please make another selection for the estudante field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudante estudante = ingressoexameadmissao.getEstudante();
            if (estudante != null) {
                estudante = em.getReference(estudante.getClass(), estudante.getIdEstudante());
                ingressoexameadmissao.setEstudante(estudante);
            }
            Provincia provinciaAdmissao = ingressoexameadmissao.getProvinciaAdmissao();
            if (provinciaAdmissao != null) {
                provinciaAdmissao = em.getReference(provinciaAdmissao.getClass(), provinciaAdmissao.getIdProvincia());
                ingressoexameadmissao.setProvinciaAdmissao(provinciaAdmissao);
            }
            em.persist(ingressoexameadmissao);
            if (estudante != null) {
                estudante.setIngressoexameadmissao(ingressoexameadmissao);
                estudante = em.merge(estudante);
            }
            if (provinciaAdmissao != null) {
                provinciaAdmissao.getIngressoexameadmissaoList().add(ingressoexameadmissao);
                provinciaAdmissao = em.merge(provinciaAdmissao);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIngressoexameadmissao(ingressoexameadmissao.getIdEstudante()) != null) {
                throw new PreexistingEntityException("Ingressoexameadmissao " + ingressoexameadmissao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingressoexameadmissao ingressoexameadmissao) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingressoexameadmissao persistentIngressoexameadmissao = em.find(Ingressoexameadmissao.class, ingressoexameadmissao.getIdEstudante());
            Estudante estudanteOld = persistentIngressoexameadmissao.getEstudante();
            Estudante estudanteNew = ingressoexameadmissao.getEstudante();
            Provincia provinciaAdmissaoOld = persistentIngressoexameadmissao.getProvinciaAdmissao();
            Provincia provinciaAdmissaoNew = ingressoexameadmissao.getProvinciaAdmissao();
            List<String> illegalOrphanMessages = null;
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                Ingressoexameadmissao oldIngressoexameadmissaoOfEstudante = estudanteNew.getIngressoexameadmissao();
                if (oldIngressoexameadmissaoOfEstudante != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Estudante " + estudanteNew + " already has an item of type Ingressoexameadmissao whose estudante column cannot be null. Please make another selection for the estudante field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estudanteNew != null) {
                estudanteNew = em.getReference(estudanteNew.getClass(), estudanteNew.getIdEstudante());
                ingressoexameadmissao.setEstudante(estudanteNew);
            }
            if (provinciaAdmissaoNew != null) {
                provinciaAdmissaoNew = em.getReference(provinciaAdmissaoNew.getClass(), provinciaAdmissaoNew.getIdProvincia());
                ingressoexameadmissao.setProvinciaAdmissao(provinciaAdmissaoNew);
            }
            ingressoexameadmissao = em.merge(ingressoexameadmissao);
            if (estudanteOld != null && !estudanteOld.equals(estudanteNew)) {
                estudanteOld.setIngressoexameadmissao(null);
                estudanteOld = em.merge(estudanteOld);
            }
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                estudanteNew.setIngressoexameadmissao(ingressoexameadmissao);
                estudanteNew = em.merge(estudanteNew);
            }
            if (provinciaAdmissaoOld != null && !provinciaAdmissaoOld.equals(provinciaAdmissaoNew)) {
                provinciaAdmissaoOld.getIngressoexameadmissaoList().remove(ingressoexameadmissao);
                provinciaAdmissaoOld = em.merge(provinciaAdmissaoOld);
            }
            if (provinciaAdmissaoNew != null && !provinciaAdmissaoNew.equals(provinciaAdmissaoOld)) {
                provinciaAdmissaoNew.getIngressoexameadmissaoList().add(ingressoexameadmissao);
                provinciaAdmissaoNew = em.merge(provinciaAdmissaoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ingressoexameadmissao.getIdEstudante();
                if (findIngressoexameadmissao(id) == null) {
                    throw new NonexistentEntityException("The ingressoexameadmissao with id " + id + " no longer exists.");
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
            Ingressoexameadmissao ingressoexameadmissao;
            try {
                ingressoexameadmissao = em.getReference(Ingressoexameadmissao.class, id);
                ingressoexameadmissao.getIdEstudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingressoexameadmissao with id " + id + " no longer exists.", enfe);
            }
            Estudante estudante = ingressoexameadmissao.getEstudante();
            if (estudante != null) {
                estudante.setIngressoexameadmissao(null);
                estudante = em.merge(estudante);
            }
            Provincia provinciaAdmissao = ingressoexameadmissao.getProvinciaAdmissao();
            if (provinciaAdmissao != null) {
                provinciaAdmissao.getIngressoexameadmissaoList().remove(ingressoexameadmissao);
                provinciaAdmissao = em.merge(provinciaAdmissao);
            }
            em.remove(ingressoexameadmissao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingressoexameadmissao> findIngressoexameadmissaoEntities() {
        return findIngressoexameadmissaoEntities(true, -1, -1);
    }

    public List<Ingressoexameadmissao> findIngressoexameadmissaoEntities(int maxResults, int firstResult) {
        return findIngressoexameadmissaoEntities(false, maxResults, firstResult);
    }

    private List<Ingressoexameadmissao> findIngressoexameadmissaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingressoexameadmissao.class));
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

    public Ingressoexameadmissao findIngressoexameadmissao(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingressoexameadmissao.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngressoexameadmissaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingressoexameadmissao> rt = cq.from(Ingressoexameadmissao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
