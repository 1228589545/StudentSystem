/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examples.controller;

import examples.controller.exceptions.NonexistentEntityException;
import examples.controller.exceptions.PreexistingEntityException;
import examples.entity.Teacher;
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
public class TeacherJpaController implements Serializable {

    public TeacherJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Teacher teacher) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(teacher);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTeacher(teacher.getTno()) != null) {
                throw new PreexistingEntityException("Teacher " + teacher + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Teacher teacher) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            teacher = em.merge(teacher);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = teacher.getTno();
                if (findTeacher(id) == null) {
                    throw new NonexistentEntityException("The teacher with id " + id + " no longer exists.");
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
            Teacher teacher;
            try {
                teacher = em.getReference(Teacher.class, id);
                teacher.getTno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The teacher with id " + id + " no longer exists.", enfe);
            }
            em.remove(teacher);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Teacher> findTeacherEntities() {
        return findTeacherEntities(true, -1, -1);
    }

    public List<Teacher> findTeacherEntities(int maxResults, int firstResult) {
        return findTeacherEntities(false, maxResults, firstResult);
    }

    private List<Teacher> findTeacherEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Teacher.class));
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
     public List teachertext(String username,String password){
          EntityManager em = getEntityManager();
          Query query = em.createNativeQuery("select * from teacher where username="+username+" and password="+password);
          List result = query.getResultList();
          return result;
    }

    public Teacher findTeacher(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Teacher.class, id);
        } finally {
            em.close();
        }
    }

    public int getTeacherCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Teacher> rt = cq.from(Teacher.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
