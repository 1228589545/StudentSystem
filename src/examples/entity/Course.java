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
@Table(name = "course")
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c")})
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cno")
    private Integer cno;
    @Basic(optional = false)
    @Column(name = "cname")
    private String cname;
    @Column(name = "term")
    private String term;
    @Column(name = "tno")
    private Integer tno;

    public Course() {
    }

    public Course(Integer cno) {
        this.cno = cno;
    }

    public Course(Integer cno, String cname) {
        this.cno = cno;
        this.cname = cname;
    }

    public Integer getCno() {
        return cno;
    }

    public void setCno(Integer cno) {
        this.cno = cno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getTno() {
        return tno;
    }

    public void setTno(Integer tno) {
        this.tno = tno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cno != null ? cno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.cno == null && other.cno != null) || (this.cno != null && !this.cno.equals(other.cno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "examples.entity.Course[ cno=" + cno + " ]";
    }
    
}
