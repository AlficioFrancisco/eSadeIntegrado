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
import modelo.Docente;
import modelo.Estudante;
import modelo.Faculdade;
import modelo.Funcionario;
import modelo.Grupo;
import modelo.Usergrupo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Users;

/**
 *
 * @author Paulino Francisco
 */
public class UsersJpaController implements Serializable {

    public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) throws PreexistingEntityException, Exception {
        if (users.getUsergrupoList() == null) {
            users.setUsergrupoList(new ArrayList<Usergrupo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Docente iddocente = users.getIddocente();
            if (iddocente != null) {
                iddocente = em.getReference(iddocente.getClass(), iddocente.getIddocente());
                users.setIddocente(iddocente);
            }
            Estudante idEstudante = users.getIdEstudante();
            if (idEstudante != null) {
                idEstudante = em.getReference(idEstudante.getClass(), idEstudante.getIdEstudante());
                users.setIdEstudante(idEstudante);
            }
            Faculdade faculdade = users.getFaculdade();
            if (faculdade != null) {
                faculdade = em.getReference(faculdade.getClass(), faculdade.getIdFaculdade());
                users.setFaculdade(faculdade);
            }
            Funcionario idFuncionario = users.getIdFuncionario();
            if (idFuncionario != null) {
                idFuncionario = em.getReference(idFuncionario.getClass(), idFuncionario.getIdFuncionario());
                users.setIdFuncionario(idFuncionario);
            }
            Grupo idGrupo = users.getIdGrupo();
            if (idGrupo != null) {
                idGrupo = em.getReference(idGrupo.getClass(), idGrupo.getIdGrupo());
                users.setIdGrupo(idGrupo);
            }
            List<Usergrupo> attachedUsergrupoList = new ArrayList<Usergrupo>();
            for (Usergrupo usergrupoListUsergrupoToAttach : users.getUsergrupoList()) {
                usergrupoListUsergrupoToAttach = em.getReference(usergrupoListUsergrupoToAttach.getClass(), usergrupoListUsergrupoToAttach.getUsergrupoPK());
                attachedUsergrupoList.add(usergrupoListUsergrupoToAttach);
            }
            users.setUsergrupoList(attachedUsergrupoList);
            em.persist(users);
            if (iddocente != null) {
                iddocente.getUsersList().add(users);
                iddocente = em.merge(iddocente);
            }
            if (idEstudante != null) {
                idEstudante.getUsersList().add(users);
                idEstudante = em.merge(idEstudante);
            }
            if (faculdade != null) {
                faculdade.getUsersList().add(users);
                faculdade = em.merge(faculdade);
            }
            if (idFuncionario != null) {
                idFuncionario.getUsersList().add(users);
                idFuncionario = em.merge(idFuncionario);
            }
            if (idGrupo != null) {
                idGrupo.getUsersList().add(users);
                idGrupo = em.merge(idGrupo);
            }
            for (Usergrupo usergrupoListUsergrupo : users.getUsergrupoList()) {
                Users oldUsersOfUsergrupoListUsergrupo = usergrupoListUsergrupo.getUsers();
                usergrupoListUsergrupo.setUsers(users);
                usergrupoListUsergrupo = em.merge(usergrupoListUsergrupo);
                if (oldUsersOfUsergrupoListUsergrupo != null) {
                    oldUsersOfUsergrupoListUsergrupo.getUsergrupoList().remove(usergrupoListUsergrupo);
                    oldUsersOfUsergrupoListUsergrupo = em.merge(oldUsersOfUsergrupoListUsergrupo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsers(users.getUtilizador()) != null) {
                throw new PreexistingEntityException("Users " + users + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getUtilizador());
            Docente iddocenteOld = persistentUsers.getIddocente();
            Docente iddocenteNew = users.getIddocente();
            Estudante idEstudanteOld = persistentUsers.getIdEstudante();
            Estudante idEstudanteNew = users.getIdEstudante();
            Faculdade faculdadeOld = persistentUsers.getFaculdade();
            Faculdade faculdadeNew = users.getFaculdade();
            Funcionario idFuncionarioOld = persistentUsers.getIdFuncionario();
            Funcionario idFuncionarioNew = users.getIdFuncionario();
            Grupo idGrupoOld = persistentUsers.getIdGrupo();
            Grupo idGrupoNew = users.getIdGrupo();
            List<Usergrupo> usergrupoListOld = persistentUsers.getUsergrupoList();
            List<Usergrupo> usergrupoListNew = users.getUsergrupoList();
            List<String> illegalOrphanMessages = null;
            for (Usergrupo usergrupoListOldUsergrupo : usergrupoListOld) {
                if (!usergrupoListNew.contains(usergrupoListOldUsergrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usergrupo " + usergrupoListOldUsergrupo + " since its users field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (iddocenteNew != null) {
                iddocenteNew = em.getReference(iddocenteNew.getClass(), iddocenteNew.getIddocente());
                users.setIddocente(iddocenteNew);
            }
            if (idEstudanteNew != null) {
                idEstudanteNew = em.getReference(idEstudanteNew.getClass(), idEstudanteNew.getIdEstudante());
                users.setIdEstudante(idEstudanteNew);
            }
            if (faculdadeNew != null) {
                faculdadeNew = em.getReference(faculdadeNew.getClass(), faculdadeNew.getIdFaculdade());
                users.setFaculdade(faculdadeNew);
            }
            if (idFuncionarioNew != null) {
                idFuncionarioNew = em.getReference(idFuncionarioNew.getClass(), idFuncionarioNew.getIdFuncionario());
                users.setIdFuncionario(idFuncionarioNew);
            }
            if (idGrupoNew != null) {
                idGrupoNew = em.getReference(idGrupoNew.getClass(), idGrupoNew.getIdGrupo());
                users.setIdGrupo(idGrupoNew);
            }
            List<Usergrupo> attachedUsergrupoListNew = new ArrayList<Usergrupo>();
            for (Usergrupo usergrupoListNewUsergrupoToAttach : usergrupoListNew) {
                usergrupoListNewUsergrupoToAttach = em.getReference(usergrupoListNewUsergrupoToAttach.getClass(), usergrupoListNewUsergrupoToAttach.getUsergrupoPK());
                attachedUsergrupoListNew.add(usergrupoListNewUsergrupoToAttach);
            }
            usergrupoListNew = attachedUsergrupoListNew;
            users.setUsergrupoList(usergrupoListNew);
            users = em.merge(users);
            if (iddocenteOld != null && !iddocenteOld.equals(iddocenteNew)) {
                iddocenteOld.getUsersList().remove(users);
                iddocenteOld = em.merge(iddocenteOld);
            }
            if (iddocenteNew != null && !iddocenteNew.equals(iddocenteOld)) {
                iddocenteNew.getUsersList().add(users);
                iddocenteNew = em.merge(iddocenteNew);
            }
            if (idEstudanteOld != null && !idEstudanteOld.equals(idEstudanteNew)) {
                idEstudanteOld.getUsersList().remove(users);
                idEstudanteOld = em.merge(idEstudanteOld);
            }
            if (idEstudanteNew != null && !idEstudanteNew.equals(idEstudanteOld)) {
                idEstudanteNew.getUsersList().add(users);
                idEstudanteNew = em.merge(idEstudanteNew);
            }
            if (faculdadeOld != null && !faculdadeOld.equals(faculdadeNew)) {
                faculdadeOld.getUsersList().remove(users);
                faculdadeOld = em.merge(faculdadeOld);
            }
            if (faculdadeNew != null && !faculdadeNew.equals(faculdadeOld)) {
                faculdadeNew.getUsersList().add(users);
                faculdadeNew = em.merge(faculdadeNew);
            }
            if (idFuncionarioOld != null && !idFuncionarioOld.equals(idFuncionarioNew)) {
                idFuncionarioOld.getUsersList().remove(users);
                idFuncionarioOld = em.merge(idFuncionarioOld);
            }
            if (idFuncionarioNew != null && !idFuncionarioNew.equals(idFuncionarioOld)) {
                idFuncionarioNew.getUsersList().add(users);
                idFuncionarioNew = em.merge(idFuncionarioNew);
            }
            if (idGrupoOld != null && !idGrupoOld.equals(idGrupoNew)) {
                idGrupoOld.getUsersList().remove(users);
                idGrupoOld = em.merge(idGrupoOld);
            }
            if (idGrupoNew != null && !idGrupoNew.equals(idGrupoOld)) {
                idGrupoNew.getUsersList().add(users);
                idGrupoNew = em.merge(idGrupoNew);
            }
            for (Usergrupo usergrupoListNewUsergrupo : usergrupoListNew) {
                if (!usergrupoListOld.contains(usergrupoListNewUsergrupo)) {
                    Users oldUsersOfUsergrupoListNewUsergrupo = usergrupoListNewUsergrupo.getUsers();
                    usergrupoListNewUsergrupo.setUsers(users);
                    usergrupoListNewUsergrupo = em.merge(usergrupoListNewUsergrupo);
                    if (oldUsersOfUsergrupoListNewUsergrupo != null && !oldUsersOfUsergrupoListNewUsergrupo.equals(users)) {
                        oldUsersOfUsergrupoListNewUsergrupo.getUsergrupoList().remove(usergrupoListNewUsergrupo);
                        oldUsersOfUsergrupoListNewUsergrupo = em.merge(oldUsersOfUsergrupoListNewUsergrupo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = users.getUtilizador();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getUtilizador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usergrupo> usergrupoListOrphanCheck = users.getUsergrupoList();
            for (Usergrupo usergrupoListOrphanCheckUsergrupo : usergrupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Usergrupo " + usergrupoListOrphanCheckUsergrupo + " in its usergrupoList field has a non-nullable users field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Docente iddocente = users.getIddocente();
            if (iddocente != null) {
                iddocente.getUsersList().remove(users);
                iddocente = em.merge(iddocente);
            }
            Estudante idEstudante = users.getIdEstudante();
            if (idEstudante != null) {
                idEstudante.getUsersList().remove(users);
                idEstudante = em.merge(idEstudante);
            }
            Faculdade faculdade = users.getFaculdade();
            if (faculdade != null) {
                faculdade.getUsersList().remove(users);
                faculdade = em.merge(faculdade);
            }
            Funcionario idFuncionario = users.getIdFuncionario();
            if (idFuncionario != null) {
                idFuncionario.getUsersList().remove(users);
                idFuncionario = em.merge(idFuncionario);
            }
            Grupo idGrupo = users.getIdGrupo();
            if (idGrupo != null) {
                idGrupo.getUsersList().remove(users);
                idGrupo = em.merge(idGrupo);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
