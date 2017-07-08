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
@Table(name = "parametroautoavaliacao", catalog = "integra", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametroautoavaliacao.findAll", query = "SELECT p FROM Parametroautoavaliacao p")})
public class Parametroautoavaliacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idparametroautoavaliacao", nullable = false)
    private Short idparametroautoavaliacao;
    @Column(name = "descricao", length = 255)
    private String descricao;
    @Column(name = "codigo", length = 16)
    private String codigo;
    @Column(name = "quantidade")
    private Short quantidade;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pontos", precision = 8, scale = 8)
    private Float pontos;
    @Column(name = "bonos", precision = 8, scale = 8)
    private Float bonos;
    @JoinColumn(name = "idquestaoautoavaliacao", referencedColumnName = "idquestaoautoavaliacao")
    @ManyToOne(fetch = FetchType.LAZY)
    private Questaoautoavaliacao idquestaoautoavaliacao;

    public Parametroautoavaliacao() {
    }

    public Parametroautoavaliacao(Short idparametroautoavaliacao) {
        this.idparametroautoavaliacao = idparametroautoavaliacao;
    }

    public Short getIdparametroautoavaliacao() {
        return idparametroautoavaliacao;
    }

    public void setIdparametroautoavaliacao(Short idparametroautoavaliacao) {
        this.idparametroautoavaliacao = idparametroautoavaliacao;
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

    public Short getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Short quantidade) {
        this.quantidade = quantidade;
    }

    public Float getPontos() {
        return pontos;
    }

    public void setPontos(Float pontos) {
        this.pontos = pontos;
    }

    public Float getBonos() {
        return bonos;
    }

    public void setBonos(Float bonos) {
        this.bonos = bonos;
    }

    public Questaoautoavaliacao getIdquestaoautoavaliacao() {
        return idquestaoautoavaliacao;
    }

    public void setIdquestaoautoavaliacao(Questaoautoavaliacao idquestaoautoavaliacao) {
        this.idquestaoautoavaliacao = idquestaoautoavaliacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idparametroautoavaliacao != null ? idparametroautoavaliacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametroautoavaliacao)) {
            return false;
        }
        Parametroautoavaliacao other = (Parametroautoavaliacao) object;
        if ((this.idparametroautoavaliacao == null && other.idparametroautoavaliacao != null) || (this.idparametroautoavaliacao != null && !this.idparametroautoavaliacao.equals(other.idparametroautoavaliacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Parametroautoavaliacao[ idparametroautoavaliacao=" + idparametroautoavaliacao + " ]";
    }
    
}
