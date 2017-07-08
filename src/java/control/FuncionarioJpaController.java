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
import modelo.Tipochefia;
import modelo.Faculdade;
import modelo.Notificacao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Users;
import modelo.Matricula;
import modelo.Pauta;
import modelo.Disciplinaanulada;
import modelo.Funcionario;
import modelo.Operacaopedido;
import modelo.Matriculaanulada;

/**
 *
 * @author Paulino Francisco
 */
public class FuncionarioJpaController implements Serializable {

    public FuncionarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionario funcionario) {
        if (funcionario.getNotificacaoList() == null) {
            funcionario.setNotificacaoList(new ArrayList<Notificacao>());
        }
        if (funcionario.getUsersList() == null) {
            funcionario.setUsersList(new ArrayList<Users>());
        }
        if (funcionario.getMatriculaList() == null) {
            funcionario.setMatriculaList(new ArrayList<Matricula>());
        }
        if (funcionario.getFaculdadeList() == null) {
            funcionario.setFaculdadeList(new ArrayList<Faculdade>());
        }
        if (funcionario.getPautaList() == null) {
            funcionario.setPautaList(new ArrayList<Pauta>());
        }
        if (funcionario.getDisciplinaanuladaList() == null) {
            funcionario.setDisciplinaanuladaList(new ArrayList<Disciplinaanulada>());
        }
        if (funcionario.getOperacaopedidoList() == null) {
            funcionario.setOperacaopedidoList(new ArrayList<Operacaopedido>());
        }
        if (funcionario.getOperacaopedidoList1() == null) {
            funcionario.setOperacaopedidoList1(new ArrayList<Operacaopedido>());
        }
        if (funcionario.getMatriculaanuladaList() == null) {
            funcionario.setMatriculaanuladaList(new ArrayList<Matriculaanulada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipochefia tipochefia = funcionario.getTipochefia();
            if (tipochefia != null) {
                tipochefia = em.getReference(tipochefia.getClass(), tipochefia.getIdfuncionario());
                funcionario.setTipochefia(tipochefia);
            }
            Faculdade faculdade = funcionario.getFaculdade();
            if (faculdade != null) {
                faculdade = em.getReference(faculdade.getClass(), faculdade.getIdFaculdade());
                funcionario.setFaculdade(faculdade);
            }
            List<Notificacao> attachedNotificacaoList = new ArrayList<Notificacao>();
            for (Notificacao notificacaoListNotificacaoToAttach : funcionario.getNotificacaoList()) {
                notificacaoListNotificacaoToAttach = em.getReference(notificacaoListNotificacaoToAttach.getClass(), notificacaoListNotificacaoToAttach.getData());
                attachedNotificacaoList.add(notificacaoListNotificacaoToAttach);
            }
            funcionario.setNotificacaoList(attachedNotificacaoList);
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : funcionario.getUsersList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUtilizador());
                attachedUsersList.add(usersListUsersToAttach);
            }
            funcionario.setUsersList(attachedUsersList);
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : funcionario.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getMatriculaPK());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            funcionario.setMatriculaList(attachedMatriculaList);
            List<Faculdade> attachedFaculdadeList = new ArrayList<Faculdade>();
            for (Faculdade faculdadeListFaculdadeToAttach : funcionario.getFaculdadeList()) {
                faculdadeListFaculdadeToAttach = em.getReference(faculdadeListFaculdadeToAttach.getClass(), faculdadeListFaculdadeToAttach.getIdFaculdade());
                attachedFaculdadeList.add(faculdadeListFaculdadeToAttach);
            }
            funcionario.setFaculdadeList(attachedFaculdadeList);
            List<Pauta> attachedPautaList = new ArrayList<Pauta>();
            for (Pauta pautaListPautaToAttach : funcionario.getPautaList()) {
                pautaListPautaToAttach = em.getReference(pautaListPautaToAttach.getClass(), pautaListPautaToAttach.getPautaPK());
                attachedPautaList.add(pautaListPautaToAttach);
            }
            funcionario.setPautaList(attachedPautaList);
            List<Disciplinaanulada> attachedDisciplinaanuladaList = new ArrayList<Disciplinaanulada>();
            for (Disciplinaanulada disciplinaanuladaListDisciplinaanuladaToAttach : funcionario.getDisciplinaanuladaList()) {
                disciplinaanuladaListDisciplinaanuladaToAttach = em.getReference(disciplinaanuladaListDisciplinaanuladaToAttach.getClass(), disciplinaanuladaListDisciplinaanuladaToAttach.getIdanulacao());
                attachedDisciplinaanuladaList.add(disciplinaanuladaListDisciplinaanuladaToAttach);
            }
            funcionario.setDisciplinaanuladaList(attachedDisciplinaanuladaList);
            List<Operacaopedido> attachedOperacaopedidoList = new ArrayList<Operacaopedido>();
            for (Operacaopedido operacaopedidoListOperacaopedidoToAttach : funcionario.getOperacaopedidoList()) {
                operacaopedidoListOperacaopedidoToAttach = em.getReference(operacaopedidoListOperacaopedidoToAttach.getClass(), operacaopedidoListOperacaopedidoToAttach.getIdpermissao());
                attachedOperacaopedidoList.add(operacaopedidoListOperacaopedidoToAttach);
            }
            funcionario.setOperacaopedidoList(attachedOperacaopedidoList);
            List<Operacaopedido> attachedOperacaopedidoList1 = new ArrayList<Operacaopedido>();
            for (Operacaopedido operacaopedidoList1OperacaopedidoToAttach : funcionario.getOperacaopedidoList1()) {
                operacaopedidoList1OperacaopedidoToAttach = em.getReference(operacaopedidoList1OperacaopedidoToAttach.getClass(), operacaopedidoList1OperacaopedidoToAttach.getIdpermissao());
                attachedOperacaopedidoList1.add(operacaopedidoList1OperacaopedidoToAttach);
            }
            funcionario.setOperacaopedidoList1(attachedOperacaopedidoList1);
            List<Matriculaanulada> attachedMatriculaanuladaList = new ArrayList<Matriculaanulada>();
            for (Matriculaanulada matriculaanuladaListMatriculaanuladaToAttach : funcionario.getMatriculaanuladaList()) {
                matriculaanuladaListMatriculaanuladaToAttach = em.getReference(matriculaanuladaListMatriculaanuladaToAttach.getClass(), matriculaanuladaListMatriculaanuladaToAttach.getMatriculaanuladaPK());
                attachedMatriculaanuladaList.add(matriculaanuladaListMatriculaanuladaToAttach);
            }
            funcionario.setMatriculaanuladaList(attachedMatriculaanuladaList);
            em.persist(funcionario);
            if (tipochefia != null) {
                Funcionario oldFuncionarioOfTipochefia = tipochefia.getFuncionario();
                if (oldFuncionarioOfTipochefia != null) {
                    oldFuncionarioOfTipochefia.setTipochefia(null);
                    oldFuncionarioOfTipochefia = em.merge(oldFuncionarioOfTipochefia);
                }
                tipochefia.setFuncionario(funcionario);
                tipochefia = em.merge(tipochefia);
            }
            if (faculdade != null) {
                faculdade.getFuncionarioList().add(funcionario);
                faculdade = em.merge(faculdade);
            }
            for (Notificacao notificacaoListNotificacao : funcionario.getNotificacaoList()) {
                Funcionario oldIdFuncionarioOfNotificacaoListNotificacao = notificacaoListNotificacao.getIdFuncionario();
                notificacaoListNotificacao.setIdFuncionario(funcionario);
                notificacaoListNotificacao = em.merge(notificacaoListNotificacao);
                if (oldIdFuncionarioOfNotificacaoListNotificacao != null) {
                    oldIdFuncionarioOfNotificacaoListNotificacao.getNotificacaoList().remove(notificacaoListNotificacao);
                    oldIdFuncionarioOfNotificacaoListNotificacao = em.merge(oldIdFuncionarioOfNotificacaoListNotificacao);
                }
            }
            for (Users usersListUsers : funcionario.getUsersList()) {
                Funcionario oldIdFuncionarioOfUsersListUsers = usersListUsers.getIdFuncionario();
                usersListUsers.setIdFuncionario(funcionario);
                usersListUsers = em.merge(usersListUsers);
                if (oldIdFuncionarioOfUsersListUsers != null) {
                    oldIdFuncionarioOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldIdFuncionarioOfUsersListUsers = em.merge(oldIdFuncionarioOfUsersListUsers);
                }
            }
            for (Matricula matriculaListMatricula : funcionario.getMatriculaList()) {
                Funcionario oldFuncionarioOfMatriculaListMatricula = matriculaListMatricula.getFuncionario();
                matriculaListMatricula.setFuncionario(funcionario);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldFuncionarioOfMatriculaListMatricula != null) {
                    oldFuncionarioOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldFuncionarioOfMatriculaListMatricula = em.merge(oldFuncionarioOfMatriculaListMatricula);
                }
            }
            for (Faculdade faculdadeListFaculdade : funcionario.getFaculdadeList()) {
                Funcionario oldDirectorOfFaculdadeListFaculdade = faculdadeListFaculdade.getDirector();
                faculdadeListFaculdade.setDirector(funcionario);
                faculdadeListFaculdade = em.merge(faculdadeListFaculdade);
                if (oldDirectorOfFaculdadeListFaculdade != null) {
                    oldDirectorOfFaculdadeListFaculdade.getFaculdadeList().remove(faculdadeListFaculdade);
                    oldDirectorOfFaculdadeListFaculdade = em.merge(oldDirectorOfFaculdadeListFaculdade);
                }
            }
            for (Pauta pautaListPauta : funcionario.getPautaList()) {
                Funcionario oldFuncionarioOfPautaListPauta = pautaListPauta.getFuncionario();
                pautaListPauta.setFuncionario(funcionario);
                pautaListPauta = em.merge(pautaListPauta);
                if (oldFuncionarioOfPautaListPauta != null) {
                    oldFuncionarioOfPautaListPauta.getPautaList().remove(pautaListPauta);
                    oldFuncionarioOfPautaListPauta = em.merge(oldFuncionarioOfPautaListPauta);
                }
            }
            for (Disciplinaanulada disciplinaanuladaListDisciplinaanulada : funcionario.getDisciplinaanuladaList()) {
                Funcionario oldFuncionarioOfDisciplinaanuladaListDisciplinaanulada = disciplinaanuladaListDisciplinaanulada.getFuncionario();
                disciplinaanuladaListDisciplinaanulada.setFuncionario(funcionario);
                disciplinaanuladaListDisciplinaanulada = em.merge(disciplinaanuladaListDisciplinaanulada);
                if (oldFuncionarioOfDisciplinaanuladaListDisciplinaanulada != null) {
                    oldFuncionarioOfDisciplinaanuladaListDisciplinaanulada.getDisciplinaanuladaList().remove(disciplinaanuladaListDisciplinaanulada);
                    oldFuncionarioOfDisciplinaanuladaListDisciplinaanulada = em.merge(oldFuncionarioOfDisciplinaanuladaListDisciplinaanulada);
                }
            }
            for (Operacaopedido operacaopedidoListOperacaopedido : funcionario.getOperacaopedidoList()) {
                Funcionario oldAutorOfOperacaopedidoListOperacaopedido = operacaopedidoListOperacaopedido.getAutor();
                operacaopedidoListOperacaopedido.setAutor(funcionario);
                operacaopedidoListOperacaopedido = em.merge(operacaopedidoListOperacaopedido);
                if (oldAutorOfOperacaopedidoListOperacaopedido != null) {
                    oldAutorOfOperacaopedidoListOperacaopedido.getOperacaopedidoList().remove(operacaopedidoListOperacaopedido);
                    oldAutorOfOperacaopedidoListOperacaopedido = em.merge(oldAutorOfOperacaopedidoListOperacaopedido);
                }
            }
            for (Operacaopedido operacaopedidoList1Operacaopedido : funcionario.getOperacaopedidoList1()) {
                Funcionario oldFuncionarioOfOperacaopedidoList1Operacaopedido = operacaopedidoList1Operacaopedido.getFuncionario();
                operacaopedidoList1Operacaopedido.setFuncionario(funcionario);
                operacaopedidoList1Operacaopedido = em.merge(operacaopedidoList1Operacaopedido);
                if (oldFuncionarioOfOperacaopedidoList1Operacaopedido != null) {
                    oldFuncionarioOfOperacaopedidoList1Operacaopedido.getOperacaopedidoList1().remove(operacaopedidoList1Operacaopedido);
                    oldFuncionarioOfOperacaopedidoList1Operacaopedido = em.merge(oldFuncionarioOfOperacaopedidoList1Operacaopedido);
                }
            }
            for (Matriculaanulada matriculaanuladaListMatriculaanulada : funcionario.getMatriculaanuladaList()) {
                Funcionario oldFuncionarioOfMatriculaanuladaListMatriculaanulada = matriculaanuladaListMatriculaanulada.getFuncionario();
                matriculaanuladaListMatriculaanulada.setFuncionario(funcionario);
                matriculaanuladaListMatriculaanulada = em.merge(matriculaanuladaListMatriculaanulada);
                if (oldFuncionarioOfMatriculaanuladaListMatriculaanulada != null) {
                    oldFuncionarioOfMatriculaanuladaListMatriculaanulada.getMatriculaanuladaList().remove(matriculaanuladaListMatriculaanulada);
                    oldFuncionarioOfMatriculaanuladaListMatriculaanulada = em.merge(oldFuncionarioOfMatriculaanuladaListMatriculaanulada);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionario funcionario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario persistentFuncionario = em.find(Funcionario.class, funcionario.getIdFuncionario());
            Tipochefia tipochefiaOld = persistentFuncionario.getTipochefia();
            Tipochefia tipochefiaNew = funcionario.getTipochefia();
            Faculdade faculdadeOld = persistentFuncionario.getFaculdade();
            Faculdade faculdadeNew = funcionario.getFaculdade();
            List<Notificacao> notificacaoListOld = persistentFuncionario.getNotificacaoList();
            List<Notificacao> notificacaoListNew = funcionario.getNotificacaoList();
            List<Users> usersListOld = persistentFuncionario.getUsersList();
            List<Users> usersListNew = funcionario.getUsersList();
            List<Matricula> matriculaListOld = persistentFuncionario.getMatriculaList();
            List<Matricula> matriculaListNew = funcionario.getMatriculaList();
            List<Faculdade> faculdadeListOld = persistentFuncionario.getFaculdadeList();
            List<Faculdade> faculdadeListNew = funcionario.getFaculdadeList();
            List<Pauta> pautaListOld = persistentFuncionario.getPautaList();
            List<Pauta> pautaListNew = funcionario.getPautaList();
            List<Disciplinaanulada> disciplinaanuladaListOld = persistentFuncionario.getDisciplinaanuladaList();
            List<Disciplinaanulada> disciplinaanuladaListNew = funcionario.getDisciplinaanuladaList();
            List<Operacaopedido> operacaopedidoListOld = persistentFuncionario.getOperacaopedidoList();
            List<Operacaopedido> operacaopedidoListNew = funcionario.getOperacaopedidoList();
            List<Operacaopedido> operacaopedidoList1Old = persistentFuncionario.getOperacaopedidoList1();
            List<Operacaopedido> operacaopedidoList1New = funcionario.getOperacaopedidoList1();
            List<Matriculaanulada> matriculaanuladaListOld = persistentFuncionario.getMatriculaanuladaList();
            List<Matriculaanulada> matriculaanuladaListNew = funcionario.getMatriculaanuladaList();
            List<String> illegalOrphanMessages = null;
            if (tipochefiaOld != null && !tipochefiaOld.equals(tipochefiaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Tipochefia " + tipochefiaOld + " since its funcionario field is not nullable.");
            }
            for (Notificacao notificacaoListOldNotificacao : notificacaoListOld) {
                if (!notificacaoListNew.contains(notificacaoListOldNotificacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notificacao " + notificacaoListOldNotificacao + " since its idFuncionario field is not nullable.");
                }
            }
            for (Faculdade faculdadeListOldFaculdade : faculdadeListOld) {
                if (!faculdadeListNew.contains(faculdadeListOldFaculdade)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Faculdade " + faculdadeListOldFaculdade + " since its director field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipochefiaNew != null) {
                tipochefiaNew = em.getReference(tipochefiaNew.getClass(), tipochefiaNew.getIdfuncionario());
                funcionario.setTipochefia(tipochefiaNew);
            }
            if (faculdadeNew != null) {
                faculdadeNew = em.getReference(faculdadeNew.getClass(), faculdadeNew.getIdFaculdade());
                funcionario.setFaculdade(faculdadeNew);
            }
            List<Notificacao> attachedNotificacaoListNew = new ArrayList<Notificacao>();
            for (Notificacao notificacaoListNewNotificacaoToAttach : notificacaoListNew) {
                notificacaoListNewNotificacaoToAttach = em.getReference(notificacaoListNewNotificacaoToAttach.getClass(), notificacaoListNewNotificacaoToAttach.getData());
                attachedNotificacaoListNew.add(notificacaoListNewNotificacaoToAttach);
            }
            notificacaoListNew = attachedNotificacaoListNew;
            funcionario.setNotificacaoList(notificacaoListNew);
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUtilizador());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            funcionario.setUsersList(usersListNew);
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getMatriculaPK());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            funcionario.setMatriculaList(matriculaListNew);
            List<Faculdade> attachedFaculdadeListNew = new ArrayList<Faculdade>();
            for (Faculdade faculdadeListNewFaculdadeToAttach : faculdadeListNew) {
                faculdadeListNewFaculdadeToAttach = em.getReference(faculdadeListNewFaculdadeToAttach.getClass(), faculdadeListNewFaculdadeToAttach.getIdFaculdade());
                attachedFaculdadeListNew.add(faculdadeListNewFaculdadeToAttach);
            }
            faculdadeListNew = attachedFaculdadeListNew;
            funcionario.setFaculdadeList(faculdadeListNew);
            List<Pauta> attachedPautaListNew = new ArrayList<Pauta>();
            for (Pauta pautaListNewPautaToAttach : pautaListNew) {
                pautaListNewPautaToAttach = em.getReference(pautaListNewPautaToAttach.getClass(), pautaListNewPautaToAttach.getPautaPK());
                attachedPautaListNew.add(pautaListNewPautaToAttach);
            }
            pautaListNew = attachedPautaListNew;
            funcionario.setPautaList(pautaListNew);
            List<Disciplinaanulada> attachedDisciplinaanuladaListNew = new ArrayList<Disciplinaanulada>();
            for (Disciplinaanulada disciplinaanuladaListNewDisciplinaanuladaToAttach : disciplinaanuladaListNew) {
                disciplinaanuladaListNewDisciplinaanuladaToAttach = em.getReference(disciplinaanuladaListNewDisciplinaanuladaToAttach.getClass(), disciplinaanuladaListNewDisciplinaanuladaToAttach.getIdanulacao());
                attachedDisciplinaanuladaListNew.add(disciplinaanuladaListNewDisciplinaanuladaToAttach);
            }
            disciplinaanuladaListNew = attachedDisciplinaanuladaListNew;
            funcionario.setDisciplinaanuladaList(disciplinaanuladaListNew);
            List<Operacaopedido> attachedOperacaopedidoListNew = new ArrayList<Operacaopedido>();
            for (Operacaopedido operacaopedidoListNewOperacaopedidoToAttach : operacaopedidoListNew) {
                operacaopedidoListNewOperacaopedidoToAttach = em.getReference(operacaopedidoListNewOperacaopedidoToAttach.getClass(), operacaopedidoListNewOperacaopedidoToAttach.getIdpermissao());
                attachedOperacaopedidoListNew.add(operacaopedidoListNewOperacaopedidoToAttach);
            }
            operacaopedidoListNew = attachedOperacaopedidoListNew;
            funcionario.setOperacaopedidoList(operacaopedidoListNew);
            List<Operacaopedido> attachedOperacaopedidoList1New = new ArrayList<Operacaopedido>();
            for (Operacaopedido operacaopedidoList1NewOperacaopedidoToAttach : operacaopedidoList1New) {
                operacaopedidoList1NewOperacaopedidoToAttach = em.getReference(operacaopedidoList1NewOperacaopedidoToAttach.getClass(), operacaopedidoList1NewOperacaopedidoToAttach.getIdpermissao());
                attachedOperacaopedidoList1New.add(operacaopedidoList1NewOperacaopedidoToAttach);
            }
            operacaopedidoList1New = attachedOperacaopedidoList1New;
            funcionario.setOperacaopedidoList1(operacaopedidoList1New);
            List<Matriculaanulada> attachedMatriculaanuladaListNew = new ArrayList<Matriculaanulada>();
            for (Matriculaanulada matriculaanuladaListNewMatriculaanuladaToAttach : matriculaanuladaListNew) {
                matriculaanuladaListNewMatriculaanuladaToAttach = em.getReference(matriculaanuladaListNewMatriculaanuladaToAttach.getClass(), matriculaanuladaListNewMatriculaanuladaToAttach.getMatriculaanuladaPK());
                attachedMatriculaanuladaListNew.add(matriculaanuladaListNewMatriculaanuladaToAttach);
            }
            matriculaanuladaListNew = attachedMatriculaanuladaListNew;
            funcionario.setMatriculaanuladaList(matriculaanuladaListNew);
            funcionario = em.merge(funcionario);
            if (tipochefiaNew != null && !tipochefiaNew.equals(tipochefiaOld)) {
                Funcionario oldFuncionarioOfTipochefia = tipochefiaNew.getFuncionario();
                if (oldFuncionarioOfTipochefia != null) {
                    oldFuncionarioOfTipochefia.setTipochefia(null);
                    oldFuncionarioOfTipochefia = em.merge(oldFuncionarioOfTipochefia);
                }
                tipochefiaNew.setFuncionario(funcionario);
                tipochefiaNew = em.merge(tipochefiaNew);
            }
            if (faculdadeOld != null && !faculdadeOld.equals(faculdadeNew)) {
                faculdadeOld.getFuncionarioList().remove(funcionario);
                faculdadeOld = em.merge(faculdadeOld);
            }
            if (faculdadeNew != null && !faculdadeNew.equals(faculdadeOld)) {
                faculdadeNew.getFuncionarioList().add(funcionario);
                faculdadeNew = em.merge(faculdadeNew);
            }
            for (Notificacao notificacaoListNewNotificacao : notificacaoListNew) {
                if (!notificacaoListOld.contains(notificacaoListNewNotificacao)) {
                    Funcionario oldIdFuncionarioOfNotificacaoListNewNotificacao = notificacaoListNewNotificacao.getIdFuncionario();
                    notificacaoListNewNotificacao.setIdFuncionario(funcionario);
                    notificacaoListNewNotificacao = em.merge(notificacaoListNewNotificacao);
                    if (oldIdFuncionarioOfNotificacaoListNewNotificacao != null && !oldIdFuncionarioOfNotificacaoListNewNotificacao.equals(funcionario)) {
                        oldIdFuncionarioOfNotificacaoListNewNotificacao.getNotificacaoList().remove(notificacaoListNewNotificacao);
                        oldIdFuncionarioOfNotificacaoListNewNotificacao = em.merge(oldIdFuncionarioOfNotificacaoListNewNotificacao);
                    }
                }
            }
            for (Users usersListOldUsers : usersListOld) {
                if (!usersListNew.contains(usersListOldUsers)) {
                    usersListOldUsers.setIdFuncionario(null);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (Users usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    Funcionario oldIdFuncionarioOfUsersListNewUsers = usersListNewUsers.getIdFuncionario();
                    usersListNewUsers.setIdFuncionario(funcionario);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldIdFuncionarioOfUsersListNewUsers != null && !oldIdFuncionarioOfUsersListNewUsers.equals(funcionario)) {
                        oldIdFuncionarioOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldIdFuncionarioOfUsersListNewUsers = em.merge(oldIdFuncionarioOfUsersListNewUsers);
                    }
                }
            }
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    matriculaListOldMatricula.setFuncionario(null);
                    matriculaListOldMatricula = em.merge(matriculaListOldMatricula);
                }
            }
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Funcionario oldFuncionarioOfMatriculaListNewMatricula = matriculaListNewMatricula.getFuncionario();
                    matriculaListNewMatricula.setFuncionario(funcionario);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldFuncionarioOfMatriculaListNewMatricula != null && !oldFuncionarioOfMatriculaListNewMatricula.equals(funcionario)) {
                        oldFuncionarioOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldFuncionarioOfMatriculaListNewMatricula = em.merge(oldFuncionarioOfMatriculaListNewMatricula);
                    }
                }
            }
            for (Faculdade faculdadeListNewFaculdade : faculdadeListNew) {
                if (!faculdadeListOld.contains(faculdadeListNewFaculdade)) {
                    Funcionario oldDirectorOfFaculdadeListNewFaculdade = faculdadeListNewFaculdade.getDirector();
                    faculdadeListNewFaculdade.setDirector(funcionario);
                    faculdadeListNewFaculdade = em.merge(faculdadeListNewFaculdade);
                    if (oldDirectorOfFaculdadeListNewFaculdade != null && !oldDirectorOfFaculdadeListNewFaculdade.equals(funcionario)) {
                        oldDirectorOfFaculdadeListNewFaculdade.getFaculdadeList().remove(faculdadeListNewFaculdade);
                        oldDirectorOfFaculdadeListNewFaculdade = em.merge(oldDirectorOfFaculdadeListNewFaculdade);
                    }
                }
            }
            for (Pauta pautaListOldPauta : pautaListOld) {
                if (!pautaListNew.contains(pautaListOldPauta)) {
                    pautaListOldPauta.setFuncionario(null);
                    pautaListOldPauta = em.merge(pautaListOldPauta);
                }
            }
            for (Pauta pautaListNewPauta : pautaListNew) {
                if (!pautaListOld.contains(pautaListNewPauta)) {
                    Funcionario oldFuncionarioOfPautaListNewPauta = pautaListNewPauta.getFuncionario();
                    pautaListNewPauta.setFuncionario(funcionario);
                    pautaListNewPauta = em.merge(pautaListNewPauta);
                    if (oldFuncionarioOfPautaListNewPauta != null && !oldFuncionarioOfPautaListNewPauta.equals(funcionario)) {
                        oldFuncionarioOfPautaListNewPauta.getPautaList().remove(pautaListNewPauta);
                        oldFuncionarioOfPautaListNewPauta = em.merge(oldFuncionarioOfPautaListNewPauta);
                    }
                }
            }
            for (Disciplinaanulada disciplinaanuladaListOldDisciplinaanulada : disciplinaanuladaListOld) {
                if (!disciplinaanuladaListNew.contains(disciplinaanuladaListOldDisciplinaanulada)) {
                    disciplinaanuladaListOldDisciplinaanulada.setFuncionario(null);
                    disciplinaanuladaListOldDisciplinaanulada = em.merge(disciplinaanuladaListOldDisciplinaanulada);
                }
            }
            for (Disciplinaanulada disciplinaanuladaListNewDisciplinaanulada : disciplinaanuladaListNew) {
                if (!disciplinaanuladaListOld.contains(disciplinaanuladaListNewDisciplinaanulada)) {
                    Funcionario oldFuncionarioOfDisciplinaanuladaListNewDisciplinaanulada = disciplinaanuladaListNewDisciplinaanulada.getFuncionario();
                    disciplinaanuladaListNewDisciplinaanulada.setFuncionario(funcionario);
                    disciplinaanuladaListNewDisciplinaanulada = em.merge(disciplinaanuladaListNewDisciplinaanulada);
                    if (oldFuncionarioOfDisciplinaanuladaListNewDisciplinaanulada != null && !oldFuncionarioOfDisciplinaanuladaListNewDisciplinaanulada.equals(funcionario)) {
                        oldFuncionarioOfDisciplinaanuladaListNewDisciplinaanulada.getDisciplinaanuladaList().remove(disciplinaanuladaListNewDisciplinaanulada);
                        oldFuncionarioOfDisciplinaanuladaListNewDisciplinaanulada = em.merge(oldFuncionarioOfDisciplinaanuladaListNewDisciplinaanulada);
                    }
                }
            }
            for (Operacaopedido operacaopedidoListOldOperacaopedido : operacaopedidoListOld) {
                if (!operacaopedidoListNew.contains(operacaopedidoListOldOperacaopedido)) {
                    operacaopedidoListOldOperacaopedido.setAutor(null);
                    operacaopedidoListOldOperacaopedido = em.merge(operacaopedidoListOldOperacaopedido);
                }
            }
            for (Operacaopedido operacaopedidoListNewOperacaopedido : operacaopedidoListNew) {
                if (!operacaopedidoListOld.contains(operacaopedidoListNewOperacaopedido)) {
                    Funcionario oldAutorOfOperacaopedidoListNewOperacaopedido = operacaopedidoListNewOperacaopedido.getAutor();
                    operacaopedidoListNewOperacaopedido.setAutor(funcionario);
                    operacaopedidoListNewOperacaopedido = em.merge(operacaopedidoListNewOperacaopedido);
                    if (oldAutorOfOperacaopedidoListNewOperacaopedido != null && !oldAutorOfOperacaopedidoListNewOperacaopedido.equals(funcionario)) {
                        oldAutorOfOperacaopedidoListNewOperacaopedido.getOperacaopedidoList().remove(operacaopedidoListNewOperacaopedido);
                        oldAutorOfOperacaopedidoListNewOperacaopedido = em.merge(oldAutorOfOperacaopedidoListNewOperacaopedido);
                    }
                }
            }
            for (Operacaopedido operacaopedidoList1OldOperacaopedido : operacaopedidoList1Old) {
                if (!operacaopedidoList1New.contains(operacaopedidoList1OldOperacaopedido)) {
                    operacaopedidoList1OldOperacaopedido.setFuncionario(null);
                    operacaopedidoList1OldOperacaopedido = em.merge(operacaopedidoList1OldOperacaopedido);
                }
            }
            for (Operacaopedido operacaopedidoList1NewOperacaopedido : operacaopedidoList1New) {
                if (!operacaopedidoList1Old.contains(operacaopedidoList1NewOperacaopedido)) {
                    Funcionario oldFuncionarioOfOperacaopedidoList1NewOperacaopedido = operacaopedidoList1NewOperacaopedido.getFuncionario();
                    operacaopedidoList1NewOperacaopedido.setFuncionario(funcionario);
                    operacaopedidoList1NewOperacaopedido = em.merge(operacaopedidoList1NewOperacaopedido);
                    if (oldFuncionarioOfOperacaopedidoList1NewOperacaopedido != null && !oldFuncionarioOfOperacaopedidoList1NewOperacaopedido.equals(funcionario)) {
                        oldFuncionarioOfOperacaopedidoList1NewOperacaopedido.getOperacaopedidoList1().remove(operacaopedidoList1NewOperacaopedido);
                        oldFuncionarioOfOperacaopedidoList1NewOperacaopedido = em.merge(oldFuncionarioOfOperacaopedidoList1NewOperacaopedido);
                    }
                }
            }
            for (Matriculaanulada matriculaanuladaListOldMatriculaanulada : matriculaanuladaListOld) {
                if (!matriculaanuladaListNew.contains(matriculaanuladaListOldMatriculaanulada)) {
                    matriculaanuladaListOldMatriculaanulada.setFuncionario(null);
                    matriculaanuladaListOldMatriculaanulada = em.merge(matriculaanuladaListOldMatriculaanulada);
                }
            }
            for (Matriculaanulada matriculaanuladaListNewMatriculaanulada : matriculaanuladaListNew) {
                if (!matriculaanuladaListOld.contains(matriculaanuladaListNewMatriculaanulada)) {
                    Funcionario oldFuncionarioOfMatriculaanuladaListNewMatriculaanulada = matriculaanuladaListNewMatriculaanulada.getFuncionario();
                    matriculaanuladaListNewMatriculaanulada.setFuncionario(funcionario);
                    matriculaanuladaListNewMatriculaanulada = em.merge(matriculaanuladaListNewMatriculaanulada);
                    if (oldFuncionarioOfMatriculaanuladaListNewMatriculaanulada != null && !oldFuncionarioOfMatriculaanuladaListNewMatriculaanulada.equals(funcionario)) {
                        oldFuncionarioOfMatriculaanuladaListNewMatriculaanulada.getMatriculaanuladaList().remove(matriculaanuladaListNewMatriculaanulada);
                        oldFuncionarioOfMatriculaanuladaListNewMatriculaanulada = em.merge(oldFuncionarioOfMatriculaanuladaListNewMatriculaanulada);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = funcionario.getIdFuncionario();
                if (findFuncionario(id) == null) {
                    throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.");
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
            Funcionario funcionario;
            try {
                funcionario = em.getReference(Funcionario.class, id);
                funcionario.getIdFuncionario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Tipochefia tipochefiaOrphanCheck = funcionario.getTipochefia();
            if (tipochefiaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the Tipochefia " + tipochefiaOrphanCheck + " in its tipochefia field has a non-nullable funcionario field.");
            }
            List<Notificacao> notificacaoListOrphanCheck = funcionario.getNotificacaoList();
            for (Notificacao notificacaoListOrphanCheckNotificacao : notificacaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the Notificacao " + notificacaoListOrphanCheckNotificacao + " in its notificacaoList field has a non-nullable idFuncionario field.");
            }
            List<Faculdade> faculdadeListOrphanCheck = funcionario.getFaculdadeList();
            for (Faculdade faculdadeListOrphanCheckFaculdade : faculdadeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the Faculdade " + faculdadeListOrphanCheckFaculdade + " in its faculdadeList field has a non-nullable director field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Faculdade faculdade = funcionario.getFaculdade();
            if (faculdade != null) {
                faculdade.getFuncionarioList().remove(funcionario);
                faculdade = em.merge(faculdade);
            }
            List<Users> usersList = funcionario.getUsersList();
            for (Users usersListUsers : usersList) {
                usersListUsers.setIdFuncionario(null);
                usersListUsers = em.merge(usersListUsers);
            }
            List<Matricula> matriculaList = funcionario.getMatriculaList();
            for (Matricula matriculaListMatricula : matriculaList) {
                matriculaListMatricula.setFuncionario(null);
                matriculaListMatricula = em.merge(matriculaListMatricula);
            }
            List<Pauta> pautaList = funcionario.getPautaList();
            for (Pauta pautaListPauta : pautaList) {
                pautaListPauta.setFuncionario(null);
                pautaListPauta = em.merge(pautaListPauta);
            }
            List<Disciplinaanulada> disciplinaanuladaList = funcionario.getDisciplinaanuladaList();
            for (Disciplinaanulada disciplinaanuladaListDisciplinaanulada : disciplinaanuladaList) {
                disciplinaanuladaListDisciplinaanulada.setFuncionario(null);
                disciplinaanuladaListDisciplinaanulada = em.merge(disciplinaanuladaListDisciplinaanulada);
            }
            List<Operacaopedido> operacaopedidoList = funcionario.getOperacaopedidoList();
            for (Operacaopedido operacaopedidoListOperacaopedido : operacaopedidoList) {
                operacaopedidoListOperacaopedido.setAutor(null);
                operacaopedidoListOperacaopedido = em.merge(operacaopedidoListOperacaopedido);
            }
            List<Operacaopedido> operacaopedidoList1 = funcionario.getOperacaopedidoList1();
            for (Operacaopedido operacaopedidoList1Operacaopedido : operacaopedidoList1) {
                operacaopedidoList1Operacaopedido.setFuncionario(null);
                operacaopedidoList1Operacaopedido = em.merge(operacaopedidoList1Operacaopedido);
            }
            List<Matriculaanulada> matriculaanuladaList = funcionario.getMatriculaanuladaList();
            for (Matriculaanulada matriculaanuladaListMatriculaanulada : matriculaanuladaList) {
                matriculaanuladaListMatriculaanulada.setFuncionario(null);
                matriculaanuladaListMatriculaanulada = em.merge(matriculaanuladaListMatriculaanulada);
            }
            em.remove(funcionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionario> findFuncionarioEntities() {
        return findFuncionarioEntities(true, -1, -1);
    }

    public List<Funcionario> findFuncionarioEntities(int maxResults, int firstResult) {
        return findFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Funcionario> findFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
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

    public Funcionario findFuncionario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionario> rt = cq.from(Funcionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
