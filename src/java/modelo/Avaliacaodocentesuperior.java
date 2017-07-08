/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Paulino Francisco
 */
@Entity
@Table(name = "avaliacaodocentesuperior", catalog = "integra", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Avaliacaodocentesuperior.findAll", query = "SELECT a FROM Avaliacaodocentesuperior a")})
public class Avaliacaodocentesuperior implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idavaliacaodocentesuperior", nullable = false)
    private Short idavaliacaodocentesuperior;
    @Column(name = "anoletivo")
    private Short anoletivo;
    @Column(name = "iddocente")
    private BigInteger iddocente;
    @Column(name = "iddisciplina")
    private Short iddisciplina;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totalpontuacao", precision = 17, scale = 17)
    private Double totalpontuacao;
    @JoinTable(name = "avaliacaoquestaods", joinColumns = {
        @JoinColumn(name = "idavaliacaodocentesuperior", referencedColumnName = "idavaliacaodocentesuperior", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "idquestaodocentesuperior", referencedColumnName = "idquestaodocentesuperior", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Questaodocentesuperior> questaodocentesuperiorList;

    public Avaliacaodocentesuperior() {
    }

    public Avaliacaodocentesuperior(Short idavaliacaodocentesuperior) {
        this.idavaliacaodocentesuperior = idavaliacaodocentesuperior;
    }

    public Short getIdavaliacaodocentesuperior() {
        return idavaliacaodocentesuperior;
    }

    public void setIdavaliacaodocentesuperior(Short idavaliacaodocentesuperior) {
        this.idavaliacaodocentesuperior = idavaliacaodocentesuperior;
    }

    public Short getAnoletivo() {
        return anoletivo;
    }

    public void setAnoletivo(Short anoletivo) {
        this.anoletivo = anoletivo;
    }

    public BigInteger getIddocente() {
        return iddocente;
    }

    public void setIddocente(BigInteger iddocente) {
        this.iddocente = iddocente;
    }

    public Short getIddisciplina() {
        return iddisciplina;
    }

    public void setIddisciplina(Short iddisciplina) {
        this.iddisciplina = iddisciplina;
    }

    public Double getTotalpontuacao() {
        return totalpontuacao;
    }

    public void setTotalpontuacao(Double totalpontuacao) {
        this.totalpontuacao = totalpontuacao;
    }

    @XmlTransient
    public List<Questaodocentesuperior> getQuestaodocentesuperiorList() {
        return questaodocentesuperiorList;
    }

    public void setQuestaodocentesuperiorList(List<Questaodocentesuperior> questaodocentesuperiorList) {
        this.questaodocentesuperiorList = questaodocentesuperiorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idavaliacaodocentesuperior != null ? idavaliacaodocentesuperior.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avaliacaodocentesuperior)) {
            return false;
        }
        Avaliacaodocentesuperior other = (Avaliacaodocentesuperior) object;
        if ((this.idavaliacaodocentesuperior == null && other.idavaliacaodocentesuperior != null) || (this.idavaliacaodocentesuperior != null && !this.idavaliacaodocentesuperior.equals(other.idavaliacaodocentesuperior))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Avaliacaodocentesuperior[ idavaliacaodocentesuperior=" + idavaliacaodocentesuperior + " ]";
    }
    
}
