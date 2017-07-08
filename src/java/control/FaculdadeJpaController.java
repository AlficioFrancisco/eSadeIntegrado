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
import modelo.Funcionario;
import modelo.PlanificacaoAnoLectivo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Users;
import modelo.Curso;
import modelo.Faculdade;
import modelo.Validacaopendente;

/**
 *
 * @author Paulino Francisco
 */
public class FaculdadeJpaController implements Serializable {

    public FaculdadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Faculdade faculdade) throws IllegalOrphanException {
        if (faculdade.getPlanificacaoAnoLectivoList() == null) {
            faculdade.setPlanificacaoAnoLectivoList(new ArrayList<PlanificacaoAnoLectivo>());
        }
        if (faculdade.getFuncionarioList() == null) {
            faculdade.setFuncionarioList(new ArrayList<Funcionario>());
        }
        if (faculdade.getUsersList() == null) {
            faculdade.setUsersList(new ArrayList<Users>());
        }
        if (faculdade.getCursoList() == null) {
            faculdade.setCursoList(new ArrayList<Curso>());
        }
        if (faculdade.getValidacaopendenteList() == null) {
            faculdade.setValidacaopendenteList(new ArrayList<Validacaopendente>());
        }
        List<String> illegalOrphanMessages = null;
        Funcionario directorOrphanCheck = faculdade.getDirector();
        if (directorOrphanCheck != null) {
            Faculdade oldFaculdadeOfDirector = directorOrphanCheck.getFaculdade();
            if (oldFaculdadeOfDirector != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Funcionario " + directorOrphanCheck + " already has an item of type Faculdade whose director column cannot be null. Please make another selection for the director field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario director = faculdade.getDirector();
            if (director != null) {
                director = em.getReference(director.getClass(), director.getIdFuncionario());
                faculdade.setDirector(director);
            }
            List<PlanificacaoAnoLectivo> attachedPlanificacaoAnoLectivoList = new ArrayList<PlanificacaoAnoLectivo>();
            for (PlanificacaoAnoLectivo planificacaoAnoLectivoListPlanificacaoAnoLectivoToAttach : faculdade.getPlanificacaoAnoLectivoList()) {
                planificacaoAnoLectivoListPlanificacaoAnoLectivoToAttach = em.getReference(planificacaoAnoLectivoListPlanificacaoAnoLectivoToAttach.getClass(), planificacaoAnoLectivoListPlanificacaoAnoLectivoToAttach.getAno());
                attachedPlanificacaoAnoLectivoList.add(planificacaoAnoLectivoListPlanificacaoAnoLectivoToAttach);
            }
            faculdade.setPlanificacaoAnoLectivoList(attachedPlanificacaoAnoLectivoList);
            List<Funcionario> attachedFuncionarioList = new ArrayList<Funcionario>();
            for (Funcionario funcionarioListFuncionarioToAttach : faculdade.getFuncionarioList()) {
                funcionarioListFuncionarioToAttach = em.getReference(funcionarioListFuncionarioToAttach.getClass(), funcionarioListFuncionarioToAttach.getIdFuncionario());
                attachedFuncionarioList.add(funcionarioListFuncionarioToAttach);
            }
            faculdade.setFuncionarioList(attachedFuncionarioList);
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : faculdade.getUsersList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUtilizador());
                attachedUsersList.add(usersListUsersToAttach);
            }
            faculdade.setUsersList(attachedUsersList);
            List<Curso> attachedCursoList = new ArrayList<Curso>();
            for (Curso cursoListCursoToAttach : faculdade.getCursoList()) {
                cursoListCursoToAttach = em.getReference(cursoListCursoToAttach.getClass(), cursoListCursoToAttach.getIdCurso());
                attachedCursoList.add(cursoListCursoToAttach);
            }
            faculdade.setCursoList(attachedCursoList);
            List<Validacaopendente> attachedValidacaopendenteList = new ArrayList<Validacaopendente>();
            for (Validacaopendente validacaopendenteListValidacaopendenteToAttach : faculdade.getValidacaopendenteList()) {
                validacaopendenteListValidacaopendenteToAttach = em.getReference(validacaopendenteListValidacaopendenteToAttach.getClass(), validacaopendenteListValidacaopendenteToAttach.getValidacaopendentePK());
                attachedValidacaopendenteList.add(validacaopendenteListValidacaopendenteToAttach);
            }
            faculdade.setValidacaopendenteList(attachedValidacaopendenteList);
            em.persist(faculdade);
            if (director != null) {
                director.setFaculdade(faculdade);
                director = em.merge(director);
            }
            for (PlanificacaoAnoLectivo planificacaoAnoLectivoListPlanificacaoAnoLectivo : faculdade.getPlanificacaoAnoLectivoList()) {
                Faculdade oldFaculdadeOfPlanificacaoAnoLectivoListPlanificacaoAnoLectivo = planificacaoAnoLectivoListPlanificacaoAnoLectivo.getFaculdade();
                planificacaoAnoLectivoListPlanificacaoAnoLectivo.setFaculdade(faculdade);
                planificacaoAnoLectivoListPlanificacaoAnoLectivo = em.merge(planificacaoAnoLectivoListPlanificacaoAnoLectivo);
                if (oldFaculdadeOfPlanificacaoAnoLectivoListPlanificacaoAnoLectivo != null) {
                    oldFaculdadeOfPlanificacaoAnoLectivoListPlanificacaoAnoLectivo.getPlanificacaoAnoLectivoList().remove(planificacaoAnoLectivoListPlanificacaoAnoLectivo);
                    oldFaculdadeOfPlanificacaoAnoLectivoListPlanificacaoAnoLectivo = em.merge(oldFaculdadeOfPlanificacaoAnoLectivoListPlanificacaoAnoLectivo);
                }
            }
            for (Funcionario funcionarioListFuncionario : faculdade.getFuncionarioList()) {
                Faculdade oldFaculdadeOfFuncionarioListFuncionario = funcionarioListFuncionario.getFaculdade();
                funcionarioListFuncionario.setFaculdade(faculdade);
                funcionarioListFuncionario = em.merge(funcionarioListFuncionario);
                if (oldFaculdadeOfFuncionarioListFuncionario != null) {
                    oldFaculdadeOfFuncionarioListFuncionario.getFuncionarioList().remove(funcionarioListFuncionario);
                    oldFaculdadeOfFuncionarioListFuncionario = em.merge(oldFaculdadeOfFuncionarioListFuncionario);
                }
            }
            for (Users usersListUsers : faculdade.getUsersList()) {
                Faculdade oldFaculdadeOfUsersListUsers = usersListUsers.getFaculdade();
                usersListUsers.setFaculdade(faculdade);
                usersListUsers = em.merge(usersListUsers);
                if (oldFaculdadeOfUsersListUsers != null) {
                    oldFaculdadeOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldFaculdadeOfUsersListUsers = em.merge(oldFaculdadeOfUsersListUsers);
                }
            }
            for (Curso cursoListCurso : faculdade.getCursoList()) {
                Faculdade oldFaculdadeOfCursoListCurso = cursoListCurso.getFaculdade();
                cursoListCurso.setFaculdade(faculdade);
                cursoListCurso = em.merge(cursoListCurso);
                if (oldFaculdadeOfCursoListCurso != null) {
                    oldFaculdadeOfCursoListCurso.getCursoList().remove(cursoListCurso);
                    oldFaculdadeOfCursoListCurso = em.merge(oldFaculdadeOfCursoListCurso);
                }
            }
            for (Validacaopendente validacaopendenteListValidacaopendente : faculdade.getValidacaopendenteList()) {
                Faculdade oldFaculdadeOfValidacaopendenteListValidacaopendente = validacaopendenteListValidacaopendente.getFaculdade();
                validacaopendenteListValidacaopendente.setFaculdade(faculdade);
                validacaopendenteListValidacaopendente = em.merge(validacaopendenteListValidacaopendente);
                if (oldFaculdadeOfValidacaopendenteListValidacaopendente != null) {
                    oldFaculdadeOfValidacaopendenteListValidacaopendente.getValidacaopendenteList().remove(validacaopendenteListValidacaopendente);
                    oldFaculdadeOfValidacaopendenteListValidacaopendente = em.merge(oldFaculdadeOfValidacaopendenteListValidacaopendente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Faculdade faculdade) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faculdade persistentFaculdade = em.find(Faculdade.class, faculdade.getIdFaculdade());
            Funcionario directorOld = persistentFaculdade.getDirector();
            Funcionario directorNew = faculdade.getDirector();
            List<PlanificacaoAnoLectivo> planificacaoAnoLectivoListOld = persistentFaculdade.getPlanificacaoAnoLectivoList();
            List<PlanificacaoAnoLectivo> planificacaoAnoLectivoListNew = faculdade.getPlanificacaoAnoLectivoList();
            List<Funcionario> funcionarioListOld = persistentFaculdade.getFuncionarioList();
            List<Funcionario> funcionarioListNew = faculdade.getFuncionarioList();
            List<Users> usersListOld = persistentFaculdade.getUsersList();
            List<Users> usersListNew = faculdade.getUsersList();
            List<Curso> cursoListOld = persistentFaculdade.getCursoList();
            List<Curso> cursoListNew = faculdade.getCursoList();
            List<Validacaopendente> validacaopendenteListOld = persistentFaculdade.getValidacaopendenteList();
            List<Validacaopendente> validacaopendenteListNew = faculdade.getValidacaopendenteList();
            List<String> illegalOrphanMessages = null;
            if (directorNew != null && !directorNew.equals(directorOld)) {
                Faculdade oldFaculdadeOfDirector = directorNew.getFaculdade();
                if (oldFaculdadeOfDirector != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Funcionario " + directorNew + " already has an item of type Faculdade whose director column cannot be null. Please make another selection for the director field.");
                }
            }
            for (Validacaopendente validacaopendenteListOldValidacaopendente : validacaopendenteListOld) {
                if (!validacaopendenteListNew.contains(validacaopendenteListOldValidacaopendente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Validacaopendente " + validacaopendenteListOldValidacaopendente + " since its faculdade field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (directorNew != null) {
                directorNew = em.getReference(directorNew.getClass(), directorNew.getIdFuncionario());
                faculdade.setDirector(directorNew);
            }
            List<PlanificacaoAnoLectivo> attachedPlanificacaoAnoLectivoListNew = new ArrayList<PlanificacaoAnoLectivo>();
            for (PlanificacaoAnoLectivo planificacaoAnoLectivoListNewPlanificacaoAnoLectivoToAttach : planificacaoAnoLectivoListNew) {
                planificacaoAnoLectivoListNewPlanificacaoAnoLectivoToAttach = em.getReference(planificacaoAnoLectivoListNewPlanificacaoAnoLectivoToAttach.getClass(), planificacaoAnoLectivoListNewPlanificacaoAnoLectivoToAttach.getAno());
                attachedPlanificacaoAnoLectivoListNew.add(planificacaoAnoLectivoListNewPlanificacaoAnoLectivoToAttach);
            }
            planificacaoAnoLectivoListNew = attachedPlanificacaoAnoLectivoListNew;
            faculdade.setPlanificacaoAnoLectivoList(planificacaoAnoLectivoListNew);
            List<Funcionario> attachedFuncionarioListNew = new ArrayList<Funcionario>();
            for (Funcionario funcionarioListNewFuncionarioToAttach : funcionarioListNew) {
                funcionarioListNewFuncionarioToAttach = em.getReference(funcionarioListNewFuncionarioToAttach.getClass(), funcionarioListNewFuncionarioToAttach.getIdFuncionario());
                attachedFuncionarioListNew.add(funcionarioListNewFuncionarioToAttach);
            }
            funcionarioListNew = attachedFuncionarioListNew;
            faculdade.setFuncionarioList(funcionarioListNew);
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUtilizador());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            faculdade.setUsersList(usersListNew);
            List<Curso> attachedCursoListNew = new ArrayList<Curso>();
            for (Curso cursoListNewCursoToAttach : cursoListNew) {
                cursoListNewCursoToAttach = em.getReference(cursoListNewCursoToAttach.getClass(), cursoListNewCursoToAttach.getIdCurso());
                attachedCursoListNew.add(cursoListNewCursoToAttach);
            }
            cursoListNew = attachedCursoListNew;
            faculdade.setCursoList(cursoListNew);
            List<Validacaopendente> attachedValidacaopendenteListNew = new ArrayList<Validacaopendente>();
            for (Validacaopendente validacaopendenteListNewValidacaopendenteToAttach : validacaopendenteListNew) {
                validacaopendenteListNewValidacaopendenteToAttach = em.getReference(validacaopendenteListNewValidacaopendenteToAttach.getClass(), validacaopendenteListNewValidacaopendenteToAttach.getValidacaopendentePK());
                attachedValidacaopendenteListNew.add(validacaopendenteListNewValidacaopendenteToAttach);
            }
            validacaopendenteListNew = attachedValidacaopendenteListNew;
            faculdade.setValidacaopendenteList(validacaopendenteListNew);
            faculdade = em.merge(faculdade);
            if (directorOld != null && !directorOld.equals(directorNew)) {
                directorOld.setFaculdade(null);
                directorOld = em.merge(directorOld);
            }
            if (directorNew != null && !directorNew.equals(directorOld)) {
                directorNew.setFaculdade(faculdade);
                directorNew = em.merge(directorNew);
            }
            for (PlanificacaoAnoLectivo planificacaoAnoLectivoListOldPlanificacaoAnoLectivo : planificacaoAnoLectivoListOld) {
                if (!planificacaoAnoLectivoListNew.contains(planificacaoAnoLectivoListOldPlanificacaoAnoLectivo)) {
                    planificacaoAnoLectivoListOldPlanificacaoAnoLectivo.setFaculdade(null);
                    planificacaoAnoLectivoListOldPlanificacaoAnoLectivo = em.merge(planificacaoAnoLectivoListOldPlanificacaoAnoLectivo);
                }
            }
            for (PlanificacaoAnoLectivo planificacaoAnoLectivoListNewPlanificacaoAnoLectivo : planificacaoAnoLectivoListNew) {
                if (!planificacaoAnoLectivoListOld.contains(planificacaoAnoLectivoListNewPlanificacaoAnoLectivo)) {
                    Faculdade oldFaculdadeOfPlanificacaoAnoLectivoListNewPlanificacaoAnoLectivo = planificacaoAnoLectivoListNewPlanificacaoAnoLectivo.getFaculdade();
                    planificacaoAnoLectivoListNewPlanificacaoAnoLectivo.setFaculdade(faculdade);
                    planificacaoAnoLectivoListNewPlanificacaoAnoLectivo = em.merge(planificacaoAnoLectivoListNewPlanificacaoAnoLectivo);
                    if (oldFaculdadeOfPlanificacaoAnoLectivoListNewPlanificacaoAnoLectivo != null && !oldFaculdadeOfPlanificacaoAnoLectivoListNewPlanificacaoAnoLectivo.equals(faculdade)) {
                        oldFaculdadeOfPlanificacaoAnoLectivoListNewPlanificacaoAnoLectivo.getPlanificacaoAnoLectivoList().remove(planificacaoAnoLectivoListNewPlanificacaoAnoLectivo);
                        oldFaculdadeOfPlanificacaoAnoLectivoListNewPlanificacaoAnoLectivo = em.merge(oldFaculdadeOfPlanificacaoAnoLectivoListNewPlanificacaoAnoLectivo);
                    }
                }
            }
            for (Funcionario funcionarioListOldFuncionario : funcionarioListOld) {
                if (!funcionarioListNew.contains(funcionarioListOldFuncionario)) {
                    funcionarioListOldFuncionario.setFaculdade(null);
                    funcionarioListOldFuncionario = em.merge(funcionarioListOldFuncionario);
                }
            }
            for (Funcionario funcionarioListNewFuncionario : funcionarioListNew) {
                if (!funcionarioListOld.contains(funcionarioListNewFuncionario)) {
                    Faculdade oldFaculdadeOfFuncionarioListNewFuncionario = funcionarioListNewFuncionario.getFaculdade();
                    funcionarioListNewFuncionario.setFaculdade(faculdade);
                    funcionarioListNewFuncionario = em.merge(funcionarioListNewFuncionario);
                    if (oldFaculdadeOfFuncionarioListNewFuncionario != null && !oldFaculdadeOfFuncionarioListNewFuncionario.equals(faculdade)) {
                        oldFaculdadeOfFuncionarioListNewFuncionario.getFuncionarioList().remove(funcionarioListNewFuncionario);
                        oldFaculdadeOfFuncionarioListNewFuncionario = em.merge(oldFaculdadeOfFuncionarioListNewFuncionario);
                    }
                }
            }
            for (Users usersListOldUsers : usersListOld) {
                if (!usersListNew.contains(usersListOldUsers)) {
                    usersListOldUsers.setFaculdade(null);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (Users usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    Faculdade oldFaculdadeOfUsersListNewUsers = usersListNewUsers.getFaculdade();
                    usersListNewUsers.setFaculdade(faculdade);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldFaculdadeOfUsersListNewUsers != null && !oldFaculdadeOfUsersListNewUsers.equals(faculdade)) {
                        oldFaculdadeOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldFaculdadeOfUsersListNewUsers = em.merge(oldFaculdadeOfUsersListNewUsers);
                    }
                }
            }
            for (Curso cursoListOldCurso : cursoListOld) {
                if (!cursoListNew.contains(cursoListOldCurso)) {
                    cursoListOldCurso.setFaculdade(null);
                    cursoListOldCurso = em.merge(cursoListOldCurso);
                }
            }
            for (Curso cursoListNewCurso : cursoListNew) {
                if (!cursoListOld.contains(cursoListNewCurso)) {
                    Faculdade oldFaculdadeOfCursoListNewCurso = cursoListNewCurso.getFaculdade();
                    cursoListNewCurso.setFaculdade(faculdade);
                    cursoListNewCurso = em.merge(cursoListNewCurso);
                    if (oldFaculdadeOfCursoListNewCurso != null && !oldFaculdadeOfCursoListNewCurso.equals(faculdade)) {
                        oldFaculdadeOfCursoListNewCurso.getCursoList().remove(cursoListNewCurso);
                        oldFaculdadeOfCursoListNewCurso = em.merge(oldFaculdadeOfCursoListNewCurso);
                    }
                }
            }
            for (Validacaopendente validacaopendenteListNewValidacaopendente : validacaopendenteListNew) {
                if (!validacaopendenteListOld.contains(validacaopendenteListNewValidacaopendente)) {
                    Faculdade oldFaculdadeOfValidacaopendenteListNewValidacaopendente = validacaopendenteListNewValidacaopendente.getFaculdade();
                    validacaopendenteListNewValidacaopendente.setFaculdade(faculdade);
                    validacaopendenteListNewValidacaopendente = em.merge(validacaopendenteListNewValidacaopendente);
                    if (oldFaculdadeOfValidacaopendenteListNewValidacaopendente != null && !oldFaculdadeOfValidacaopendenteListNewValidacaopendente.equals(faculdade)) {
                        oldFaculdadeOfValidacaopendenteListNewValidacaopendente.getValidacaopendenteList().remove(validacaopendenteListNewValidacaopendente);
                        oldFaculdadeOfValidacaopendenteListNewValidacaopendente = em.merge(oldFaculdadeOfValidacaopendenteListNewValidacaopendente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = faculdade.getIdFaculdade();
                if (findFaculdade(id) == null) {
                    throw new NonexistentEntityException("The faculdade with id " + id + " no longer exists.");
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
            Faculdade faculdade;
            try {
                faculdade = em.getReference(Faculdade.class, id);
                faculdade.getIdFaculdade();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The faculdade with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Validacaopendente> validacaopendenteListOrphanCheck = faculdade.getValidacaopendenteList();
            for (Validacaopendente validacaopendenteListOrphanCheckValidacaopendente : validacaopendenteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Faculdade (" + faculdade + ") cannot be destroyed since the Validacaopendente " + validacaopendenteListOrphanCheckValidacaopendente + " in its validacaopendenteList field has a non-nullable faculdade field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Funcionario director = faculdade.getDirector();
            if (director != null) {
                director.setFaculdade(null);
                director = em.merge(director);
            }
            List<PlanificacaoAnoLectivo> planificacaoAnoLectivoList = faculdade.getPlanificacaoAnoLectivoList();
            for (PlanificacaoAnoLectivo planificacaoAnoLectivoListPlanificacaoAnoLectivo : planificacaoAnoLectivoList) {
                planificacaoAnoLectivoListPlanificacaoAnoLectivo.setFaculdade(null);
                planificacaoAnoLectivoListPlanificacaoAnoLectivo = em.merge(planificacaoAnoLectivoListPlanificacaoAnoLectivo);
            }
            List<Funcionario> funcionarioList = faculdade.getFuncionarioList();
            for (Funcionario funcionarioListFuncionario : funcionarioList) {
                funcionarioListFuncionario.setFaculdade(null);
                funcionarioListFuncionario = em.merge(funcionarioListFuncionario);
            }
            List<Users> usersList = faculdade.getUsersList();
            for (Users usersListUsers : usersList) {
                usersListUsers.setFaculdade(null);
                usersListUsers = em.merge(usersListUsers);
            }
            List<Curso> cursoList = faculdade.getCursoList();
            for (Curso cursoListCurso : cursoList) {
                cursoListCurso.setFaculdade(null);
                cursoListCurso = em.merge(cursoListCurso);
            }
            em.remove(faculdade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Faculdade> findFaculdadeEntities() {
        return findFaculdadeEntities(true, -1, -1);
    }

    public List<Faculdade> findFaculdadeEntities(int maxResults, int firstResult) {
        return findFaculdadeEntities(false, maxResults, firstResult);
    }

    private List<Faculdade> findFaculdadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Faculdade.class));
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

    public Faculdade findFaculdade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Faculdade.class, id);
        } finally {
            em.close();
        }
    }

    public int getFaculdadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Faculdade> rt = cq.from(Faculdade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
