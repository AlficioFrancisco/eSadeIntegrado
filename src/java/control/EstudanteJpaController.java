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
import modelo.Documento;
import modelo.Ingressotransferencia;
import modelo.Profissao;
import modelo.Especial;
import modelo.Ingressoexameadmissao;
import modelo.Bolsa;
import modelo.Curso;
import modelo.Estadocivil;
import modelo.Pais;
import modelo.Viaingresso;
import modelo.Enderecof;
import modelo.Ingressobolseiro;
import modelo.Endereco;
import modelo.Ingressomudancauniversidade;
import modelo.Users;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Mudancacurso;
import modelo.Matricula;
import modelo.Disciplinaanulada;
import modelo.Estudante;
import modelo.Ingressopercabolsa;
import modelo.Notapauta;

/**
 *
 * @author Paulino Francisco
 */
public class EstudanteJpaController implements Serializable {

    public EstudanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudante estudante) {
        if (estudante.getUsersList() == null) {
            estudante.setUsersList(new ArrayList<Users>());
        }
        if (estudante.getMudancacursoList() == null) {
            estudante.setMudancacursoList(new ArrayList<Mudancacurso>());
        }
        if (estudante.getMatriculaList() == null) {
            estudante.setMatriculaList(new ArrayList<Matricula>());
        }
        if (estudante.getDisciplinaanuladaList() == null) {
            estudante.setDisciplinaanuladaList(new ArrayList<Disciplinaanulada>());
        }
        if (estudante.getIngressopercabolsaList() == null) {
            estudante.setIngressopercabolsaList(new ArrayList<Ingressopercabolsa>());
        }
        if (estudante.getNotapautaList() == null) {
            estudante.setNotapautaList(new ArrayList<Notapauta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documento documento = estudante.getDocumento();
            if (documento != null) {
                documento = em.getReference(documento.getClass(), documento.getIdEstudante());
                estudante.setDocumento(documento);
            }
            Ingressotransferencia ingressotransferencia = estudante.getIngressotransferencia();
            if (ingressotransferencia != null) {
                ingressotransferencia = em.getReference(ingressotransferencia.getClass(), ingressotransferencia.getIdEstudante());
                estudante.setIngressotransferencia(ingressotransferencia);
            }
            Profissao profissao = estudante.getProfissao();
            if (profissao != null) {
                profissao = em.getReference(profissao.getClass(), profissao.getIdEstudante());
                estudante.setProfissao(profissao);
            }
            Especial especial = estudante.getEspecial();
            if (especial != null) {
                especial = em.getReference(especial.getClass(), especial.getIdEstudante());
                estudante.setEspecial(especial);
            }
            Ingressoexameadmissao ingressoexameadmissao = estudante.getIngressoexameadmissao();
            if (ingressoexameadmissao != null) {
                ingressoexameadmissao = em.getReference(ingressoexameadmissao.getClass(), ingressoexameadmissao.getIdEstudante());
                estudante.setIngressoexameadmissao(ingressoexameadmissao);
            }
            Bolsa bolsa = estudante.getBolsa();
            if (bolsa != null) {
                bolsa = em.getReference(bolsa.getClass(), bolsa.getIdBolsa());
                estudante.setBolsa(bolsa);
            }
            Curso idCurso = estudante.getIdCurso();
            if (idCurso != null) {
                idCurso = em.getReference(idCurso.getClass(), idCurso.getIdCurso());
                estudante.setIdCurso(idCurso);
            }
            Curso cursoingresso = estudante.getCursoingresso();
            if (cursoingresso != null) {
                cursoingresso = em.getReference(cursoingresso.getClass(), cursoingresso.getIdCurso());
                estudante.setCursoingresso(cursoingresso);
            }
            Curso cursocurrente = estudante.getCursocurrente();
            if (cursocurrente != null) {
                cursocurrente = em.getReference(cursocurrente.getClass(), cursocurrente.getIdCurso());
                estudante.setCursocurrente(cursocurrente);
            }
            Estadocivil estadoCivil = estudante.getEstadoCivil();
            if (estadoCivil != null) {
                estadoCivil = em.getReference(estadoCivil.getClass(), estadoCivil.getIdEstado());
                estudante.setEstadoCivil(estadoCivil);
            }
            Pais nacionalidade = estudante.getNacionalidade();
            if (nacionalidade != null) {
                nacionalidade = em.getReference(nacionalidade.getClass(), nacionalidade.getIdPais());
                estudante.setNacionalidade(nacionalidade);
            }
            Pais escolaPais = estudante.getEscolaPais();
            if (escolaPais != null) {
                escolaPais = em.getReference(escolaPais.getClass(), escolaPais.getIdPais());
                estudante.setEscolaPais(escolaPais);
            }
            Viaingresso viaIngresso = estudante.getViaIngresso();
            if (viaIngresso != null) {
                viaIngresso = em.getReference(viaIngresso.getClass(), viaIngresso.getIdViaIngresso());
                estudante.setViaIngresso(viaIngresso);
            }
            Enderecof enderecof = estudante.getEnderecof();
            if (enderecof != null) {
                enderecof = em.getReference(enderecof.getClass(), enderecof.getIdEstudante());
                estudante.setEnderecof(enderecof);
            }
            Ingressobolseiro ingressobolseiro = estudante.getIngressobolseiro();
            if (ingressobolseiro != null) {
                ingressobolseiro = em.getReference(ingressobolseiro.getClass(), ingressobolseiro.getIdEstudante());
                estudante.setIngressobolseiro(ingressobolseiro);
            }
            Endereco endereco = estudante.getEndereco();
            if (endereco != null) {
                endereco = em.getReference(endereco.getClass(), endereco.getIdEstudante());
                estudante.setEndereco(endereco);
            }
            Ingressomudancauniversidade ingressomudancauniversidade = estudante.getIngressomudancauniversidade();
            if (ingressomudancauniversidade != null) {
                ingressomudancauniversidade = em.getReference(ingressomudancauniversidade.getClass(), ingressomudancauniversidade.getIdEstudante());
                estudante.setIngressomudancauniversidade(ingressomudancauniversidade);
            }
            List<Users> attachedUsersList = new ArrayList<Users>();
            for (Users usersListUsersToAttach : estudante.getUsersList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUtilizador());
                attachedUsersList.add(usersListUsersToAttach);
            }
            estudante.setUsersList(attachedUsersList);
            List<Mudancacurso> attachedMudancacursoList = new ArrayList<Mudancacurso>();
            for (Mudancacurso mudancacursoListMudancacursoToAttach : estudante.getMudancacursoList()) {
                mudancacursoListMudancacursoToAttach = em.getReference(mudancacursoListMudancacursoToAttach.getClass(), mudancacursoListMudancacursoToAttach.getIdmudanca());
                attachedMudancacursoList.add(mudancacursoListMudancacursoToAttach);
            }
            estudante.setMudancacursoList(attachedMudancacursoList);
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : estudante.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getMatriculaPK());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            estudante.setMatriculaList(attachedMatriculaList);
            List<Disciplinaanulada> attachedDisciplinaanuladaList = new ArrayList<Disciplinaanulada>();
            for (Disciplinaanulada disciplinaanuladaListDisciplinaanuladaToAttach : estudante.getDisciplinaanuladaList()) {
                disciplinaanuladaListDisciplinaanuladaToAttach = em.getReference(disciplinaanuladaListDisciplinaanuladaToAttach.getClass(), disciplinaanuladaListDisciplinaanuladaToAttach.getIdanulacao());
                attachedDisciplinaanuladaList.add(disciplinaanuladaListDisciplinaanuladaToAttach);
            }
            estudante.setDisciplinaanuladaList(attachedDisciplinaanuladaList);
            List<Ingressopercabolsa> attachedIngressopercabolsaList = new ArrayList<Ingressopercabolsa>();
            for (Ingressopercabolsa ingressopercabolsaListIngressopercabolsaToAttach : estudante.getIngressopercabolsaList()) {
                ingressopercabolsaListIngressopercabolsaToAttach = em.getReference(ingressopercabolsaListIngressopercabolsaToAttach.getClass(), ingressopercabolsaListIngressopercabolsaToAttach.getIngressopercabolsaPK());
                attachedIngressopercabolsaList.add(ingressopercabolsaListIngressopercabolsaToAttach);
            }
            estudante.setIngressopercabolsaList(attachedIngressopercabolsaList);
            List<Notapauta> attachedNotapautaList = new ArrayList<Notapauta>();
            for (Notapauta notapautaListNotapautaToAttach : estudante.getNotapautaList()) {
                notapautaListNotapautaToAttach = em.getReference(notapautaListNotapautaToAttach.getClass(), notapautaListNotapautaToAttach.getNotapautaPK());
                attachedNotapautaList.add(notapautaListNotapautaToAttach);
            }
            estudante.setNotapautaList(attachedNotapautaList);
            em.persist(estudante);
            if (documento != null) {
                Estudante oldEstudanteOfDocumento = documento.getEstudante();
                if (oldEstudanteOfDocumento != null) {
                    oldEstudanteOfDocumento.setDocumento(null);
                    oldEstudanteOfDocumento = em.merge(oldEstudanteOfDocumento);
                }
                documento.setEstudante(estudante);
                documento = em.merge(documento);
            }
            if (ingressotransferencia != null) {
                Estudante oldEstudanteOfIngressotransferencia = ingressotransferencia.getEstudante();
                if (oldEstudanteOfIngressotransferencia != null) {
                    oldEstudanteOfIngressotransferencia.setIngressotransferencia(null);
                    oldEstudanteOfIngressotransferencia = em.merge(oldEstudanteOfIngressotransferencia);
                }
                ingressotransferencia.setEstudante(estudante);
                ingressotransferencia = em.merge(ingressotransferencia);
            }
            if (profissao != null) {
                Estudante oldEstudanteOfProfissao = profissao.getEstudante();
                if (oldEstudanteOfProfissao != null) {
                    oldEstudanteOfProfissao.setProfissao(null);
                    oldEstudanteOfProfissao = em.merge(oldEstudanteOfProfissao);
                }
                profissao.setEstudante(estudante);
                profissao = em.merge(profissao);
            }
            if (especial != null) {
                Estudante oldEstudanteOfEspecial = especial.getEstudante();
                if (oldEstudanteOfEspecial != null) {
                    oldEstudanteOfEspecial.setEspecial(null);
                    oldEstudanteOfEspecial = em.merge(oldEstudanteOfEspecial);
                }
                especial.setEstudante(estudante);
                especial = em.merge(especial);
            }
            if (ingressoexameadmissao != null) {
                Estudante oldEstudanteOfIngressoexameadmissao = ingressoexameadmissao.getEstudante();
                if (oldEstudanteOfIngressoexameadmissao != null) {
                    oldEstudanteOfIngressoexameadmissao.setIngressoexameadmissao(null);
                    oldEstudanteOfIngressoexameadmissao = em.merge(oldEstudanteOfIngressoexameadmissao);
                }
                ingressoexameadmissao.setEstudante(estudante);
                ingressoexameadmissao = em.merge(ingressoexameadmissao);
            }
            if (bolsa != null) {
                bolsa.getEstudanteList().add(estudante);
                bolsa = em.merge(bolsa);
            }
            if (idCurso != null) {
                idCurso.getEstudanteList().add(estudante);
                idCurso = em.merge(idCurso);
            }
            if (cursoingresso != null) {
                cursoingresso.getEstudanteList().add(estudante);
                cursoingresso = em.merge(cursoingresso);
            }
            if (cursocurrente != null) {
                cursocurrente.getEstudanteList().add(estudante);
                cursocurrente = em.merge(cursocurrente);
            }
            if (estadoCivil != null) {
                estadoCivil.getEstudanteList().add(estudante);
                estadoCivil = em.merge(estadoCivil);
            }
            if (nacionalidade != null) {
                nacionalidade.getEstudanteList().add(estudante);
                nacionalidade = em.merge(nacionalidade);
            }
            if (escolaPais != null) {
                escolaPais.getEstudanteList().add(estudante);
                escolaPais = em.merge(escolaPais);
            }
            if (viaIngresso != null) {
                viaIngresso.getEstudanteList().add(estudante);
                viaIngresso = em.merge(viaIngresso);
            }
            if (enderecof != null) {
                Estudante oldEstudanteOfEnderecof = enderecof.getEstudante();
                if (oldEstudanteOfEnderecof != null) {
                    oldEstudanteOfEnderecof.setEnderecof(null);
                    oldEstudanteOfEnderecof = em.merge(oldEstudanteOfEnderecof);
                }
                enderecof.setEstudante(estudante);
                enderecof = em.merge(enderecof);
            }
            if (ingressobolseiro != null) {
                Estudante oldEstudanteOfIngressobolseiro = ingressobolseiro.getEstudante();
                if (oldEstudanteOfIngressobolseiro != null) {
                    oldEstudanteOfIngressobolseiro.setIngressobolseiro(null);
                    oldEstudanteOfIngressobolseiro = em.merge(oldEstudanteOfIngressobolseiro);
                }
                ingressobolseiro.setEstudante(estudante);
                ingressobolseiro = em.merge(ingressobolseiro);
            }
            if (endereco != null) {
                Estudante oldEstudanteOfEndereco = endereco.getEstudante();
                if (oldEstudanteOfEndereco != null) {
                    oldEstudanteOfEndereco.setEndereco(null);
                    oldEstudanteOfEndereco = em.merge(oldEstudanteOfEndereco);
                }
                endereco.setEstudante(estudante);
                endereco = em.merge(endereco);
            }
            if (ingressomudancauniversidade != null) {
                Estudante oldEstudanteOfIngressomudancauniversidade = ingressomudancauniversidade.getEstudante();
                if (oldEstudanteOfIngressomudancauniversidade != null) {
                    oldEstudanteOfIngressomudancauniversidade.setIngressomudancauniversidade(null);
                    oldEstudanteOfIngressomudancauniversidade = em.merge(oldEstudanteOfIngressomudancauniversidade);
                }
                ingressomudancauniversidade.setEstudante(estudante);
                ingressomudancauniversidade = em.merge(ingressomudancauniversidade);
            }
            for (Users usersListUsers : estudante.getUsersList()) {
                Estudante oldIdEstudanteOfUsersListUsers = usersListUsers.getIdEstudante();
                usersListUsers.setIdEstudante(estudante);
                usersListUsers = em.merge(usersListUsers);
                if (oldIdEstudanteOfUsersListUsers != null) {
                    oldIdEstudanteOfUsersListUsers.getUsersList().remove(usersListUsers);
                    oldIdEstudanteOfUsersListUsers = em.merge(oldIdEstudanteOfUsersListUsers);
                }
            }
            for (Mudancacurso mudancacursoListMudancacurso : estudante.getMudancacursoList()) {
                Estudante oldIdEstudanteOfMudancacursoListMudancacurso = mudancacursoListMudancacurso.getIdEstudante();
                mudancacursoListMudancacurso.setIdEstudante(estudante);
                mudancacursoListMudancacurso = em.merge(mudancacursoListMudancacurso);
                if (oldIdEstudanteOfMudancacursoListMudancacurso != null) {
                    oldIdEstudanteOfMudancacursoListMudancacurso.getMudancacursoList().remove(mudancacursoListMudancacurso);
                    oldIdEstudanteOfMudancacursoListMudancacurso = em.merge(oldIdEstudanteOfMudancacursoListMudancacurso);
                }
            }
            for (Matricula matriculaListMatricula : estudante.getMatriculaList()) {
                Estudante oldEstudanteOfMatriculaListMatricula = matriculaListMatricula.getEstudante();
                matriculaListMatricula.setEstudante(estudante);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldEstudanteOfMatriculaListMatricula != null) {
                    oldEstudanteOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldEstudanteOfMatriculaListMatricula = em.merge(oldEstudanteOfMatriculaListMatricula);
                }
            }
            for (Disciplinaanulada disciplinaanuladaListDisciplinaanulada : estudante.getDisciplinaanuladaList()) {
                Estudante oldIdEstudanteOfDisciplinaanuladaListDisciplinaanulada = disciplinaanuladaListDisciplinaanulada.getIdEstudante();
                disciplinaanuladaListDisciplinaanulada.setIdEstudante(estudante);
                disciplinaanuladaListDisciplinaanulada = em.merge(disciplinaanuladaListDisciplinaanulada);
                if (oldIdEstudanteOfDisciplinaanuladaListDisciplinaanulada != null) {
                    oldIdEstudanteOfDisciplinaanuladaListDisciplinaanulada.getDisciplinaanuladaList().remove(disciplinaanuladaListDisciplinaanulada);
                    oldIdEstudanteOfDisciplinaanuladaListDisciplinaanulada = em.merge(oldIdEstudanteOfDisciplinaanuladaListDisciplinaanulada);
                }
            }
            for (Ingressopercabolsa ingressopercabolsaListIngressopercabolsa : estudante.getIngressopercabolsaList()) {
                Estudante oldEstudanteOfIngressopercabolsaListIngressopercabolsa = ingressopercabolsaListIngressopercabolsa.getEstudante();
                ingressopercabolsaListIngressopercabolsa.setEstudante(estudante);
                ingressopercabolsaListIngressopercabolsa = em.merge(ingressopercabolsaListIngressopercabolsa);
                if (oldEstudanteOfIngressopercabolsaListIngressopercabolsa != null) {
                    oldEstudanteOfIngressopercabolsaListIngressopercabolsa.getIngressopercabolsaList().remove(ingressopercabolsaListIngressopercabolsa);
                    oldEstudanteOfIngressopercabolsaListIngressopercabolsa = em.merge(oldEstudanteOfIngressopercabolsaListIngressopercabolsa);
                }
            }
            for (Notapauta notapautaListNotapauta : estudante.getNotapautaList()) {
                Estudante oldEstudanteOfNotapautaListNotapauta = notapautaListNotapauta.getEstudante();
                notapautaListNotapauta.setEstudante(estudante);
                notapautaListNotapauta = em.merge(notapautaListNotapauta);
                if (oldEstudanteOfNotapautaListNotapauta != null) {
                    oldEstudanteOfNotapautaListNotapauta.getNotapautaList().remove(notapautaListNotapauta);
                    oldEstudanteOfNotapautaListNotapauta = em.merge(oldEstudanteOfNotapautaListNotapauta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudante estudante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudante persistentEstudante = em.find(Estudante.class, estudante.getIdEstudante());
            Documento documentoOld = persistentEstudante.getDocumento();
            Documento documentoNew = estudante.getDocumento();
            Ingressotransferencia ingressotransferenciaOld = persistentEstudante.getIngressotransferencia();
            Ingressotransferencia ingressotransferenciaNew = estudante.getIngressotransferencia();
            Profissao profissaoOld = persistentEstudante.getProfissao();
            Profissao profissaoNew = estudante.getProfissao();
            Especial especialOld = persistentEstudante.getEspecial();
            Especial especialNew = estudante.getEspecial();
            Ingressoexameadmissao ingressoexameadmissaoOld = persistentEstudante.getIngressoexameadmissao();
            Ingressoexameadmissao ingressoexameadmissaoNew = estudante.getIngressoexameadmissao();
            Bolsa bolsaOld = persistentEstudante.getBolsa();
            Bolsa bolsaNew = estudante.getBolsa();
            Curso idCursoOld = persistentEstudante.getIdCurso();
            Curso idCursoNew = estudante.getIdCurso();
            Curso cursoingressoOld = persistentEstudante.getCursoingresso();
            Curso cursoingressoNew = estudante.getCursoingresso();
            Curso cursocurrenteOld = persistentEstudante.getCursocurrente();
            Curso cursocurrenteNew = estudante.getCursocurrente();
            Estadocivil estadoCivilOld = persistentEstudante.getEstadoCivil();
            Estadocivil estadoCivilNew = estudante.getEstadoCivil();
            Pais nacionalidadeOld = persistentEstudante.getNacionalidade();
            Pais nacionalidadeNew = estudante.getNacionalidade();
            Pais escolaPaisOld = persistentEstudante.getEscolaPais();
            Pais escolaPaisNew = estudante.getEscolaPais();
            Viaingresso viaIngressoOld = persistentEstudante.getViaIngresso();
            Viaingresso viaIngressoNew = estudante.getViaIngresso();
            Enderecof enderecofOld = persistentEstudante.getEnderecof();
            Enderecof enderecofNew = estudante.getEnderecof();
            Ingressobolseiro ingressobolseiroOld = persistentEstudante.getIngressobolseiro();
            Ingressobolseiro ingressobolseiroNew = estudante.getIngressobolseiro();
            Endereco enderecoOld = persistentEstudante.getEndereco();
            Endereco enderecoNew = estudante.getEndereco();
            Ingressomudancauniversidade ingressomudancauniversidadeOld = persistentEstudante.getIngressomudancauniversidade();
            Ingressomudancauniversidade ingressomudancauniversidadeNew = estudante.getIngressomudancauniversidade();
            List<Users> usersListOld = persistentEstudante.getUsersList();
            List<Users> usersListNew = estudante.getUsersList();
            List<Mudancacurso> mudancacursoListOld = persistentEstudante.getMudancacursoList();
            List<Mudancacurso> mudancacursoListNew = estudante.getMudancacursoList();
            List<Matricula> matriculaListOld = persistentEstudante.getMatriculaList();
            List<Matricula> matriculaListNew = estudante.getMatriculaList();
            List<Disciplinaanulada> disciplinaanuladaListOld = persistentEstudante.getDisciplinaanuladaList();
            List<Disciplinaanulada> disciplinaanuladaListNew = estudante.getDisciplinaanuladaList();
            List<Ingressopercabolsa> ingressopercabolsaListOld = persistentEstudante.getIngressopercabolsaList();
            List<Ingressopercabolsa> ingressopercabolsaListNew = estudante.getIngressopercabolsaList();
            List<Notapauta> notapautaListOld = persistentEstudante.getNotapautaList();
            List<Notapauta> notapautaListNew = estudante.getNotapautaList();
            List<String> illegalOrphanMessages = null;
            if (documentoOld != null && !documentoOld.equals(documentoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Documento " + documentoOld + " since its estudante field is not nullable.");
            }
            if (ingressotransferenciaOld != null && !ingressotransferenciaOld.equals(ingressotransferenciaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Ingressotransferencia " + ingressotransferenciaOld + " since its estudante field is not nullable.");
            }
            if (profissaoOld != null && !profissaoOld.equals(profissaoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Profissao " + profissaoOld + " since its estudante field is not nullable.");
            }
            if (especialOld != null && !especialOld.equals(especialNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Especial " + especialOld + " since its estudante field is not nullable.");
            }
            if (ingressoexameadmissaoOld != null && !ingressoexameadmissaoOld.equals(ingressoexameadmissaoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Ingressoexameadmissao " + ingressoexameadmissaoOld + " since its estudante field is not nullable.");
            }
            if (enderecofOld != null && !enderecofOld.equals(enderecofNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Enderecof " + enderecofOld + " since its estudante field is not nullable.");
            }
            if (ingressobolseiroOld != null && !ingressobolseiroOld.equals(ingressobolseiroNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Ingressobolseiro " + ingressobolseiroOld + " since its estudante field is not nullable.");
            }
            if (enderecoOld != null && !enderecoOld.equals(enderecoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Endereco " + enderecoOld + " since its estudante field is not nullable.");
            }
            if (ingressomudancauniversidadeOld != null && !ingressomudancauniversidadeOld.equals(ingressomudancauniversidadeNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Ingressomudancauniversidade " + ingressomudancauniversidadeOld + " since its estudante field is not nullable.");
            }
            for (Mudancacurso mudancacursoListOldMudancacurso : mudancacursoListOld) {
                if (!mudancacursoListNew.contains(mudancacursoListOldMudancacurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mudancacurso " + mudancacursoListOldMudancacurso + " since its idEstudante field is not nullable.");
                }
            }
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matricula " + matriculaListOldMatricula + " since its estudante field is not nullable.");
                }
            }
            for (Ingressopercabolsa ingressopercabolsaListOldIngressopercabolsa : ingressopercabolsaListOld) {
                if (!ingressopercabolsaListNew.contains(ingressopercabolsaListOldIngressopercabolsa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ingressopercabolsa " + ingressopercabolsaListOldIngressopercabolsa + " since its estudante field is not nullable.");
                }
            }
            for (Notapauta notapautaListOldNotapauta : notapautaListOld) {
                if (!notapautaListNew.contains(notapautaListOldNotapauta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notapauta " + notapautaListOldNotapauta + " since its estudante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (documentoNew != null) {
                documentoNew = em.getReference(documentoNew.getClass(), documentoNew.getIdEstudante());
                estudante.setDocumento(documentoNew);
            }
            if (ingressotransferenciaNew != null) {
                ingressotransferenciaNew = em.getReference(ingressotransferenciaNew.getClass(), ingressotransferenciaNew.getIdEstudante());
                estudante.setIngressotransferencia(ingressotransferenciaNew);
            }
            if (profissaoNew != null) {
                profissaoNew = em.getReference(profissaoNew.getClass(), profissaoNew.getIdEstudante());
                estudante.setProfissao(profissaoNew);
            }
            if (especialNew != null) {
                especialNew = em.getReference(especialNew.getClass(), especialNew.getIdEstudante());
                estudante.setEspecial(especialNew);
            }
            if (ingressoexameadmissaoNew != null) {
                ingressoexameadmissaoNew = em.getReference(ingressoexameadmissaoNew.getClass(), ingressoexameadmissaoNew.getIdEstudante());
                estudante.setIngressoexameadmissao(ingressoexameadmissaoNew);
            }
            if (bolsaNew != null) {
                bolsaNew = em.getReference(bolsaNew.getClass(), bolsaNew.getIdBolsa());
                estudante.setBolsa(bolsaNew);
            }
            if (idCursoNew != null) {
                idCursoNew = em.getReference(idCursoNew.getClass(), idCursoNew.getIdCurso());
                estudante.setIdCurso(idCursoNew);
            }
            if (cursoingressoNew != null) {
                cursoingressoNew = em.getReference(cursoingressoNew.getClass(), cursoingressoNew.getIdCurso());
                estudante.setCursoingresso(cursoingressoNew);
            }
            if (cursocurrenteNew != null) {
                cursocurrenteNew = em.getReference(cursocurrenteNew.getClass(), cursocurrenteNew.getIdCurso());
                estudante.setCursocurrente(cursocurrenteNew);
            }
            if (estadoCivilNew != null) {
                estadoCivilNew = em.getReference(estadoCivilNew.getClass(), estadoCivilNew.getIdEstado());
                estudante.setEstadoCivil(estadoCivilNew);
            }
            if (nacionalidadeNew != null) {
                nacionalidadeNew = em.getReference(nacionalidadeNew.getClass(), nacionalidadeNew.getIdPais());
                estudante.setNacionalidade(nacionalidadeNew);
            }
            if (escolaPaisNew != null) {
                escolaPaisNew = em.getReference(escolaPaisNew.getClass(), escolaPaisNew.getIdPais());
                estudante.setEscolaPais(escolaPaisNew);
            }
            if (viaIngressoNew != null) {
                viaIngressoNew = em.getReference(viaIngressoNew.getClass(), viaIngressoNew.getIdViaIngresso());
                estudante.setViaIngresso(viaIngressoNew);
            }
            if (enderecofNew != null) {
                enderecofNew = em.getReference(enderecofNew.getClass(), enderecofNew.getIdEstudante());
                estudante.setEnderecof(enderecofNew);
            }
            if (ingressobolseiroNew != null) {
                ingressobolseiroNew = em.getReference(ingressobolseiroNew.getClass(), ingressobolseiroNew.getIdEstudante());
                estudante.setIngressobolseiro(ingressobolseiroNew);
            }
            if (enderecoNew != null) {
                enderecoNew = em.getReference(enderecoNew.getClass(), enderecoNew.getIdEstudante());
                estudante.setEndereco(enderecoNew);
            }
            if (ingressomudancauniversidadeNew != null) {
                ingressomudancauniversidadeNew = em.getReference(ingressomudancauniversidadeNew.getClass(), ingressomudancauniversidadeNew.getIdEstudante());
                estudante.setIngressomudancauniversidade(ingressomudancauniversidadeNew);
            }
            List<Users> attachedUsersListNew = new ArrayList<Users>();
            for (Users usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUtilizador());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            estudante.setUsersList(usersListNew);
            List<Mudancacurso> attachedMudancacursoListNew = new ArrayList<Mudancacurso>();
            for (Mudancacurso mudancacursoListNewMudancacursoToAttach : mudancacursoListNew) {
                mudancacursoListNewMudancacursoToAttach = em.getReference(mudancacursoListNewMudancacursoToAttach.getClass(), mudancacursoListNewMudancacursoToAttach.getIdmudanca());
                attachedMudancacursoListNew.add(mudancacursoListNewMudancacursoToAttach);
            }
            mudancacursoListNew = attachedMudancacursoListNew;
            estudante.setMudancacursoList(mudancacursoListNew);
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getMatriculaPK());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            estudante.setMatriculaList(matriculaListNew);
            List<Disciplinaanulada> attachedDisciplinaanuladaListNew = new ArrayList<Disciplinaanulada>();
            for (Disciplinaanulada disciplinaanuladaListNewDisciplinaanuladaToAttach : disciplinaanuladaListNew) {
                disciplinaanuladaListNewDisciplinaanuladaToAttach = em.getReference(disciplinaanuladaListNewDisciplinaanuladaToAttach.getClass(), disciplinaanuladaListNewDisciplinaanuladaToAttach.getIdanulacao());
                attachedDisciplinaanuladaListNew.add(disciplinaanuladaListNewDisciplinaanuladaToAttach);
            }
            disciplinaanuladaListNew = attachedDisciplinaanuladaListNew;
            estudante.setDisciplinaanuladaList(disciplinaanuladaListNew);
            List<Ingressopercabolsa> attachedIngressopercabolsaListNew = new ArrayList<Ingressopercabolsa>();
            for (Ingressopercabolsa ingressopercabolsaListNewIngressopercabolsaToAttach : ingressopercabolsaListNew) {
                ingressopercabolsaListNewIngressopercabolsaToAttach = em.getReference(ingressopercabolsaListNewIngressopercabolsaToAttach.getClass(), ingressopercabolsaListNewIngressopercabolsaToAttach.getIngressopercabolsaPK());
                attachedIngressopercabolsaListNew.add(ingressopercabolsaListNewIngressopercabolsaToAttach);
            }
            ingressopercabolsaListNew = attachedIngressopercabolsaListNew;
            estudante.setIngressopercabolsaList(ingressopercabolsaListNew);
            List<Notapauta> attachedNotapautaListNew = new ArrayList<Notapauta>();
            for (Notapauta notapautaListNewNotapautaToAttach : notapautaListNew) {
                notapautaListNewNotapautaToAttach = em.getReference(notapautaListNewNotapautaToAttach.getClass(), notapautaListNewNotapautaToAttach.getNotapautaPK());
                attachedNotapautaListNew.add(notapautaListNewNotapautaToAttach);
            }
            notapautaListNew = attachedNotapautaListNew;
            estudante.setNotapautaList(notapautaListNew);
            estudante = em.merge(estudante);
            if (documentoNew != null && !documentoNew.equals(documentoOld)) {
                Estudante oldEstudanteOfDocumento = documentoNew.getEstudante();
                if (oldEstudanteOfDocumento != null) {
                    oldEstudanteOfDocumento.setDocumento(null);
                    oldEstudanteOfDocumento = em.merge(oldEstudanteOfDocumento);
                }
                documentoNew.setEstudante(estudante);
                documentoNew = em.merge(documentoNew);
            }
            if (ingressotransferenciaNew != null && !ingressotransferenciaNew.equals(ingressotransferenciaOld)) {
                Estudante oldEstudanteOfIngressotransferencia = ingressotransferenciaNew.getEstudante();
                if (oldEstudanteOfIngressotransferencia != null) {
                    oldEstudanteOfIngressotransferencia.setIngressotransferencia(null);
                    oldEstudanteOfIngressotransferencia = em.merge(oldEstudanteOfIngressotransferencia);
                }
                ingressotransferenciaNew.setEstudante(estudante);
                ingressotransferenciaNew = em.merge(ingressotransferenciaNew);
            }
            if (profissaoNew != null && !profissaoNew.equals(profissaoOld)) {
                Estudante oldEstudanteOfProfissao = profissaoNew.getEstudante();
                if (oldEstudanteOfProfissao != null) {
                    oldEstudanteOfProfissao.setProfissao(null);
                    oldEstudanteOfProfissao = em.merge(oldEstudanteOfProfissao);
                }
                profissaoNew.setEstudante(estudante);
                profissaoNew = em.merge(profissaoNew);
            }
            if (especialNew != null && !especialNew.equals(especialOld)) {
                Estudante oldEstudanteOfEspecial = especialNew.getEstudante();
                if (oldEstudanteOfEspecial != null) {
                    oldEstudanteOfEspecial.setEspecial(null);
                    oldEstudanteOfEspecial = em.merge(oldEstudanteOfEspecial);
                }
                especialNew.setEstudante(estudante);
                especialNew = em.merge(especialNew);
            }
            if (ingressoexameadmissaoNew != null && !ingressoexameadmissaoNew.equals(ingressoexameadmissaoOld)) {
                Estudante oldEstudanteOfIngressoexameadmissao = ingressoexameadmissaoNew.getEstudante();
                if (oldEstudanteOfIngressoexameadmissao != null) {
                    oldEstudanteOfIngressoexameadmissao.setIngressoexameadmissao(null);
                    oldEstudanteOfIngressoexameadmissao = em.merge(oldEstudanteOfIngressoexameadmissao);
                }
                ingressoexameadmissaoNew.setEstudante(estudante);
                ingressoexameadmissaoNew = em.merge(ingressoexameadmissaoNew);
            }
            if (bolsaOld != null && !bolsaOld.equals(bolsaNew)) {
                bolsaOld.getEstudanteList().remove(estudante);
                bolsaOld = em.merge(bolsaOld);
            }
            if (bolsaNew != null && !bolsaNew.equals(bolsaOld)) {
                bolsaNew.getEstudanteList().add(estudante);
                bolsaNew = em.merge(bolsaNew);
            }
            if (idCursoOld != null && !idCursoOld.equals(idCursoNew)) {
                idCursoOld.getEstudanteList().remove(estudante);
                idCursoOld = em.merge(idCursoOld);
            }
            if (idCursoNew != null && !idCursoNew.equals(idCursoOld)) {
                idCursoNew.getEstudanteList().add(estudante);
                idCursoNew = em.merge(idCursoNew);
            }
            if (cursoingressoOld != null && !cursoingressoOld.equals(cursoingressoNew)) {
                cursoingressoOld.getEstudanteList().remove(estudante);
                cursoingressoOld = em.merge(cursoingressoOld);
            }
            if (cursoingressoNew != null && !cursoingressoNew.equals(cursoingressoOld)) {
                cursoingressoNew.getEstudanteList().add(estudante);
                cursoingressoNew = em.merge(cursoingressoNew);
            }
            if (cursocurrenteOld != null && !cursocurrenteOld.equals(cursocurrenteNew)) {
                cursocurrenteOld.getEstudanteList().remove(estudante);
                cursocurrenteOld = em.merge(cursocurrenteOld);
            }
            if (cursocurrenteNew != null && !cursocurrenteNew.equals(cursocurrenteOld)) {
                cursocurrenteNew.getEstudanteList().add(estudante);
                cursocurrenteNew = em.merge(cursocurrenteNew);
            }
            if (estadoCivilOld != null && !estadoCivilOld.equals(estadoCivilNew)) {
                estadoCivilOld.getEstudanteList().remove(estudante);
                estadoCivilOld = em.merge(estadoCivilOld);
            }
            if (estadoCivilNew != null && !estadoCivilNew.equals(estadoCivilOld)) {
                estadoCivilNew.getEstudanteList().add(estudante);
                estadoCivilNew = em.merge(estadoCivilNew);
            }
            if (nacionalidadeOld != null && !nacionalidadeOld.equals(nacionalidadeNew)) {
                nacionalidadeOld.getEstudanteList().remove(estudante);
                nacionalidadeOld = em.merge(nacionalidadeOld);
            }
            if (nacionalidadeNew != null && !nacionalidadeNew.equals(nacionalidadeOld)) {
                nacionalidadeNew.getEstudanteList().add(estudante);
                nacionalidadeNew = em.merge(nacionalidadeNew);
            }
            if (escolaPaisOld != null && !escolaPaisOld.equals(escolaPaisNew)) {
                escolaPaisOld.getEstudanteList().remove(estudante);
                escolaPaisOld = em.merge(escolaPaisOld);
            }
            if (escolaPaisNew != null && !escolaPaisNew.equals(escolaPaisOld)) {
                escolaPaisNew.getEstudanteList().add(estudante);
                escolaPaisNew = em.merge(escolaPaisNew);
            }
            if (viaIngressoOld != null && !viaIngressoOld.equals(viaIngressoNew)) {
                viaIngressoOld.getEstudanteList().remove(estudante);
                viaIngressoOld = em.merge(viaIngressoOld);
            }
            if (viaIngressoNew != null && !viaIngressoNew.equals(viaIngressoOld)) {
                viaIngressoNew.getEstudanteList().add(estudante);
                viaIngressoNew = em.merge(viaIngressoNew);
            }
            if (enderecofNew != null && !enderecofNew.equals(enderecofOld)) {
                Estudante oldEstudanteOfEnderecof = enderecofNew.getEstudante();
                if (oldEstudanteOfEnderecof != null) {
                    oldEstudanteOfEnderecof.setEnderecof(null);
                    oldEstudanteOfEnderecof = em.merge(oldEstudanteOfEnderecof);
                }
                enderecofNew.setEstudante(estudante);
                enderecofNew = em.merge(enderecofNew);
            }
            if (ingressobolseiroNew != null && !ingressobolseiroNew.equals(ingressobolseiroOld)) {
                Estudante oldEstudanteOfIngressobolseiro = ingressobolseiroNew.getEstudante();
                if (oldEstudanteOfIngressobolseiro != null) {
                    oldEstudanteOfIngressobolseiro.setIngressobolseiro(null);
                    oldEstudanteOfIngressobolseiro = em.merge(oldEstudanteOfIngressobolseiro);
                }
                ingressobolseiroNew.setEstudante(estudante);
                ingressobolseiroNew = em.merge(ingressobolseiroNew);
            }
            if (enderecoNew != null && !enderecoNew.equals(enderecoOld)) {
                Estudante oldEstudanteOfEndereco = enderecoNew.getEstudante();
                if (oldEstudanteOfEndereco != null) {
                    oldEstudanteOfEndereco.setEndereco(null);
                    oldEstudanteOfEndereco = em.merge(oldEstudanteOfEndereco);
                }
                enderecoNew.setEstudante(estudante);
                enderecoNew = em.merge(enderecoNew);
            }
            if (ingressomudancauniversidadeNew != null && !ingressomudancauniversidadeNew.equals(ingressomudancauniversidadeOld)) {
                Estudante oldEstudanteOfIngressomudancauniversidade = ingressomudancauniversidadeNew.getEstudante();
                if (oldEstudanteOfIngressomudancauniversidade != null) {
                    oldEstudanteOfIngressomudancauniversidade.setIngressomudancauniversidade(null);
                    oldEstudanteOfIngressomudancauniversidade = em.merge(oldEstudanteOfIngressomudancauniversidade);
                }
                ingressomudancauniversidadeNew.setEstudante(estudante);
                ingressomudancauniversidadeNew = em.merge(ingressomudancauniversidadeNew);
            }
            for (Users usersListOldUsers : usersListOld) {
                if (!usersListNew.contains(usersListOldUsers)) {
                    usersListOldUsers.setIdEstudante(null);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (Users usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    Estudante oldIdEstudanteOfUsersListNewUsers = usersListNewUsers.getIdEstudante();
                    usersListNewUsers.setIdEstudante(estudante);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldIdEstudanteOfUsersListNewUsers != null && !oldIdEstudanteOfUsersListNewUsers.equals(estudante)) {
                        oldIdEstudanteOfUsersListNewUsers.getUsersList().remove(usersListNewUsers);
                        oldIdEstudanteOfUsersListNewUsers = em.merge(oldIdEstudanteOfUsersListNewUsers);
                    }
                }
            }
            for (Mudancacurso mudancacursoListNewMudancacurso : mudancacursoListNew) {
                if (!mudancacursoListOld.contains(mudancacursoListNewMudancacurso)) {
                    Estudante oldIdEstudanteOfMudancacursoListNewMudancacurso = mudancacursoListNewMudancacurso.getIdEstudante();
                    mudancacursoListNewMudancacurso.setIdEstudante(estudante);
                    mudancacursoListNewMudancacurso = em.merge(mudancacursoListNewMudancacurso);
                    if (oldIdEstudanteOfMudancacursoListNewMudancacurso != null && !oldIdEstudanteOfMudancacursoListNewMudancacurso.equals(estudante)) {
                        oldIdEstudanteOfMudancacursoListNewMudancacurso.getMudancacursoList().remove(mudancacursoListNewMudancacurso);
                        oldIdEstudanteOfMudancacursoListNewMudancacurso = em.merge(oldIdEstudanteOfMudancacursoListNewMudancacurso);
                    }
                }
            }
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Estudante oldEstudanteOfMatriculaListNewMatricula = matriculaListNewMatricula.getEstudante();
                    matriculaListNewMatricula.setEstudante(estudante);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldEstudanteOfMatriculaListNewMatricula != null && !oldEstudanteOfMatriculaListNewMatricula.equals(estudante)) {
                        oldEstudanteOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldEstudanteOfMatriculaListNewMatricula = em.merge(oldEstudanteOfMatriculaListNewMatricula);
                    }
                }
            }
            for (Disciplinaanulada disciplinaanuladaListOldDisciplinaanulada : disciplinaanuladaListOld) {
                if (!disciplinaanuladaListNew.contains(disciplinaanuladaListOldDisciplinaanulada)) {
                    disciplinaanuladaListOldDisciplinaanulada.setIdEstudante(null);
                    disciplinaanuladaListOldDisciplinaanulada = em.merge(disciplinaanuladaListOldDisciplinaanulada);
                }
            }
            for (Disciplinaanulada disciplinaanuladaListNewDisciplinaanulada : disciplinaanuladaListNew) {
                if (!disciplinaanuladaListOld.contains(disciplinaanuladaListNewDisciplinaanulada)) {
                    Estudante oldIdEstudanteOfDisciplinaanuladaListNewDisciplinaanulada = disciplinaanuladaListNewDisciplinaanulada.getIdEstudante();
                    disciplinaanuladaListNewDisciplinaanulada.setIdEstudante(estudante);
                    disciplinaanuladaListNewDisciplinaanulada = em.merge(disciplinaanuladaListNewDisciplinaanulada);
                    if (oldIdEstudanteOfDisciplinaanuladaListNewDisciplinaanulada != null && !oldIdEstudanteOfDisciplinaanuladaListNewDisciplinaanulada.equals(estudante)) {
                        oldIdEstudanteOfDisciplinaanuladaListNewDisciplinaanulada.getDisciplinaanuladaList().remove(disciplinaanuladaListNewDisciplinaanulada);
                        oldIdEstudanteOfDisciplinaanuladaListNewDisciplinaanulada = em.merge(oldIdEstudanteOfDisciplinaanuladaListNewDisciplinaanulada);
                    }
                }
            }
            for (Ingressopercabolsa ingressopercabolsaListNewIngressopercabolsa : ingressopercabolsaListNew) {
                if (!ingressopercabolsaListOld.contains(ingressopercabolsaListNewIngressopercabolsa)) {
                    Estudante oldEstudanteOfIngressopercabolsaListNewIngressopercabolsa = ingressopercabolsaListNewIngressopercabolsa.getEstudante();
                    ingressopercabolsaListNewIngressopercabolsa.setEstudante(estudante);
                    ingressopercabolsaListNewIngressopercabolsa = em.merge(ingressopercabolsaListNewIngressopercabolsa);
                    if (oldEstudanteOfIngressopercabolsaListNewIngressopercabolsa != null && !oldEstudanteOfIngressopercabolsaListNewIngressopercabolsa.equals(estudante)) {
                        oldEstudanteOfIngressopercabolsaListNewIngressopercabolsa.getIngressopercabolsaList().remove(ingressopercabolsaListNewIngressopercabolsa);
                        oldEstudanteOfIngressopercabolsaListNewIngressopercabolsa = em.merge(oldEstudanteOfIngressopercabolsaListNewIngressopercabolsa);
                    }
                }
            }
            for (Notapauta notapautaListNewNotapauta : notapautaListNew) {
                if (!notapautaListOld.contains(notapautaListNewNotapauta)) {
                    Estudante oldEstudanteOfNotapautaListNewNotapauta = notapautaListNewNotapauta.getEstudante();
                    notapautaListNewNotapauta.setEstudante(estudante);
                    notapautaListNewNotapauta = em.merge(notapautaListNewNotapauta);
                    if (oldEstudanteOfNotapautaListNewNotapauta != null && !oldEstudanteOfNotapautaListNewNotapauta.equals(estudante)) {
                        oldEstudanteOfNotapautaListNewNotapauta.getNotapautaList().remove(notapautaListNewNotapauta);
                        oldEstudanteOfNotapautaListNewNotapauta = em.merge(oldEstudanteOfNotapautaListNewNotapauta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = estudante.getIdEstudante();
                if (findEstudante(id) == null) {
                    throw new NonexistentEntityException("The estudante with id " + id + " no longer exists.");
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
            Estudante estudante;
            try {
                estudante = em.getReference(Estudante.class, id);
                estudante.getIdEstudante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Documento documentoOrphanCheck = estudante.getDocumento();
            if (documentoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Documento " + documentoOrphanCheck + " in its documento field has a non-nullable estudante field.");
            }
            Ingressotransferencia ingressotransferenciaOrphanCheck = estudante.getIngressotransferencia();
            if (ingressotransferenciaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Ingressotransferencia " + ingressotransferenciaOrphanCheck + " in its ingressotransferencia field has a non-nullable estudante field.");
            }
            Profissao profissaoOrphanCheck = estudante.getProfissao();
            if (profissaoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Profissao " + profissaoOrphanCheck + " in its profissao field has a non-nullable estudante field.");
            }
            Especial especialOrphanCheck = estudante.getEspecial();
            if (especialOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Especial " + especialOrphanCheck + " in its especial field has a non-nullable estudante field.");
            }
            Ingressoexameadmissao ingressoexameadmissaoOrphanCheck = estudante.getIngressoexameadmissao();
            if (ingressoexameadmissaoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Ingressoexameadmissao " + ingressoexameadmissaoOrphanCheck + " in its ingressoexameadmissao field has a non-nullable estudante field.");
            }
            Enderecof enderecofOrphanCheck = estudante.getEnderecof();
            if (enderecofOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Enderecof " + enderecofOrphanCheck + " in its enderecof field has a non-nullable estudante field.");
            }
            Ingressobolseiro ingressobolseiroOrphanCheck = estudante.getIngressobolseiro();
            if (ingressobolseiroOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Ingressobolseiro " + ingressobolseiroOrphanCheck + " in its ingressobolseiro field has a non-nullable estudante field.");
            }
            Endereco enderecoOrphanCheck = estudante.getEndereco();
            if (enderecoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Endereco " + enderecoOrphanCheck + " in its endereco field has a non-nullable estudante field.");
            }
            Ingressomudancauniversidade ingressomudancauniversidadeOrphanCheck = estudante.getIngressomudancauniversidade();
            if (ingressomudancauniversidadeOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Ingressomudancauniversidade " + ingressomudancauniversidadeOrphanCheck + " in its ingressomudancauniversidade field has a non-nullable estudante field.");
            }
            List<Mudancacurso> mudancacursoListOrphanCheck = estudante.getMudancacursoList();
            for (Mudancacurso mudancacursoListOrphanCheckMudancacurso : mudancacursoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Mudancacurso " + mudancacursoListOrphanCheckMudancacurso + " in its mudancacursoList field has a non-nullable idEstudante field.");
            }
            List<Matricula> matriculaListOrphanCheck = estudante.getMatriculaList();
            for (Matricula matriculaListOrphanCheckMatricula : matriculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Matricula " + matriculaListOrphanCheckMatricula + " in its matriculaList field has a non-nullable estudante field.");
            }
            List<Ingressopercabolsa> ingressopercabolsaListOrphanCheck = estudante.getIngressopercabolsaList();
            for (Ingressopercabolsa ingressopercabolsaListOrphanCheckIngressopercabolsa : ingressopercabolsaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Ingressopercabolsa " + ingressopercabolsaListOrphanCheckIngressopercabolsa + " in its ingressopercabolsaList field has a non-nullable estudante field.");
            }
            List<Notapauta> notapautaListOrphanCheck = estudante.getNotapautaList();
            for (Notapauta notapautaListOrphanCheckNotapauta : notapautaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estudante (" + estudante + ") cannot be destroyed since the Notapauta " + notapautaListOrphanCheckNotapauta + " in its notapautaList field has a non-nullable estudante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Bolsa bolsa = estudante.getBolsa();
            if (bolsa != null) {
                bolsa.getEstudanteList().remove(estudante);
                bolsa = em.merge(bolsa);
            }
            Curso idCurso = estudante.getIdCurso();
            if (idCurso != null) {
                idCurso.getEstudanteList().remove(estudante);
                idCurso = em.merge(idCurso);
            }
            Curso cursoingresso = estudante.getCursoingresso();
            if (cursoingresso != null) {
                cursoingresso.getEstudanteList().remove(estudante);
                cursoingresso = em.merge(cursoingresso);
            }
            Curso cursocurrente = estudante.getCursocurrente();
            if (cursocurrente != null) {
                cursocurrente.getEstudanteList().remove(estudante);
                cursocurrente = em.merge(cursocurrente);
            }
            Estadocivil estadoCivil = estudante.getEstadoCivil();
            if (estadoCivil != null) {
                estadoCivil.getEstudanteList().remove(estudante);
                estadoCivil = em.merge(estadoCivil);
            }
            Pais nacionalidade = estudante.getNacionalidade();
            if (nacionalidade != null) {
                nacionalidade.getEstudanteList().remove(estudante);
                nacionalidade = em.merge(nacionalidade);
            }
            Pais escolaPais = estudante.getEscolaPais();
            if (escolaPais != null) {
                escolaPais.getEstudanteList().remove(estudante);
                escolaPais = em.merge(escolaPais);
            }
            Viaingresso viaIngresso = estudante.getViaIngresso();
            if (viaIngresso != null) {
                viaIngresso.getEstudanteList().remove(estudante);
                viaIngresso = em.merge(viaIngresso);
            }
            List<Users> usersList = estudante.getUsersList();
            for (Users usersListUsers : usersList) {
                usersListUsers.setIdEstudante(null);
                usersListUsers = em.merge(usersListUsers);
            }
            List<Disciplinaanulada> disciplinaanuladaList = estudante.getDisciplinaanuladaList();
            for (Disciplinaanulada disciplinaanuladaListDisciplinaanulada : disciplinaanuladaList) {
                disciplinaanuladaListDisciplinaanulada.setIdEstudante(null);
                disciplinaanuladaListDisciplinaanulada = em.merge(disciplinaanuladaListDisciplinaanulada);
            }
            em.remove(estudante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estudante> findEstudanteEntities() {
        return findEstudanteEntities(true, -1, -1);
    }

    public List<Estudante> findEstudanteEntities(int maxResults, int firstResult) {
        return findEstudanteEntities(false, maxResults, firstResult);
    }

    private List<Estudante> findEstudanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudante.class));
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

    public Estudante findEstudante(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudante.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstudanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudante> rt = cq.from(Estudante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
