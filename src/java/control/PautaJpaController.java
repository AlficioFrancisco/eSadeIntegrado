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
import modelo.Disciplina;
import modelo.Docente;
import modelo.Funcionario;
import modelo.Notapauta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Pauta;
import modelo.PautaPK;

/**
 *
 * @author Paulino Francisco
 */
public class PautaJpaController implements Serializable {

    public PautaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pauta pauta) throws PreexistingEntityException, Exception {
        if (pauta.getPautaPK() == null) {
            pauta.setPautaPK(new PautaPK());
        }
        if (pauta.getNotapautaList() == null) {
            pauta.setNotapautaList(new ArrayList<Notapauta>());
        }
        pauta.getPautaPK().setIddisc(pauta.getDisciplina().getIdDisc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina disciplina = pauta.getDisciplina();
            if (disciplina != null) {
                disciplina = em.getReference(disciplina.getClass(), disciplina.getIdDisc());
                pauta.setDisciplina(disciplina);
            }
            Docente docente = pauta.getDocente();
            if (docente != null) {
                docente = em.getReference(docente.getClass(), docente.getIddocente());
                pauta.setDocente(docente);
            }
            Funcionario funcionario = pauta.getFuncionario();
            if (funcionario != null) {
                funcionario = em.getReference(funcionario.getClass(), funcionario.getIdFuncionario());
                pauta.setFuncionario(funcionario);
            }
            List<Notapauta> attachedNotapautaList = new ArrayList<Notapauta>();
            for (Notapauta notapautaListNotapautaToAttach : pauta.getNotapautaList()) {
                notapautaListNotapautaToAttach = em.getReference(notapautaListNotapautaToAttach.getClass(), notapautaListNotapautaToAttach.getNotapautaPK());
                attachedNotapautaList.add(notapautaListNotapautaToAttach);
            }
            pauta.setNotapautaList(attachedNotapautaList);
            em.persist(pauta);
            if (disciplina != null) {
                disciplina.getPautaList().add(pauta);
                disciplina = em.merge(disciplina);
            }
            if (docente != null) {
                docente.getPautaList().add(pauta);
                docente = em.merge(docente);
            }
            if (funcionario != null) {
                funcionario.getPautaList().add(pauta);
                funcionario = em.merge(funcionario);
            }
            for (Notapauta notapautaListNotapauta : pauta.getNotapautaList()) {
                Pauta oldPautaOfNotapautaListNotapauta = notapautaListNotapauta.getPauta();
                notapautaListNotapauta.setPauta(pauta);
                notapautaListNotapauta = em.merge(notapautaListNotapauta);
                if (oldPautaOfNotapautaListNotapauta != null) {
                    oldPautaOfNotapautaListNotapauta.getNotapautaList().remove(notapautaListNotapauta);
                    oldPautaOfNotapautaListNotapauta = em.merge(oldPautaOfNotapautaListNotapauta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPauta(pauta.getPautaPK()) != null) {
                throw new PreexistingEntityException("Pauta " + pauta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pauta pauta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        pauta.getPautaPK().setIddisc(pauta.getDisciplina().getIdDisc());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pauta persistentPauta = em.find(Pauta.class, pauta.getPautaPK());
            Disciplina disciplinaOld = persistentPauta.getDisciplina();
            Disciplina disciplinaNew = pauta.getDisciplina();
            Docente docenteOld = persistentPauta.getDocente();
            Docente docenteNew = pauta.getDocente();
            Funcionario funcionarioOld = persistentPauta.getFuncionario();
            Funcionario funcionarioNew = pauta.getFuncionario();
            List<Notapauta> notapautaListOld = persistentPauta.getNotapautaList();
            List<Notapauta> notapautaListNew = pauta.getNotapautaList();
            List<String> illegalOrphanMessages = null;
            for (Notapauta notapautaListOldNotapauta : notapautaListOld) {
                if (!notapautaListNew.contains(notapautaListOldNotapauta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notapauta " + notapautaListOldNotapauta + " since its pauta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (disciplinaNew != null) {
                disciplinaNew = em.getReference(disciplinaNew.getClass(), disciplinaNew.getIdDisc());
                pauta.setDisciplina(disciplinaNew);
            }
            if (docenteNew != null) {
                docenteNew = em.getReference(docenteNew.getClass(), docenteNew.getIddocente());
                pauta.setDocente(docenteNew);
            }
            if (funcionarioNew != null) {
                funcionarioNew = em.getReference(funcionarioNew.getClass(), funcionarioNew.getIdFuncionario());
                pauta.setFuncionario(funcionarioNew);
            }
            List<Notapauta> attachedNotapautaListNew = new ArrayList<Notapauta>();
            for (Notapauta notapautaListNewNotapautaToAttach : notapautaListNew) {
                notapautaListNewNotapautaToAttach = em.getReference(notapautaListNewNotapautaToAttach.getClass(), notapautaListNewNotapautaToAttach.getNotapautaPK());
                attachedNotapautaListNew.add(notapautaListNewNotapautaToAttach);
            }
            notapautaListNew = attachedNotapautaListNew;
            pauta.setNotapautaList(notapautaListNew);
            pauta = em.merge(pauta);
            if (disciplinaOld != null && !disciplinaOld.equals(disciplinaNew)) {
                disciplinaOld.getPautaList().remove(pauta);
                disciplinaOld = em.merge(disciplinaOld);
            }
            if (disciplinaNew != null && !disciplinaNew.equals(disciplinaOld)) {
                disciplinaNew.getPautaList().add(pauta);
                disciplinaNew = em.merge(disciplinaNew);
            }
            if (docenteOld != null && !docenteOld.equals(docenteNew)) {
                docenteOld.getPautaList().remove(pauta);
                docenteOld = em.merge(docenteOld);
            }
            if (docenteNew != null && !docenteNew.equals(docenteOld)) {
                docenteNew.getPautaList().add(pauta);
                docenteNew = em.merge(docenteNew);
            }
            if (funcionarioOld != null && !funcionarioOld.equals(funcionarioNew)) {
                funcionarioOld.getPautaList().remove(pauta);
                funcionarioOld = em.merge(funcionarioOld);
            }
            if (funcionarioNew != null && !funcionarioNew.equals(funcionarioOld)) {
                funcionarioNew.getPautaList().add(pauta);
                funcionarioNew = em.merge(funcionarioNew);
            }
            for (Notapauta notapautaListNewNotapauta : notapautaListNew) {
                if (!notapautaListOld.contains(notapautaListNewNotapauta)) {
                    Pauta oldPautaOfNotapautaListNewNotapauta = notapautaListNewNotapauta.getPauta();
                    notapautaListNewNotapauta.setPauta(pauta);
                    notapautaListNewNotapauta = em.merge(notapautaListNewNotapauta);
                    if (oldPautaOfNotapautaListNewNotapauta != null && !oldPautaOfNotapautaListNewNotapauta.equals(pauta)) {
                        oldPautaOfNotapautaListNewNotapauta.getNotapautaList().remove(notapautaListNewNotapauta);
                        oldPautaOfNotapautaListNewNotapauta = em.merge(oldPautaOfNotapautaListNewNotapauta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PautaPK id = pauta.getPautaPK();
                if (findPauta(id) == null) {
                    throw new NonexistentEntityException("The pauta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PautaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pauta pauta;
            try {
                pauta = em.getReference(Pauta.class, id);
                pauta.getPautaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pauta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Notapauta> notapautaListOrphanCheck = pauta.getNotapautaList();
            for (Notapauta notapautaListOrphanCheckNotapauta : notapautaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pauta (" + pauta + ") cannot be destroyed since the Notapauta " + notapautaListOrphanCheckNotapauta + " in its notapautaList field has a non-nullable pauta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Disciplina disciplina = pauta.getDisciplina();
            if (disciplina != null) {
                disciplina.getPautaList().remove(pauta);
                disciplina = em.merge(disciplina);
            }
            Docente docente = pauta.getDocente();
            if (docente != null) {
                docente.getPautaList().remove(pauta);
                docente = em.merge(docente);
            }
            Funcionario funcionario = pauta.getFuncionario();
            if (funcionario != null) {
                funcionario.getPautaList().remove(pauta);
                funcionario = em.merge(funcionario);
            }
            em.remove(pauta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pauta> findPautaEntities() {
        return findPautaEntities(true, -1, -1);
    }

    public List<Pauta> findPautaEntities(int maxResults, int firstResult) {
        return findPautaEntities(false, maxResults, firstResult);
    }

    private List<Pauta> findPautaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pauta.class));
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

    public Pauta findPauta(PautaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pauta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPautaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pauta> rt = cq.from(Pauta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
