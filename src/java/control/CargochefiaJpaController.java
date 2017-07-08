/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Tipochefia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Cargochefia;
import modelo.Docente;

/**
 *
 * @author Paulino Francisco
 */
public class CargochefiaJpaController implements Serializable {

    public CargochefiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cargochefia cargochefia) {
        if (cargochefia.getTipochefiaList() == null) {
            cargochefia.setTipochefiaList(new ArrayList<Tipochefia>());
        }
        if (cargochefia.getDocenteList() == null) {
            cargochefia.setDocenteList(new ArrayList<Docente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tipochefia> attachedTipochefiaList = new ArrayList<Tipochefia>();
            for (Tipochefia tipochefiaListTipochefiaToAttach : cargochefia.getTipochefiaList()) {
                tipochefiaListTipochefiaToAttach = em.getReference(tipochefiaListTipochefiaToAttach.getClass(), tipochefiaListTipochefiaToAttach.getIdfuncionario());
                attachedTipochefiaList.add(tipochefiaListTipochefiaToAttach);
            }
            cargochefia.setTipochefiaList(attachedTipochefiaList);
            List<Docente> attachedDocenteList = new ArrayList<Docente>();
            for (Docente docenteListDocenteToAttach : cargochefia.getDocenteList()) {
                docenteListDocenteToAttach = em.getReference(docenteListDocenteToAttach.getClass(), docenteListDocenteToAttach.getIddocente());
                attachedDocenteList.add(docenteListDocenteToAttach);
            }
            cargochefia.setDocenteList(attachedDocenteList);
            em.persist(cargochefia);
            for (Tipochefia tipochefiaListTipochefia : cargochefia.getTipochefiaList()) {
                Cargochefia oldCargochefiaOfTipochefiaListTipochefia = tipochefiaListTipochefia.getCargochefia();
                tipochefiaListTipochefia.setCargochefia(cargochefia);
                tipochefiaListTipochefia = em.merge(tipochefiaListTipochefia);
                if (oldCargochefiaOfTipochefiaListTipochefia != null) {
                    oldCargochefiaOfTipochefiaListTipochefia.getTipochefiaList().remove(tipochefiaListTipochefia);
                    oldCargochefiaOfTipochefiaListTipochefia = em.merge(oldCargochefiaOfTipochefiaListTipochefia);
                }
            }
            for (Docente docenteListDocente : cargochefia.getDocenteList()) {
                Cargochefia oldIdcargochefiaOfDocenteListDocente = docenteListDocente.getIdcargochefia();
                docenteListDocente.setIdcargochefia(cargochefia);
                docenteListDocente = em.merge(docenteListDocente);
                if (oldIdcargochefiaOfDocenteListDocente != null) {
                    oldIdcargochefiaOfDocenteListDocente.getDocenteList().remove(docenteListDocente);
                    oldIdcargochefiaOfDocenteListDocente = em.merge(oldIdcargochefiaOfDocenteListDocente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cargochefia cargochefia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargochefia persistentCargochefia = em.find(Cargochefia.class, cargochefia.getIdcargochefia());
            List<Tipochefia> tipochefiaListOld = persistentCargochefia.getTipochefiaList();
            List<Tipochefia> tipochefiaListNew = cargochefia.getTipochefiaList();
            List<Docente> docenteListOld = persistentCargochefia.getDocenteList();
            List<Docente> docenteListNew = cargochefia.getDocenteList();
            List<Tipochefia> attachedTipochefiaListNew = new ArrayList<Tipochefia>();
            for (Tipochefia tipochefiaListNewTipochefiaToAttach : tipochefiaListNew) {
                tipochefiaListNewTipochefiaToAttach = em.getReference(tipochefiaListNewTipochefiaToAttach.getClass(), tipochefiaListNewTipochefiaToAttach.getIdfuncionario());
                attachedTipochefiaListNew.add(tipochefiaListNewTipochefiaToAttach);
            }
            tipochefiaListNew = attachedTipochefiaListNew;
            cargochefia.setTipochefiaList(tipochefiaListNew);
            List<Docente> attachedDocenteListNew = new ArrayList<Docente>();
            for (Docente docenteListNewDocenteToAttach : docenteListNew) {
                docenteListNewDocenteToAttach = em.getReference(docenteListNewDocenteToAttach.getClass(), docenteListNewDocenteToAttach.getIddocente());
                attachedDocenteListNew.add(docenteListNewDocenteToAttach);
            }
            docenteListNew = attachedDocenteListNew;
            cargochefia.setDocenteList(docenteListNew);
            cargochefia = em.merge(cargochefia);
            for (Tipochefia tipochefiaListOldTipochefia : tipochefiaListOld) {
                if (!tipochefiaListNew.contains(tipochefiaListOldTipochefia)) {
                    tipochefiaListOldTipochefia.setCargochefia(null);
                    tipochefiaListOldTipochefia = em.merge(tipochefiaListOldTipochefia);
                }
            }
            for (Tipochefia tipochefiaListNewTipochefia : tipochefiaListNew) {
                if (!tipochefiaListOld.contains(tipochefiaListNewTipochefia)) {
                    Cargochefia oldCargochefiaOfTipochefiaListNewTipochefia = tipochefiaListNewTipochefia.getCargochefia();
                    tipochefiaListNewTipochefia.setCargochefia(cargochefia);
                    tipochefiaListNewTipochefia = em.merge(tipochefiaListNewTipochefia);
                    if (oldCargochefiaOfTipochefiaListNewTipochefia != null && !oldCargochefiaOfTipochefiaListNewTipochefia.equals(cargochefia)) {
                        oldCargochefiaOfTipochefiaListNewTipochefia.getTipochefiaList().remove(tipochefiaListNewTipochefia);
                        oldCargochefiaOfTipochefiaListNewTipochefia = em.merge(oldCargochefiaOfTipochefiaListNewTipochefia);
                    }
                }
            }
            for (Docente docenteListOldDocente : docenteListOld) {
                if (!docenteListNew.contains(docenteListOldDocente)) {
                    docenteListOldDocente.setIdcargochefia(null);
                    docenteListOldDocente = em.merge(docenteListOldDocente);
                }
            }
            for (Docente docenteListNewDocente : docenteListNew) {
                if (!docenteListOld.contains(docenteListNewDocente)) {
                    Cargochefia oldIdcargochefiaOfDocenteListNewDocente = docenteListNewDocente.getIdcargochefia();
                    docenteListNewDocente.setIdcargochefia(cargochefia);
                    docenteListNewDocente = em.merge(docenteListNewDocente);
                    if (oldIdcargochefiaOfDocenteListNewDocente != null && !oldIdcargochefiaOfDocenteListNewDocente.equals(cargochefia)) {
                        oldIdcargochefiaOfDocenteListNewDocente.getDocenteList().remove(docenteListNewDocente);
                        oldIdcargochefiaOfDocenteListNewDocente = em.merge(oldIdcargochefiaOfDocenteListNewDocente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargochefia.getIdcargochefia();
                if (findCargochefia(id) == null) {
                    throw new NonexistentEntityException("The cargochefia with id " + id + " no longer exists.");
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
            Cargochefia cargochefia;
            try {
                cargochefia = em.getReference(Cargochefia.class, id);
                cargochefia.getIdcargochefia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargochefia with id " + id + " no longer exists.", enfe);
            }
            List<Tipochefia> tipochefiaList = cargochefia.getTipochefiaList();
            for (Tipochefia tipochefiaListTipochefia : tipochefiaList) {
                tipochefiaListTipochefia.setCargochefia(null);
                tipochefiaListTipochefia = em.merge(tipochefiaListTipochefia);
            }
            List<Docente> docenteList = cargochefia.getDocenteList();
            for (Docente docenteListDocente : docenteList) {
                docenteListDocente.setIdcargochefia(null);
                docenteListDocente = em.merge(docenteListDocente);
            }
            em.remove(cargochefia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cargochefia> findCargochefiaEntities() {
        return findCargochefiaEntities(true, -1, -1);
    }

    public List<Cargochefia> findCargochefiaEntities(int maxResults, int firstResult) {
        return findCargochefiaEntities(false, maxResults, firstResult);
    }

    private List<Cargochefia> findCargochefiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargochefia.class));
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

    public Cargochefia findCargochefia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargochefia.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargochefiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargochefia> rt = cq.from(Cargochefia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
