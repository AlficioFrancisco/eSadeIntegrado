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
import modelo.Documento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Tipodocumento;

/**
 *
 * @author Paulino Francisco
 */
public class TipodocumentoJpaController implements Serializable {

    public TipodocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipodocumento tipodocumento) {
        if (tipodocumento.getDocumentoList() == null) {
            tipodocumento.setDocumentoList(new ArrayList<Documento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Documento> attachedDocumentoList = new ArrayList<Documento>();
            for (Documento documentoListDocumentoToAttach : tipodocumento.getDocumentoList()) {
                documentoListDocumentoToAttach = em.getReference(documentoListDocumentoToAttach.getClass(), documentoListDocumentoToAttach.getIdEstudante());
                attachedDocumentoList.add(documentoListDocumentoToAttach);
            }
            tipodocumento.setDocumentoList(attachedDocumentoList);
            em.persist(tipodocumento);
            for (Documento documentoListDocumento : tipodocumento.getDocumentoList()) {
                Tipodocumento oldTipoOfDocumentoListDocumento = documentoListDocumento.getTipo();
                documentoListDocumento.setTipo(tipodocumento);
                documentoListDocumento = em.merge(documentoListDocumento);
                if (oldTipoOfDocumentoListDocumento != null) {
                    oldTipoOfDocumentoListDocumento.getDocumentoList().remove(documentoListDocumento);
                    oldTipoOfDocumentoListDocumento = em.merge(oldTipoOfDocumentoListDocumento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipodocumento tipodocumento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipodocumento persistentTipodocumento = em.find(Tipodocumento.class, tipodocumento.getIdTipo());
            List<Documento> documentoListOld = persistentTipodocumento.getDocumentoList();
            List<Documento> documentoListNew = tipodocumento.getDocumentoList();
            List<Documento> attachedDocumentoListNew = new ArrayList<Documento>();
            for (Documento documentoListNewDocumentoToAttach : documentoListNew) {
                documentoListNewDocumentoToAttach = em.getReference(documentoListNewDocumentoToAttach.getClass(), documentoListNewDocumentoToAttach.getIdEstudante());
                attachedDocumentoListNew.add(documentoListNewDocumentoToAttach);
            }
            documentoListNew = attachedDocumentoListNew;
            tipodocumento.setDocumentoList(documentoListNew);
            tipodocumento = em.merge(tipodocumento);
            for (Documento documentoListOldDocumento : documentoListOld) {
                if (!documentoListNew.contains(documentoListOldDocumento)) {
                    documentoListOldDocumento.setTipo(null);
                    documentoListOldDocumento = em.merge(documentoListOldDocumento);
                }
            }
            for (Documento documentoListNewDocumento : documentoListNew) {
                if (!documentoListOld.contains(documentoListNewDocumento)) {
                    Tipodocumento oldTipoOfDocumentoListNewDocumento = documentoListNewDocumento.getTipo();
                    documentoListNewDocumento.setTipo(tipodocumento);
                    documentoListNewDocumento = em.merge(documentoListNewDocumento);
                    if (oldTipoOfDocumentoListNewDocumento != null && !oldTipoOfDocumentoListNewDocumento.equals(tipodocumento)) {
                        oldTipoOfDocumentoListNewDocumento.getDocumentoList().remove(documentoListNewDocumento);
                        oldTipoOfDocumentoListNewDocumento = em.merge(oldTipoOfDocumentoListNewDocumento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipodocumento.getIdTipo();
                if (findTipodocumento(id) == null) {
                    throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.");
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
            Tipodocumento tipodocumento;
            try {
                tipodocumento = em.getReference(Tipodocumento.class, id);
                tipodocumento.getIdTipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipodocumento with id " + id + " no longer exists.", enfe);
            }
            List<Documento> documentoList = tipodocumento.getDocumentoList();
            for (Documento documentoListDocumento : documentoList) {
                documentoListDocumento.setTipo(null);
                documentoListDocumento = em.merge(documentoListDocumento);
            }
            em.remove(tipodocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipodocumento> findTipodocumentoEntities() {
        return findTipodocumentoEntities(true, -1, -1);
    }

    public List<Tipodocumento> findTipodocumentoEntities(int maxResults, int firstResult) {
        return findTipodocumentoEntities(false, maxResults, firstResult);
    }

    private List<Tipodocumento> findTipodocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipodocumento.class));
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

    public Tipodocumento findTipodocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipodocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipodocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipodocumento> rt = cq.from(Tipodocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
