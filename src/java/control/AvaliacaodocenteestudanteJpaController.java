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
import modelo.Questaodocenteestudante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Avaliacaodocenteestudante;
import modelo.Pontuacaoconjugada;

/**
 *
 * @author Paulino Francisco
 */
public class AvaliacaodocenteestudanteJpaController implements Serializable {

    public AvaliacaodocenteestudanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Avaliacaodocenteestudante avaliacaodocenteestudante) {
        if (avaliacaodocenteestudante.getQuestaodocenteestudanteList() == null) {
            avaliacaodocenteestudante.setQuestaodocenteestudanteList(new ArrayList<Questaodocenteestudante>());
        }
        if (avaliacaodocenteestudante.getPontuacaoconjugadaList() == null) {
            avaliacaodocenteestudante.setPontuacaoconjugadaList(new ArrayList<Pontuacaoconjugada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Questaodocenteestudante> attachedQuestaodocenteestudanteList = new ArrayList<Questaodocenteestudante>();
            for (Questaodocenteestudante questaodocenteestudanteListQuestaodocenteestudanteToAttach : avaliacaodocenteestudante.getQuestaodocenteestudanteList()) {
                questaodocenteestudanteListQuestaodocenteestudanteToAttach = em.getReference(questaodocenteestudanteListQuestaodocenteestudanteToAttach.getClass(), questaodocenteestudanteListQuestaodocenteestudanteToAttach.getIdquestaodocenteestudante());
                attachedQuestaodocenteestudanteList.add(questaodocenteestudanteListQuestaodocenteestudanteToAttach);
            }
            avaliacaodocenteestudante.setQuestaodocenteestudanteList(attachedQuestaodocenteestudanteList);
            List<Pontuacaoconjugada> attachedPontuacaoconjugadaList = new ArrayList<Pontuacaoconjugada>();
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugadaToAttach : avaliacaodocenteestudante.getPontuacaoconjugadaList()) {
                pontuacaoconjugadaListPontuacaoconjugadaToAttach = em.getReference(pontuacaoconjugadaListPontuacaoconjugadaToAttach.getClass(), pontuacaoconjugadaListPontuacaoconjugadaToAttach.getIdpontuacaoconjugada());
                attachedPontuacaoconjugadaList.add(pontuacaoconjugadaListPontuacaoconjugadaToAttach);
            }
            avaliacaodocenteestudante.setPontuacaoconjugadaList(attachedPontuacaoconjugadaList);
            em.persist(avaliacaodocenteestudante);
            for (Questaodocenteestudante questaodocenteestudanteListQuestaodocenteestudante : avaliacaodocenteestudante.getQuestaodocenteestudanteList()) {
                questaodocenteestudanteListQuestaodocenteestudante.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                questaodocenteestudanteListQuestaodocenteestudante = em.merge(questaodocenteestudanteListQuestaodocenteestudante);
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugada : avaliacaodocenteestudante.getPontuacaoconjugadaList()) {
                pontuacaoconjugadaListPontuacaoconjugada.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                pontuacaoconjugadaListPontuacaoconjugada = em.merge(pontuacaoconjugadaListPontuacaoconjugada);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Avaliacaodocenteestudante avaliacaodocenteestudante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avaliacaodocenteestudante persistentAvaliacaodocenteestudante = em.find(Avaliacaodocenteestudante.class, avaliacaodocenteestudante.getIdavaliacaodocenteestudante());
            List<Questaodocenteestudante> questaodocenteestudanteListOld = persistentAvaliacaodocenteestudante.getQuestaodocenteestudanteList();
            List<Questaodocenteestudante> questaodocenteestudanteListNew = avaliacaodocenteestudante.getQuestaodocenteestudanteList();
            List<Pontuacaoconjugada> pontuacaoconjugadaListOld = persistentAvaliacaodocenteestudante.getPontuacaoconjugadaList();
            List<Pontuacaoconjugada> pontuacaoconjugadaListNew = avaliacaodocenteestudante.getPontuacaoconjugadaList();
            List<Questaodocenteestudante> attachedQuestaodocenteestudanteListNew = new ArrayList<Questaodocenteestudante>();
            for (Questaodocenteestudante questaodocenteestudanteListNewQuestaodocenteestudanteToAttach : questaodocenteestudanteListNew) {
                questaodocenteestudanteListNewQuestaodocenteestudanteToAttach = em.getReference(questaodocenteestudanteListNewQuestaodocenteestudanteToAttach.getClass(), questaodocenteestudanteListNewQuestaodocenteestudanteToAttach.getIdquestaodocenteestudante());
                attachedQuestaodocenteestudanteListNew.add(questaodocenteestudanteListNewQuestaodocenteestudanteToAttach);
            }
            questaodocenteestudanteListNew = attachedQuestaodocenteestudanteListNew;
            avaliacaodocenteestudante.setQuestaodocenteestudanteList(questaodocenteestudanteListNew);
            List<Pontuacaoconjugada> attachedPontuacaoconjugadaListNew = new ArrayList<Pontuacaoconjugada>();
            for (Pontuacaoconjugada pontuacaoconjugadaListNewPontuacaoconjugadaToAttach : pontuacaoconjugadaListNew) {
                pontuacaoconjugadaListNewPontuacaoconjugadaToAttach = em.getReference(pontuacaoconjugadaListNewPontuacaoconjugadaToAttach.getClass(), pontuacaoconjugadaListNewPontuacaoconjugadaToAttach.getIdpontuacaoconjugada());
                attachedPontuacaoconjugadaListNew.add(pontuacaoconjugadaListNewPontuacaoconjugadaToAttach);
            }
            pontuacaoconjugadaListNew = attachedPontuacaoconjugadaListNew;
            avaliacaodocenteestudante.setPontuacaoconjugadaList(pontuacaoconjugadaListNew);
            avaliacaodocenteestudante = em.merge(avaliacaodocenteestudante);
            for (Questaodocenteestudante questaodocenteestudanteListOldQuestaodocenteestudante : questaodocenteestudanteListOld) {
                if (!questaodocenteestudanteListNew.contains(questaodocenteestudanteListOldQuestaodocenteestudante)) {
                    questaodocenteestudanteListOldQuestaodocenteestudante.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                    questaodocenteestudanteListOldQuestaodocenteestudante = em.merge(questaodocenteestudanteListOldQuestaodocenteestudante);
                }
            }
            for (Questaodocenteestudante questaodocenteestudanteListNewQuestaodocenteestudante : questaodocenteestudanteListNew) {
                if (!questaodocenteestudanteListOld.contains(questaodocenteestudanteListNewQuestaodocenteestudante)) {
                    questaodocenteestudanteListNewQuestaodocenteestudante.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                    questaodocenteestudanteListNewQuestaodocenteestudante = em.merge(questaodocenteestudanteListNewQuestaodocenteestudante);
                }
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListOldPontuacaoconjugada : pontuacaoconjugadaListOld) {
                if (!pontuacaoconjugadaListNew.contains(pontuacaoconjugadaListOldPontuacaoconjugada)) {
                    pontuacaoconjugadaListOldPontuacaoconjugada.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                    pontuacaoconjugadaListOldPontuacaoconjugada = em.merge(pontuacaoconjugadaListOldPontuacaoconjugada);
                }
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListNewPontuacaoconjugada : pontuacaoconjugadaListNew) {
                if (!pontuacaoconjugadaListOld.contains(pontuacaoconjugadaListNewPontuacaoconjugada)) {
                    pontuacaoconjugadaListNewPontuacaoconjugada.getAvaliacaodocenteestudanteList().add(avaliacaodocenteestudante);
                    pontuacaoconjugadaListNewPontuacaoconjugada = em.merge(pontuacaoconjugadaListNewPontuacaoconjugada);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = avaliacaodocenteestudante.getIdavaliacaodocenteestudante();
                if (findAvaliacaodocenteestudante(id) == null) {
                    throw new NonexistentEntityException("The avaliacaodocenteestudante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avaliacaodocenteestudante avaliacaodocenteestudante;
            try {
                avaliacaodocenteestudante = em.getReference(Avaliacaodocenteestudante.class, id);
                avaliacaodocenteestudante.getIdavaliacaodocenteestudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avaliacaodocenteestudante with id " + id + " no longer exists.", enfe);
            }
            List<Questaodocenteestudante> questaodocenteestudanteList = avaliacaodocenteestudante.getQuestaodocenteestudanteList();
            for (Questaodocenteestudante questaodocenteestudanteListQuestaodocenteestudante : questaodocenteestudanteList) {
                questaodocenteestudanteListQuestaodocenteestudante.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                questaodocenteestudanteListQuestaodocenteestudante = em.merge(questaodocenteestudanteListQuestaodocenteestudante);
            }
            List<Pontuacaoconjugada> pontuacaoconjugadaList = avaliacaodocenteestudante.getPontuacaoconjugadaList();
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugada : pontuacaoconjugadaList) {
                pontuacaoconjugadaListPontuacaoconjugada.getAvaliacaodocenteestudanteList().remove(avaliacaodocenteestudante);
                pontuacaoconjugadaListPontuacaoconjugada = em.merge(pontuacaoconjugadaListPontuacaoconjugada);
            }
            em.remove(avaliacaodocenteestudante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Avaliacaodocenteestudante> findAvaliacaodocenteestudanteEntities() {
        return findAvaliacaodocenteestudanteEntities(true, -1, -1);
    }

    public List<Avaliacaodocenteestudante> findAvaliacaodocenteestudanteEntities(int maxResults, int firstResult) {
        return findAvaliacaodocenteestudanteEntities(false, maxResults, firstResult);
    }

    private List<Avaliacaodocenteestudante> findAvaliacaodocenteestudanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Avaliacaodocenteestudante.class));
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

    public Avaliacaodocenteestudante findAvaliacaodocenteestudante(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Avaliacaodocenteestudante.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvaliacaodocenteestudanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Avaliacaodocenteestudante> rt = cq.from(Avaliacaodocenteestudante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
