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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "questaodocentesuperior", catalog = "integra", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Questaodocentesuperior.findAll", query = "SELECT q FROM Questaodocentesuperior q")})
public class Questaodocentesuperior implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idquestaodocentesuperior", nullable = false)
    private Short idquestaodocentesuperior;
    @Column(name = "descricao", length = 255)
    private String descricao;
    @ManyToMany(mappedBy = "questaodocentesuperiorList", fetch = FetchType.LAZY)
    private List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList;
    @JoinColumn(name = "idcaracteristicaquestaosuperior", referencedColumnName = "idcaracteristicasuperior")
    @ManyToOne(fetch = FetchType.LAZY)
    private Caracteristiquestaosuperior idcaracteristicaquestaosuperior;
    @JoinColumn(name = "idpontuacaosuperiorhierarquico", referencedColumnName = "idpontuacaosuperiorhier")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pontuacaosuperiorhierarquico idpontuacaosuperiorhierarquico;

    public Questaodocentesuperior() {
    }

    public Questaodocentesuperior(Short idquestaodocentesuperior) {
        this.idquestaodocentesuperior = idquestaodocentesuperior;
    }

    public Short getIdquestaodocentesuperior() {
        return idquestaodocentesuperior;
    }

    public void setIdquestaodocentesuperior(Short idquestaodocentesuperior) {
        this.idquestaodocentesuperior = idquestaodocentesuperior;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Avaliacaodocentesuperior> getAvaliacaodocentesuperiorList() {
        return avaliacaodocentesuperiorList;
    }

    public void setAvaliacaodocentesuperiorList(List<Avaliacaodocentesuperior> avaliacaodocentesuperiorList) {
        this.avaliacaodocentesuperiorList = avaliacaodocentesuperiorList;
    }

    public Caracteristiquestaosuperior getIdcaracteristicaquestaosuperior() {
        return idcaracteristicaquestaosuperior;
    }

    public void setIdcaracteristicaquestaosuperior(Caracteristiquestaosuperior idcaracteristicaquestaosuperior) {
        this.idcaracteristicaquestaosuperior = idcaracteristicaquestaosuperior;
    }

    public Pontuacaosuperiorhierarquico getIdpontuacaosuperiorhierarquico() {
        return idpontuacaosuperiorhierarquico;
    }

    public void setIdpontuacaosuperiorhierarquico(Pontuacaosuperiorhierarquico idpontuacaosuperiorhierarquico) {
        this.idpontuacaosuperiorhierarquico = idpontuacaosuperiorhierarquico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idquestaodocentesuperior != null ? idquestaodocentesuperior.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Questaodocentesuperior)) {
            return false;
        }
        Questaodocentesuperior other = (Questaodocentesuperior) object;
        if ((this.idquestaodocentesuperior == null && other.idquestaodocentesuperior != null) || (this.idquestaodocentesuperior != null && !this.idquestaodocentesuperior.equals(other.idquestaodocentesuperior))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Questaodocentesuperior[ idquestaodocentesuperior=" + idquestaodocentesuperior + " ]";
    }
    
}
