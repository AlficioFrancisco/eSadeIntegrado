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
import modelo.Faculdade;
import modelo.Ingressotransferencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Mudancacurso;
import modelo.Matricula;
import modelo.Estudante;

/**
 *
 * @author Paulino Francisco
 */
public class CursoJpaController implements Serializable {

    public CursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) {
        if (curso.getIngressotransferenciaList() == null) {
            curso.setIngressotransferenciaList(new ArrayList<Ingressotransferencia>());
        }
        if (curso.getDisciplinaList() == null) {
            curso.setDisciplinaList(new ArrayList<Disciplina>());
        }
        if (curso.getMudancacursoList() == null) {
            curso.setMudancacursoList(new ArrayList<Mudancacurso>());
        }
        if (curso.getMudancacursoList1() == null) {
            curso.setMudancacursoList1(new ArrayList<Mudancacurso>());
        }
        if (curso.getMatriculaList() == null) {
            curso.setMatriculaList(new ArrayList<Matricula>());
        }
        if (curso.getEstudanteList() == null) {
            curso.setEstudanteList(new ArrayList<Estudante>());
        }
        if (curso.getEstudanteList1() == null) {
            curso.setEstudanteList1(new ArrayList<Estudante>());
        }
        if (curso.getEstudanteList2() == null) {
            curso.setEstudanteList2(new ArrayList<Estudante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faculdade faculdade = curso.getFaculdade();
            if (faculdade != null) {
                faculdade = em.getReference(faculdade.getClass(), faculdade.getIdFaculdade());
                curso.setFaculdade(faculdade);
            }
            List<Ingressotransferencia> attachedIngressotransferenciaList = new ArrayList<Ingressotransferencia>();
            for (Ingressotransferencia ingressotransferenciaListIngressotransferenciaToAttach : curso.getIngressotransferenciaList()) {
                ingressotransferenciaListIngressotransferenciaToAttach = em.getReference(ingressotransferenciaListIngressotransferenciaToAttach.getClass(), ingressotransferenciaListIngressotransferenciaToAttach.getIdEstudante());
                attachedIngressotransferenciaList.add(ingressotransferenciaListIngressotransferenciaToAttach);
            }
            curso.setIngressotransferenciaList(attachedIngressotransferenciaList);
            List<Disciplina> attachedDisciplinaList = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListDisciplinaToAttach : curso.getDisciplinaList()) {
                disciplinaListDisciplinaToAttach = em.getReference(disciplinaListDisciplinaToAttach.getClass(), disciplinaListDisciplinaToAttach.getIdDisc());
                attachedDisciplinaList.add(disciplinaListDisciplinaToAttach);
            }
            curso.setDisciplinaList(attachedDisciplinaList);
            List<Mudancacurso> attachedMudancacursoList = new ArrayList<Mudancacurso>();
            for (Mudancacurso mudancacursoListMudancacursoToAttach : curso.getMudancacursoList()) {
                mudancacursoListMudancacursoToAttach = em.getReference(mudancacursoListMudancacursoToAttach.getClass(), mudancacursoListMudancacursoToAttach.getIdmudanca());
                attachedMudancacursoList.add(mudancacursoListMudancacursoToAttach);
            }
            curso.setMudancacursoList(attachedMudancacursoList);
            List<Mudancacurso> attachedMudancacursoList1 = new ArrayList<Mudancacurso>();
            for (Mudancacurso mudancacursoList1MudancacursoToAttach : curso.getMudancacursoList1()) {
                mudancacursoList1MudancacursoToAttach = em.getReference(mudancacursoList1MudancacursoToAttach.getClass(), mudancacursoList1MudancacursoToAttach.getIdmudanca());
                attachedMudancacursoList1.add(mudancacursoList1MudancacursoToAttach);
            }
            curso.setMudancacursoList1(attachedMudancacursoList1);
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : curso.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getMatriculaPK());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            curso.setMatriculaList(attachedMatriculaList);
            List<Estudante> attachedEstudanteList = new ArrayList<Estudante>();
            for (Estudante estudanteListEstudanteToAttach : curso.getEstudanteList()) {
                estudanteListEstudanteToAttach = em.getReference(estudanteListEstudanteToAttach.getClass(), estudanteListEstudanteToAttach.getIdEstudante());
                attachedEstudanteList.add(estudanteListEstudanteToAttach);
            }
            curso.setEstudanteList(attachedEstudanteList);
            List<Estudante> attachedEstudanteList1 = new ArrayList<Estudante>();
            for (Estudante estudanteList1EstudanteToAttach : curso.getEstudanteList1()) {
                estudanteList1EstudanteToAttach = em.getReference(estudanteList1EstudanteToAttach.getClass(), estudanteList1EstudanteToAttach.getIdEstudante());
                attachedEstudanteList1.add(estudanteList1EstudanteToAttach);
            }
            curso.setEstudanteList1(attachedEstudanteList1);
            List<Estudante> attachedEstudanteList2 = new ArrayList<Estudante>();
            for (Estudante estudanteList2EstudanteToAttach : curso.getEstudanteList2()) {
                estudanteList2EstudanteToAttach = em.getReference(estudanteList2EstudanteToAttach.getClass(), estudanteList2EstudanteToAttach.getIdEstudante());
                attachedEstudanteList2.add(estudanteList2EstudanteToAttach);
            }
            curso.setEstudanteList2(attachedEstudanteList2);
            em.persist(curso);
            if (faculdade != null) {
                faculdade.getCursoList().add(curso);
                faculdade = em.merge(faculdade);
            }
            for (Ingressotransferencia ingressotransferenciaListIngressotransferencia : curso.getIngressotransferenciaList()) {
                Curso oldCursoOfIngressotransferenciaListIngressotransferencia = ingressotransferenciaListIngressotransferencia.getCurso();
                ingressotransferenciaListIngressotransferencia.setCurso(curso);
                ingressotransferenciaListIngressotransferencia = em.merge(ingressotransferenciaListIngressotransferencia);
                if (oldCursoOfIngressotransferenciaListIngressotransferencia != null) {
                    oldCursoOfIngressotransferenciaListIngressotransferencia.getIngressotransferenciaList().remove(ingressotransferenciaListIngressotransferencia);
                    oldCursoOfIngressotransferenciaListIngressotransferencia = em.merge(oldCursoOfIngressotransferenciaListIngressotransferencia);
                }
            }
            for (Disciplina disciplinaListDisciplina : curso.getDisciplinaList()) {
                Curso oldCursoOfDisciplinaListDisciplina = disciplinaListDisciplina.getCurso();
                disciplinaListDisciplina.setCurso(curso);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
                if (oldCursoOfDisciplinaListDisciplina != null) {
                    oldCursoOfDisciplinaListDisciplina.getDisciplinaList().remove(disciplinaListDisciplina);
                    oldCursoOfDisciplinaListDisciplina = em.merge(oldCursoOfDisciplinaListDisciplina);
                }
            }
            for (Mudancacurso mudancacursoListMudancacurso : curso.getMudancacursoList()) {
                Curso oldCursoDestinoOfMudancacursoListMudancacurso = mudancacursoListMudancacurso.getCursoDestino();
                mudancacursoListMudancacurso.setCursoDestino(curso);
                mudancacursoListMudancacurso = em.merge(mudancacursoListMudancacurso);
                if (oldCursoDestinoOfMudancacursoListMudancacurso != null) {
                    oldCursoDestinoOfMudancacursoListMudancacurso.getMudancacursoList().remove(mudancacursoListMudancacurso);
                    oldCursoDestinoOfMudancacursoListMudancacurso = em.merge(oldCursoDestinoOfMudancacursoListMudancacurso);
                }
            }
            for (Mudancacurso mudancacursoList1Mudancacurso : curso.getMudancacursoList1()) {
                Curso oldCursoOrigemOfMudancacursoList1Mudancacurso = mudancacursoList1Mudancacurso.getCursoOrigem();
                mudancacursoList1Mudancacurso.setCursoOrigem(curso);
                mudancacursoList1Mudancacurso = em.merge(mudancacursoList1Mudancacurso);
                if (oldCursoOrigemOfMudancacursoList1Mudancacurso != null) {
                    oldCursoOrigemOfMudancacursoList1Mudancacurso.getMudancacursoList1().remove(mudancacursoList1Mudancacurso);
                    oldCursoOrigemOfMudancacursoList1Mudancacurso = em.merge(oldCursoOrigemOfMudancacursoList1Mudancacurso);
                }
            }
            for (Matricula matriculaListMatricula : curso.getMatriculaList()) {
                Curso oldCursoOfMatriculaListMatricula = matriculaListMatricula.getCurso();
                matriculaListMatricula.setCurso(curso);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldCursoOfMatriculaListMatricula != null) {
                    oldCursoOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldCursoOfMatriculaListMatricula = em.merge(oldCursoOfMatriculaListMatricula);
                }
            }
            for (Estudante estudanteListEstudante : curso.getEstudanteList()) {
                Curso oldIdCursoOfEstudanteListEstudante = estudanteListEstudante.getIdCurso();
                estudanteListEstudante.setIdCurso(curso);
                estudanteListEstudante = em.merge(estudanteListEstudante);
                if (oldIdCursoOfEstudanteListEstudante != null) {
                    oldIdCursoOfEstudanteListEstudante.getEstudanteList().remove(estudanteListEstudante);
                    oldIdCursoOfEstudanteListEstudante = em.merge(oldIdCursoOfEstudanteListEstudante);
                }
            }
            for (Estudante estudanteList1Estudante : curso.getEstudanteList1()) {
                Curso oldCursoingressoOfEstudanteList1Estudante = estudanteList1Estudante.getCursoingresso();
                estudanteList1Estudante.setCursoingresso(curso);
                estudanteList1Estudante = em.merge(estudanteList1Estudante);
                if (oldCursoingressoOfEstudanteList1Estudante != null) {
                    oldCursoingressoOfEstudanteList1Estudante.getEstudanteList1().remove(estudanteList1Estudante);
                    oldCursoingressoOfEstudanteList1Estudante = em.merge(oldCursoingressoOfEstudanteList1Estudante);
                }
            }
            for (Estudante estudanteList2Estudante : curso.getEstudanteList2()) {
                Curso oldCursocurrenteOfEstudanteList2Estudante = estudanteList2Estudante.getCursocurrente();
                estudanteList2Estudante.setCursocurrente(curso);
                estudanteList2Estudante = em.merge(estudanteList2Estudante);
                if (oldCursocurrenteOfEstudanteList2Estudante != null) {
                    oldCursocurrenteOfEstudanteList2Estudante.getEstudanteList2().remove(estudanteList2Estudante);
                    oldCursocurrenteOfEstudanteList2Estudante = em.merge(oldCursocurrenteOfEstudanteList2Estudante);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Curso curso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso persistentCurso = em.find(Curso.class, curso.getIdCurso());
            Faculdade faculdadeOld = persistentCurso.getFaculdade();
            Faculdade faculdadeNew = curso.getFaculdade();
            List<Ingressotransferencia> ingressotransferenciaListOld = persistentCurso.getIngressotransferenciaList();
            List<Ingressotransferencia> ingressotransferenciaListNew = curso.getIngressotransferenciaList();
            List<Disciplina> disciplinaListOld = persistentCurso.getDisciplinaList();
            List<Disciplina> disciplinaListNew = curso.getDisciplinaList();
            List<Mudancacurso> mudancacursoListOld = persistentCurso.getMudancacursoList();
            List<Mudancacurso> mudancacursoListNew = curso.getMudancacursoList();
            List<Mudancacurso> mudancacursoList1Old = persistentCurso.getMudancacursoList1();
            List<Mudancacurso> mudancacursoList1New = curso.getMudancacursoList1();
            List<Matricula> matriculaListOld = persistentCurso.getMatriculaList();
            List<Matricula> matriculaListNew = curso.getMatriculaList();
            List<Estudante> estudanteListOld = persistentCurso.getEstudanteList();
            List<Estudante> estudanteListNew = curso.getEstudanteList();
            List<Estudante> estudanteList1Old = persistentCurso.getEstudanteList1();
            List<Estudante> estudanteList1New = curso.getEstudanteList1();
            List<Estudante> estudanteList2Old = persistentCurso.getEstudanteList2();
            List<Estudante> estudanteList2New = curso.getEstudanteList2();
            List<String> illegalOrphanMessages = null;
            for (Ingressotransferencia ingressotransferenciaListOldIngressotransferencia : ingressotransferenciaListOld) {
                if (!ingressotransferenciaListNew.contains(ingressotransferenciaListOldIngressotransferencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ingressotransferencia " + ingressotransferenciaListOldIngressotransferencia + " since its curso field is not nullable.");
                }
            }
            for (Mudancacurso mudancacursoListOldMudancacurso : mudancacursoListOld) {
                if (!mudancacursoListNew.contains(mudancacursoListOldMudancacurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mudancacurso " + mudancacursoListOldMudancacurso + " since its cursoDestino field is not nullable.");
                }
            }
            for (Mudancacurso mudancacursoList1OldMudancacurso : mudancacursoList1Old) {
                if (!mudancacursoList1New.contains(mudancacursoList1OldMudancacurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mudancacurso " + mudancacursoList1OldMudancacurso + " since its cursoOrigem field is not nullable.");
                }
            }
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matricula " + matriculaListOldMatricula + " since its curso field is not nullable.");
                }
            }
            for (Estudante estudanteList1OldEstudante : estudanteList1Old) {
                if (!estudanteList1New.contains(estudanteList1OldEstudante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudante " + estudanteList1OldEstudante + " since its cursoingresso field is not nullable.");
                }
            }
            for (Estudante estudanteList2OldEstudante : estudanteList2Old) {
                if (!estudanteList2New.contains(estudanteList2OldEstudante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estudante " + estudanteList2OldEstudante + " since its cursocurrente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (faculdadeNew != null) {
                faculdadeNew = em.getReference(faculdadeNew.getClass(), faculdadeNew.getIdFaculdade());
                curso.setFaculdade(faculdadeNew);
            }
            List<Ingressotransferencia> attachedIngressotransferenciaListNew = new ArrayList<Ingressotransferencia>();
            for (Ingressotransferencia ingressotransferenciaListNewIngressotransferenciaToAttach : ingressotransferenciaListNew) {
                ingressotransferenciaListNewIngressotransferenciaToAttach = em.getReference(ingressotransferenciaListNewIngressotransferenciaToAttach.getClass(), ingressotransferenciaListNewIngressotransferenciaToAttach.getIdEstudante());
                attachedIngressotransferenciaListNew.add(ingressotransferenciaListNewIngressotransferenciaToAttach);
            }
            ingressotransferenciaListNew = attachedIngressotransferenciaListNew;
            curso.setIngressotransferenciaList(ingressotransferenciaListNew);
            List<Disciplina> attachedDisciplinaListNew = new ArrayList<Disciplina>();
            for (Disciplina disciplinaListNewDisciplinaToAttach : disciplinaListNew) {
                disciplinaListNewDisciplinaToAttach = em.getReference(disciplinaListNewDisciplinaToAttach.getClass(), disciplinaListNewDisciplinaToAttach.getIdDisc());
                attachedDisciplinaListNew.add(disciplinaListNewDisciplinaToAttach);
            }
            disciplinaListNew = attachedDisciplinaListNew;
            curso.setDisciplinaList(disciplinaListNew);
            List<Mudancacurso> attachedMudancacursoListNew = new ArrayList<Mudancacurso>();
            for (Mudancacurso mudancacursoListNewMudancacursoToAttach : mudancacursoListNew) {
                mudancacursoListNewMudancacursoToAttach = em.getReference(mudancacursoListNewMudancacursoToAttach.getClass(), mudancacursoListNewMudancacursoToAttach.getIdmudanca());
                attachedMudancacursoListNew.add(mudancacursoListNewMudancacursoToAttach);
            }
            mudancacursoListNew = attachedMudancacursoListNew;
            curso.setMudancacursoList(mudancacursoListNew);
            List<Mudancacurso> attachedMudancacursoList1New = new ArrayList<Mudancacurso>();
            for (Mudancacurso mudancacursoList1NewMudancacursoToAttach : mudancacursoList1New) {
                mudancacursoList1NewMudancacursoToAttach = em.getReference(mudancacursoList1NewMudancacursoToAttach.getClass(), mudancacursoList1NewMudancacursoToAttach.getIdmudanca());
                attachedMudancacursoList1New.add(mudancacursoList1NewMudancacursoToAttach);
            }
            mudancacursoList1New = attachedMudancacursoList1New;
            curso.setMudancacursoList1(mudancacursoList1New);
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getMatriculaPK());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            curso.setMatriculaList(matriculaListNew);
            List<Estudante> attachedEstudanteListNew = new ArrayList<Estudante>();
            for (Estudante estudanteListNewEstudanteToAttach : estudanteListNew) {
                estudanteListNewEstudanteToAttach = em.getReference(estudanteListNewEstudanteToAttach.getClass(), estudanteListNewEstudanteToAttach.getIdEstudante());
                attachedEstudanteListNew.add(estudanteListNewEstudanteToAttach);
            }
            estudanteListNew = attachedEstudanteListNew;
            curso.setEstudanteList(estudanteListNew);
            List<Estudante> attachedEstudanteList1New = new ArrayList<Estudante>();
            for (Estudante estudanteList1NewEstudanteToAttach : estudanteList1New) {
                estudanteList1NewEstudanteToAttach = em.getReference(estudanteList1NewEstudanteToAttach.getClass(), estudanteList1NewEstudanteToAttach.getIdEstudante());
                attachedEstudanteList1New.add(estudanteList1NewEstudanteToAttach);
            }
            estudanteList1New = attachedEstudanteList1New;
            curso.setEstudanteList1(estudanteList1New);
            List<Estudante> attachedEstudanteList2New = new ArrayList<Estudante>();
            for (Estudante estudanteList2NewEstudanteToAttach : estudanteList2New) {
                estudanteList2NewEstudanteToAttach = em.getReference(estudanteList2NewEstudanteToAttach.getClass(), estudanteList2NewEstudanteToAttach.getIdEstudante());
                attachedEstudanteList2New.add(estudanteList2NewEstudanteToAttach);
            }
            estudanteList2New = attachedEstudanteList2New;
            curso.setEstudanteList2(estudanteList2New);
            curso = em.merge(curso);
            if (faculdadeOld != null && !faculdadeOld.equals(faculdadeNew)) {
                faculdadeOld.getCursoList().remove(curso);
                faculdadeOld = em.merge(faculdadeOld);
            }
            if (faculdadeNew != null && !faculdadeNew.equals(faculdadeOld)) {
                faculdadeNew.getCursoList().add(curso);
                faculdadeNew = em.merge(faculdadeNew);
            }
            for (Ingressotransferencia ingressotransferenciaListNewIngressotransferencia : ingressotransferenciaListNew) {
                if (!ingressotransferenciaListOld.contains(ingressotransferenciaListNewIngressotransferencia)) {
                    Curso oldCursoOfIngressotransferenciaListNewIngressotransferencia = ingressotransferenciaListNewIngressotransferencia.getCurso();
                    ingressotransferenciaListNewIngressotransferencia.setCurso(curso);
                    ingressotransferenciaListNewIngressotransferencia = em.merge(ingressotransferenciaListNewIngressotransferencia);
                    if (oldCursoOfIngressotransferenciaListNewIngressotransferencia != null && !oldCursoOfIngressotransferenciaListNewIngressotransferencia.equals(curso)) {
                        oldCursoOfIngressotransferenciaListNewIngressotransferencia.getIngressotransferenciaList().remove(ingressotransferenciaListNewIngressotransferencia);
                        oldCursoOfIngressotransferenciaListNewIngressotransferencia = em.merge(oldCursoOfIngressotransferenciaListNewIngressotransferencia);
                    }
                }
            }
            for (Disciplina disciplinaListOldDisciplina : disciplinaListOld) {
                if (!disciplinaListNew.contains(disciplinaListOldDisciplina)) {
                    disciplinaListOldDisciplina.setCurso(null);
                    disciplinaListOldDisciplina = em.merge(disciplinaListOldDisciplina);
                }
            }
            for (Disciplina disciplinaListNewDisciplina : disciplinaListNew) {
                if (!disciplinaListOld.contains(disciplinaListNewDisciplina)) {
                    Curso oldCursoOfDisciplinaListNewDisciplina = disciplinaListNewDisciplina.getCurso();
                    disciplinaListNewDisciplina.setCurso(curso);
                    disciplinaListNewDisciplina = em.merge(disciplinaListNewDisciplina);
                    if (oldCursoOfDisciplinaListNewDisciplina != null && !oldCursoOfDisciplinaListNewDisciplina.equals(curso)) {
                        oldCursoOfDisciplinaListNewDisciplina.getDisciplinaList().remove(disciplinaListNewDisciplina);
                        oldCursoOfDisciplinaListNewDisciplina = em.merge(oldCursoOfDisciplinaListNewDisciplina);
                    }
                }
            }
            for (Mudancacurso mudancacursoListNewMudancacurso : mudancacursoListNew) {
                if (!mudancacursoListOld.contains(mudancacursoListNewMudancacurso)) {
                    Curso oldCursoDestinoOfMudancacursoListNewMudancacurso = mudancacursoListNewMudancacurso.getCursoDestino();
                    mudancacursoListNewMudancacurso.setCursoDestino(curso);
                    mudancacursoListNewMudancacurso = em.merge(mudancacursoListNewMudancacurso);
                    if (oldCursoDestinoOfMudancacursoListNewMudancacurso != null && !oldCursoDestinoOfMudancacursoListNewMudancacurso.equals(curso)) {
                        oldCursoDestinoOfMudancacursoListNewMudancacurso.getMudancacursoList().remove(mudancacursoListNewMudancacurso);
                        oldCursoDestinoOfMudancacursoListNewMudancacurso = em.merge(oldCursoDestinoOfMudancacursoListNewMudancacurso);
                    }
                }
            }
            for (Mudancacurso mudancacursoList1NewMudancacurso : mudancacursoList1New) {
                if (!mudancacursoList1Old.contains(mudancacursoList1NewMudancacurso)) {
                    Curso oldCursoOrigemOfMudancacursoList1NewMudancacurso = mudancacursoList1NewMudancacurso.getCursoOrigem();
                    mudancacursoList1NewMudancacurso.setCursoOrigem(curso);
                    mudancacursoList1NewMudancacurso = em.merge(mudancacursoList1NewMudancacurso);
                    if (oldCursoOrigemOfMudancacursoList1NewMudancacurso != null && !oldCursoOrigemOfMudancacursoList1NewMudancacurso.equals(curso)) {
                        oldCursoOrigemOfMudancacursoList1NewMudancacurso.getMudancacursoList1().remove(mudancacursoList1NewMudancacurso);
                        oldCursoOrigemOfMudancacursoList1NewMudancacurso = em.merge(oldCursoOrigemOfMudancacursoList1NewMudancacurso);
                    }
                }
            }
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Curso oldCursoOfMatriculaListNewMatricula = matriculaListNewMatricula.getCurso();
                    matriculaListNewMatricula.setCurso(curso);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldCursoOfMatriculaListNewMatricula != null && !oldCursoOfMatriculaListNewMatricula.equals(curso)) {
                        oldCursoOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldCursoOfMatriculaListNewMatricula = em.merge(oldCursoOfMatriculaListNewMatricula);
                    }
                }
            }
            for (Estudante estudanteListOldEstudante : estudanteListOld) {
                if (!estudanteListNew.contains(estudanteListOldEstudante)) {
                    estudanteListOldEstudante.setIdCurso(null);
                    estudanteListOldEstudante = em.merge(estudanteListOldEstudante);
                }
            }
            for (Estudante estudanteListNewEstudante : estudanteListNew) {
                if (!estudanteListOld.contains(estudanteListNewEstudante)) {
                    Curso oldIdCursoOfEstudanteListNewEstudante = estudanteListNewEstudante.getIdCurso();
                    estudanteListNewEstudante.setIdCurso(curso);
                    estudanteListNewEstudante = em.merge(estudanteListNewEstudante);
                    if (oldIdCursoOfEstudanteListNewEstudante != null && !oldIdCursoOfEstudanteListNewEstudante.equals(curso)) {
                        oldIdCursoOfEstudanteListNewEstudante.getEstudanteList().remove(estudanteListNewEstudante);
                        oldIdCursoOfEstudanteListNewEstudante = em.merge(oldIdCursoOfEstudanteListNewEstudante);
                    }
                }
            }
            for (Estudante estudanteList1NewEstudante : estudanteList1New) {
                if (!estudanteList1Old.contains(estudanteList1NewEstudante)) {
                    Curso oldCursoingressoOfEstudanteList1NewEstudante = estudanteList1NewEstudante.getCursoingresso();
                    estudanteList1NewEstudante.setCursoingresso(curso);
                    estudanteList1NewEstudante = em.merge(estudanteList1NewEstudante);
                    if (oldCursoingressoOfEstudanteList1NewEstudante != null && !oldCursoingressoOfEstudanteList1NewEstudante.equals(curso)) {
                        oldCursoingressoOfEstudanteList1NewEstudante.getEstudanteList1().remove(estudanteList1NewEstudante);
                        oldCursoingressoOfEstudanteList1NewEstudante = em.merge(oldCursoingressoOfEstudanteList1NewEstudante);
                    }
                }
            }
            for (Estudante estudanteList2NewEstudante : estudanteList2New) {
                if (!estudanteList2Old.contains(estudanteList2NewEstudante)) {
                    Curso oldCursocurrenteOfEstudanteList2NewEstudante = estudanteList2NewEstudante.getCursocurrente();
                    estudanteList2NewEstudante.setCursocurrente(curso);
                    estudanteList2NewEstudante = em.merge(estudanteList2NewEstudante);
                    if (oldCursocurrenteOfEstudanteList2NewEstudante != null && !oldCursocurrenteOfEstudanteList2NewEstudante.equals(curso)) {
                        oldCursocurrenteOfEstudanteList2NewEstudante.getEstudanteList2().remove(estudanteList2NewEstudante);
                        oldCursocurrenteOfEstudanteList2NewEstudante = em.merge(oldCursocurrenteOfEstudanteList2NewEstudante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = curso.getIdCurso();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getIdCurso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ingressotransferencia> ingressotransferenciaListOrphanCheck = curso.getIngressotransferenciaList();
            for (Ingressotransferencia ingressotransferenciaListOrphanCheckIngressotransferencia : ingressotransferenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the Ingressotransferencia " + ingressotransferenciaListOrphanCheckIngressotransferencia + " in its ingressotransferenciaList field has a non-nullable curso field.");
            }
            List<Mudancacurso> mudancacursoListOrphanCheck = curso.getMudancacursoList();
            for (Mudancacurso mudancacursoListOrphanCheckMudancacurso : mudancacursoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the Mudancacurso " + mudancacursoListOrphanCheckMudancacurso + " in its mudancacursoList field has a non-nullable cursoDestino field.");
            }
            List<Mudancacurso> mudancacursoList1OrphanCheck = curso.getMudancacursoList1();
            for (Mudancacurso mudancacursoList1OrphanCheckMudancacurso : mudancacursoList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the Mudancacurso " + mudancacursoList1OrphanCheckMudancacurso + " in its mudancacursoList1 field has a non-nullable cursoOrigem field.");
            }
            List<Matricula> matriculaListOrphanCheck = curso.getMatriculaList();
            for (Matricula matriculaListOrphanCheckMatricula : matriculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the Matricula " + matriculaListOrphanCheckMatricula + " in its matriculaList field has a non-nullable curso field.");
            }
            List<Estudante> estudanteList1OrphanCheck = curso.getEstudanteList1();
            for (Estudante estudanteList1OrphanCheckEstudante : estudanteList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the Estudante " + estudanteList1OrphanCheckEstudante + " in its estudanteList1 field has a non-nullable cursoingresso field.");
            }
            List<Estudante> estudanteList2OrphanCheck = curso.getEstudanteList2();
            for (Estudante estudanteList2OrphanCheckEstudante : estudanteList2OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Curso (" + curso + ") cannot be destroyed since the Estudante " + estudanteList2OrphanCheckEstudante + " in its estudanteList2 field has a non-nullable cursocurrente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Faculdade faculdade = curso.getFaculdade();
            if (faculdade != null) {
                faculdade.getCursoList().remove(curso);
                faculdade = em.merge(faculdade);
            }
            List<Disciplina> disciplinaList = curso.getDisciplinaList();
            for (Disciplina disciplinaListDisciplina : disciplinaList) {
                disciplinaListDisciplina.setCurso(null);
                disciplinaListDisciplina = em.merge(disciplinaListDisciplina);
            }
            List<Estudante> estudanteList = curso.getEstudanteList();
            for (Estudante estudanteListEstudante : estudanteList) {
                estudanteListEstudante.setIdCurso(null);
                estudanteListEstudante = em.merge(estudanteListEstudante);
            }
            em.remove(curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
