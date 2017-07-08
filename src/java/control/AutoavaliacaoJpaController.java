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
import modelo.Pontuacaoconjugada;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Autoavaliacao;
import modelo.Questaoautoavaliacao;

/**
 *
 * @author Paulino Francisco
 */
public class AutoavaliacaoJpaController implements Serializable {

    public AutoavaliacaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Autoavaliacao autoavaliacao) {
        if (autoavaliacao.getPontuacaoconjugadaList() == null) {
            autoavaliacao.setPontuacaoconjugadaList(new ArrayList<Pontuacaoconjugada>());
        }
        if (autoavaliacao.getQuestaoautoavaliacaoList() == null) {
            autoavaliacao.setQuestaoautoavaliacaoList(new ArrayList<Questaoautoavaliacao>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pontuacaoconjugada> attachedPontuacaoconjugadaList = new ArrayList<Pontuacaoconjugada>();
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugadaToAttach : autoavaliacao.getPontuacaoconjugadaList()) {
                pontuacaoconjugadaListPontuacaoconjugadaToAttach = em.getReference(pontuacaoconjugadaListPontuacaoconjugadaToAttach.getClass(), pontuacaoconjugadaListPontuacaoconjugadaToAttach.getIdpontuacaoconjugada());
                attachedPontuacaoconjugadaList.add(pontuacaoconjugadaListPontuacaoconjugadaToAttach);
            }
            autoavaliacao.setPontuacaoconjugadaList(attachedPontuacaoconjugadaList);
            List<Questaoautoavaliacao> attachedQuestaoautoavaliacaoList = new ArrayList<Questaoautoavaliacao>();
            for (Questaoautoavaliacao questaoautoavaliacaoListQuestaoautoavaliacaoToAttach : autoavaliacao.getQuestaoautoavaliacaoList()) {
                questaoautoavaliacaoListQuestaoautoavaliacaoToAttach = em.getReference(questaoautoavaliacaoListQuestaoautoavaliacaoToAttach.getClass(), questaoautoavaliacaoListQuestaoautoavaliacaoToAttach.getIdquestaoautoavaliacao());
                attachedQuestaoautoavaliacaoList.add(questaoautoavaliacaoListQuestaoautoavaliacaoToAttach);
            }
            autoavaliacao.setQuestaoautoavaliacaoList(attachedQuestaoautoavaliacaoList);
            em.persist(autoavaliacao);
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugada : autoavaliacao.getPontuacaoconjugadaList()) {
                pontuacaoconjugadaListPontuacaoconjugada.getAutoavaliacaoList().add(autoavaliacao);
                pontuacaoconjugadaListPontuacaoconjugada = em.merge(pontuacaoconjugadaListPontuacaoconjugada);
            }
            for (Questaoautoavaliacao questaoautoavaliacaoListQuestaoautoavaliacao : autoavaliacao.getQuestaoautoavaliacaoList()) {
                questaoautoavaliacaoListQuestaoautoavaliacao.getAutoavaliacaoList().add(autoavaliacao);
                questaoautoavaliacaoListQuestaoautoavaliacao = em.merge(questaoautoavaliacaoListQuestaoautoavaliacao);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Autoavaliacao autoavaliacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autoavaliacao persistentAutoavaliacao = em.find(Autoavaliacao.class, autoavaliacao.getIdautoavaliacao());
            List<Pontuacaoconjugada> pontuacaoconjugadaListOld = persistentAutoavaliacao.getPontuacaoconjugadaList();
            List<Pontuacaoconjugada> pontuacaoconjugadaListNew = autoavaliacao.getPontuacaoconjugadaList();
            List<Questaoautoavaliacao> questaoautoavaliacaoListOld = persistentAutoavaliacao.getQuestaoautoavaliacaoList();
            List<Questaoautoavaliacao> questaoautoavaliacaoListNew = autoavaliacao.getQuestaoautoavaliacaoList();
            List<Pontuacaoconjugada> attachedPontuacaoconjugadaListNew = new ArrayList<Pontuacaoconjugada>();
            for (Pontuacaoconjugada pontuacaoconjugadaListNewPontuacaoconjugadaToAttach : pontuacaoconjugadaListNew) {
                pontuacaoconjugadaListNewPontuacaoconjugadaToAttach = em.getReference(pontuacaoconjugadaListNewPontuacaoconjugadaToAttach.getClass(), pontuacaoconjugadaListNewPontuacaoconjugadaToAttach.getIdpontuacaoconjugada());
                attachedPontuacaoconjugadaListNew.add(pontuacaoconjugadaListNewPontuacaoconjugadaToAttach);
            }
            pontuacaoconjugadaListNew = attachedPontuacaoconjugadaListNew;
            autoavaliacao.setPontuacaoconjugadaList(pontuacaoconjugadaListNew);
            List<Questaoautoavaliacao> attachedQuestaoautoavaliacaoListNew = new ArrayList<Questaoautoavaliacao>();
            for (Questaoautoavaliacao questaoautoavaliacaoListNewQuestaoautoavaliacaoToAttach : questaoautoavaliacaoListNew) {
                questaoautoavaliacaoListNewQuestaoautoavaliacaoToAttach = em.getReference(questaoautoavaliacaoListNewQuestaoautoavaliacaoToAttach.getClass(), questaoautoavaliacaoListNewQuestaoautoavaliacaoToAttach.getIdquestaoautoavaliacao());
                attachedQuestaoautoavaliacaoListNew.add(questaoautoavaliacaoListNewQuestaoautoavaliacaoToAttach);
            }
            questaoautoavaliacaoListNew = attachedQuestaoautoavaliacaoListNew;
            autoavaliacao.setQuestaoautoavaliacaoList(questaoautoavaliacaoListNew);
            autoavaliacao = em.merge(autoavaliacao);
            for (Pontuacaoconjugada pontuacaoconjugadaListOldPontuacaoconjugada : pontuacaoconjugadaListOld) {
                if (!pontuacaoconjugadaListNew.contains(pontuacaoconjugadaListOldPontuacaoconjugada)) {
                    pontuacaoconjugadaListOldPontuacaoconjugada.getAutoavaliacaoList().remove(autoavaliacao);
                    pontuacaoconjugadaListOldPontuacaoconjugada = em.merge(pontuacaoconjugadaListOldPontuacaoconjugada);
                }
            }
            for (Pontuacaoconjugada pontuacaoconjugadaListNewPontuacaoconjugada : pontuacaoconjugadaListNew) {
                if (!pontuacaoconjugadaListOld.contains(pontuacaoconjugadaListNewPontuacaoconjugada)) {
                    pontuacaoconjugadaListNewPontuacaoconjugada.getAutoavaliacaoList().add(autoavaliacao);
                    pontuacaoconjugadaListNewPontuacaoconjugada = em.merge(pontuacaoconjugadaListNewPontuacaoconjugada);
                }
            }
            for (Questaoautoavaliacao questaoautoavaliacaoListOldQuestaoautoavaliacao : questaoautoavaliacaoListOld) {
                if (!questaoautoavaliacaoListNew.contains(questaoautoavaliacaoListOldQuestaoautoavaliacao)) {
                    questaoautoavaliacaoListOldQuestaoautoavaliacao.getAutoavaliacaoList().remove(autoavaliacao);
                    questaoautoavaliacaoListOldQuestaoautoavaliacao = em.merge(questaoautoavaliacaoListOldQuestaoautoavaliacao);
                }
            }
            for (Questaoautoavaliacao questaoautoavaliacaoListNewQuestaoautoavaliacao : questaoautoavaliacaoListNew) {
                if (!questaoautoavaliacaoListOld.contains(questaoautoavaliacaoListNewQuestaoautoavaliacao)) {
                    questaoautoavaliacaoListNewQuestaoautoavaliacao.getAutoavaliacaoList().add(autoavaliacao);
                    questaoautoavaliacaoListNewQuestaoautoavaliacao = em.merge(questaoautoavaliacaoListNewQuestaoautoavaliacao);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = autoavaliacao.getIdautoavaliacao();
                if (findAutoavaliacao(id) == null) {
                    throw new NonexistentEntityException("The autoavaliacao with id " + id + " no longer exists.");
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
            Autoavaliacao autoavaliacao;
            try {
                autoavaliacao = em.getReference(Autoavaliacao.class, id);
                autoavaliacao.getIdautoavaliacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autoavaliacao with id " + id + " no longer exists.", enfe);
            }
            List<Pontuacaoconjugada> pontuacaoconjugadaList = autoavaliacao.getPontuacaoconjugadaList();
            for (Pontuacaoconjugada pontuacaoconjugadaListPontuacaoconjugada : pontuacaoconjugadaList) {
                pontuacaoconjugadaListPontuacaoconjugada.getAutoavaliacaoList().remove(autoavaliacao);
                pontuacaoconjugadaListPontuacaoconjugada = em.merge(pontuacaoconjugadaListPontuacaoconjugada);
            }
            List<Questaoautoavaliacao> questaoautoavaliacaoList = autoavaliacao.getQuestaoautoavaliacaoList();
            for (Questaoautoavaliacao questaoautoavaliacaoListQuestaoautoavaliacao : questaoautoavaliacaoList) {
                questaoautoavaliacaoListQuestaoautoavaliacao.getAutoavaliacaoList().remove(autoavaliacao);
                questaoautoavaliacaoListQuestaoautoavaliacao = em.merge(questaoautoavaliacaoListQuestaoautoavaliacao);
            }
            em.remove(autoavaliacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Autoavaliacao> findAutoavaliacaoEntities() {
        return findAutoavaliacaoEntities(true, -1, -1);
    }

    public List<Autoavaliacao> findAutoavaliacaoEntities(int maxResults, int firstResult) {
        return findAutoavaliacaoEntities(false, maxResults, firstResult);
    }

    private List<Autoavaliacao> findAutoavaliacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Autoavaliacao.class));
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

    public Autoavaliacao findAutoavaliacao(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autoavaliacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutoavaliacaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Autoavaliacao> rt = cq.from(Autoavaliacao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
