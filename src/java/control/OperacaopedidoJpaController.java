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
import modelo.Funcionario;
import modelo.Operacao;
import modelo.Operacaopedido;

/**
 *
 * @author Paulino Francisco
 */
public class OperacaopedidoJpaController implements Serializable {

    public OperacaopedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Operacaopedido operacaopedido) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario autor = operacaopedido.getAutor();
            if (autor != null) {
                autor = em.getReference(autor.getClass(), autor.getIdFuncionario());
                operacaopedido.setAutor(autor);
            }
            Funcionario funcionario = operacaopedido.getFuncionario();
            if (funcionario != null) {
                funcionario = em.getReference(funcionario.getClass(), funcionario.getIdFuncionario());
                operacaopedido.setFuncionario(funcionario);
            }
            Operacao operacao = operacaopedido.getOperacao();
            if (operacao != null) {
                operacao = em.getReference(operacao.getClass(), operacao.getIdoperacao());
                operacaopedido.setOperacao(operacao);
            }
            em.persist(operacaopedido);
            if (autor != null) {
                autor.getOperacaopedidoList().add(operacaopedido);
                autor = em.merge(autor);
            }
            if (funcionario != null) {
                funcionario.getOperacaopedidoList().add(operacaopedido);
                funcionario = em.merge(funcionario);
            }
            if (operacao != null) {
                operacao.getOperacaopedidoList().add(operacaopedido);
                operacao = em.merge(operacao);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Operacaopedido operacaopedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Operacaopedido persistentOperacaopedido = em.find(Operacaopedido.class, operacaopedido.getIdpermissao());
            Funcionario autorOld = persistentOperacaopedido.getAutor();
            Funcionario autorNew = operacaopedido.getAutor();
            Funcionario funcionarioOld = persistentOperacaopedido.getFuncionario();
            Funcionario funcionarioNew = operacaopedido.getFuncionario();
            Operacao operacaoOld = persistentOperacaopedido.getOperacao();
            Operacao operacaoNew = operacaopedido.getOperacao();
            if (autorNew != null) {
                autorNew = em.getReference(autorNew.getClass(), autorNew.getIdFuncionario());
                operacaopedido.setAutor(autorNew);
            }
            if (funcionarioNew != null) {
                funcionarioNew = em.getReference(funcionarioNew.getClass(), funcionarioNew.getIdFuncionario());
                operacaopedido.setFuncionario(funcionarioNew);
            }
            if (operacaoNew != null) {
                operacaoNew = em.getReference(operacaoNew.getClass(), operacaoNew.getIdoperacao());
                operacaopedido.setOperacao(operacaoNew);
            }
            operacaopedido = em.merge(operacaopedido);
            if (autorOld != null && !autorOld.equals(autorNew)) {
                autorOld.getOperacaopedidoList().remove(operacaopedido);
                autorOld = em.merge(autorOld);
            }
            if (autorNew != null && !autorNew.equals(autorOld)) {
                autorNew.getOperacaopedidoList().add(operacaopedido);
                autorNew = em.merge(autorNew);
            }
            if (funcionarioOld != null && !funcionarioOld.equals(funcionarioNew)) {
                funcionarioOld.getOperacaopedidoList().remove(operacaopedido);
                funcionarioOld = em.merge(funcionarioOld);
            }
            if (funcionarioNew != null && !funcionarioNew.equals(funcionarioOld)) {
                funcionarioNew.getOperacaopedidoList().add(operacaopedido);
                funcionarioNew = em.merge(funcionarioNew);
            }
            if (operacaoOld != null && !operacaoOld.equals(operacaoNew)) {
                operacaoOld.getOperacaopedidoList().remove(operacaopedido);
                operacaoOld = em.merge(operacaoOld);
            }
            if (operacaoNew != null && !operacaoNew.equals(operacaoOld)) {
                operacaoNew.getOperacaopedidoList().add(operacaopedido);
                operacaoNew = em.merge(operacaoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = operacaopedido.getIdpermissao();
                if (findOperacaopedido(id) == null) {
                    throw new NonexistentEntityException("The operacaopedido with id " + id + " no longer exists.");
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
            Operacaopedido operacaopedido;
            try {
                operacaopedido = em.getReference(Operacaopedido.class, id);
                operacaopedido.getIdpermissao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The operacaopedido with id " + id + " no longer exists.", enfe);
            }
            Funcionario autor = operacaopedido.getAutor();
            if (autor != null) {
                autor.getOperacaopedidoList().remove(operacaopedido);
                autor = em.merge(autor);
            }
            Funcionario funcionario = operacaopedido.getFuncionario();
            if (funcionario != null) {
                funcionario.getOperacaopedidoList().remove(operacaopedido);
                funcionario = em.merge(funcionario);
            }
            Operacao operacao = operacaopedido.getOperacao();
            if (operacao != null) {
                operacao.getOperacaopedidoList().remove(operacaopedido);
                operacao = em.merge(operacao);
            }
            em.remove(operacaopedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Operacaopedido> findOperacaopedidoEntities() {
        return findOperacaopedidoEntities(true, -1, -1);
    }

    public List<Operacaopedido> findOperacaopedidoEntities(int maxResults, int firstResult) {
        return findOperacaopedidoEntities(false, maxResults, firstResult);
    }

    private List<Operacaopedido> findOperacaopedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Operacaopedido.class));
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

    public Operacaopedido findOperacaopedido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Operacaopedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getOperacaopedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Operacaopedido> rt = cq.from(Operacaopedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
