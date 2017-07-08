/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "docente", catalog = "integra", schema = "esira")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Docente.findAll", query = "SELECT d FROM Docente d")})
public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "iddocente", nullable = false)
    private Long iddocente;
    @Column(name = "area", length = 45)
    private String area;
    @Column(name = "grau", length = 45)
    private String grau;
    @Column(name = "nome", length = 255)
    private String nome;
    @Column(name = "idregime")
    private Short idregime;
    @Column(name = "iddepartamento")
    private Short iddepartamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docente", fetch = FetchType.LAZY)
    private List<Lecciona> leccionaList;
    @OneToMany(mappedBy = "iddocente", fetch = FetchType.LAZY)
    private List<Users> usersList;
    @OneToMany(mappedBy = "docente", fetch = FetchType.LAZY)
    private List<Pauta> pautaList;
    @JoinColumn(name = "idcargochefia", referencedColumnName = "idcargochefia")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cargochefia idcargochefia;

    public Docente() {
    }

    public Docente(Long iddocente) {
        this.iddocente = iddocente;
    }

    public Long getIddocente() {
        return iddocente;
    }

    public void setIddocente(Long iddocente) {
        this.iddocente = iddocente;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Short getIdregime() {
        return idregime;
    }

    public void setIdregime(Short idregime) {
        this.idregime = idregime;
    }

    public Short getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(Short iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    @XmlTransient
    public List<Lecciona> getLeccionaList() {
        return leccionaList;
    }

    public void setLeccionaList(List<Lecciona> leccionaList) {
        this.leccionaList = leccionaList;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @XmlTransient
    public List<Pauta> getPautaList() {
        return pautaList;
    }

    public void setPautaList(List<Pauta> pautaList) {
        this.pautaList = pautaList;
    }

    public Cargochefia getIdcargochefia() {
        return idcargochefia;
    }

    public void setIdcargochefia(Cargochefia idcargochefia) {
        this.idcargochefia = idcargochefia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddocente != null ? iddocente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Docente)) {
            return false;
        }
        Docente other = (Docente) object;
        if ((this.iddocente == null && other.iddocente != null) || (this.iddocente != null && !this.iddocente.equals(other.iddocente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Docente[ iddocente=" + iddocente + " ]";
    }
    
}
