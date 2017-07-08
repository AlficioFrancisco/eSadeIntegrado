/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Paulino Francisco
 */
@Entity
@Table(name = "estudante", catalog = "integra", schema = "esira")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudante.findAll", query = "SELECT e FROM Estudante e")})
public class Estudante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estudante", nullable = false)
    private Long idEstudante;
    @Column(name = "nr_estudante", length = 45)
    private String nrEstudante;
    @Column(name = "nome_completo", length = 255)
    private String nomeCompleto;
    @Column(name = "apelido", length = 45)
    private String apelido;
    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @Column(name = "nome_pai", length = 255)
    private String nomePai;
    @Column(name = "nome_mae", length = 255)
    private String nomeMae;
    @Column(name = "naturalidade", length = 45)
    private String naturalidade;
    @Column(name = "localidade", length = 255)
    private String localidade;
    @Column(name = "distrito", length = 45)
    private String distrito;
    @Column(name = "ano_ter_medio")
    private Integer anoTerMedio;
    @Column(name = "escola", length = 255)
    private String escola;
    @Column(name = "ano_ingresso")
    private Integer anoIngresso;
    @Column(name = "nivel_frequencia")
    private Integer nivelFrequencia;
    @Column(name = "pasta_documento", length = 255)
    private String pastaDocumento;
    @Column(name = "nome_encarregado", length = 255)
    private String nomeEncarregado;
    @Column(name = "contacto_encarregado", length = 45)
    private String contactoEncarregado;
    @Column(name = "grau_parentesco", length = 45)
    private String grauParentesco;
    @Column(name = "tam_agregado_familiar")
    private Integer tamAgregadoFamiliar;
    @Column(name = "primeira_universidade", length = 255)
    private String primeiraUniversidade;
    @Column(name = "idioma", length = 45)
    private String idioma;
    @Column(name = "email", length = 45)
    private String email;
    @Column(name = "nota_admissao")
    private Integer notaAdmissao;
    @Column(name = "outra_via_ingresso", length = 45)
    private String outraViaIngresso;
    @Column(name = "istrabalhador")
    private Boolean istrabalhador;
    @Column(name = "is_transferencia")
    private Boolean isTransferencia;
    @Column(name = "is_mudanca_un")
    private Boolean isMudancaUn;
    @Column(name = "is_bolseiro")
    private Boolean isBolseiro;
    @Column(name = "masculino")
    private Boolean masculino;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "testudo")
    private Integer testudo;
    @Column(name = "ultimamatricula")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimamatricula;
    @Column(name = "mudancac")
    private Short mudancac;
    @Column(name = "transferido")
    private Short transferido;
    @Column(name = "turno")
    private Integer turno;
    @Column(name = "turma")
    private Integer turma;
    @Column(name = "graduado")
    private Boolean graduado;
    @Column(name = "planoc")
    private Integer planoc;
    @Column(name = "provincia")
    private BigInteger provincia;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private Documento documento;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private Ingressotransferencia ingressotransferencia;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private Profissao profissao;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private Especial especial;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private Ingressoexameadmissao ingressoexameadmissao;
    @OneToMany(mappedBy = "idEstudante", fetch = FetchType.LAZY)
    private List<Users> usersList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstudante", fetch = FetchType.LAZY)
    private List<Mudancacurso> mudancacursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private List<Matricula> matriculaList;
    @JoinColumn(name = "bolsa", referencedColumnName = "id_bolsa")
    @ManyToOne(fetch = FetchType.LAZY)
    private Bolsa bolsa;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne(fetch = FetchType.LAZY)
    private Curso idCurso;
    @JoinColumn(name = "cursoingresso", referencedColumnName = "id_curso", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Curso cursoingresso;
    @JoinColumn(name = "cursocurrente", referencedColumnName = "id_curso", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Curso cursocurrente;
    @JoinColumn(name = "estado_civil", referencedColumnName = "id_estado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Estadocivil estadoCivil;
    @JoinColumn(name = "nacionalidade", referencedColumnName = "id_pais", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pais nacionalidade;
    @JoinColumn(name = "escola_pais", referencedColumnName = "id_pais")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pais escolaPais;
    @JoinColumn(name = "via_ingresso", referencedColumnName = "id_via_ingresso")
    @ManyToOne(fetch = FetchType.LAZY)
    private Viaingresso viaIngresso;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private Enderecof enderecof;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private Ingressobolseiro ingressobolseiro;
    @OneToMany(mappedBy = "idEstudante", fetch = FetchType.LAZY)
    private List<Disciplinaanulada> disciplinaanuladaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private List<Ingressopercabolsa> ingressopercabolsaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private List<Notapauta> notapautaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private Endereco endereco;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "estudante", fetch = FetchType.LAZY)
    private Ingressomudancauniversidade ingressomudancauniversidade;

    public Estudante() {
    }

    public Estudante(Long idEstudante) {
        this.idEstudante = idEstudante;
    }

    public Long getIdEstudante() {
        return idEstudante;
    }

    public void setIdEstudante(Long idEstudante) {
        this.idEstudante = idEstudante;
    }

    public String getNrEstudante() {
        return nrEstudante;
    }

    public void setNrEstudante(String nrEstudante) {
        this.nrEstudante = nrEstudante;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public Integer getAnoTerMedio() {
        return anoTerMedio;
    }

    public void setAnoTerMedio(Integer anoTerMedio) {
        this.anoTerMedio = anoTerMedio;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public Integer getAnoIngresso() {
        return anoIngresso;
    }

    public void setAnoIngresso(Integer anoIngresso) {
        this.anoIngresso = anoIngresso;
    }

    public Integer getNivelFrequencia() {
        return nivelFrequencia;
    }

    public void setNivelFrequencia(Integer nivelFrequencia) {
        this.nivelFrequencia = nivelFrequencia;
    }

    public String getPastaDocumento() {
        return pastaDocumento;
    }

    public void setPastaDocumento(String pastaDocumento) {
        this.pastaDocumento = pastaDocumento;
    }

    public String getNomeEncarregado() {
        return nomeEncarregado;
    }

    public void setNomeEncarregado(String nomeEncarregado) {
        this.nomeEncarregado = nomeEncarregado;
    }

    public String getContactoEncarregado() {
        return contactoEncarregado;
    }

    public void setContactoEncarregado(String contactoEncarregado) {
        this.contactoEncarregado = contactoEncarregado;
    }

    public String getGrauParentesco() {
        return grauParentesco;
    }

    public void setGrauParentesco(String grauParentesco) {
        this.grauParentesco = grauParentesco;
    }

    public Integer getTamAgregadoFamiliar() {
        return tamAgregadoFamiliar;
    }

    public void setTamAgregadoFamiliar(Integer tamAgregadoFamiliar) {
        this.tamAgregadoFamiliar = tamAgregadoFamiliar;
    }

    public String getPrimeiraUniversidade() {
        return primeiraUniversidade;
    }

    public void setPrimeiraUniversidade(String primeiraUniversidade) {
        this.primeiraUniversidade = primeiraUniversidade;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNotaAdmissao() {
        return notaAdmissao;
    }

    public void setNotaAdmissao(Integer notaAdmissao) {
        this.notaAdmissao = notaAdmissao;
    }

    public String getOutraViaIngresso() {
        return outraViaIngresso;
    }

    public void setOutraViaIngresso(String outraViaIngresso) {
        this.outraViaIngresso = outraViaIngresso;
    }

    public Boolean getIstrabalhador() {
        return istrabalhador;
    }

    public void setIstrabalhador(Boolean istrabalhador) {
        this.istrabalhador = istrabalhador;
    }

    public Boolean getIsTransferencia() {
        return isTransferencia;
    }

    public void setIsTransferencia(Boolean isTransferencia) {
        this.isTransferencia = isTransferencia;
    }

    public Boolean getIsMudancaUn() {
        return isMudancaUn;
    }

    public void setIsMudancaUn(Boolean isMudancaUn) {
        this.isMudancaUn = isMudancaUn;
    }

    public Boolean getIsBolseiro() {
        return isBolseiro;
    }

    public void setIsBolseiro(Boolean isBolseiro) {
        this.isBolseiro = isBolseiro;
    }

    public Boolean getMasculino() {
        return masculino;
    }

    public void setMasculino(Boolean masculino) {
        this.masculino = masculino;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getTestudo() {
        return testudo;
    }

    public void setTestudo(Integer testudo) {
        this.testudo = testudo;
    }

    public Date getUltimamatricula() {
        return ultimamatricula;
    }

    public void setUltimamatricula(Date ultimamatricula) {
        this.ultimamatricula = ultimamatricula;
    }

    public Short getMudancac() {
        return mudancac;
    }

    public void setMudancac(Short mudancac) {
        this.mudancac = mudancac;
    }

    public Short getTransferido() {
        return transferido;
    }

    public void setTransferido(Short transferido) {
        this.transferido = transferido;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public Integer getTurma() {
        return turma;
    }

    public void setTurma(Integer turma) {
        this.turma = turma;
    }

    public Boolean getGraduado() {
        return graduado;
    }

    public void setGraduado(Boolean graduado) {
        this.graduado = graduado;
    }

    public Integer getPlanoc() {
        return planoc;
    }

    public void setPlanoc(Integer planoc) {
        this.planoc = planoc;
    }

    public BigInteger getProvincia() {
        return provincia;
    }

    public void setProvincia(BigInteger provincia) {
        this.provincia = provincia;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Ingressotransferencia getIngressotransferencia() {
        return ingressotransferencia;
    }

    public void setIngressotransferencia(Ingressotransferencia ingressotransferencia) {
        this.ingressotransferencia = ingressotransferencia;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public Especial getEspecial() {
        return especial;
    }

    public void setEspecial(Especial especial) {
        this.especial = especial;
    }

    public Ingressoexameadmissao getIngressoexameadmissao() {
        return ingressoexameadmissao;
    }

    public void setIngressoexameadmissao(Ingressoexameadmissao ingressoexameadmissao) {
        this.ingressoexameadmissao = ingressoexameadmissao;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @XmlTransient
    public List<Mudancacurso> getMudancacursoList() {
        return mudancacursoList;
    }

    public void setMudancacursoList(List<Mudancacurso> mudancacursoList) {
        this.mudancacursoList = mudancacursoList;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    public Bolsa getBolsa() {
        return bolsa;
    }

    public void setBolsa(Bolsa bolsa) {
        this.bolsa = bolsa;
    }

    public Curso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Curso idCurso) {
        this.idCurso = idCurso;
    }

    public Curso getCursoingresso() {
        return cursoingresso;
    }

    public void setCursoingresso(Curso cursoingresso) {
        this.cursoingresso = cursoingresso;
    }

    public Curso getCursocurrente() {
        return cursocurrente;
    }

    public void setCursocurrente(Curso cursocurrente) {
        this.cursocurrente = cursocurrente;
    }

    public Estadocivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Estadocivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Pais getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Pais nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Pais getEscolaPais() {
        return escolaPais;
    }

    public void setEscolaPais(Pais escolaPais) {
        this.escolaPais = escolaPais;
    }

    public Viaingresso getViaIngresso() {
        return viaIngresso;
    }

    public void setViaIngresso(Viaingresso viaIngresso) {
        this.viaIngresso = viaIngresso;
    }

    public Enderecof getEnderecof() {
        return enderecof;
    }

    public void setEnderecof(Enderecof enderecof) {
        this.enderecof = enderecof;
    }

    public Ingressobolseiro getIngressobolseiro() {
        return ingressobolseiro;
    }

    public void setIngressobolseiro(Ingressobolseiro ingressobolseiro) {
        this.ingressobolseiro = ingressobolseiro;
    }

    @XmlTransient
    public List<Disciplinaanulada> getDisciplinaanuladaList() {
        return disciplinaanuladaList;
    }

    public void setDisciplinaanuladaList(List<Disciplinaanulada> disciplinaanuladaList) {
        this.disciplinaanuladaList = disciplinaanuladaList;
    }

    @XmlTransient
    public List<Ingressopercabolsa> getIngressopercabolsaList() {
        return ingressopercabolsaList;
    }

    public void setIngressopercabolsaList(List<Ingressopercabolsa> ingressopercabolsaList) {
        this.ingressopercabolsaList = ingressopercabolsaList;
    }

    @XmlTransient
    public List<Notapauta> getNotapautaList() {
        return notapautaList;
    }

    public void setNotapautaList(List<Notapauta> notapautaList) {
        this.notapautaList = notapautaList;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Ingressomudancauniversidade getIngressomudancauniversidade() {
        return ingressomudancauniversidade;
    }

    public void setIngressomudancauniversidade(Ingressomudancauniversidade ingressomudancauniversidade) {
        this.ingressomudancauniversidade = ingressomudancauniversidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstudante != null ? idEstudante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudante)) {
            return false;
        }
        Estudante other = (Estudante) object;
        if ((this.idEstudante == null && other.idEstudante != null) || (this.idEstudante != null && !this.idEstudante.equals(other.idEstudante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Estudante[ idEstudante=" + idEstudante + " ]";
    }
    
}
