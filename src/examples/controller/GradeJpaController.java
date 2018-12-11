/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examples.controller;

import examples.controller.exceptions.NonexistentEntityException;
import examples.entity.Grade;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Dell
 */
//持久化时需要序列化。
public class GradeJpaController implements Serializable {

    public GradeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
//自定义方法，操作数据库（也可以调用存储过程，嘻嘻）
     public List studentgrade(Integer sno){
          EntityManager em = getEntityManager();
          Query query = em.createNativeQuery("select grade,cname from grade,course where grade.cno = course.cno and sno = "+sno);
          List result = query.getResultList();
          return result;
    }
     public List bujige(){
         EntityManager em = getEntityManager();
         Query query = em.createNativeQuery("select cname,count(grade.cno) as 及格人数 from grade,course where grade.cno = course.cno and grade > 60 group by grade.cno");
         List result  = query.getResultList();
         return result;
     }
     public List jige(){
         EntityManager em  = getEntityManager();
         Query query = em.createNativeQuery("select cname,count(grade.cno) as 及格人数 from grade,course where grade.cno = course.cno and grade < 60 group by grade.cno");
         List result = query.getResultList();
         return result;
     }
     public List anbanjicha(String classname){
         EntityManager em  = getEntityManager();
         Query query = em.createNativeQuery("SELECT classname,grade.sno,grade,cname FROM class,grade,course WHERE  grade.sno=class.sno AND grade.cno=course.cno AND classname="+"\""+classname+"\""+"ORDER BY grade DESC");
         List result = query.getResultList();
         return result;
     }
       public List youxiu(){
         EntityManager em  = getEntityManager();
         Query query = em.createNativeQuery("select cname,grade as 优秀 from grade,course where grade.cno = course.cno and grade >= 80 group by grade.cno");
         List result = query.getResultList();
         return result;
     }
       
    public void create(Grade grade) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(grade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grade grade) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            grade = em.merge(grade);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grade.getId();
                if (findGrade(id) == null) {
                    throw new NonexistentEntityException("The grade with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grade grade;
            try {
                grade = em.getReference(Grade.class, id);
                grade.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grade with id " + id + " no longer exists.", enfe);
            }
            em.remove(grade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grade> findGradeEntities() {
        return findGradeEntities(true, -1, -1);
    }

    public List<Grade> findGradeEntities(int maxResults, int firstResult) {
        return findGradeEntities(false, maxResults, firstResult);
    }

    private List<Grade> findGradeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grade.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Grade findGrade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grade.class, id);
        } finally {
            em.close();
        }
    }

    public int getGradeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grade> rt = cq.from(Grade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
