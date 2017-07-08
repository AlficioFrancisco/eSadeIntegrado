/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Faculdade;
import modelo.Validacaopendente;
import modelo.ValidacaopendentePK;

/**
 *
 * @author Paulino Francisco
 */
public class ValidacaopendenteJpaController implements Serializable {

    public ValidacaopendenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Validacaopendente validacaopendente) throws PreexistingEntityException, Exception {
        if (validacaopendente.getValidacaopendentePK() == null) {
            validacaopendente.setValidacaopendentePK(new ValidacaopendentePK());
        }
        validacaopendente.getValidacaopendentePK().setIdfaculdade(validacaopendente.getFaculdade().getIdFaculdade());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faculdade faculdade = validacaopendente.getFaculdade();
            if (faculdade != null) {
                faculdade = em.getReference(faculdade.getClass(), faculdade.getIdFaculdade());
                validacaopendente.setFaculdade(faculdade);
            }
            em.persist(validacaopendente);
            if (faculdade != null) {
                faculdade.getValidacaopendenteList().add(validacaopendente);
                faculdade = em.merge(faculdade);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findValidacaopendente(validacaopendente.getValidacaopendentePK()) != null) {
                throw new PreexistingEntityException("Validacaopendente " + validacaopendente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Validacaopendente validacaopendente) throws NonexistentEntityException, Exception {
        validacaopendente.getValidacaopendentePK().setIdfaculdade(validacaopendente.getFaculdade().getIdFaculdade());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Validacaopendente persistentValidacaopendente = em.find(Validacaopendente.class, validacaopendente.getValidacaopendentePK());
            Faculdade faculdadeOld = persistentValidacaopendente.getFaculdade();
            Faculdade faculdadeNew = validacaopendente.getFaculdade();
            if (faculdadeNew != null) {
                faculdadeNew = em.getReference(faculdadeNew.getClass(), faculdadeNew.getIdFaculdade());
                validacaopendente.setFaculdade(faculdadeNew);
            }
            validacaopendente = em.merge(validacaopendente);
            if (faculdadeOld != null && !faculdadeOld.equals(faculdadeNew)) {
                faculdadeOld.getValidacaopendenteList().remove(validacaopendente);
                faculdadeOld = em.merge(faculdadeOld);
            }
            if (faculdadeNew != null && !faculdadeNew.equals(faculdadeOld)) {
                faculdadeNew.getValidacaopendenteList().add(validacaopendente);
                faculdadeNew = em.merge(faculdadeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ValidacaopendentePK id = validacaopendente.getValidacaopendentePK();
                if (findValidacaopendente(id) == null) {
                    throw new NonexistentEntityException("The validacaopendente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ValidacaopendentePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Validacaopendente validacaopendente;
            try {
                validacaopendente = em.getReference(Validacaopendente.class, id);
                validacaopendente.getValidacaopendentePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The validacaopendente with id " + id + " no longer exists.", enfe);
            }
            Faculdade faculdade = validacaopendente.getFaculdade();
            if (faculdade != null) {
                faculdade.getValidacaopendenteList().remove(validacaopendente);
                faculdade = em.merge(faculdade);
            }
            em.remove(validacaopendente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Validacaopendente> findValidacaopendenteEntities() {
        return findValidacaopendenteEntities(true, -1, -1);
    }

    public List<Validacaopendente> findValidacaopendenteEntities(int maxResults, int firstResult) {
        return findValidacaopendenteEntities(false, maxResults, firstResult);
    }

    private List<Validacaopendente> findValidacaopendenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Validacaopendente.class));
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

    public Validacaopendente findValidacaopendente(ValidacaopendentePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Validacaopendente.class, id);
        } finally {
            em.close();
        }
    }

    public int getValidacaopendenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Validacaopendente> rt = cq.from(Validacaopendente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
