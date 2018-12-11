/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examples.controller;

import examples.controller.exceptions.NonexistentEntityException;
import examples.controller.exceptions.PreexistingEntityException;
import examples.entity.Class;
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
public class ClassJpaController implements Serializable {

    public ClassJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Class class1) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(class1);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClass(class1.getCid()) != null) {
                throw new PreexistingEntityException("Class " + class1 + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Class class1) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            class1 = em.merge(class1);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = class1.getCid();
                if (findClass(id) == null) {
                    throw new NonexistentEntityException("The class with id " + id + " no longer exists.");
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
            Class class1;
            try {
                class1 = em.getReference(Class.class, id);
                class1.getCid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The class1 with id " + id + " no longer exists.", enfe);
            }
            em.remove(class1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Class> findClassEntities() {
        return findClassEntities(true, -1, -1);
    }

    public List<Class> findClassEntities(int maxResults, int firstResult) {
        return findClassEntities(false, maxResults, firstResult);
    }

    private List<Class> findClassEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Class.class));
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

    public Class findClass(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Class.class, id);
        } finally {
            em.close();
        }
    }

    public int getClassCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Class> rt = cq.from(Class.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
