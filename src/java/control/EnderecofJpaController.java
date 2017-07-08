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
import modelo.Enderecof;

/**
 *
 * @author Paulino Francisco
 */
public class EnderecofJpaController implements Serializable {

    public EnderecofJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Enderecof enderecof) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Estudante estudanteOrphanCheck = enderecof.getEstudante();
        if (estudanteOrphanCheck != null) {
            Enderecof oldEnderecofOfEstudante = estudanteOrphanCheck.getEnderecof();
            if (oldEnderecofOfEstudante != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Estudante " + estudanteOrphanCheck + " already has an item of type Enderecof whose estudante column cannot be null. Please make another selection for the estudante field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudante estudante = enderecof.getEstudante();
            if (estudante != null) {
                estudante = em.getReference(estudante.getClass(), estudante.getIdEstudante());
                enderecof.setEstudante(estudante);
            }
            Provincia provincia = enderecof.getProvincia();
            if (provincia != null) {
                provincia = em.getReference(provincia.getClass(), provincia.getIdProvincia());
                enderecof.setProvincia(provincia);
            }
            em.persist(enderecof);
            if (estudante != null) {
                estudante.setEnderecof(enderecof);
                estudante = em.merge(estudante);
            }
            if (provincia != null) {
                provincia.getEnderecofList().add(enderecof);
                provincia = em.merge(provincia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEnderecof(enderecof.getIdEstudante()) != null) {
                throw new PreexistingEntityException("Enderecof " + enderecof + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Enderecof enderecof) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enderecof persistentEnderecof = em.find(Enderecof.class, enderecof.getIdEstudante());
            Estudante estudanteOld = persistentEnderecof.getEstudante();
            Estudante estudanteNew = enderecof.getEstudante();
            Provincia provinciaOld = persistentEnderecof.getProvincia();
            Provincia provinciaNew = enderecof.getProvincia();
            List<String> illegalOrphanMessages = null;
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                Enderecof oldEnderecofOfEstudante = estudanteNew.getEnderecof();
                if (oldEnderecofOfEstudante != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Estudante " + estudanteNew + " already has an item of type Enderecof whose estudante column cannot be null. Please make another selection for the estudante field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estudanteNew != null) {
                estudanteNew = em.getReference(estudanteNew.getClass(), estudanteNew.getIdEstudante());
                enderecof.setEstudante(estudanteNew);
            }
            if (provinciaNew != null) {
                provinciaNew = em.getReference(provinciaNew.getClass(), provinciaNew.getIdProvincia());
                enderecof.setProvincia(provinciaNew);
            }
            enderecof = em.merge(enderecof);
            if (estudanteOld != null && !estudanteOld.equals(estudanteNew)) {
                estudanteOld.setEnderecof(null);
                estudanteOld = em.merge(estudanteOld);
            }
            if (estudanteNew != null && !estudanteNew.equals(estudanteOld)) {
                estudanteNew.setEnderecof(enderecof);
                estudanteNew = em.merge(estudanteNew);
            }
            if (provinciaOld != null && !provinciaOld.equals(provinciaNew)) {
                provinciaOld.getEnderecofList().remove(enderecof);
                provinciaOld = em.merge(provinciaOld);
            }
            if (provinciaNew != null && !provinciaNew.equals(provinciaOld)) {
                provinciaNew.getEnderecofList().add(enderecof);
                provinciaNew = em.merge(provinciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = enderecof.getIdEstudante();
                if (findEnderecof(id) == null) {
                    throw new NonexistentEntityException("The enderecof with id " + id + " no longer exists.");
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
            Enderecof enderecof;
            try {
                enderecof = em.getReference(Enderecof.class, id);
                enderecof.getIdEstudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enderecof with id " + id + " no longer exists.", enfe);
            }
            Estudante estudante = enderecof.getEstudante();
            if (estudante != null) {
                estudante.setEnderecof(null);
                estudante = em.merge(estudante);
            }
            Provincia provincia = enderecof.getProvincia();
            if (provincia != null) {
                provincia.getEnderecofList().remove(enderecof);
                provincia = em.merge(provincia);
            }
            em.remove(enderecof);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Enderecof> findEnderecofEntities() {
        return findEnderecofEntities(true, -1, -1);
    }

    public List<Enderecof> findEnderecofEntities(int maxResults, int firstResult) {
        return findEnderecofEntities(false, maxResults, firstResult);
    }

    private List<Enderecof> findEnderecofEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enderecof.class));
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

    public Enderecof findEnderecof(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Enderecof.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnderecofCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Enderecof> rt = cq.from(Enderecof.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
