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
import modelo.Estudante;
import modelo.Notapauta;
import modelo.NotapautaPK;
import modelo.Pauta;

/**
 *
 * @author Paulino Francisco
 */
public class NotapautaJpaController implements Serializable {

    public NotapautaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notapauta notapauta) throws PreexistingEntityException, Exception {
        if (notapauta.getNotapautaPK() == null) {
            notapauta.setNotapautaPK(new NotapautaPK());
        }
        notapauta.getNotapautaPK().setDatap(notapauta.getPauta().getPautaPK().getDatap());
        notapauta.getNotapautaPK().setAno(notapauta.getPauta().getPautaPK().getAno());
        notapauta.getNotapautaPK().setSemestre(notapauta.getPauta().getPautaPK().getSemestre());
        notapauta.getNotapautaPK().setIddisc(notapauta.getPauta().getPautaPK().getIddisc());
        notapauta.getNotapautaPK().setIdestudante(notapauta.getEstudante().getIdEstudante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudante estudante = notapauta.getEstudante();
            if (estudante != null) {
                estudante = em.getReference(estudante.getClass(), estudante.getIdEstudante());
                notapauta.setEstudante(estudante);
            }
            Pauta pauta = notapauta.getPauta();
            if (pauta != null) {
                pauta = em.getReference(pauta.getClass(), pauta.getPautaPK());
                notapauta.setPauta(pauta);
            }
            em.persist(notapauta);
            if (estudante != null) {
                estudante.getNotapautaList().add(notapauta);
                estudante = em.merge(estudante);
            }
            if (pauta != null) {
                pauta.getNotapautaList().add(notapauta);
                pauta = em.merge(pauta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNotapauta(notapauta.getNotapautaPK()) != null) {
                throw new PreexistingEntityException("Notapauta " + notapauta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notapauta notapauta) throws NonexistentEntityException, Exception {
        notapauta.getNotapautaPK().setDatap(notapauta.getPauta().getPautaPK().getDatap());
        notapauta.getNotapautaPK().setAno(notapauta.getPauta().getPautaPK().getAno());
        notapauta.getNotapautaPK().setSemestre(notapauta.getPauta().getPautaPK().getSemestre());
        notapauta.getNotapautaPK().setIddisc(notapauta.getPauta().getPautaPK().getIddisc());
        notapauta.getNotapautaPK().setIdestudante(notapauta.getEstudante().getIdEstudante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notapauta persistentNotapauta = em.find(Notapauta.class, notapauta.getNotapautaPK());
            Estudante estudanteOld = persistentNotapauta.getEstudante();
            Estudante estudanteNew = notapauta.getEstudante();
            Pauta pautaOld = persistentNotapauta.getPauta();
            Pauta pautaNew = notapauta.getPauta();
            if (estudanteNew != null) {
                estudanteNew = em.getReference(estudanteNew.getClass(), estudanteNew.getIdEstudante());
                notapauta.setEstudante(estudanteNew);
            }
            if (pautaNew != null) {
                pautaNew = em.getReference(pautaNew.getClass(), pautaNew.getPautaPK());
                notapauta.setPauta(pautaNew);
            }
            notapauta = em.merge(notapauta);
            if (estudanteOld != null && !estudanteOld.equals(estudanteNew)) {
                estudanteOld.getNotapautaList().remove(notapauta);
                estudanteOld = em.merge(estudanteOld);
            }
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                estudanteNew.getNotapautaList().add(notapauta);
                estudanteNew = em.merge(estudanteNew);
            }
            if (pautaOld != null && !pautaOld.equals(pautaNew)) {
                pautaOld.getNotapautaList().remove(notapauta);
                pautaOld = em.merge(pautaOld);
            }
            if (pautaNew != null && !pautaNew.equals(pautaOld)) {
                pautaNew.getNotapautaList().add(notapauta);
                pautaNew = em.merge(pautaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NotapautaPK id = notapauta.getNotapautaPK();
                if (findNotapauta(id) == null) {
                    throw new NonexistentEntityException("The notapauta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NotapautaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notapauta notapauta;
            try {
                notapauta = em.getReference(Notapauta.class, id);
                notapauta.getNotapautaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notapauta with id " + id + " no longer exists.", enfe);
            }
            Estudante estudante = notapauta.getEstudante();
            if (estudante != null) {
                estudante.getNotapautaList().remove(notapauta);
                estudante = em.merge(estudante);
            }
            Pauta pauta = notapauta.getPauta();
            if (pauta != null) {
                pauta.getNotapautaList().remove(notapauta);
                pauta = em.merge(pauta);
            }
            em.remove(notapauta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notapauta> findNotapautaEntities() {
        return findNotapautaEntities(true, -1, -1);
    }

    public List<Notapauta> findNotapautaEntities(int maxResults, int firstResult) {
        return findNotapautaEntities(false, maxResults, firstResult);
    }

    private List<Notapauta> findNotapautaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notapauta.class));
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

    public Notapauta findNotapauta(NotapautaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notapauta.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotapautaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notapauta> rt = cq.from(Notapauta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
