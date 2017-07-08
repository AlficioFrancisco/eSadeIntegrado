/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Funcionario;
import modelo.Notificacao;

/**
 *
 * @author Paulino Francisco
 */
public class NotificacaoJpaController implements Serializable {

    public NotificacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notificacao notificacao) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario idFuncionario = notificacao.getIdFuncionario();
            if (idFuncionario != null) {
                idFuncionario = em.getReference(idFuncionario.getClass(), idFuncionario.getIdFuncionario());
                notificacao.setIdFuncionario(idFuncionario);
            }
            em.persist(notificacao);
            if (idFuncionario != null) {
                idFuncionario.getNotificacaoList().add(notificacao);
                idFuncionario = em.merge(idFuncionario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNotificacao(notificacao.getData()) != null) {
                throw new PreexistingEntityException("Notificacao " + notificacao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notificacao notificacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notificacao persistentNotificacao = em.find(Notificacao.class, notificacao.getData());
            Funcionario idFuncionarioOld = persistentNotificacao.getIdFuncionario();
            Funcionario idFuncionarioNew = notificacao.getIdFuncionario();
            if (idFuncionarioNew != null) {
                idFuncionarioNew = em.getReference(idFuncionarioNew.getClass(), idFuncionarioNew.getIdFuncionario());
                notificacao.setIdFuncionario(idFuncionarioNew);
            }
            notificacao = em.merge(notificacao);
            if (idFuncionarioOld != null && !idFuncionarioOld.equals(idFuncionarioNew)) {
                idFuncionarioOld.getNotificacaoList().remove(notificacao);
                idFuncionarioOld = em.merge(idFuncionarioOld);
            }
            if (idFuncionarioNew != null && !idFuncionarioNew.equals(idFuncionarioOld)) {
                idFuncionarioNew.getNotificacaoList().add(notificacao);
                idFuncionarioNew = em.merge(idFuncionarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Date id = notificacao.getData();
                if (findNotificacao(id) == null) {
                    throw new NonexistentEntityException("The notificacao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Date id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notificacao notificacao;
            try {
                notificacao = em.getReference(Notificacao.class, id);
                notificacao.getData();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificacao with id " + id + " no longer exists.", enfe);
            }
            Funcionario idFuncionario = notificacao.getIdFuncionario();
            if (idFuncionario != null) {
                idFuncionario.getNotificacaoList().remove(notificacao);
                idFuncionario = em.merge(idFuncionario);
            }
            em.remove(notificacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notificacao> findNotificacaoEntities() {
        return findNotificacaoEntities(true, -1, -1);
    }

    public List<Notificacao> findNotificacaoEntities(int maxResults, int firstResult) {
        return findNotificacaoEntities(false, maxResults, firstResult);
    }

    private List<Notificacao> findNotificacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notificacao.class));
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

    public Notificacao findNotificacao(Date id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notificacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notificacao> rt = cq.from(Notificacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
