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
import modelo.Cargochefia;
import modelo.Lecciona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Docente;
import modelo.Users;
import modelo.Pauta;

/**
 *
 * @author Paulino Francisco
 */
public class DocenteJpaController implements Serializable {

    public DocenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Docente docente) throws PreexistingEntityException, Exception {
        if (docente.getLeccionaList() == null) {
            docente.setLeccionaList(new ArrayList<Lecciona>());
        }
        if (docente.getUsersList() == null) {
            docente.setUsersList(new ArrayList<Users>());
        }
        if (docente.getPautaList() == null) {
            docente.setPautaList(new ArrayList<Pauta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargochefia idcargochefia = docente.getIdcargochefia();
            if (idcargochefia != null) {
                idcargochefia = em.getReference(idcargochefia.getClass(), idcargochefia.getIdcargochefia());
                docente.setIdcargochefia(idcargochefia);
            }
            List<Lecciona> attachedLeccionaList = new ArrayList<Lecciona>();
            for (Lecciona leccionaListLeccionaToAttach : docente.getLeccionaList()) {
                leccionaListLeccionaToAttach = em.getReference(leccionaListLeccionaToAttach.getClass(), leccionaListLeccionaToAttach.getLeccionaPK());
                attachedLeccionaList.add(leccionaListLeccionaToAttach);
            }
            docente.setLeccionaList(attachedLeccionaList);
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : docente.getUsersList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUtilizador());
                attachedUsersList.add(usersListUsersToAttach);
            }
            docente.setUsersList(attachedUsersList);
            List<Pauta> attachedPautaList = new ArrayList<Pauta>();
            for (Pauta pautaListPautaToAttach : docente.getPautaList()) {
                pautaListPautaToAttach = em.getReference(pautaListPautaToAttach.getClass(), pautaListPautaToAttach.getPautaPK());
                attachedPautaList.add(pautaListPautaToAttach);
            }
            docente.setPautaList(attachedPautaList);
            em.persist(docente);
            if (idcargochefia != null) {
                idcargochefia.getDocenteList().add(docente);
                idcargochefia = em.merge(idcargochefia);
            }
            for (Lecciona leccionaListLecciona : docente.getLeccionaList()) {
                Docente oldDocenteOfLeccionaListLecciona = leccionaListLecciona.getDocente();
                leccionaListLecciona.setDocente(docente);
                leccionaListLecciona = em.merge(leccionaListLecciona);
                if (oldDocenteOfLeccionaListLecciona != null) {
                    oldDocenteOfLeccionaListLecciona.getLeccionaList().remove(leccionaListLecciona);
                    oldDocenteOfLeccionaListLecciona = em.merge(oldDocenteOfLeccionaListLecciona);
                }
            }
            for (Users usersListUsers : docente.getUsersList()) {
                Docente oldIddocenteOfUsersListUsers = usersListUsers.getIddocente();
                usersListUsers.setIddocente(docente);
                usersListUsers = em.merge(usersListUsers);
                if (oldIddocenteOfUsersListUsers != null) {
                    oldIddocenteOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldIddocenteOfUsersListUsers = em.merge(oldIddocenteOfUsersListUsers);
                }
            }
            for (Pauta pautaListPauta : docente.getPautaList()) {
                Docente oldDocenteOfPautaListPauta = pautaListPauta.getDocente();
                pautaListPauta.setDocente(docente);
                pautaListPauta = em.merge(pautaListPauta);
                if (oldDocenteOfPautaListPauta != null) {
                    oldDocenteOfPautaListPauta.getPautaList().remove(pautaListPauta);
                    oldDocenteOfPautaListPauta = em.merge(oldDocenteOfPautaListPauta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocente(docente.getIddocente()) != null) {
                throw new PreexistingEntityException("Docente " + docente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Docente docente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente persistentDocente = em.find(Docente.class, docente.getIddocente());
            Cargochefia idcargochefiaOld = persistentDocente.getIdcargochefia();
            Cargochefia idcargochefiaNew = docente.getIdcargochefia();
            List<Lecciona> leccionaListOld = persistentDocente.getLeccionaList();
            List<Lecciona> leccionaListNew = docente.getLeccionaList();
            List<Users> usersListOld = persistentDocente.getUsersList();
            List<Users> usersListNew = docente.getUsersList();
            List<Pauta> pautaListOld = persistentDocente.getPautaList();
            List<Pauta> pautaListNew = docente.getPautaList();
            List<String> illegalOrphanMessages = null;
            for (Lecciona leccionaListOldLecciona : leccionaListOld) {
                if (!leccionaListNew.contains(leccionaListOldLecciona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lecciona " + leccionaListOldLecciona + " since its docente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idcargochefiaNew != null) {
                idcargochefiaNew = em.getReference(idcargochefiaNew.getClass(), idcargochefiaNew.getIdcargochefia());
                docente.setIdcargochefia(idcargochefiaNew);
            }
            List<Lecciona> attachedLeccionaListNew = new ArrayList<Lecciona>();
            for (Lecciona leccionaListNewLeccionaToAttach : leccionaListNew) {
                leccionaListNewLeccionaToAttach = em.getReference(leccionaListNewLeccionaToAttach.getClass(), leccionaListNewLeccionaToAttach.getLeccionaPK());
                attachedLeccionaListNew.add(leccionaListNewLeccionaToAttach);
            }
            leccionaListNew = attachedLeccionaListNew;
            docente.setLeccionaList(leccionaListNew);
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUtilizador());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            docente.setUsersList(usersListNew);
            List<Pauta> attachedPautaListNew = new ArrayList<Pauta>();
            for (Pauta pautaListNewPautaToAttach : pautaListNew) {
                pautaListNewPautaToAttach = em.getReference(pautaListNewPautaToAttach.getClass(), pautaListNewPautaToAttach.getPautaPK());
                attachedPautaListNew.add(pautaListNewPautaToAttach);
            }
            pautaListNew = attachedPautaListNew;
            docente.setPautaList(pautaListNew);
            docente = em.merge(docente);
            if (idcargochefiaOld != null && !idcargochefiaOld.equals(idcargochefiaNew)) {
                idcargochefiaOld.getDocenteList().remove(docente);
                idcargochefiaOld = em.merge(idcargochefiaOld);
            }
            if (idcargochefiaNew != null && !idcargochefiaNew.equals(idcargochefiaOld)) {
                idcargochefiaNew.getDocenteList().add(docente);
                idcargochefiaNew = em.merge(idcargochefiaNew);
            }
            for (Lecciona leccionaListNewLecciona : leccionaListNew) {
                if (!leccionaListOld.contains(leccionaListNewLecciona)) {
                    Docente oldDocenteOfLeccionaListNewLecciona = leccionaListNewLecciona.getDocente();
                    leccionaListNewLecciona.setDocente(docente);
                    leccionaListNewLecciona = em.merge(leccionaListNewLecciona);
                    if (oldDocenteOfLeccionaListNewLecciona != null && !oldDocenteOfLeccionaListNewLecciona.equals(docente)) {
                        oldDocenteOfLeccionaListNewLecciona.getLeccionaList().remove(leccionaListNewLecciona);
                        oldDocenteOfLeccionaListNewLecciona = em.merge(oldDocenteOfLeccionaListNewLecciona);
                    }
                }
            }
            for (Users usersListOldUsers : usersListOld) {
                if (!usersListNew.contains(usersListOldUsers)) {
                    usersListOldUsers.setIddocente(null);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (Users usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    Docente oldIddocenteOfUsersListNewUsers = usersListNewUsers.getIddocente();
                    usersListNewUsers.setIddocente(docente);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldIddocenteOfUsersListNewUsers != null && !oldIddocenteOfUsersListNewUsers.equals(docente)) {
                        oldIddocenteOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldIddocenteOfUsersListNewUsers = em.merge(oldIddocenteOfUsersListNewUsers);
                    }
                }
            }
            for (Pauta pautaListOldPauta : pautaListOld) {
                if (!pautaListNew.contains(pautaListOldPauta)) {
                    pautaListOldPauta.setDocente(null);
                    pautaListOldPauta = em.merge(pautaListOldPauta);
                }
            }
            for (Pauta pautaListNewPauta : pautaListNew) {
                if (!pautaListOld.contains(pautaListNewPauta)) {
                    Docente oldDocenteOfPautaListNewPauta = pautaListNewPauta.getDocente();
                    pautaListNewPauta.setDocente(docente);
                    pautaListNewPauta = em.merge(pautaListNewPauta);
                    if (oldDocenteOfPautaListNewPauta != null && !oldDocenteOfPautaListNewPauta.equals(docente)) {
                        oldDocenteOfPautaListNewPauta.getPautaList().remove(pautaListNewPauta);
                        oldDocenteOfPautaListNewPauta = em.merge(oldDocenteOfPautaListNewPauta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = docente.getIddocente();
                if (findDocente(id) == null) {
                    throw new NonexistentEntityException("The docente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente docente;
            try {
                docente = em.getReference(Docente.class, id);
                docente.getIddocente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The docente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Lecciona> leccionaListOrphanCheck = docente.getLeccionaList();
            for (Lecciona leccionaListOrphanCheckLecciona : leccionaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Docente (" + docente + ") cannot be destroyed since the Lecciona " + leccionaListOrphanCheckLecciona + " in its leccionaList field has a non-nullable docente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cargochefia idcargochefia = docente.getIdcargochefia();
            if (idcargochefia != null) {
                idcargochefia.getDocenteList().remove(docente);
                idcargochefia = em.merge(idcargochefia);
            }
            List<Users> usersList = docente.getUsersList();
            for (Users usersListUsers : usersList) {
                usersListUsers.setIddocente(null);
                usersListUsers = em.merge(usersListUsers);
            }
            List<Pauta> pautaList = docente.getPautaList();
            for (Pauta pautaListPauta : pautaList) {
                pautaListPauta.setDocente(null);
                pautaListPauta = em.merge(pautaListPauta);
            }
            em.remove(docente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Docente> findDocenteEntities() {
        return findDocenteEntities(true, -1, -1);
    }

    public List<Docente> findDocenteEntities(int maxResults, int firstResult) {
        return findDocenteEntities(false, maxResults, firstResult);
    }

    private List<Docente> findDocenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Docente.class));
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

    public Docente findDocente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Docente.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Docente> rt = cq.from(Docente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
