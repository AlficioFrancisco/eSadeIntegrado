/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Paulino Francisco
 */
@Entity
@Table(name = "disciplinaanulada", catalog = "integra", schema = "esira")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disciplinaanulada.findAll", query = "SELECT d FROM Disciplinaanulada d")})
public class Disciplinaanulada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idanulacao", nullable = false)
    private Integer idanulacao;
    @Column(name = "motivo", length = 2147483647)
    private String motivo;
    @Column(name = "dataanulacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataanulacao;
    @Column(name = "doc", length = 2147483647)
    private String doc;
    @Column(name = "dataconfirmacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataconfirmacao;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "id_estudante", referencedColumnName = "id_estudante")
    @ManyToOne(fetch = FetchType.LAZY)
    private Estudante idEstudante;
    @JoinColumn(name = "funcionario", referencedColumnName = "id_funcionario")
    @ManyToOne(fetch = FetchType.LAZY)
    private Funcionario funcionario;

    public Disciplinaanulada() {
    }

    public Disciplinaanulada(Integer idanulacao) {
        this.idanulacao = idanulacao;
    }

    public Integer getIdanulacao() {
        return idanulacao;
    }

    public void setIdanulacao(Integer idanulacao) {
        this.idanulacao = idanulacao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getDataanulacao() {
        return dataanulacao;
    }

    public void setDataanulacao(Date dataanulacao) {
        this.dataanulacao = dataanulacao;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public Date getDataconfirmacao() {
        return dataconfirmacao;
    }

    public void setDataconfirmacao(Date dataconfirmacao) {
        this.dataconfirmacao = dataconfirmacao;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Estudante getIdEstudante() {
        return idEstudante;
    }

    public void setIdEstudante(Estudante idEstudante) {
        this.idEstudante = idEstudante;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idanulacao != null ? idanulacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disciplinaanulada)) {
            return false;
        }
        Disciplinaanulada other = (Disciplinaanulada) object;
        if ((this.idanulacao == null && other.idanulacao != null) || (this.idanulacao != null && !this.idanulacao.equals(other.idanulacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Disciplinaanulada[ idanulacao=" + idanulacao + " ]";
    }
    
}
