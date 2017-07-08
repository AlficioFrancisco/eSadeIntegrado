/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Paulino Francisco
 */
@Entity
@Table(name = "notapauta_old", catalog = "integra", schema = "esira")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotapautaOld.findAll", query = "SELECT n FROM NotapautaOld n")})
public class NotapautaOld implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotapautaOldPK notapautaOldPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nota", precision = 8, scale = 8)
    private Float nota;
    @Column(name = "classif", length = 45)
    private String classif;
    @Column(name = "publicado")
    private Boolean publicado;
    @Column(name = "estado")
    private Boolean estado;

    public NotapautaOld() {
    }

    public NotapautaOld(NotapautaOldPK notapautaOldPK) {
        this.notapautaOldPK = notapautaOldPK;
    }

    public NotapautaOld(long iddisc, int ano, short semestre, Date datap, long idestudante) {
        this.notapautaOldPK = new NotapautaOldPK(iddisc, ano, semestre, datap, idestudante);
    }

    public NotapautaOldPK getNotapautaOldPK() {
        return notapautaOldPK;
    }

    public void setNotapautaOldPK(NotapautaOldPK notapautaOldPK) {
        this.notapautaOldPK = notapautaOldPK;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public String getClassif() {
        return classif;
    }

    public void setClassif(String classif) {
        this.classif = classif;
    }

    public Boolean getPublicado() {
        return publicado;
    }

    public void setPublicado(Boolean publicado) {
        this.publicado = publicado;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notapautaOldPK != null ? notapautaOldPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotapautaOld)) {
            return false;
        }
        NotapautaOld other = (NotapautaOld) object;
        if ((this.notapautaOldPK == null && other.notapautaOldPK != null) || (this.notapautaOldPK != null && !this.notapautaOldPK.equals(other.notapautaOldPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.NotapautaOld[ notapautaOldPK=" + notapautaOldPK + " ]";
    }
    
}
