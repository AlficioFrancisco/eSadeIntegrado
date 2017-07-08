/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Operacaopedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Operacao;

/**
 *
 * @author Paulino Francisco
 */
public class OperacaoJpaController implements Serializable {

    public OperacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Operacao operacao) throws PreexistingEntityException, Exception {
        if (operacao.getOperacaopedidoList() == null) {
            operacao.setOperacaopedidoList(new ArrayList<Operacaopedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Operacaopedido> attachedOperacaopedidoList = new ArrayList<Operacaopedido>();
            for (Operacaopedido operacaopedidoListOperacaopedidoToAttach : operacao.getOperacaopedidoList()) {
                operacaopedidoListOperacaopedidoToAttach = em.getReference(operacaopedidoListOperacaopedidoToAttach.getClass(), operacaopedidoListOperacaopedidoToAttach.getIdpermissao());
                attachedOperacaopedidoList.add(operacaopedidoListOperacaopedidoToAttach);
            }
            operacao.setOperacaopedidoList(attachedOperacaopedidoList);
            em.persist(operacao);
            for (Operacaopedido operacaopedidoListOperacaopedido : operacao.getOperacaopedidoList()) {
                Operacao oldOperacaoOfOperacaopedidoListOperacaopedido = operacaopedidoListOperacaopedido.getOperacao();
                operacaopedidoListOperacaopedido.setOperacao(operacao);
                operacaopedidoListOperacaopedido = em.merge(operacaopedidoListOperacaopedido);
                if (oldOperacaoOfOperacaopedidoListOperacaopedido != null) {
                    oldOperacaoOfOperacaopedidoListOperacaopedido.getOperacaopedidoList().remove(operacaopedidoListOperacaopedido);
                    oldOperacaoOfOperacaopedidoListOperacaopedido = em.merge(oldOperacaoOfOperacaopedidoListOperacaopedido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOperacao(operacao.getIdoperacao()) != null) {
                throw new PreexistingEntityException("Operacao " + operacao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Operacao operacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Operacao persistentOperacao = em.find(Operacao.class, operacao.getIdoperacao());
            List<Operacaopedido> operacaopedidoListOld = persistentOperacao.getOperacaopedidoList();
            List<Operacaopedido> operacaopedidoListNew = operacao.getOperacaopedidoList();
            List<Operacaopedido> attachedOperacaopedidoListNew = new ArrayList<Operacaopedido>();
            for (Operacaopedido operacaopedidoListNewOperacaopedidoToAttach : operacaopedidoListNew) {
                operacaopedidoListNewOperacaopedidoToAttach = em.getReference(operacaopedidoListNewOperacaopedidoToAttach.getClass(), operacaopedidoListNewOperacaopedidoToAttach.getIdpermissao());
                attachedOperacaopedidoListNew.add(operacaopedidoListNewOperacaopedidoToAttach);
            }
            operacaopedidoListNew = attachedOperacaopedidoListNew;
            operacao.setOperacaopedidoList(operacaopedidoListNew);
            operacao = em.merge(operacao);
            for (Operacaopedido operacaopedidoListOldOperacaopedido : operacaopedidoListOld) {
                if (!operacaopedidoListNew.contains(operacaopedidoListOldOperacaopedido)) {
                    operacaopedidoListOldOperacaopedido.setOperacao(null);
                    operacaopedidoListOldOperacaopedido = em.merge(operacaopedidoListOldOperacaopedido);
                }
            }
            for (Operacaopedido operacaopedidoListNewOperacaopedido : operacaopedidoListNew) {
                if (!operacaopedidoListOld.contains(operacaopedidoListNewOperacaopedido)) {
                    Operacao oldOperacaoOfOperacaopedidoListNewOperacaopedido = operacaopedidoListNewOperacaopedido.getOperacao();
                    operacaopedidoListNewOperacaopedido.setOperacao(operacao);
                    operacaopedidoListNewOperacaopedido = em.merge(operacaopedidoListNewOperacaopedido);
                    if (oldOperacaoOfOperacaopedidoListNewOperacaopedido != null && !oldOperacaoOfOperacaopedidoListNewOperacaopedido.equals(operacao)) {
                        oldOperacaoOfOperacaopedidoListNewOperacaopedido.getOperacaopedidoList().remove(operacaopedidoListNewOperacaopedido);
                        oldOperacaoOfOperacaopedidoListNewOperacaopedido = em.merge(oldOperacaoOfOperacaopedidoListNewOperacaopedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = operacao.getIdoperacao();
                if (findOperacao(id) == null) {
                    throw new NonexistentEntityException("The operacao with id " + id + " no longer exists.");
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
            Operacao operacao;
            try {
                operacao = em.getReference(Operacao.class, id);
                operacao.getIdoperacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operacao with id " + id + " no longer exists.", enfe);
            }
            List<Operacaopedido> operacaopedidoList = operacao.getOperacaopedidoList();
            for (Operacaopedido operacaopedidoListOperacaopedido : operacaopedidoList) {
                operacaopedidoListOperacaopedido.setOperacao(null);
                operacaopedidoListOperacaopedido = em.merge(operacaopedidoListOperacaopedido);
            }
            em.remove(operacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Operacao> findOperacaoEntities() {
        return findOperacaoEntities(true, -1, -1);
    }

    public List<Operacao> findOperacaoEntities(int maxResults, int firstResult) {
        return findOperacaoEntities(false, maxResults, firstResult);
    }

    private List<Operacao> findOperacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operacao.class));
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

    public Operacao findOperacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Operacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Operacao> rt = cq.from(Operacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
