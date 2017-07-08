/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Paulino Francisco
 */
@Entity
@Table(name = "questaodocenteestudante", catalog = "integra", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questaodocenteestudante.findAll", query = "SELECT q FROM Questaodocenteestudante q")})
public class Questaodocenteestudante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idquestaodocenteestudante", nullable = false)
    private Short idquestaodocenteestudante;
    @Column(name = "descricao", length = 255)
    private String descricao;
    @Column(name = "codigo", length = 255)
    private String codigo;
    @ManyToMany(mappedBy = "questaodocenteestudanteList", fetch = FetchType.LAZY)
    private List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList;
    @OneToMany(mappedBy = "idquestaodocenteestudante", fetch = FetchType.LAZY)
    private List<Parametrodocenteestudante> parametrodocenteestudanteList;

    public Questaodocenteestudante() {
    }

    public Questaodocenteestudante(Short idquestaodocenteestudante) {
        this.idquestaodocenteestudante = idquestaodocenteestudante;
    }

    public Short getIdquestaodocenteestudante() {
        return idquestaodocenteestudante;
    }

    public void setIdquestaodocenteestudante(Short idquestaodocenteestudante) {
        this.idquestaodocenteestudante = idquestaodocenteestudante;
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

    @XmlTransient
    public List<Avaliacaodocenteestudante> getAvaliacaodocenteestudanteList() {
        return avaliacaodocenteestudanteList;
    }

    public void setAvaliacaodocenteestudanteList(List<Avaliacaodocenteestudante> avaliacaodocenteestudanteList) {
        this.avaliacaodocenteestudanteList = avaliacaodocenteestudanteList;
    }

    @XmlTransient
    public List<Parametrodocenteestudante> getParametrodocenteestudanteList() {
        return parametrodocenteestudanteList;
    }

    public void setParametrodocenteestudanteList(List<Parametrodocenteestudante> parametrodocenteestudanteList) {
        this.parametrodocenteestudanteList = parametrodocenteestudanteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idquestaodocenteestudante != null ? idquestaodocenteestudante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questaodocenteestudante)) {
            return false;
        }
        Questaodocenteestudante other = (Questaodocenteestudante) object;
        if ((this.idquestaodocenteestudante == null && other.idquestaodocenteestudante != null) || (this.idquestaodocenteestudante != null && !this.idquestaodocenteestudante.equals(other.idquestaodocenteestudante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Questaodocenteestudante[ idquestaodocenteestudante=" + idquestaodocenteestudante + " ]";
    }
    
}
