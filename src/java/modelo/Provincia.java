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
@Table(name = "provincia", catalog = "integra", schema = "esira")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provincia.findAll", query = "SELECT p FROM Provincia p")})
public class Provincia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_provincia", nullable = false)
    private Long idProvincia;
    @Column(name = "descricao", length = 45)
    private String descricao;
    @Column(name = "ordregiao")
    private Integer ordregiao;
    @OneToMany(mappedBy = "provinciapr", fetch = FetchType.LAZY)
    private List<Profissao> profissaoList;
    @OneToMany(mappedBy = "provinciaAdmissao", fetch = FetchType.LAZY)
    private List<Ingressoexameadmissao> ingressoexameadmissaoList;
    @OneToMany(mappedBy = "provincia", fetch = FetchType.LAZY)
    private List<Enderecof> enderecofList;
    @OneToMany(mappedBy = "provincia", fetch = FetchType.LAZY)
    private List<Endereco> enderecoList;

    public Provincia() {
    }

    public Provincia(Long idProvincia) {
        this.idProvincia = idProvincia;
    }

    public Long getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Long idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getOrdregiao() {
        return ordregiao;
    }

    public void setOrdregiao(Integer ordregiao) {
        this.ordregiao = ordregiao;
    }

    @XmlTransient
    public List<Profissao> getProfissaoList() {
        return profissaoList;
    }

    public void setProfissaoList(List<Profissao> profissaoList) {
        this.profissaoList = profissaoList;
    }

    @XmlTransient
    public List<Ingressoexameadmissao> getIngressoexameadmissaoList() {
        return ingressoexameadmissaoList;
    }

    public void setIngressoexameadmissaoList(List<Ingressoexameadmissao> ingressoexameadmissaoList) {
        this.ingressoexameadmissaoList = ingressoexameadmissaoList;
    }

    @XmlTransient
    public List<Enderecof> getEnderecofList() {
        return enderecofList;
    }

    public void setEnderecofList(List<Enderecof> enderecofList) {
        this.enderecofList = enderecofList;
    }

    @XmlTransient
    public List<Endereco> getEnderecoList() {
        return enderecoList;
    }

    public void setEnderecoList(List<Endereco> enderecoList) {
        this.enderecoList = enderecoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProvincia != null ? idProvincia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provincia)) {
            return false;
        }
        Provincia other = (Provincia) object;
        if ((this.idProvincia == null && other.idProvincia != null) || (this.idProvincia != null && !this.idProvincia.equals(other.idProvincia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Provincia[ idProvincia=" + idProvincia + " ]";
    }
    
}
