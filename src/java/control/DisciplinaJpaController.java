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
import modelo.Areacientifica;
import modelo.Caracter;
import modelo.Curso;
import modelo.Periodo;
import modelo.Precedencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Disciplina;
import modelo.Planoavaliacao;
import modelo.Lecciona;
import modelo.Pauta;

/**
 *
 * @author Paulino Francisco
 */
public class DisciplinaJpaController implements Serializable {

    public DisciplinaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Disciplina disciplina) {
        if (disciplina.getPrecedenciaList() == null) {
            disciplina.setPrecedenciaList(new ArrayList<Precedencia>());
        }
        if (disciplina.getPrecedenciaList1() == null) {
            disciplina.setPrecedenciaList1(new ArrayList<Precedencia>());
        }
        if (disciplina.getPlanoavaliacaoList() == null) {
            disciplina.setPlanoavaliacaoList(new ArrayList<Planoavaliacao>());
        }
        if (disciplina.getLeccionaList() == null) {
            disciplina.setLeccionaList(new ArrayList<Lecciona>());
        }
        if (disciplina.getPautaList() == null) {
            disciplina.setPautaList(new ArrayList<Pauta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Areacientifica areaCientifica = disciplina.getAreaCientifica();
            if (areaCientifica != null) {
                areaCientifica = em.getReference(areaCientifica.getClass(), areaCientifica.getIdarea());
                disciplina.setAreaCientifica(areaCientifica);
            }
            Caracter caracter = disciplina.getCaracter();
            if (caracter != null) {
                caracter = em.getReference(caracter.getClass(), caracter.getIdCaracter());
                disciplina.setCaracter(caracter);
            }
            Curso curso = disciplina.getCurso();
            if (curso != null) {
                curso = em.getReference(curso.getClass(), curso.getIdCurso());
                disciplina.setCurso(curso);
            }
            Periodo idPeriodo = disciplina.getIdPeriodo();
            if (idPeriodo != null) {
                idPeriodo = em.getReference(idPeriodo.getClass(), idPeriodo.getIdPeriodo());
                disciplina.setIdPeriodo(idPeriodo);
            }
            List<Precedencia> attachedPrecedenciaList = new ArrayList<Precedencia>();
            for (Precedencia precedenciaListPrecedenciaToAttach : disciplina.getPrecedenciaList()) {
                precedenciaListPrecedenciaToAttach = em.getReference(precedenciaListPrecedenciaToAttach.getClass(), precedenciaListPrecedenciaToAttach.getPrecedenciaPK());
                attachedPrecedenciaList.add(precedenciaListPrecedenciaToAttach);
            }
            disciplina.setPrecedenciaList(attachedPrecedenciaList);
            List<Precedencia> attachedPrecedenciaList1 = new ArrayList<Precedencia>();
            for (Precedencia precedenciaList1PrecedenciaToAttach : disciplina.getPrecedenciaList1()) {
                precedenciaList1PrecedenciaToAttach = em.getReference(precedenciaList1PrecedenciaToAttach.getClass(), precedenciaList1PrecedenciaToAttach.getPrecedenciaPK());
                attachedPrecedenciaList1.add(precedenciaList1PrecedenciaToAttach);
            }
            disciplina.setPrecedenciaList1(attachedPrecedenciaList1);
            List<Planoavaliacao> attachedPlanoavaliacaoList = new ArrayList<Planoavaliacao>();
            for (Planoavaliacao planoavaliacaoListPlanoavaliacaoToAttach : disciplina.getPlanoavaliacaoList()) {
                planoavaliacaoListPlanoavaliacaoToAttach = em.getReference(planoavaliacaoListPlanoavaliacaoToAttach.getClass(), planoavaliacaoListPlanoavaliacaoToAttach.getPlanoavaliacaoPK());
                attachedPlanoavaliacaoList.add(planoavaliacaoListPlanoavaliacaoToAttach);
            }
            disciplina.setPlanoavaliacaoList(attachedPlanoavaliacaoList);
            List<Lecciona> attachedLeccionaList = new ArrayList<Lecciona>();
            for (Lecciona leccionaListLeccionaToAttach : disciplina.getLeccionaList()) {
                leccionaListLeccionaToAttach = em.getReference(leccionaListLeccionaToAttach.getClass(), leccionaListLeccionaToAttach.getLeccionaPK());
                attachedLeccionaList.add(leccionaListLeccionaToAttach);
            }
            disciplina.setLeccionaList(attachedLeccionaList);
            List<Pauta> attachedPautaList = new ArrayList<Pauta>();
            for (Pauta pautaListPautaToAttach : disciplina.getPautaList()) {
                pautaListPautaToAttach = em.getReference(pautaListPautaToAttach.getClass(), pautaListPautaToAttach.getPautaPK());
                attachedPautaList.add(pautaListPautaToAttach);
            }
            disciplina.setPautaList(attachedPautaList);
            em.persist(disciplina);
            if (areaCientifica != null) {
                areaCientifica.getDisciplinaList().add(disciplina);
                areaCientifica = em.merge(areaCientifica);
            }
            if (caracter != null) {
                caracter.getDisciplinaList().add(disciplina);
                caracter = em.merge(caracter);
            }
            if (curso != null) {
                curso.getDisciplinaList().add(disciplina);
                curso = em.merge(curso);
            }
            if (idPeriodo != null) {
                idPeriodo.getDisciplinaList().add(disciplina);
                idPeriodo = em.merge(idPeriodo);
            }
            for (Precedencia precedenciaListPrecedencia : disciplina.getPrecedenciaList()) {
                Disciplina oldDisciplinaOfPrecedenciaListPrecedencia = precedenciaListPrecedencia.getDisciplina();
                precedenciaListPrecedencia.setDisciplina(disciplina);
                precedenciaListPrecedencia = em.merge(precedenciaListPrecedencia);
                if (oldDisciplinaOfPrecedenciaListPrecedencia != null) {
                    oldDisciplinaOfPrecedenciaListPrecedencia.getPrecedenciaList().remove(precedenciaListPrecedencia);
                    oldDisciplinaOfPrecedenciaListPrecedencia = em.merge(oldDisciplinaOfPrecedenciaListPrecedencia);
                }
            }
            for (Precedencia precedenciaList1Precedencia : disciplina.getPrecedenciaList1()) {
                Disciplina oldDisciplina1OfPrecedenciaList1Precedencia = precedenciaList1Precedencia.getDisciplina1();
                precedenciaList1Precedencia.setDisciplina1(disciplina);
                precedenciaList1Precedencia = em.merge(precedenciaList1Precedencia);
                if (oldDisciplina1OfPrecedenciaList1Precedencia != null) {
                    oldDisciplina1OfPrecedenciaList1Precedencia.getPrecedenciaList1().remove(precedenciaList1Precedencia);
                    oldDisciplina1OfPrecedenciaList1Precedencia = em.merge(oldDisciplina1OfPrecedenciaList1Precedencia);
                }
            }
            for (Planoavaliacao planoavaliacaoListPlanoavaliacao : disciplina.getPlanoavaliacaoList()) {
                Disciplina oldDisciplinaOfPlanoavaliacaoListPlanoavaliacao = planoavaliacaoListPlanoavaliacao.getDisciplina();
                planoavaliacaoListPlanoavaliacao.setDisciplina(disciplina);
                planoavaliacaoListPlanoavaliacao = em.merge(planoavaliacaoListPlanoavaliacao);
                if (oldDisciplinaOfPlanoavaliacaoListPlanoavaliacao != null) {
                    oldDisciplinaOfPlanoavaliacaoListPlanoavaliacao.getPlanoavaliacaoList().remove(planoavaliacaoListPlanoavaliacao);
                    oldDisciplinaOfPlanoavaliacaoListPlanoavaliacao = em.merge(oldDisciplinaOfPlanoavaliacaoListPlanoavaliacao);
                }
            }
            for (Lecciona leccionaListLecciona : disciplina.getLeccionaList()) {
                Disciplina oldDisciplinaOfLeccionaListLecciona = leccionaListLecciona.getDisciplina();
                leccionaListLecciona.setDisciplina(disciplina);
                leccionaListLecciona = em.merge(leccionaListLecciona);
                if (oldDisciplinaOfLeccionaListLecciona != null) {
                    oldDisciplinaOfLeccionaListLecciona.getLeccionaList().remove(leccionaListLecciona);
                    oldDisciplinaOfLeccionaListLecciona = em.merge(oldDisciplinaOfLeccionaListLecciona);
                }
            }
            for (Pauta pautaListPauta : disciplina.getPautaList()) {
                Disciplina oldDisciplinaOfPautaListPauta = pautaListPauta.getDisciplina();
                pautaListPauta.setDisciplina(disciplina);
                pautaListPauta = em.merge(pautaListPauta);
                if (oldDisciplinaOfPautaListPauta != null) {
                    oldDisciplinaOfPautaListPauta.getPautaList().remove(pautaListPauta);
                    oldDisciplinaOfPautaListPauta = em.merge(oldDisciplinaOfPautaListPauta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Disciplina disciplina) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disciplina persistentDisciplina = em.find(Disciplina.class, disciplina.getIdDisc());
            Areacientifica areaCientificaOld = persistentDisciplina.getAreaCientifica();
            Areacientifica areaCientificaNew = disciplina.getAreaCientifica();
            Caracter caracterOld = persistentDisciplina.getCaracter();
            Caracter caracterNew = disciplina.getCaracter();
            Curso cursoOld = persistentDisciplina.getCurso();
            Curso cursoNew = disciplina.getCurso();
            Periodo idPeriodoOld = persistentDisciplina.getIdPeriodo();
            Periodo idPeriodoNew = disciplina.getIdPeriodo();
            List<Precedencia> precedenciaListOld = persistentDisciplina.getPrecedenciaList();
            List<Precedencia> precedenciaListNew = disciplina.getPrecedenciaList();
            List<Precedencia> precedenciaList1Old = persistentDisciplina.getPrecedenciaList1();
            List<Precedencia> precedenciaList1New = disciplina.getPrecedenciaList1();
            List<Planoavaliacao> planoavaliacaoListOld = persistentDisciplina.getPlanoavaliacaoList();
            List<Planoavaliacao> planoavaliacaoListNew = disciplina.getPlanoavaliacaoList();
            List<Lecciona> leccionaListOld = persistentDisciplina.getLeccionaList();
            List<Lecciona> leccionaListNew = disciplina.getLeccionaList();
            List<Pauta> pautaListOld = persistentDisciplina.getPautaList();
            List<Pauta> pautaListNew = disciplina.getPautaList();
            List<String> illegalOrphanMessages = null;
            for (Precedencia precedenciaListOldPrecedencia : precedenciaListOld) {
                if (!precedenciaListNew.contains(precedenciaListOldPrecedencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Precedencia " + precedenciaListOldPrecedencia + " since its disciplina field is not nullable.");
                }
            }
            for (Precedencia precedenciaList1OldPrecedencia : precedenciaList1Old) {
                if (!precedenciaList1New.contains(precedenciaList1OldPrecedencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Precedencia " + precedenciaList1OldPrecedencia + " since its disciplina1 field is not nullable.");
                }
            }
            for (Planoavaliacao planoavaliacaoListOldPlanoavaliacao : planoavaliacaoListOld) {
                if (!planoavaliacaoListNew.contains(planoavaliacaoListOldPlanoavaliacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Planoavaliacao " + planoavaliacaoListOldPlanoavaliacao + " since its disciplina field is not nullable.");
                }
            }
            for (Lecciona leccionaListOldLecciona : leccionaListOld) {
                if (!leccionaListNew.contains(leccionaListOldLecciona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lecciona " + leccionaListOldLecciona + " since its disciplina field is not nullable.");
                }
            }
            for (Pauta pautaListOldPauta : pautaListOld) {
                if (!pautaListNew.contains(pautaListOldPauta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pauta " + pautaListOldPauta + " since its disciplina field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (areaCientificaNew != null) {
                areaCientificaNew = em.getReference(areaCientificaNew.getClass(), areaCientificaNew.getIdarea());
                disciplina.setAreaCientifica(areaCientificaNew);
            }
            if (caracterNew != null) {
                caracterNew = em.getReference(caracterNew.getClass(), caracterNew.getIdCaracter());
                disciplina.setCaracter(caracterNew);
            }
            if (cursoNew != null) {
                cursoNew = em.getReference(cursoNew.getClass(), cursoNew.getIdCurso());
                disciplina.setCurso(cursoNew);
            }
            if (idPeriodoNew != null) {
                idPeriodoNew = em.getReference(idPeriodoNew.getClass(), idPeriodoNew.getIdPeriodo());
                disciplina.setIdPeriodo(idPeriodoNew);
            }
            List<Precedencia> attachedPrecedenciaListNew = new ArrayList<Precedencia>();
            for (Precedencia precedenciaListNewPrecedenciaToAttach : precedenciaListNew) {
                precedenciaListNewPrecedenciaToAttach = em.getReference(precedenciaListNewPrecedenciaToAttach.getClass(), precedenciaListNewPrecedenciaToAttach.getPrecedenciaPK());
                attachedPrecedenciaListNew.add(precedenciaListNewPrecedenciaToAttach);
            }
            precedenciaListNew = attachedPrecedenciaListNew;
            disciplina.setPrecedenciaList(precedenciaListNew);
            List<Precedencia> attachedPrecedenciaList1New = new ArrayList<Precedencia>();
            for (Precedencia precedenciaList1NewPrecedenciaToAttach : precedenciaList1New) {
                precedenciaList1NewPrecedenciaToAttach = em.getReference(precedenciaList1NewPrecedenciaToAttach.getClass(), precedenciaList1NewPrecedenciaToAttach.getPrecedenciaPK());
                attachedPrecedenciaList1New.add(precedenciaList1NewPrecedenciaToAttach);
            }
            precedenciaList1New = attachedPrecedenciaList1New;
            disciplina.setPrecedenciaList1(precedenciaList1New);
            List<Planoavaliacao> attachedPlanoavaliacaoListNew = new ArrayList<Planoavaliacao>();
            for (Planoavaliacao planoavaliacaoListNewPlanoavaliacaoToAttach : planoavaliacaoListNew) {
                planoavaliacaoListNewPlanoavaliacaoToAttach = em.getReference(planoavaliacaoListNewPlanoavaliacaoToAttach.getClass(), planoavaliacaoListNewPlanoavaliacaoToAttach.getPlanoavaliacaoPK());
                attachedPlanoavaliacaoListNew.add(planoavaliacaoListNewPlanoavaliacaoToAttach);
            }
            planoavaliacaoListNew = attachedPlanoavaliacaoListNew;
            disciplina.setPlanoavaliacaoList(planoavaliacaoListNew);
            List<Lecciona> attachedLeccionaListNew = new ArrayList<Lecciona>();
            for (Lecciona leccionaListNewLeccionaToAttach : leccionaListNew) {
                leccionaListNewLeccionaToAttach = em.getReference(leccionaListNewLeccionaToAttach.getClass(), leccionaListNewLeccionaToAttach.getLeccionaPK());
                attachedLeccionaListNew.add(leccionaListNewLeccionaToAttach);
            }
            leccionaListNew = attachedLeccionaListNew;
            disciplina.setLeccionaList(leccionaListNew);
            List<Pauta> attachedPautaListNew = new ArrayList<Pauta>();
            for (Pauta pautaListNewPautaToAttach : pautaListNew) {
                pautaListNewPautaToAttach = em.getReference(pautaListNewPautaToAttach.getClass(), pautaListNewPautaToAttach.getPautaPK());
                attachedPautaListNew.add(pautaListNewPautaToAttach);
            }
            pautaListNew = attachedPautaListNew;
            disciplina.setPautaList(pautaListNew);
            disciplina = em.merge(disciplina);
            if (areaCientificaOld != null && !areaCientificaOld.equals(areaCientificaNew)) {
                areaCientificaOld.getDisciplinaList().remove(disciplina);
                areaCientificaOld = em.merge(areaCientificaOld);
            }
            if (areaCientificaNew != null && !areaCientificaNew.equals(areaCientificaOld)) {
                areaCientificaNew.getDisciplinaList().add(disciplina);
                areaCientificaNew = em.merge(areaCientificaNew);
            }
            if (caracterOld != null && !caracterOld.equals(caracterNew)) {
                caracterOld.getDisciplinaList().remove(disciplina);
                caracterOld = em.merge(caracterOld);
            }
            if (caracterNew != null && !caracterNew.equals(caracterOld)) {
                caracterNew.getDisciplinaList().add(disciplina);
                caracterNew = em.merge(caracterNew);
            }
            if (cursoOld != null && !cursoOld.equals(cursoNew)) {
                cursoOld.getDisciplinaList().remove(disciplina);
                cursoOld = em.merge(cursoOld);
            }
            if (cursoNew != null && !cursoNew.equals(cursoOld)) {
                cursoNew.getDisciplinaList().add(disciplina);
                cursoNew = em.merge(cursoNew);
            }
            if (idPeriodoOld != null && !idPeriodoOld.equals(idPeriodoNew)) {
                idPeriodoOld.getDisciplinaList().remove(disciplina);
                idPeriodoOld = em.merge(idPeriodoOld);
            }
            if (idPeriodoNew != null && !idPeriodoNew.equals(idPeriodoOld)) {
                idPeriodoNew.getDisciplinaList().add(disciplina);
                idPeriodoNew = em.merge(idPeriodoNew);
            }
            for (Precedencia precedenciaListNewPrecedencia : precedenciaListNew) {
                if (!precedenciaListOld.contains(precedenciaListNewPrecedencia)) {
                    Disciplina oldDisciplinaOfPrecedenciaListNewPrecedencia = precedenciaListNewPrecedencia.getDisciplina();
                    precedenciaListNewPrecedencia.setDisciplina(disciplina);
                    precedenciaListNewPrecedencia = em.merge(precedenciaListNewPrecedencia);
                    if (oldDisciplinaOfPrecedenciaListNewPrecedencia != null && !oldDisciplinaOfPrecedenciaListNewPrecedencia.equals(disciplina)) {
                        oldDisciplinaOfPrecedenciaListNewPrecedencia.getPrecedenciaList().remove(precedenciaListNewPrecedencia);
                        oldDisciplinaOfPrecedenciaListNewPrecedencia = em.merge(oldDisciplinaOfPrecedenciaListNewPrecedencia);
                    }
                }
            }
            for (Precedencia precedenciaList1NewPrecedencia : precedenciaList1New) {
                if (!precedenciaList1Old.contains(precedenciaList1NewPrecedencia)) {
                    Disciplina oldDisciplina1OfPrecedenciaList1NewPrecedencia = precedenciaList1NewPrecedencia.getDisciplina1();
                    precedenciaList1NewPrecedencia.setDisciplina1(disciplina);
                    precedenciaList1NewPrecedencia = em.merge(precedenciaList1NewPrecedencia);
                    if (oldDisciplina1OfPrecedenciaList1NewPrecedencia != null && !oldDisciplina1OfPrecedenciaList1NewPrecedencia.equals(disciplina)) {
                        oldDisciplina1OfPrecedenciaList1NewPrecedencia.getPrecedenciaList1().remove(precedenciaList1NewPrecedencia);
                        oldDisciplina1OfPrecedenciaList1NewPrecedencia = em.merge(oldDisciplina1OfPrecedenciaList1NewPrecedencia);
                    }
                }
            }
            for (Planoavaliacao planoavaliacaoListNewPlanoavaliacao : planoavaliacaoListNew) {
                if (!planoavaliacaoListOld.contains(planoavaliacaoListNewPlanoavaliacao)) {
                    Disciplina oldDisciplinaOfPlanoavaliacaoListNewPlanoavaliacao = planoavaliacaoListNewPlanoavaliacao.getDisciplina();
                    planoavaliacaoListNewPlanoavaliacao.setDisciplina(disciplina);
                    planoavaliacaoListNewPlanoavaliacao = em.merge(planoavaliacaoListNewPlanoavaliacao);
                    if (oldDisciplinaOfPlanoavaliacaoListNewPlanoavaliacao != null && !oldDisciplinaOfPlanoavaliacaoListNewPlanoavaliacao.equals(disciplina)) {
                        oldDisciplinaOfPlanoavaliacaoListNewPlanoavaliacao.getPlanoavaliacaoList().remove(planoavaliacaoListNewPlanoavaliacao);
                        oldDisciplinaOfPlanoavaliacaoListNewPlanoavaliacao = em.merge(oldDisciplinaOfPlanoavaliacaoListNewPlanoavaliacao);
                    }
                }
            }
            for (Lecciona leccionaListNewLecciona : leccionaListNew) {
                if (!leccionaListOld.contains(leccionaListNewLecciona)) {
                    Disciplina oldDisciplinaOfLeccionaListNewLecciona = leccionaListNewLecciona.getDisciplina();
                    leccionaListNewLecciona.setDisciplina(disciplina);
                    leccionaListNewLecciona = em.merge(leccionaListNewLecciona);
                    if (oldDisciplinaOfLeccionaListNewLecciona != null && !oldDisciplinaOfLeccionaListNewLecciona.equals(disciplina)) {
                        oldDisciplinaOfLeccionaListNewLecciona.getLeccionaList().remove(leccionaListNewLecciona);
                        oldDisciplinaOfLeccionaListNewLecciona = em.merge(oldDisciplinaOfLeccionaListNewLecciona);
                    }
                }
            }
            for (Pauta pautaListNewPauta : pautaListNew) {
                if (!pautaListOld.contains(pautaListNewPauta)) {
                    Disciplina oldDisciplinaOfPautaListNewPauta = pautaListNewPauta.getDisciplina();
                    pautaListNewPauta.setDisciplina(disciplina);
                    pautaListNewPauta = em.merge(pautaListNewPauta);
                    if (oldDisciplinaOfPautaListNewPauta != null && !oldDisciplinaOfPautaListNewPauta.equals(disciplina)) {
                        oldDisciplinaOfPautaListNewPauta.getPautaList().remove(pautaListNewPauta);
                        oldDisciplinaOfPautaListNewPauta = em.merge(oldDisciplinaOfPautaListNewPauta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = disciplina.getIdDisc();
                if (findDisciplina(id) == null) {
                    throw new NonexistentEntityException("The disciplina with id " + id + " no longer exists.");
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
            Disciplina disciplina;
            try {
                disciplina = em.getReference(Disciplina.class, id);
                disciplina.getIdDisc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The disciplina with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Precedencia> precedenciaListOrphanCheck = disciplina.getPrecedenciaList();
            for (Precedencia precedenciaListOrphanCheckPrecedencia : precedenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Disciplina (" + disciplina + ") cannot be destroyed since the Precedencia " + precedenciaListOrphanCheckPrecedencia + " in its precedenciaList field has a non-nullable disciplina field.");
            }
            List<Precedencia> precedenciaList1OrphanCheck = disciplina.getPrecedenciaList1();
            for (Precedencia precedenciaList1OrphanCheckPrecedencia : precedenciaList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Disciplina (" + disciplina + ") cannot be destroyed since the Precedencia " + precedenciaList1OrphanCheckPrecedencia + " in its precedenciaList1 field has a non-nullable disciplina1 field.");
            }
            List<Planoavaliacao> planoavaliacaoListOrphanCheck = disciplina.getPlanoavaliacaoList();
            for (Planoavaliacao planoavaliacaoListOrphanCheckPlanoavaliacao : planoavaliacaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Disciplina (" + disciplina + ") cannot be destroyed since the Planoavaliacao " + planoavaliacaoListOrphanCheckPlanoavaliacao + " in its planoavaliacaoList field has a non-nullable disciplina field.");
            }
            List<Lecciona> leccionaListOrphanCheck = disciplina.getLeccionaList();
            for (Lecciona leccionaListOrphanCheckLecciona : leccionaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Disciplina (" + disciplina + ") cannot be destroyed since the Lecciona " + leccionaListOrphanCheckLecciona + " in its leccionaList field has a non-nullable disciplina field.");
            }
            List<Pauta> pautaListOrphanCheck = disciplina.getPautaList();
            for (Pauta pautaListOrphanCheckPauta : pautaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Disciplina (" + disciplina + ") cannot be destroyed since the Pauta " + pautaListOrphanCheckPauta + " in its pautaList field has a non-nullable disciplina field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Areacientifica areaCientifica = disciplina.getAreaCientifica();
            if (areaCientifica != null) {
                areaCientifica.getDisciplinaList().remove(disciplina);
                areaCientifica = em.merge(areaCientifica);
            }
            Caracter caracter = disciplina.getCaracter();
            if (caracter != null) {
                caracter.getDisciplinaList().remove(disciplina);
                caracter = em.merge(caracter);
            }
            Curso curso = disciplina.getCurso();
            if (curso != null) {
                curso.getDisciplinaList().remove(disciplina);
                curso = em.merge(curso);
            }
            Periodo idPeriodo = disciplina.getIdPeriodo();
            if (idPeriodo != null) {
                idPeriodo.getDisciplinaList().remove(disciplina);
                idPeriodo = em.merge(idPeriodo);
            }
            em.remove(disciplina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Disciplina> findDisciplinaEntities() {
        return findDisciplinaEntities(true, -1, -1);
    }

    public List<Disciplina> findDisciplinaEntities(int maxResults, int firstResult) {
        return findDisciplinaEntities(false, maxResults, firstResult);
    }

    private List<Disciplina> findDisciplinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Disciplina.class));
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

    public Disciplina findDisciplina(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Disciplina.class, id);
        } finally {
            em.close();
        }
    }

    public int getDisciplinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Disciplina> rt = cq.from(Disciplina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
