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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Paulino Francisco
 */
@Entity
@Table(name = "regime", catalog = "integra", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Regime.findAll", query = "SELECT r FROM Regime r")})
public class Regime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idregime", nullable = false)
    private Short idregime;
    @Column(name = "descricao", length = 255)
    private String descricao;

    public Regime() {
    }

    public Regime(Short idregime) {
        this.idregime = idregime;
    }

    public Short getIdregime() {
        return idregime;
    }

    public void setIdregime(Short idregime) {
        this.idregime = idregime;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idregime != null ? idregime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Regime)) {
            return false;
        }
        Regime other = (Regime) object;
        if ((this.idregime == null && other.idregime != null) || (this.idregime != null && !this.idregime.equals(other.idregime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Regime[ idregime=" + idregime + " ]";
    }
    
}
