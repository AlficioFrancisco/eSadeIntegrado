/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Estudante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Ingressomudancauniversidade;
import modelo.Pais;

/**
 *
 * @author Paulino Francisco
 */
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) {
        if (pais.getEstudanteList() == null) {
            pais.setEstudanteList(new ArrayList<Estudante>());
        }
        if (pais.getEstudanteList1() == null) {
            pais.setEstudanteList1(new ArrayList<Estudante>());
        }
        if (pais.getIngressomudancauniversidadeList() == null) {
            pais.setIngressomudancauniversidadeList(new ArrayList<Ingressomudancauniversidade>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estudante> attachedEstudanteList = new ArrayList<Estudante>();
            for (Estudante estudanteListEstudanteToAttach : pais.getEstudanteList()) {
                estudanteListEstudanteToAttach = em.getReference(estudanteListEstudanteToAttach.getClass(), estudanteListEstudanteToAttach.getIdEstudante());
                attachedEstudanteList.add(estudanteListEstudanteToAttach);
            }
            pais.setEstudanteList(attachedEstudanteList);
            List<Estudante> attachedEstudanteList1 = new ArrayList<Estudante>();
            for (Estudante estudanteList1EstudanteToAttach : pais.getEstudanteList1()) {
                estudanteList1EstudanteToAttach = em.getReference(estudanteList1EstudanteToAttach.getClass(), estudanteList1EstudanteToAttach.getIdEstudante());
                attachedEstudanteList1.add(estudanteList1EstudanteToAttach);
            }
            pais.setEstudanteList1(attachedEstudanteList1);
            List<Ingressomudancauniversidade> attachedIngressomudancauniversidadeList = new ArrayList<Ingressomudancauniversidade>();
            for (Ingressomudancauniversidade ingressomudancauniversidadeListIngressomudancauniversidadeToAttach : pais.getIngressomudancauniversidadeList()) {
                ingressomudancauniversidadeListIngressomudancauniversidadeToAttach = em.getReference(ingressomudancauniversidadeListIngressomudancauniversidadeToAttach.getClass(), ingressomudancauniversidadeListIngressomudancauniversidadeToAttach.getIdEstudante());
                attachedIngressomudancauniversidadeList.add(ingressomudancauniversidadeListIngressomudancauniversidadeToAttach);
            }
            pais.setIngressomudancauniversidadeList(attachedIngressomudancauniversidadeList);
            em.persist(pais);
            for (Estudante estudanteListEstudante : pais.getEstudanteList()) {
                Pais oldNacionalidadeOfEstudanteListEstudante = estudanteListEstudante.getNacionalidade();
                estudanteListEstudante.setNacionalidade(pais);
                estudanteListEstudante = em.merge(estudanteListEstudante);
                if (oldNacionalidadeOfEstudanteListEstudante != null) {
                    oldNacionalidadeOfEstudanteListEstudante.getEstudanteList().remove(estudanteListEstudante);
                    oldNacionalidadeOfEstudanteListEstudante = em.merge(oldNacionalidadeOfEstudanteListEstudante);
                }
            }
            for (Estudante estudanteList1Estudante : pais.getEstudanteList1()) {
                Pais oldEscolaPaisOfEstudanteList1Estudante = estudanteList1Estudante.getEscolaPais();
                estudanteList1Estudante.setEscolaPais(pais);
                estudanteList1Estudante = em.merge(estudanteList1Estudante);
                if (oldEscolaPaisOfEstudanteList1Estudante != null) {
                    oldEscolaPaisOfEstudanteList1Estudante.getEstudanteList1().remove(estudanteList1Estudante);
                    oldEscolaPaisOfEstudanteList1Estudante = em.merge(oldEscolaPaisOfEstudanteList1Estudante);
                }
            }
            for (Ingressomudancauniversidade ingressomudancauniversidadeListIngressomudancauniversidade : pais.getIngressomudancauniversidadeList()) {
                Pais oldPaisUniversidadeOfIngressomudancauniversidadeListIngressomudancauniversidade = ingressomudancauniversidadeListIngressomudancauniversidade.getPaisUniversidade();
                ingressomudancauniversidadeListIngressomudancauniversidade.setPaisUniversidade(pais);
                ingressomudancauniversidadeListIngressomudancauniversidade = em.merge(ingressomudancauniversidadeListIngressomudancauniversidade);
                if (oldPaisUniversidadeOfIngressomudancauniversidadeListIngressomudancauniversidade != null) {
                    oldPaisUniversidadeOfIngressomudancauniversidadeListIngressomudancauniversidade.getIngressomudancauniversidadeList().remove(ingressomudancauniversidadeListIngressomudancauniversidade);
                    oldPaisUniversidadeOfIngressomudancauniversidadeListIngressomudancauniversidade = em.merge(oldPaisUniversidadeOfIngressomudancauniversidadeListIngressomudancauniversidade);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getIdPais());
            List<Estudante> estudanteListOld = persistentPais.getEstudanteList();
            List<Estudante> estudanteListNew = pais.getEstudanteList();
            List<Estudante> estudanteList1Old = persistentPais.getEstudanteList1();
            List<Estudante> estudanteList1New = pais.getEstudanteList1();
            List<Ingressomudancauniversidade> ingressomudancauniversidadeListOld = persistentPais.getIngressomudancauniversidadeList();
            List<Ingressomudancauniversidade> ingressomudancauniversidadeListNew = pais.getIngressomudancauniversidadeList();
            List<String> illegalOrphanMessages = null;
            for (Estudante estudanteListOldEstudante : estudanteListOld) {
                if (!estudanteListNew.contains(estudanteListOldEstudante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudante " + estudanteListOldEstudante + " since its nacionalidade field is not nullable.");
                }
            }
            for (Ingressomudancauniversidade ingressomudancauniversidadeListOldIngressomudancauniversidade : ingressomudancauniversidadeListOld) {
                if (!ingressomudancauniversidadeListNew.contains(ingressomudancauniversidadeListOldIngressomudancauniversidade)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ingressomudancauniversidade " + ingressomudancauniversidadeListOldIngressomudancauniversidade + " since its paisUniversidade field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Estudante> attachedEstudanteListNew = new ArrayList<Estudante>();
            for (Estudante estudanteListNewEstudanteToAttach : estudanteListNew) {
                estudanteListNewEstudanteToAttach = em.getReference(estudanteListNewEstudanteToAttach.getClass(), estudanteListNewEstudanteToAttach.getIdEstudante());
                attachedEstudanteListNew.add(estudanteListNewEstudanteToAttach);
            }
            estudanteListNew = attachedEstudanteListNew;
            pais.setEstudanteList(estudanteListNew);
            List<Estudante> attachedEstudanteList1New = new ArrayList<Estudante>();
            for (Estudante estudanteList1NewEstudanteToAttach : estudanteList1New) {
                estudanteList1NewEstudanteToAttach = em.getReference(estudanteList1NewEstudanteToAttach.getClass(), estudanteList1NewEstudanteToAttach.getIdEstudante());
                attachedEstudanteList1New.add(estudanteList1NewEstudanteToAttach);
            }
            estudanteList1New = attachedEstudanteList1New;
            pais.setEstudanteList1(estudanteList1New);
            List<Ingressomudancauniversidade> attachedIngressomudancauniversidadeListNew = new ArrayList<Ingressomudancauniversidade>();
            for (Ingressomudancauniversidade ingressomudancauniversidadeListNewIngressomudancauniversidadeToAttach : ingressomudancauniversidadeListNew) {
                ingressomudancauniversidadeListNewIngressomudancauniversidadeToAttach = em.getReference(ingressomudancauniversidadeListNewIngressomudancauniversidadeToAttach.getClass(), ingressomudancauniversidadeListNewIngressomudancauniversidadeToAttach.getIdEstudante());
                attachedIngressomudancauniversidadeListNew.add(ingressomudancauniversidadeListNewIngressomudancauniversidadeToAttach);
            }
            ingressomudancauniversidadeListNew = attachedIngressomudancauniversidadeListNew;
            pais.setIngressomudancauniversidadeList(ingressomudancauniversidadeListNew);
            pais = em.merge(pais);
            for (Estudante estudanteListNewEstudante : estudanteListNew) {
                if (!estudanteListOld.contains(estudanteListNewEstudante)) {
                    Pais oldNacionalidadeOfEstudanteListNewEstudante = estudanteListNewEstudante.getNacionalidade();
                    estudanteListNewEstudante.setNacionalidade(pais);
                    estudanteListNewEstudante = em.merge(estudanteListNewEstudante);
                    if (oldNacionalidadeOfEstudanteListNewEstudante != null && !oldNacionalidadeOfEstudanteListNewEstudante.equals(pais)) {
                        oldNacionalidadeOfEstudanteListNewEstudante.getEstudanteList().remove(estudanteListNewEstudante);
                        oldNacionalidadeOfEstudanteListNewEstudante = em.merge(oldNacionalidadeOfEstudanteListNewEstudante);
                    }
                }
            }
            for (Estudante estudanteList1OldEstudante : estudanteList1Old) {
                if (!estudanteList1New.contains(estudanteList1OldEstudante)) {
                    estudanteList1OldEstudante.setEscolaPais(null);
                    estudanteList1OldEstudante = em.merge(estudanteList1OldEstudante);
                }
            }
            for (Estudante estudanteList1NewEstudante : estudanteList1New) {
                if (!estudanteList1Old.contains(estudanteList1NewEstudante)) {
                    Pais oldEscolaPaisOfEstudanteList1NewEstudante = estudanteList1NewEstudante.getEscolaPais();
                    estudanteList1NewEstudante.setEscolaPais(pais);
                    estudanteList1NewEstudante = em.merge(estudanteList1NewEstudante);
                    if (oldEscolaPaisOfEstudanteList1NewEstudante != null && !oldEscolaPaisOfEstudanteList1NewEstudante.equals(pais)) {
                        oldEscolaPaisOfEstudanteList1NewEstudante.getEstudanteList1().remove(estudanteList1NewEstudante);
                        oldEscolaPaisOfEstudanteList1NewEstudante = em.merge(oldEscolaPaisOfEstudanteList1NewEstudante);
                    }
                }
            }
            for (Ingressomudancauniversidade ingressomudancauniversidadeListNewIngressomudancauniversidade : ingressomudancauniversidadeListNew) {
                if (!ingressomudancauniversidadeListOld.contains(ingressomudancauniversidadeListNewIngressomudancauniversidade)) {
                    Pais oldPaisUniversidadeOfIngressomudancauniversidadeListNewIngressomudancauniversidade = ingressomudancauniversidadeListNewIngressomudancauniversidade.getPaisUniversidade();
                    ingressomudancauniversidadeListNewIngressomudancauniversidade.setPaisUniversidade(pais);
                    ingressomudancauniversidadeListNewIngressomudancauniversidade = em.merge(ingressomudancauniversidadeListNewIngressomudancauniversidade);
                    if (oldPaisUniversidadeOfIngressomudancauniversidadeListNewIngressomudancauniversidade != null && !oldPaisUniversidadeOfIngressomudancauniversidadeListNewIngressomudancauniversidade.equals(pais)) {
                        oldPaisUniversidadeOfIngressomudancauniversidadeListNewIngressomudancauniversidade.getIngressomudancauniversidadeList().remove(ingressomudancauniversidadeListNewIngressomudancauniversidade);
                        oldPaisUniversidadeOfIngressomudancauniversidadeListNewIngressomudancauniversidade = em.merge(oldPaisUniversidadeOfIngressomudancauniversidadeListNewIngressomudancauniversidade);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pais.getIdPais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getIdPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Estudante> estudanteListOrphanCheck = pais.getEstudanteList();
            for (Estudante estudanteListOrphanCheckEstudante : estudanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Estudante " + estudanteListOrphanCheckEstudante + " in its estudanteList field has a non-nullable nacionalidade field.");
            }
            List<Ingressomudancauniversidade> ingressomudancauniversidadeListOrphanCheck = pais.getIngressomudancauniversidadeList();
            for (Ingressomudancauniversidade ingressomudancauniversidadeListOrphanCheckIngressomudancauniversidade : ingressomudancauniversidadeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Ingressomudancauniversidade " + ingressomudancauniversidadeListOrphanCheckIngressomudancauniversidade + " in its ingressomudancauniversidadeList field has a non-nullable paisUniversidade field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Estudante> estudanteList1 = pais.getEstudanteList1();
            for (Estudante estudanteList1Estudante : estudanteList1) {
                estudanteList1Estudante.setEscolaPais(null);
                estudanteList1Estudante = em.merge(estudanteList1Estudante);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
