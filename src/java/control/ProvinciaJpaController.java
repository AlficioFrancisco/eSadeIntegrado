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
import modelo.Profissao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Ingressoexameadmissao;
import modelo.Enderecof;
import modelo.Endereco;
import modelo.Provincia;

/**
 *
 * @author Paulino Francisco
 */
public class ProvinciaJpaController implements Serializable {

    public ProvinciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Provincia provincia) {
        if (provincia.getProfissaoList() == null) {
            provincia.setProfissaoList(new ArrayList<Profissao>());
        }
        if (provincia.getIngressoexameadmissaoList() == null) {
            provincia.setIngressoexameadmissaoList(new ArrayList<Ingressoexameadmissao>());
        }
        if (provincia.getEnderecofList() == null) {
            provincia.setEnderecofList(new ArrayList<Enderecof>());
        }
        if (provincia.getEnderecoList() == null) {
            provincia.setEnderecoList(new ArrayList<Endereco>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Profissao> attachedProfissaoList = new ArrayList<Profissao>();
            for (Profissao profissaoListProfissaoToAttach : provincia.getProfissaoList()) {
                profissaoListProfissaoToAttach = em.getReference(profissaoListProfissaoToAttach.getClass(), profissaoListProfissaoToAttach.getIdEstudante());
                attachedProfissaoList.add(profissaoListProfissaoToAttach);
            }
            provincia.setProfissaoList(attachedProfissaoList);
            List<Ingressoexameadmissao> attachedIngressoexameadmissaoList = new ArrayList<Ingressoexameadmissao>();
            for (Ingressoexameadmissao ingressoexameadmissaoListIngressoexameadmissaoToAttach : provincia.getIngressoexameadmissaoList()) {
                ingressoexameadmissaoListIngressoexameadmissaoToAttach = em.getReference(ingressoexameadmissaoListIngressoexameadmissaoToAttach.getClass(), ingressoexameadmissaoListIngressoexameadmissaoToAttach.getIdEstudante());
                attachedIngressoexameadmissaoList.add(ingressoexameadmissaoListIngressoexameadmissaoToAttach);
            }
            provincia.setIngressoexameadmissaoList(attachedIngressoexameadmissaoList);
            List<Enderecof> attachedEnderecofList = new ArrayList<Enderecof>();
            for (Enderecof enderecofListEnderecofToAttach : provincia.getEnderecofList()) {
                enderecofListEnderecofToAttach = em.getReference(enderecofListEnderecofToAttach.getClass(), enderecofListEnderecofToAttach.getIdEstudante());
                attachedEnderecofList.add(enderecofListEnderecofToAttach);
            }
            provincia.setEnderecofList(attachedEnderecofList);
            List<Endereco> attachedEnderecoList = new ArrayList<Endereco>();
            for (Endereco enderecoListEnderecoToAttach : provincia.getEnderecoList()) {
                enderecoListEnderecoToAttach = em.getReference(enderecoListEnderecoToAttach.getClass(), enderecoListEnderecoToAttach.getIdEstudante());
                attachedEnderecoList.add(enderecoListEnderecoToAttach);
            }
            provincia.setEnderecoList(attachedEnderecoList);
            em.persist(provincia);
            for (Profissao profissaoListProfissao : provincia.getProfissaoList()) {
                Provincia oldProvinciaprOfProfissaoListProfissao = profissaoListProfissao.getProvinciapr();
                profissaoListProfissao.setProvinciapr(provincia);
                profissaoListProfissao = em.merge(profissaoListProfissao);
                if (oldProvinciaprOfProfissaoListProfissao != null) {
                    oldProvinciaprOfProfissaoListProfissao.getProfissaoList().remove(profissaoListProfissao);
                    oldProvinciaprOfProfissaoListProfissao = em.merge(oldProvinciaprOfProfissaoListProfissao);
                }
            }
            for (Ingressoexameadmissao ingressoexameadmissaoListIngressoexameadmissao : provincia.getIngressoexameadmissaoList()) {
                Provincia oldProvinciaAdmissaoOfIngressoexameadmissaoListIngressoexameadmissao = ingressoexameadmissaoListIngressoexameadmissao.getProvinciaAdmissao();
                ingressoexameadmissaoListIngressoexameadmissao.setProvinciaAdmissao(provincia);
                ingressoexameadmissaoListIngressoexameadmissao = em.merge(ingressoexameadmissaoListIngressoexameadmissao);
                if (oldProvinciaAdmissaoOfIngressoexameadmissaoListIngressoexameadmissao != null) {
                    oldProvinciaAdmissaoOfIngressoexameadmissaoListIngressoexameadmissao.getIngressoexameadmissaoList().remove(ingressoexameadmissaoListIngressoexameadmissao);
                    oldProvinciaAdmissaoOfIngressoexameadmissaoListIngressoexameadmissao = em.merge(oldProvinciaAdmissaoOfIngressoexameadmissaoListIngressoexameadmissao);
                }
            }
            for (Enderecof enderecofListEnderecof : provincia.getEnderecofList()) {
                Provincia oldProvinciaOfEnderecofListEnderecof = enderecofListEnderecof.getProvincia();
                enderecofListEnderecof.setProvincia(provincia);
                enderecofListEnderecof = em.merge(enderecofListEnderecof);
                if (oldProvinciaOfEnderecofListEnderecof != null) {
                    oldProvinciaOfEnderecofListEnderecof.getEnderecofList().remove(enderecofListEnderecof);
                    oldProvinciaOfEnderecofListEnderecof = em.merge(oldProvinciaOfEnderecofListEnderecof);
                }
            }
            for (Endereco enderecoListEndereco : provincia.getEnderecoList()) {
                Provincia oldProvinciaOfEnderecoListEndereco = enderecoListEndereco.getProvincia();
                enderecoListEndereco.setProvincia(provincia);
                enderecoListEndereco = em.merge(enderecoListEndereco);
                if (oldProvinciaOfEnderecoListEndereco != null) {
                    oldProvinciaOfEnderecoListEndereco.getEnderecoList().remove(enderecoListEndereco);
                    oldProvinciaOfEnderecoListEndereco = em.merge(oldProvinciaOfEnderecoListEndereco);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Provincia provincia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Provincia persistentProvincia = em.find(Provincia.class, provincia.getIdProvincia());
            List<Profissao> profissaoListOld = persistentProvincia.getProfissaoList();
            List<Profissao> profissaoListNew = provincia.getProfissaoList();
            List<Ingressoexameadmissao> ingressoexameadmissaoListOld = persistentProvincia.getIngressoexameadmissaoList();
            List<Ingressoexameadmissao> ingressoexameadmissaoListNew = provincia.getIngressoexameadmissaoList();
            List<Enderecof> enderecofListOld = persistentProvincia.getEnderecofList();
            List<Enderecof> enderecofListNew = provincia.getEnderecofList();
            List<Endereco> enderecoListOld = persistentProvincia.getEnderecoList();
            List<Endereco> enderecoListNew = provincia.getEnderecoList();
            List<Profissao> attachedProfissaoListNew = new ArrayList<Profissao>();
            for (Profissao profissaoListNewProfissaoToAttach : profissaoListNew) {
                profissaoListNewProfissaoToAttach = em.getReference(profissaoListNewProfissaoToAttach.getClass(), profissaoListNewProfissaoToAttach.getIdEstudante());
                attachedProfissaoListNew.add(profissaoListNewProfissaoToAttach);
            }
            profissaoListNew = attachedProfissaoListNew;
            provincia.setProfissaoList(profissaoListNew);
            List<Ingressoexameadmissao> attachedIngressoexameadmissaoListNew = new ArrayList<Ingressoexameadmissao>();
            for (Ingressoexameadmissao ingressoexameadmissaoListNewIngressoexameadmissaoToAttach : ingressoexameadmissaoListNew) {
                ingressoexameadmissaoListNewIngressoexameadmissaoToAttach = em.getReference(ingressoexameadmissaoListNewIngressoexameadmissaoToAttach.getClass(), ingressoexameadmissaoListNewIngressoexameadmissaoToAttach.getIdEstudante());
                attachedIngressoexameadmissaoListNew.add(ingressoexameadmissaoListNewIngressoexameadmissaoToAttach);
            }
            ingressoexameadmissaoListNew = attachedIngressoexameadmissaoListNew;
            provincia.setIngressoexameadmissaoList(ingressoexameadmissaoListNew);
            List<Enderecof> attachedEnderecofListNew = new ArrayList<Enderecof>();
            for (Enderecof enderecofListNewEnderecofToAttach : enderecofListNew) {
                enderecofListNewEnderecofToAttach = em.getReference(enderecofListNewEnderecofToAttach.getClass(), enderecofListNewEnderecofToAttach.getIdEstudante());
                attachedEnderecofListNew.add(enderecofListNewEnderecofToAttach);
            }
            enderecofListNew = attachedEnderecofListNew;
            provincia.setEnderecofList(enderecofListNew);
            List<Endereco> attachedEnderecoListNew = new ArrayList<Endereco>();
            for (Endereco enderecoListNewEnderecoToAttach : enderecoListNew) {
                enderecoListNewEnderecoToAttach = em.getReference(enderecoListNewEnderecoToAttach.getClass(), enderecoListNewEnderecoToAttach.getIdEstudante());
                attachedEnderecoListNew.add(enderecoListNewEnderecoToAttach);
            }
            enderecoListNew = attachedEnderecoListNew;
            provincia.setEnderecoList(enderecoListNew);
            provincia = em.merge(provincia);
            for (Profissao profissaoListOldProfissao : profissaoListOld) {
                if (!profissaoListNew.contains(profissaoListOldProfissao)) {
                    profissaoListOldProfissao.setProvinciapr(null);
                    profissaoListOldProfissao = em.merge(profissaoListOldProfissao);
                }
            }
            for (Profissao profissaoListNewProfissao : profissaoListNew) {
                if (!profissaoListOld.contains(profissaoListNewProfissao)) {
                    Provincia oldProvinciaprOfProfissaoListNewProfissao = profissaoListNewProfissao.getProvinciapr();
                    profissaoListNewProfissao.setProvinciapr(provincia);
                    profissaoListNewProfissao = em.merge(profissaoListNewProfissao);
                    if (oldProvinciaprOfProfissaoListNewProfissao != null && !oldProvinciaprOfProfissaoListNewProfissao.equals(provincia)) {
                        oldProvinciaprOfProfissaoListNewProfissao.getProfissaoList().remove(profissaoListNewProfissao);
                        oldProvinciaprOfProfissaoListNewProfissao = em.merge(oldProvinciaprOfProfissaoListNewProfissao);
                    }
                }
            }
            for (Ingressoexameadmissao ingressoexameadmissaoListOldIngressoexameadmissao : ingressoexameadmissaoListOld) {
                if (!ingressoexameadmissaoListNew.contains(ingressoexameadmissaoListOldIngressoexameadmissao)) {
                    ingressoexameadmissaoListOldIngressoexameadmissao.setProvinciaAdmissao(null);
                    ingressoexameadmissaoListOldIngressoexameadmissao = em.merge(ingressoexameadmissaoListOldIngressoexameadmissao);
                }
            }
            for (Ingressoexameadmissao ingressoexameadmissaoListNewIngressoexameadmissao : ingressoexameadmissaoListNew) {
                if (!ingressoexameadmissaoListOld.contains(ingressoexameadmissaoListNewIngressoexameadmissao)) {
                    Provincia oldProvinciaAdmissaoOfIngressoexameadmissaoListNewIngressoexameadmissao = ingressoexameadmissaoListNewIngressoexameadmissao.getProvinciaAdmissao();
                    ingressoexameadmissaoListNewIngressoexameadmissao.setProvinciaAdmissao(provincia);
                    ingressoexameadmissaoListNewIngressoexameadmissao = em.merge(ingressoexameadmissaoListNewIngressoexameadmissao);
                    if (oldProvinciaAdmissaoOfIngressoexameadmissaoListNewIngressoexameadmissao != null && !oldProvinciaAdmissaoOfIngressoexameadmissaoListNewIngressoexameadmissao.equals(provincia)) {
                        oldProvinciaAdmissaoOfIngressoexameadmissaoListNewIngressoexameadmissao.getIngressoexameadmissaoList().remove(ingressoexameadmissaoListNewIngressoexameadmissao);
                        oldProvinciaAdmissaoOfIngressoexameadmissaoListNewIngressoexameadmissao = em.merge(oldProvinciaAdmissaoOfIngressoexameadmissaoListNewIngressoexameadmissao);
                    }
                }
            }
            for (Enderecof enderecofListOldEnderecof : enderecofListOld) {
                if (!enderecofListNew.contains(enderecofListOldEnderecof)) {
                    enderecofListOldEnderecof.setProvincia(null);
                    enderecofListOldEnderecof = em.merge(enderecofListOldEnderecof);
                }
            }
            for (Enderecof enderecofListNewEnderecof : enderecofListNew) {
                if (!enderecofListOld.contains(enderecofListNewEnderecof)) {
                    Provincia oldProvinciaOfEnderecofListNewEnderecof = enderecofListNewEnderecof.getProvincia();
                    enderecofListNewEnderecof.setProvincia(provincia);
                    enderecofListNewEnderecof = em.merge(enderecofListNewEnderecof);
                    if (oldProvinciaOfEnderecofListNewEnderecof != null && !oldProvinciaOfEnderecofListNewEnderecof.equals(provincia)) {
                        oldProvinciaOfEnderecofListNewEnderecof.getEnderecofList().remove(enderecofListNewEnderecof);
                        oldProvinciaOfEnderecofListNewEnderecof = em.merge(oldProvinciaOfEnderecofListNewEnderecof);
                    }
                }
            }
            for (Endereco enderecoListOldEndereco : enderecoListOld) {
                if (!enderecoListNew.contains(enderecoListOldEndereco)) {
                    enderecoListOldEndereco.setProvincia(null);
                    enderecoListOldEndereco = em.merge(enderecoListOldEndereco);
                }
            }
            for (Endereco enderecoListNewEndereco : enderecoListNew) {
                if (!enderecoListOld.contains(enderecoListNewEndereco)) {
                    Provincia oldProvinciaOfEnderecoListNewEndereco = enderecoListNewEndereco.getProvincia();
                    enderecoListNewEndereco.setProvincia(provincia);
                    enderecoListNewEndereco = em.merge(enderecoListNewEndereco);
                    if (oldProvinciaOfEnderecoListNewEndereco != null && !oldProvinciaOfEnderecoListNewEndereco.equals(provincia)) {
                        oldProvinciaOfEnderecoListNewEndereco.getEnderecoList().remove(enderecoListNewEndereco);
                        oldProvinciaOfEnderecoListNewEndereco = em.merge(oldProvinciaOfEnderecoListNewEndereco);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = provincia.getIdProvincia();
                if (findProvincia(id) == null) {
                    throw new NonexistentEntityException("The provincia with id " + id + " no longer exists.");
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
            Provincia provincia;
            try {
                provincia = em.getReference(Provincia.class, id);
                provincia.getIdProvincia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The provincia with id " + id + " no longer exists.", enfe);
            }
            List<Profissao> profissaoList = provincia.getProfissaoList();
            for (Profissao profissaoListProfissao : profissaoList) {
                profissaoListProfissao.setProvinciapr(null);
                profissaoListProfissao = em.merge(profissaoListProfissao);
            }
            List<Ingressoexameadmissao> ingressoexameadmissaoList = provincia.getIngressoexameadmissaoList();
            for (Ingressoexameadmissao ingressoexameadmissaoListIngressoexameadmissao : ingressoexameadmissaoList) {
                ingressoexameadmissaoListIngressoexameadmissao.setProvinciaAdmissao(null);
                ingressoexameadmissaoListIngressoexameadmissao = em.merge(ingressoexameadmissaoListIngressoexameadmissao);
            }
            List<Enderecof> enderecofList = provincia.getEnderecofList();
            for (Enderecof enderecofListEnderecof : enderecofList) {
                enderecofListEnderecof.setProvincia(null);
                enderecofListEnderecof = em.merge(enderecofListEnderecof);
            }
            List<Endereco> enderecoList = provincia.getEnderecoList();
            for (Endereco enderecoListEndereco : enderecoList) {
                enderecoListEndereco.setProvincia(null);
                enderecoListEndereco = em.merge(enderecoListEndereco);
            }
            em.remove(provincia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Provincia> findProvinciaEntities() {
        return findProvinciaEntities(true, -1, -1);
    }

    public List<Provincia> findProvinciaEntities(int maxResults, int firstResult) {
        return findProvinciaEntities(false, maxResults, firstResult);
    }

    private List<Provincia> findProvinciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Provincia.class));
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

    public Provincia findProvincia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Provincia.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvinciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Provincia> rt = cq.from(Provincia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
