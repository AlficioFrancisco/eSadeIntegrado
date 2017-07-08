/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "precedencia", catalog = "integra", schema = "esira")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Precedencia.findAll", query = "SELECT p FROM Precedencia p")})
public class Precedencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrecedenciaPK precedenciaPK;
    @Column(name = "descr", length = 32)
    private String descr;
    @JoinColumn(name = "precedencia", referencedColumnName = "id_disc", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Disciplina disciplina;
    @JoinColumn(name = "id_disc", referencedColumnName = "id_disc", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Disciplina disciplina1;

    public Precedencia() {
    }

    public Precedencia(PrecedenciaPK precedenciaPK) {
        this.precedenciaPK = precedenciaPK;
    }

    public Precedencia(long idDisc, long precedencia) {
        this.precedenciaPK = new PrecedenciaPK(idDisc, precedencia);
    }

    public PrecedenciaPK getPrecedenciaPK() {
        return precedenciaPK;
    }

    public void setPrecedenciaPK(PrecedenciaPK precedenciaPK) {
        this.precedenciaPK = precedenciaPK;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Disciplina getDisciplina1() {
        return disciplina1;
    }

    public void setDisciplina1(Disciplina disciplina1) {
        this.disciplina1 = disciplina1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (precedenciaPK != null ? precedenciaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Precedencia)) {
            return false;
        }
        Precedencia other = (Precedencia) object;
        if ((this.precedenciaPK == null && other.precedenciaPK != null) || (this.precedenciaPK != null && !this.precedenciaPK.equals(other.precedenciaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Precedencia[ precedenciaPK=" + precedenciaPK + " ]";
    }
    
}
