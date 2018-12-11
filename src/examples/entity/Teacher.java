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
@Table(name = "teacher")
@NamedQueries({
    @NamedQuery(name = "Teacher.findAll", query = "SELECT t FROM Teacher t")})
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "tno")
    private Integer tno;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public Teacher() {
    }

    public Teacher(Integer tno) {
        this.tno = tno;
    }

    public Teacher(Integer tno, String username, String password, String name) {
        this.tno = tno;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public Integer getTno() {
        return tno;
    }

    public void setTno(Integer tno) {
        this.tno = tno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tno != null ? tno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Teacher)) {
            return false;
        }
        Teacher other = (Teacher) object;
        if ((this.tno == null && other.tno != null) || (this.tno != null && !this.tno.equals(other.tno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "examples.entity.Teacher[ tno=" + tno + " ]";
    }
    
}
