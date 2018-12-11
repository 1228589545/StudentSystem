/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examples.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Dell
 */
@Entity
@Table(name = "class")
@NamedQueries({
    @NamedQuery(name = "Class.findAll", query = "SELECT c FROM Class c")})
public class Class implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cid")
    private Integer cid;
    @Basic(optional = false)
    @Column(name = "classname")
    private String classname;
    @Basic(optional = false)
    @Column(name = "sno")
    private int sno;

    public Class() {
    }

    public Class(Integer cid) {
        this.cid = cid;
    }

    public Class(Integer cid, String classname, int sno) {
        this.cid = cid;
        this.classname = classname;
        this.sno = sno;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Class)) {
            return false;
        }
        Class other = (Class) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "examples.entity.Class[ cid=" + cid + " ]";
    }
    
}
