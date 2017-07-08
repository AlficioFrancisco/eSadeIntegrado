/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Paulino Francisco
 */
@Entity
@Table(name = "parametrodocenteestudante", catalog = "integra", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametrodocenteestudante.findAll", query = "SELECT p FROM Parametrodocenteestudante p")})
public class Parametrodocenteestudante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idparametrodocenteestudante", nullable = false)
    private Short idparametrodocenteestudante;
    @Column(name = "descricao", length = 255)
    private String descricao;
    @Column(name = "codigo", length = 255)
    private String codigo;
    @JoinColumn(name = "idpontuacaodocenteestudante", referencedColumnName = "idpontuacaodocenteestudante")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pontuacaodocenteestudante idpontuacaodocenteestudante;
    @JoinColumn(name = "idquestaodocenteestudante", referencedColumnName = "idquestaodocenteestudante")
    @ManyToOne(fetch = FetchType.LAZY)
    private Questaodocenteestudante idquestaodocenteestudante;

    public Parametrodocenteestudante() {
    }

    public Parametrodocenteestudante(Short idparametrodocenteestudante) {
        this.idparametrodocenteestudante = idparametrodocenteestudante;
    }

    public Short getIdparametrodocenteestudante() {
        return idparametrodocenteestudante;
    }

    public void setIdparametrodocenteestudante(Short idparametrodocenteestudante) {
        this.idparametrodocenteestudante = idparametrodocenteestudante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Pontuacaodocenteestudante getIdpontuacaodocenteestudante() {
        return idpontuacaodocenteestudante;
    }

    public void setIdpontuacaodocenteestudante(Pontuacaodocenteestudante idpontuacaodocenteestudante) {
        this.idpontuacaodocenteestudante = idpontuacaodocenteestudante;
    }

    public Questaodocenteestudante getIdquestaodocenteestudante() {
        return idquestaodocenteestudante;
    }

    public void setIdquestaodocenteestudante(Questaodocenteestudante idquestaodocenteestudante) {
        this.idquestaodocenteestudante = idquestaodocenteestudante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idparametrodocenteestudante != null ? idparametrodocenteestudante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametrodocenteestudante)) {
            return false;
        }
        Parametrodocenteestudante other = (Parametrodocenteestudante) object;
        if ((this.idparametrodocenteestudante == null && other.idparametrodocenteestudante != null) || (this.idparametrodocenteestudante != null && !this.idparametrodocenteestudante.equals(other.idparametrodocenteestudante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Parametrodocenteestudante[ idparametrodocenteestudante=" + idparametrodocenteestudante + " ]";
    }
    
}
